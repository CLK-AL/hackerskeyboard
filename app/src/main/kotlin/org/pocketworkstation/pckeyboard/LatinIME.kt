/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.pocketworkstation.pckeyboard

import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.inputmethodservice.InputMethodService
import android.media.AudioManager
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.SystemClock
import android.os.Vibrator
import android.preference.PreferenceActivity
import android.preference.PreferenceManager
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.Log
import android.util.PrintWriterPrinter
import android.view.HapticFeedbackConstants
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.CompletionInfo
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.view.inputmethod.InputMethodManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.view.ViewParent
import android.view.inputmethod.ExtractedText
import android.view.inputmethod.ExtractedTextRequest
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import org.xmlpull.v1.XmlPullParserException
import java.io.FileDescriptor
import java.io.IOException
import java.io.PrintWriter
import java.util.Collections
import java.util.Locale
import java.util.regex.Pattern

/**
 * Input method implementation for Hacker's Keyboard.
 */
class LatinIME : InputMethodService(), ComposeSequencing,
    LatinKeyboardBaseView.OnKeyboardActionListener,
    SharedPreferences.OnSharedPreferenceChangeListener {

    companion object {
        private const val TAG = "PCKeyboardIME"

        @JvmField
        val sKeyboardSettings = GlobalKeyboardSettings()

        // Preference keys
        const val PREF_VIBRATE_ON = "vibrate_on"
        const val PREF_SOUND_ON = "sound_on"
        const val PREF_POPUP_ON = "popup_on"
        const val PREF_AUTO_CAP = "auto_cap"
        const val PREF_QUICK_FIXES = "quick_fixes"
        const val PREF_SHOW_SUGGESTIONS = "show_suggestions"
        const val PREF_AUTO_COMPLETE = "auto_complete"
        const val PREF_BIGRAM_SUGGESTIONS = "bigram_suggestion"
        const val PREF_VOICE_MODE = "voice_mode"

        const val PREF_SELECTED_LANGUAGES = "selected_languages"
        const val PREF_INPUT_LANGUAGE = "input_language"
        private const val PREF_REC_CORRECTION = "recorrection_enabled"
        const val PREF_CONNECTBOT_TAB_HACK = "connectbot_tab_hack"
        const val PREF_FULLSCREEN_OVERRIDE = "fullscreen_override"
        const val PREF_FORCE_KEYBOARD_ON = "force_keyboard_on"
        const val PREF_KEYBOARD_NOTIFICATION = "keyboard_notification"
        const val PREF_SUGGESTIONS_IN_LANDSCAPE = "suggestions_in_landscape"
        const val PREF_HEIGHT_PORTRAIT = "settings_height_portrait"
        const val PREF_HEIGHT_LANDSCAPE = "settings_height_landscape"
        const val PREF_HINT_MODE = "pref_hint_mode"
        const val PREF_LONGPRESS_TIMEOUT = "pref_long_press_duration"
        const val PREF_RENDER_MODE = "pref_render_mode"
        const val PREF_SWIPE_UP = "pref_swipe_up"
        const val PREF_SWIPE_DOWN = "pref_swipe_down"
        const val PREF_SWIPE_LEFT = "pref_swipe_left"
        const val PREF_SWIPE_RIGHT = "pref_swipe_right"
        const val PREF_VOL_UP = "pref_vol_up"
        const val PREF_VOL_DOWN = "pref_vol_down"
        const val PREF_VIBRATE_LEN = "pref_vibrate_length"

        // Handler message IDs
        private const val MSG_UPDATE_SUGGESTIONS = 0
        private const val MSG_UPDATE_SHIFT_STATE = 1
        private const val MSG_VOICE_RESULTS = 2
        private const val MSG_UPDATE_OLD_SUGGESTIONS = 4

        // How many continuous deletes before triggering the fast delete mode
        private const val DELETE_ACCELERATE_AT = 20

        // Key events coming any faster than this are long-presses
        private const val QUICK_PRESS = 200

        const val KEYCODE_ENTER = '\n'.code
        const val KEYCODE_SPACE = ' '.code
        const val KEYCODE_PERIOD = '.'.code

        // ASCII codes for characters
        private const val ASCII_ENTER = '\n'.code
        private const val ASCII_SPACE = ' '.code
        private const val ASCII_PERIOD = '.'.code
        private const val ASCII_COMMA = ','.code

        // Options dialog position constants
        private const val POS_SETTINGS = 0
        private const val POS_METHOD = 1

        // Audio volume settings
        private const val FX_VOLUME = -1.0f
        private const val FX_VOLUME_RANGE_DB = 72.0f

        // Static instance reference
        @JvmStatic
        private var sInstance: LatinIME? = null

        @JvmStatic
        fun getInstanceOrNull(): LatinIME? = sInstance

        private val NUMBER_RE: Pattern = Pattern.compile("(\\d+).*")

        @JvmStatic
        fun <E> newArrayList(vararg elements: E): ArrayList<E> {
            val capacity = (elements.size * 110) / 100 + 5
            val list = ArrayList<E>(capacity)
            Collections.addAll(list, *elements)
            return list
        }

        @JvmStatic
        fun getIntFromString(value: String, defVal: Int): Int {
            val num = NUMBER_RE.matcher(value)
            return if (!num.matches()) defVal else num.group(1)!!.toInt()
        }

        @JvmStatic
        fun getPrefInt(prefs: SharedPreferences, prefName: String, defVal: Int): Int {
            val prefVal = prefs.getString(prefName, defVal.toString())
            return getIntFromString(prefVal ?: defVal.toString(), defVal)
        }

        @JvmStatic
        fun getPrefInt(prefs: SharedPreferences, prefName: String, defStr: String): Int {
            val defVal = getIntFromString(defStr, 0)
            return getPrefInt(prefs, prefName, defVal)
        }

        @JvmStatic
        fun getHeight(prefs: SharedPreferences, prefName: String, defVal: String): Int {
            var value = getPrefInt(prefs, prefName, defVal)
            if (value < 15) value = 15
            if (value > 75) value = 75
            return value
        }
    }

    // Instance fields
    private var mKeyboardSwitcher: KeyboardSwitcher? = null
    private var mSuggest: Suggest? = null
    private var mUserDictionary: UserDictionary? = null
    private var mContactsDictionary: ContactsDictionary? = null
    private var mAutoDictionary: AutoDictionary? = null
    private var mUserBigramDictionary: UserBigramDictionary? = null
    private var mResources: Resources? = null
    private var mLanguageSwitcher: LanguageSwitcher? = null
    private var mVoiceRecognitionTrigger: VoiceRecognitionTrigger? = null
    private var mPluginManager: PluginManager? = null
    private var mNotificationReceiver: NotificationReceiver? = null
    private var mToken: IBinder? = null
    private var mConfigurationChanging = false
    private var mRefreshKeyboardRequired = false
    private var mLastKeyTime: Long = 0
    private var mEnteredText: CharSequence? = null

    // Compose mode
    private var mComposeMode = false
    private val mComposeBuffer = ComposeSequence(this)
    private val mDeadAccentBuffer = DeadAccentSequence(this)

    // Escape sequences for special keys
    private var ESC_SEQUENCES: MutableMap<Int, String>? = null
    private var CTRL_SEQUENCES: MutableMap<Int, Int>? = null

    private companion object {
        private const val NOTIFICATION_CHANNEL_ID = "PCKeyboard"
        private const val NOTIFICATION_ONGOING_ID = 1001
    }

    private val mComposing = StringBuilder()
    private var mWord = WordComposer()
    private var mCommittedLength = 0
    private var mPredicting = false
    private var mBestWord: CharSequence? = null
    private var mPredictionOnForMode = false
    private var mPredictionOnPref = false
    private var mCompletionOn = false
    private var mHasDictionary = false
    private var mAutoSpace = false
    private var mJustAddedAutoSpace = false
    private var mAutoCorrectEnabled = false
    private var mReCorrectionEnabled = false
    private var mBigramSuggestionEnabled = false
    private var mAutoCorrectOn = false
    private var mCorrectionMode = 0
    private var mInputTypeNoAutoCorrect = false
    private var mCapsLock = false
    private var mPasswordText = false
    private var mVibrateOn = false
    private var mVibrateLen = 0
    private var mSoundOn = false
    private var mPopupOn = false
    private var mAutoCapPref = false
    private var mAutoCapActive = false
    private var mDeadKeysActive = false
    private var mQuickFixes = false
    private var mShowSuggestions = false
    private var mSuggestionsInLandscape = false
    private var mIsShowingHint = false
    private var mConnectbotTabHack = false
    private var mFullscreenOverride = false
    private var mForceKeyboardOn = false
    private var mKeyboardNotification = false
    private var mSuggestionForceOn = false
    private var mSuggestionForceOff = false
    private var mEnableVoice = true
    private var mEnableVoiceButton = false
    private var mVoiceOnPrimary = false
    private var mInputLocale: String = ""
    private var mSystemLocale: Locale? = null
    private var mOrientation = 0
    private var mHeightPortrait = 0
    private var mHeightLandscape = 0
    private var mNumKeyboardModes = 3
    private var mKeyboardModeOverridePortrait = 0
    private var mKeyboardModeOverrideLandscape = 0

    private val mWordSeparators: String by lazy {
        resources.getString(R.string.word_separators)
    }
    private val mSentenceSeparators: String by lazy {
        resources.getString(R.string.sentence_separators)
    }

    // Modifier key states
    private var mModCtrl = false
    private var mModAlt = false
    private var mModMeta = false
    private var mModFn = false
    private val mShiftKeyState = ModifierKeyState()
    private val mSymbolKeyState = ModifierKeyState()
    private val mCtrlKeyState = ModifierKeyState()
    private val mAltKeyState = ModifierKeyState()
    private val mMetaKeyState = ModifierKeyState()
    private val mFnKeyState = ModifierKeyState()
    private var mSavedShiftState = 0

    // Swipe and volume key actions
    private var mSwipeUpAction: String = ""
    private var mSwipeDownAction: String = ""
    private var mSwipeLeftAction: String = ""
    private var mSwipeRightAction: String = ""
    private var mVolUpAction: String = ""
    private var mVolDownAction: String = ""

    // Audio
    private var mAudioManager: AudioManager? = null
    private var mSilentMode = false

    // Completions and suggestions
    private var mCompletions: Array<CompletionInfo>? = null
    private var mCandidateView: CandidateView? = null
    private var mCandidateViewContainer: View? = null
    private var mSuggestPuncList: MutableList<CharSequence>? = null
    private var mWordToSuggestions = HashMap<String, MutableList<CharSequence>>()
    private var mWordHistory = ArrayList<WordAlternatives>()

    // Emoji picker
    private var mEmojiPickerView: EmojiPickerView? = null
    private var mEmojiPickerShowing = false

    // Selection tracking
    private var mLastSelectionStart = 0
    private var mLastSelectionEnd = 0
    private var mJustRevertedSeparator: CharSequence? = null
    private var mJustAccepted = false

    // Deletion tracking
    private var mDeleteCount = 0

    // Options dialog
    private var mOptionsDialog: AlertDialog? = null

    // CPS measurement
    private var mLastCpsTime: Long = 0
    private val mCpsIntervals = LongArray(CPS_BUFFER_SIZE)
    private var mCpsIndex = 0

    // Handler for delayed operations
    private val mHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MSG_UPDATE_SUGGESTIONS -> updateSuggestions()
                MSG_UPDATE_SHIFT_STATE -> updateShiftKeyState(currentInputEditorInfo)
                MSG_UPDATE_OLD_SUGGESTIONS -> setOldSuggestions()
            }
        }
    }

    // Inner class for word alternatives
    open inner class WordAlternatives {
        val chosenWord: CharSequence?
        val originalWord: CharSequence?

        constructor() {
            chosenWord = null
            originalWord = null
        }

        constructor(chosenWord: CharSequence?, originalWord: CharSequence?) {
            this.chosenWord = chosenWord
            this.originalWord = originalWord
        }

        override fun hashCode(): Int {
            return chosenWord?.hashCode() ?: 0
        }

        override fun equals(other: Any?): Boolean {
            if (other !is WordAlternatives) return false
            return chosenWord == other.chosenWord
        }

        open fun getAlternatives(): MutableList<CharSequence>? {
            return mWordToSuggestions[chosenWord.toString()]
        }
    }

    inner class TypedWordAlternatives(
        chosenWord: CharSequence?,
        private val word: WordComposer?
    ) : WordAlternatives(chosenWord, word?.typedWord) {

        override fun getAlternatives(): MutableList<CharSequence>? {
            return getTypedSuggestions(word)
        }
    }

    // BroadcastReceiver for ringer mode changes
    private val mReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            updateRingerMode()
        }
    }

    override fun onCreate() {
        super.onCreate()
        sInstance = this
        mResources = resources
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        mLanguageSwitcher = LanguageSwitcher(this)
        mLanguageSwitcher!!.loadLocales(prefs)
        mKeyboardSwitcher = KeyboardSwitcher.getInstance()
        mKeyboardSwitcher!!.setLanguageSwitcher(mLanguageSwitcher)
        mSystemLocale = resources.configuration.locale
        val inputLanguage = mLanguageSwitcher!!.inputLanguage
        mInputLocale = inputLanguage ?: mSystemLocale.toString()
        sKeyboardSettings.useExtension = prefs.getBoolean("pref_use_extension", false)

        val res = resources
        mHeightPortrait = getHeight(prefs, PREF_HEIGHT_PORTRAIT, res.getString(R.string.default_height_portrait))
        mHeightLandscape = getHeight(prefs, PREF_HEIGHT_LANDSCAPE, res.getString(R.string.default_height_landscape))

        sKeyboardSettings.hintMode = prefs.getString(
            PREF_HINT_MODE,
            res.getString(R.string.default_hint_mode)
        )?.toIntOrNull() ?: 0
        sKeyboardSettings.longpressTimeout = getPrefInt(
            prefs, PREF_LONGPRESS_TIMEOUT,
            res.getString(R.string.default_long_press_duration)
        )
        sKeyboardSettings.renderMode = getPrefInt(
            prefs, PREF_RENDER_MODE,
            res.getString(R.string.default_render_mode)
        )

        mSwipeUpAction = prefs.getString(PREF_SWIPE_UP, res.getString(R.string.default_swipe_up)) ?: ""
        mSwipeDownAction = prefs.getString(PREF_SWIPE_DOWN, res.getString(R.string.default_swipe_down)) ?: ""
        mSwipeLeftAction = prefs.getString(PREF_SWIPE_LEFT, res.getString(R.string.default_swipe_left)) ?: ""
        mSwipeRightAction = prefs.getString(PREF_SWIPE_RIGHT, res.getString(R.string.default_swipe_right)) ?: ""
        mVolUpAction = prefs.getString(PREF_VOL_UP, res.getString(R.string.default_vol_up)) ?: ""
        mVolDownAction = prefs.getString(PREF_VOL_DOWN, res.getString(R.string.default_vol_down)) ?: ""

        sKeyboardSettings.initPrefs(prefs, res)
        mKeyboardSwitcher!!.setKeyboardMode(
            KeyboardSwitcher.MODE_TEXT,
            0,
            EditorInfo(), false, false, false, false
        )
        prefs.registerOnSharedPreferenceChangeListener(this)

        mConnectbotTabHack = prefs.getBoolean(PREF_CONNECTBOT_TAB_HACK, res.getBoolean(R.bool.default_connectbot_tab_hack))
        mFullscreenOverride = prefs.getBoolean(PREF_FULLSCREEN_OVERRIDE, res.getBoolean(R.bool.default_fullscreen_override))
        mForceKeyboardOn = prefs.getBoolean(PREF_FORCE_KEYBOARD_ON, res.getBoolean(R.bool.default_force_keyboard_on))
        mKeyboardNotification = prefs.getBoolean(PREF_KEYBOARD_NOTIFICATION, res.getBoolean(R.bool.default_keyboard_notification))
        mSuggestionsInLandscape = prefs.getBoolean(PREF_SUGGESTIONS_IN_LANDSCAPE, res.getBoolean(R.bool.default_suggestions_in_landscape))
    }

    override fun onDestroy() {
        if (mUserDictionary != null) {
            mUserDictionary!!.close()
            mUserDictionary = null
        }
        if (mContactsDictionary != null) {
            mContactsDictionary!!.close()
            mContactsDictionary = null
        }
        unregisterReceiver(mReceiver)
        sInstance = null
        super.onDestroy()
    }

    override fun onConfigurationChanged(conf: Configuration) {
        if (mKeyboardSwitcher == null) {
            mKeyboardSwitcher = KeyboardSwitcher.getInstance()
        }
        mLanguageSwitcher!!.onConfigurationChanged(conf, this)
        if (mKeyboardSwitcher!!.getInputView() != null) {
            mKeyboardSwitcher!!.onConfigurationChanged()
        }
        if (!reloadKeyboardsOnConfigChange(conf)) {
            super.onConfigurationChanged(conf)
        }
    }

    private fun reloadKeyboardsOnConfigChange(conf: Configuration): Boolean {
        var reload = false
        if (conf.orientation != mOrientation) {
            mOrientation = conf.orientation
            reload = true
        }
        if (mSystemLocale != conf.locale) {
            mSystemLocale = conf.locale
            reload = true
        }
        if (reload) {
            mKeyboardSwitcher!!.makeKeyboards(true)
            return true
        }
        return false
    }

    override fun onInitializeInterface() {
        if (mKeyboardSwitcher == null) {
            mKeyboardSwitcher = KeyboardSwitcher.getInstance()
        }
        mKeyboardSwitcher!!.setInputMethodService(this)
        mKeyboardSwitcher!!.setLanguageSwitcher(mLanguageSwitcher)
    }

    // ComposeSequencing implementation
    override fun onDeadKey(deadKey: Char): Boolean {
        return mDeadKeysActive && DeadAccentSequence.isDeadKey(deadKey)
    }

    override fun onComposeSequence(seq: String?) {
        if (seq == null) return
        val ic = currentInputConnection ?: return
        ic.commitText(seq, 1)
    }

    // Additional methods will be added in subsequent updates...

    private fun updateSuggestions() {
        // Implementation
    }

    private fun setOldSuggestions() {
        // Implementation
    }

    private fun updateRingerMode() {
        if (mAudioManager == null) {
            mAudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        }
        mAudioManager?.let {
            mSilentMode = it.ringerMode != AudioManager.RINGER_MODE_NORMAL
        }
    }

    override fun updateShiftKeyState(attr: EditorInfo?) {
        val ic = currentInputConnection
        if (ic != null && attr != null && mKeyboardSwitcher!!.isAlphabetMode) {
            val oldState = shiftState
            val isShifted = mShiftKeyState.isChording
            val isCapsLock = oldState == Keyboard.SHIFT_CAPS_LOCKED || oldState == Keyboard.SHIFT_LOCKED
            val isCaps = isCapsLock || getCursorCapsMode(ic, attr) != 0
            var newState = Keyboard.SHIFT_OFF
            if (isShifted) {
                newState = if (mSavedShiftState == Keyboard.SHIFT_LOCKED) Keyboard.SHIFT_CAPS else Keyboard.SHIFT_ON
            } else if (isCaps) {
                newState = if (isCapsLock) capsOrShiftLockState else Keyboard.SHIFT_CAPS
            }
            mKeyboardSwitcher!!.setShiftState(newState)
        }
        ic?.clearMetaKeyStates(
            KeyEvent.META_FUNCTION_ON or
                    KeyEvent.META_ALT_MASK or
                    KeyEvent.META_CTRL_MASK or
                    KeyEvent.META_META_MASK or
                    KeyEvent.META_SYM_ON
        )
    }

    private val shiftState: Int
        get() {
            if (mKeyboardSwitcher != null) {
                val view = mKeyboardSwitcher!!.getInputView()
                if (view != null) {
                    return view.shiftState
                }
            }
            return Keyboard.SHIFT_OFF
        }

    private val capsOrShiftLockState: Int
        get() = Keyboard.SHIFT_CAPS_LOCKED

    private fun getCursorCapsMode(ic: InputConnection, attr: EditorInfo): Int {
        val ei = currentInputEditorInfo
        return if (mAutoCapActive && ei != null && ei.inputType != EditorInfo.TYPE_NULL) {
            ic.getCursorCapsMode(attr.inputType)
        } else {
            0
        }
    }

    private fun getTypedSuggestions(word: WordComposer?): MutableList<CharSequence>? {
        return mSuggest?.getSuggestions(mKeyboardSwitcher!!.getInputView(), word, false, null)
    }

    // Swipe actions
    override fun swipeRight(): Boolean = doSwipeAction(mSwipeRightAction)
    override fun swipeLeft(): Boolean = doSwipeAction(mSwipeLeftAction)
    override fun swipeDown(): Boolean = doSwipeAction(mSwipeDownAction)
    override fun swipeUp(): Boolean = doSwipeAction(mSwipeUpAction)

    private fun doSwipeAction(action: String?): Boolean {
        if (action.isNullOrEmpty() || action == "none") {
            return false
        }
        when (action) {
            "close" -> handleClose()
            "settings" -> launchSettings()
            "suggestions" -> {
                if (mSuggestionForceOn) {
                    mSuggestionForceOn = false
                    mSuggestionForceOff = true
                } else if (mSuggestionForceOff) {
                    mSuggestionForceOn = true
                    mSuggestionForceOff = false
                } else if (isPredictionWanted()) {
                    mSuggestionForceOff = true
                } else {
                    mSuggestionForceOn = true
                }
                setCandidatesViewShown(isPredictionOn())
            }
            "lang_prev" -> toggleLanguage(false, false)
            "lang_next" -> toggleLanguage(false, true)
            "full_mode" -> {
                if (isPortrait) {
                    mKeyboardModeOverridePortrait = (mKeyboardModeOverridePortrait + 1) % mNumKeyboardModes
                } else {
                    mKeyboardModeOverrideLandscape = (mKeyboardModeOverrideLandscape + 1) % mNumKeyboardModes
                }
                toggleLanguage(true, true)
            }
            "extension" -> {
                sKeyboardSettings.useExtension = !sKeyboardSettings.useExtension
                reloadKeyboards()
            }
            "height_up" -> {
                if (isPortrait) {
                    mHeightPortrait += 5
                    if (mHeightPortrait > 70) mHeightPortrait = 70
                } else {
                    mHeightLandscape += 5
                    if (mHeightLandscape > 70) mHeightLandscape = 70
                }
                toggleLanguage(true, true)
            }
            "height_down" -> {
                if (isPortrait) {
                    mHeightPortrait -= 5
                    if (mHeightPortrait < 15) mHeightPortrait = 15
                } else {
                    mHeightLandscape -= 5
                    if (mHeightLandscape < 15) mHeightLandscape = 15
                }
                toggleLanguage(true, true)
            }
            else -> Log.i(TAG, "Unsupported swipe action config: $action")
        }
        return true
    }

    private fun handleClose() {
        requestHideSelf(0)
    }

    private fun launchSettings() {
        launchSettings(LatinIMESettings::class.java)
    }

    private fun launchSettings(settingsClass: Class<out PreferenceActivity>) {
        handleClose()
        val intent = Intent()
        intent.setClass(this@LatinIME, settingsClass)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private val isPortrait: Boolean
        get() = mOrientation == Configuration.ORIENTATION_PORTRAIT

    private fun isPredictionWanted(): Boolean {
        return mPredictionOnPref && mShowSuggestions
    }

    private fun isPredictionOn(): Boolean {
        return (mPredictionOnForMode || mSuggestionForceOn) && !mSuggestionForceOff
    }

    private fun suggestionsDisabled(): Boolean {
        return !mShowSuggestions || (!mSuggestionsInLandscape && !isPortrait)
    }

    private fun reloadKeyboards() {
        mKeyboardSwitcher!!.setLanguageSwitcher(mLanguageSwitcher)
        if (mKeyboardSwitcher!!.getInputView() != null &&
            mKeyboardSwitcher!!.keyboardMode != KeyboardSwitcher.MODE_NONE
        ) {
            mKeyboardSwitcher!!.setVoiceMode(mEnableVoice && mEnableVoiceButton, mVoiceOnPrimary)
        }
        updateKeyboardOptions()
        mKeyboardSwitcher!!.makeKeyboards(true)
    }

    private fun updateKeyboardOptions() {
        mKeyboardSwitcher!!.setKeyboardOptions(
            mHeightPortrait, mHeightLandscape,
            mKeyboardModeOverridePortrait, mKeyboardModeOverrideLandscape
        )
    }

    private fun toggleLanguage(reset: Boolean, next: Boolean) {
        if (next) {
            mLanguageSwitcher!!.next()
        } else {
            mLanguageSwitcher!!.prev()
        }
        val newInputLocale = mLanguageSwitcher!!.inputLanguage
        mInputLocale = newInputLocale ?: mSystemLocale.toString()
        mAutoCapActive = mAutoCapPref && mLanguageSwitcher!!.allowAutoCap()
        mDeadKeysActive = mLanguageSwitcher!!.allowDeadKeys()
        reloadKeyboards()
    }

    // Input view creation
    override fun onCreateInputView(): View? {
        setCandidatesViewShown(false)
        mKeyboardSwitcher!!.recreateInputView()
        mKeyboardSwitcher!!.makeKeyboards(true)
        mKeyboardSwitcher!!.setKeyboardMode(
            KeyboardSwitcher.MODE_TEXT, 0,
            shouldShowVoiceButton(currentInputEditorInfo)
        )
        return mKeyboardSwitcher!!.getInputView()
    }

    override fun onCreateInputMethodInterface(): AbstractInputMethodImpl {
        return MyInputMethodImpl()
    }

    inner class MyInputMethodImpl : InputMethodImpl() {
        override fun attachToken(token: IBinder) {
            super.attachToken(token)
            Log.i(TAG, "attachToken $token")
            if (mToken == null) {
                mToken = token
            }
        }
    }

    override fun onCreateCandidatesView(): View? {
        if (mCandidateViewContainer == null) {
            mCandidateViewContainer = layoutInflater.inflate(R.layout.candidates, null) as LinearLayout
            mCandidateView = mCandidateViewContainer!!.findViewById(R.id.candidates)
            mCandidateView!!.setPadding(0, 0, 0, 0)
            mCandidateView!!.setService(this)
            setCandidatesView(mCandidateViewContainer)
        }
        setCandidatesViewShownInternal(true, false)
        super.setCandidatesViewShown(true)
        setExtractViewShown(onEvaluateFullscreenMode())
        return mCandidateViewContainer
    }

    private fun removeCandidateViewContainer() {
        if (mCandidateViewContainer != null) {
            mCandidateViewContainer!!.removeAllViews()
            val parent: ViewParent? = mCandidateViewContainer!!.parent
            if (parent != null && parent is ViewGroup) {
                parent.removeView(mCandidateViewContainer)
            }
            mCandidateViewContainer = null
            mCandidateView = null
        }
        resetPrediction()
    }

    private fun resetPrediction() {
        mComposing.setLength(0)
        mPredicting = false
        mDeleteCount = 0
        mJustAddedAutoSpace = false
    }

    // ==================== Emoji Picker ====================

    /**
     * Toggle the emoji picker visibility.
     */
    fun toggleEmojiPicker() {
        if (mEmojiPickerShowing) {
            hideEmojiPicker()
        } else {
            showEmojiPicker()
        }
    }

    /**
     * Show the emoji picker, hiding the keyboard.
     */
    fun showEmojiPicker() {
        if (mEmojiPickerView == null) {
            mEmojiPickerView = EmojiPickerView(this).apply {
                setBackgroundColor(android.graphics.Color.parseColor("#FF2B2B2B"))
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    (resources.displayMetrics.heightPixels * 0.4).toInt()
                )
                setOnEmojiSelectedListener { emoji ->
                    commitEmojiText(emoji)
                }
            }
        }

        val inputView = mKeyboardSwitcher?.getInputView()
        if (inputView != null) {
            val parent = inputView.parent
            if (parent is ViewGroup) {
                // Hide keyboard, show emoji picker
                inputView.visibility = View.GONE

                if (mEmojiPickerView!!.parent == null) {
                    parent.addView(mEmojiPickerView)
                }
                mEmojiPickerView!!.visibility = View.VISIBLE
                mEmojiPickerShowing = true
                Log.i(TAG, "Emoji picker shown")
            }
        }
    }

    /**
     * Hide the emoji picker, showing the keyboard.
     */
    fun hideEmojiPicker() {
        val inputView = mKeyboardSwitcher?.getInputView()
        if (inputView != null) {
            inputView.visibility = View.VISIBLE
        }

        mEmojiPickerView?.let { picker ->
            picker.visibility = View.GONE
            (picker.parent as? ViewGroup)?.removeView(picker)
        }
        mEmojiPickerShowing = false
        Log.i(TAG, "Emoji picker hidden")
    }

    /**
     * Check if emoji picker is currently showing.
     */
    fun isEmojiPickerShowing(): Boolean = mEmojiPickerShowing

    /**
     * Commit emoji text to the input.
     */
    private fun commitEmojiText(emoji: String) {
        val ic = currentInputConnection ?: return
        ic.commitText(emoji, 1)
        Log.i(TAG, "Committed emoji: $emoji")
    }

    override fun onStartInput(attribute: EditorInfo, restarting: Boolean) {
        super.onStartInput(attribute, restarting)
        setCandidatesViewShownInternal(true, false)
        super.setCandidatesViewShown(true)
        hideWindow()
    }

    override fun onStartInputView(attribute: EditorInfo, restarting: Boolean) {
        sKeyboardSettings.editorPackageName = attribute.packageName
        sKeyboardSettings.editorFieldName = attribute.fieldName
        sKeyboardSettings.editorFieldId = attribute.fieldId
        sKeyboardSettings.editorInputType = attribute.inputType

        val inputView = mKeyboardSwitcher!!.getInputView() ?: return

        if (mRefreshKeyboardRequired) {
            mRefreshKeyboardRequired = false
            toggleLanguage(true, true)
        }

        mKeyboardSwitcher!!.makeKeyboards(false)
        TextEntryState.newSession(this)

        mPasswordText = false
        val variation = attribute.inputType and EditorInfo.TYPE_MASK_VARIATION
        if (variation == EditorInfo.TYPE_TEXT_VARIATION_PASSWORD ||
            variation == EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD ||
            variation == 0xe0
        ) {
            if ((attribute.inputType and EditorInfo.TYPE_MASK_CLASS) == EditorInfo.TYPE_CLASS_TEXT) {
                mPasswordText = true
            }
        }

        mEnableVoiceButton = shouldShowVoiceButton(attribute)
        val enableVoiceButton = mEnableVoiceButton && mEnableVoice

        mVoiceRecognitionTrigger?.onStartInputView()

        mInputTypeNoAutoCorrect = false
        mPredictionOnForMode = false
        mCompletionOn = false
        mCompletions = null
        mModCtrl = false
        mModAlt = false
        mModMeta = false
        mModFn = false
        mEnteredText = null
        mSuggestionForceOn = false
        mSuggestionForceOff = false
        mKeyboardModeOverridePortrait = 0
        mKeyboardModeOverrideLandscape = 0
        sKeyboardSettings.useExtension = false

        when (attribute.inputType and EditorInfo.TYPE_MASK_CLASS) {
            EditorInfo.TYPE_CLASS_NUMBER, EditorInfo.TYPE_CLASS_DATETIME, EditorInfo.TYPE_CLASS_PHONE -> {
                mKeyboardSwitcher!!.setKeyboardMode(
                    KeyboardSwitcher.MODE_PHONE,
                    attribute.imeOptions, enableVoiceButton
                )
            }
            EditorInfo.TYPE_CLASS_TEXT -> {
                mKeyboardSwitcher!!.setKeyboardMode(
                    KeyboardSwitcher.MODE_TEXT,
                    attribute.imeOptions, enableVoiceButton
                )
                mPredictionOnForMode = true
                if (mPasswordText) {
                    mPredictionOnForMode = false
                }
                if (variation == EditorInfo.TYPE_TEXT_VARIATION_EMAIL_ADDRESS ||
                    variation == EditorInfo.TYPE_TEXT_VARIATION_PERSON_NAME ||
                    !mLanguageSwitcher!!.allowAutoSpace()
                ) {
                    mAutoSpace = false
                } else {
                    mAutoSpace = true
                }
                when (variation) {
                    EditorInfo.TYPE_TEXT_VARIATION_EMAIL_ADDRESS -> {
                        mPredictionOnForMode = false
                        mKeyboardSwitcher!!.setKeyboardMode(
                            KeyboardSwitcher.MODE_EMAIL,
                            attribute.imeOptions, enableVoiceButton
                        )
                    }
                    EditorInfo.TYPE_TEXT_VARIATION_URI -> {
                        mPredictionOnForMode = false
                        mKeyboardSwitcher!!.setKeyboardMode(
                            KeyboardSwitcher.MODE_URL,
                            attribute.imeOptions, enableVoiceButton
                        )
                    }
                    EditorInfo.TYPE_TEXT_VARIATION_SHORT_MESSAGE -> {
                        mKeyboardSwitcher!!.setKeyboardMode(
                            KeyboardSwitcher.MODE_IM,
                            attribute.imeOptions, enableVoiceButton
                        )
                    }
                    EditorInfo.TYPE_TEXT_VARIATION_FILTER -> {
                        mPredictionOnForMode = false
                    }
                    EditorInfo.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT -> {
                        mKeyboardSwitcher!!.setKeyboardMode(
                            KeyboardSwitcher.MODE_WEB,
                            attribute.imeOptions, enableVoiceButton
                        )
                        if ((attribute.inputType and EditorInfo.TYPE_TEXT_FLAG_AUTO_CORRECT) == 0) {
                            mInputTypeNoAutoCorrect = true
                        }
                    }
                }
                if ((attribute.inputType and EditorInfo.TYPE_TEXT_FLAG_NO_SUGGESTIONS) != 0) {
                    mPredictionOnForMode = false
                    mInputTypeNoAutoCorrect = true
                }
                if ((attribute.inputType and EditorInfo.TYPE_TEXT_FLAG_AUTO_CORRECT) == 0 &&
                    (attribute.inputType and EditorInfo.TYPE_TEXT_FLAG_MULTI_LINE) == 0
                ) {
                    mInputTypeNoAutoCorrect = true
                }
                if ((attribute.inputType and EditorInfo.TYPE_TEXT_FLAG_AUTO_COMPLETE) != 0) {
                    mPredictionOnForMode = false
                    mCompletionOn = isFullscreenMode
                }
            }
            else -> {
                mKeyboardSwitcher!!.setKeyboardMode(
                    KeyboardSwitcher.MODE_TEXT,
                    attribute.imeOptions, enableVoiceButton
                )
            }
        }

        inputView.closing()
        resetPrediction()
        loadSettings()
        updateShiftKeyState(attribute)

        mPredictionOnPref = mCorrectionMode > 0 || mShowSuggestions
        setCandidatesViewShownInternal(isCandidateStripVisible() || mCompletionOn, false)
        updateSuggestions()

        mHasDictionary = mSuggest?.hasMainDictionary() == true
        updateCorrectionMode()

        inputView.setPreviewEnabled(mPopupOn)
        inputView.setProximityCorrectionEnabled(true)
        checkReCorrectionOnStart()
    }

    private fun shouldShowVoiceButton(attribute: EditorInfo?): Boolean {
        return true
    }

    private fun checkReCorrectionOnStart() {
        if (mReCorrectionEnabled && isPredictionOn()) {
            val ic = currentInputConnection ?: return
            val etr = ExtractedTextRequest()
            etr.token = 0
            val et = ic.getExtractedText(etr, 0) ?: return
            mLastSelectionStart = et.startOffset + et.selectionStart
            mLastSelectionEnd = et.startOffset + et.selectionEnd
            if (!TextUtils.isEmpty(et.text) && isCursorTouchingWord()) {
                postUpdateOldSuggestions()
            }
        }
    }

    override fun onFinishInput() {
        super.onFinishInput()
        onAutoCompletionStateChanged(false)
        mKeyboardSwitcher!!.getInputView()?.closing()
        mAutoDictionary?.flushPendingWrites()
        mUserBigramDictionary?.flushPendingWrites()
    }

    override fun onFinishInputView(finishingInput: Boolean) {
        super.onFinishInputView(finishingInput)
        mHandler.removeMessages(MSG_UPDATE_SUGGESTIONS)
        mHandler.removeMessages(MSG_UPDATE_OLD_SUGGESTIONS)
    }

    override fun onUpdateExtractedText(token: Int, text: ExtractedText) {
        super.onUpdateExtractedText(token, text)
    }

    override fun onUpdateSelection(
        oldSelStart: Int, oldSelEnd: Int,
        newSelStart: Int, newSelEnd: Int,
        candidatesStart: Int, candidatesEnd: Int
    ) {
        super.onUpdateSelection(oldSelStart, oldSelEnd, newSelStart, newSelEnd, candidatesStart, candidatesEnd)

        if (mComposing.isNotEmpty() && mPredicting &&
            (newSelStart != candidatesEnd || newSelEnd != candidatesEnd) &&
            mLastSelectionStart != newSelStart
        ) {
            mComposing.setLength(0)
            mPredicting = false
            postUpdateSuggestions()
            TextEntryState.reset()
            currentInputConnection?.finishComposingText()
        } else if (!mPredicting && !mJustAccepted) {
            when (TextEntryState.state) {
                TextEntryState.State.ACCEPTED_DEFAULT -> {
                    TextEntryState.reset()
                    mJustAddedAutoSpace = false
                }
                TextEntryState.State.SPACE_AFTER_PICKED -> {
                    mJustAddedAutoSpace = false
                }
                else -> {}
            }
        }
        mJustAccepted = false
        postUpdateShiftKeyState()
        mLastSelectionStart = newSelStart
        mLastSelectionEnd = newSelEnd

        if (mReCorrectionEnabled) {
            if (mKeyboardSwitcher?.getInputView()?.isShown == true) {
                if (isPredictionOn() && mJustRevertedSeparator == null &&
                    (candidatesStart == candidatesEnd ||
                            newSelStart != oldSelStart || TextEntryState.isCorrecting) &&
                    (newSelStart < newSelEnd - 1 || !mPredicting)
                ) {
                    if (isCursorTouchingWord() || mLastSelectionStart < mLastSelectionEnd) {
                        postUpdateOldSuggestions()
                    } else {
                        abortCorrection(false)
                        if (mCandidateView != null &&
                            mSuggestPuncList != mCandidateView!!.suggestions &&
                            !mCandidateView!!.isShowingAddToDictionaryHint
                        ) {
                            setNextSuggestions()
                        }
                    }
                }
            }
        }
    }

    // OnKeyboardActionListener implementation
    override fun onKey(primaryCode: Int, keyCodes: IntArray?, x: Int, y: Int) {
        val now = SystemClock.uptimeMillis()
        if (primaryCode != Keyboard.KEYCODE_DELETE || now > mLastKeyTime + QUICK_PRESS) {
            mDeleteCount = 0
        }
        mLastKeyTime = now
        val distinctMultiTouch = mKeyboardSwitcher!!.hasDistinctMultitouch()

        when (primaryCode) {
            Keyboard.KEYCODE_DELETE -> {
                if (!processMultiKey(primaryCode)) {
                    handleBackspace()
                    mDeleteCount++
                }
            }
            Keyboard.KEYCODE_SHIFT -> {
                if (!distinctMultiTouch) handleShift()
            }
            Keyboard.KEYCODE_MODE_CHANGE -> {
                if (!distinctMultiTouch) changeKeyboardMode()
            }
            LatinKeyboardView.KEYCODE_CTRL_LEFT -> {
                if (!distinctMultiTouch) setModCtrl(!mModCtrl)
            }
            LatinKeyboardView.KEYCODE_ALT_LEFT -> {
                if (!distinctMultiTouch) setModAlt(!mModAlt)
            }
            LatinKeyboardView.KEYCODE_META_LEFT -> {
                if (!distinctMultiTouch) setModMeta(!mModMeta)
            }
            LatinKeyboardView.KEYCODE_FN -> {
                if (!distinctMultiTouch) setModFn(!mModFn)
            }
            Keyboard.KEYCODE_CANCEL -> {
                if (!isShowingOptionDialog()) handleClose()
            }
            LatinKeyboardView.KEYCODE_OPTIONS -> onOptionKeyPressed()
            LatinKeyboardView.KEYCODE_OPTIONS_LONGPRESS -> onOptionKeyLongPressed()
            LatinKeyboardView.KEYCODE_COMPOSE -> {
                mComposeMode = !mComposeMode
                mComposeBuffer.clear()
            }
            LatinKeyboardView.KEYCODE_NEXT_LANGUAGE -> toggleLanguage(false, true)
            LatinKeyboardView.KEYCODE_PREV_LANGUAGE -> toggleLanguage(false, false)
            LatinKeyboardView.KEYCODE_VOICE -> {
                if (mVoiceRecognitionTrigger?.isInstalled == true) {
                    mVoiceRecognitionTrigger!!.startVoiceRecognition()
                }
            }
            LatinKeyboardView.KEYCODE_EMOJI -> toggleEmojiPicker()
            9 -> { // Tab
                if (!processMultiKey(primaryCode)) sendTab()
            }
            LatinKeyboardView.KEYCODE_ESCAPE -> {
                if (!processMultiKey(primaryCode)) sendEscape()
            }
            LatinKeyboardView.KEYCODE_DPAD_UP,
            LatinKeyboardView.KEYCODE_DPAD_DOWN,
            LatinKeyboardView.KEYCODE_DPAD_LEFT,
            LatinKeyboardView.KEYCODE_DPAD_RIGHT,
            LatinKeyboardView.KEYCODE_DPAD_CENTER,
            LatinKeyboardView.KEYCODE_HOME,
            LatinKeyboardView.KEYCODE_END,
            LatinKeyboardView.KEYCODE_PAGE_UP,
            LatinKeyboardView.KEYCODE_PAGE_DOWN,
            LatinKeyboardView.KEYCODE_FKEY_F1,
            LatinKeyboardView.KEYCODE_FKEY_F2,
            LatinKeyboardView.KEYCODE_FKEY_F3,
            LatinKeyboardView.KEYCODE_FKEY_F4,
            LatinKeyboardView.KEYCODE_FKEY_F5,
            LatinKeyboardView.KEYCODE_FKEY_F6,
            LatinKeyboardView.KEYCODE_FKEY_F7,
            LatinKeyboardView.KEYCODE_FKEY_F8,
            LatinKeyboardView.KEYCODE_FKEY_F9,
            LatinKeyboardView.KEYCODE_FKEY_F10,
            LatinKeyboardView.KEYCODE_FKEY_F11,
            LatinKeyboardView.KEYCODE_FKEY_F12,
            LatinKeyboardView.KEYCODE_FORWARD_DEL,
            LatinKeyboardView.KEYCODE_INSERT,
            LatinKeyboardView.KEYCODE_SYSRQ,
            LatinKeyboardView.KEYCODE_BREAK,
            LatinKeyboardView.KEYCODE_NUM_LOCK,
            LatinKeyboardView.KEYCODE_SCROLL_LOCK -> {
                if (!processMultiKey(primaryCode)) {
                    sendSpecialKey(-primaryCode)
                }
            }
            else -> {
                if (!mComposeMode && mDeadKeysActive &&
                    Character.getType(primaryCode) == Character.NON_SPACING_MARK.toInt()
                ) {
                    if (!mDeadAccentBuffer.execute(primaryCode)) {
                        return
                    }
                    updateShiftKeyState(currentInputEditorInfo)
                    return
                }
                if (processMultiKey(primaryCode)) return
                if (primaryCode != ASCII_ENTER) {
                    mJustAddedAutoSpace = false
                }
                RingCharBuffer.getInstance().push(primaryCode.toChar(), x, y)
                if (isWordSeparator(primaryCode)) {
                    handleSeparator(primaryCode)
                } else {
                    handleCharacter(primaryCode, keyCodes)
                }
                updateShiftKeyState(currentInputEditorInfo)
            }
        }
    }

    override fun onText(text: CharSequence?) {
        if (text == null) return
        val ic = currentInputConnection ?: return
        ic.beginBatchEdit()
        commitTyped(ic, true)
        ic.commitText(text, 1)
        ic.endBatchEdit()
        updateShiftKeyState(currentInputEditorInfo)
        mJustRevertedSeparator = null
        mJustAddedAutoSpace = false
        mEnteredText = text
    }

    override fun onPress(primaryCode: Int) {
        if (mKeyboardSwitcher!!.isVibrateAndSoundFeedbackRequired) {
            vibrate()
            playKeyClick(primaryCode)
        }
        val distinctMultiTouch = mKeyboardSwitcher!!.hasDistinctMultitouch()
        val ic = currentInputConnection
        when {
            distinctMultiTouch && primaryCode == Keyboard.KEYCODE_SHIFT -> {
                mShiftKeyState.onPress()
                startMultitouchShift()
            }
            distinctMultiTouch && primaryCode == Keyboard.KEYCODE_MODE_CHANGE -> {
                changeKeyboardMode()
                mSymbolKeyState.onPress()
                mKeyboardSwitcher!!.setAutoModeSwitchStateMomentary()
            }
            distinctMultiTouch && primaryCode == LatinKeyboardView.KEYCODE_CTRL_LEFT -> {
                setModCtrl(!mModCtrl)
                mCtrlKeyState.onPress()
                sendCtrlKey(ic, true, true)
            }
            distinctMultiTouch && primaryCode == LatinKeyboardView.KEYCODE_ALT_LEFT -> {
                setModAlt(!mModAlt)
                mAltKeyState.onPress()
                sendAltKey(ic, true, true)
            }
            distinctMultiTouch && primaryCode == LatinKeyboardView.KEYCODE_META_LEFT -> {
                setModMeta(!mModMeta)
                mMetaKeyState.onPress()
                sendMetaKey(ic, true, true)
            }
            distinctMultiTouch && primaryCode == LatinKeyboardView.KEYCODE_FN -> {
                setModFn(!mModFn)
                mFnKeyState.onPress()
            }
            else -> {
                mShiftKeyState.onOtherKeyPressed()
                mSymbolKeyState.onOtherKeyPressed()
                mCtrlKeyState.onOtherKeyPressed()
                mAltKeyState.onOtherKeyPressed()
                mMetaKeyState.onOtherKeyPressed()
                mFnKeyState.onOtherKeyPressed()
            }
        }
    }

    override fun onRelease(primaryCode: Int) {
        (mKeyboardSwitcher!!.getInputView()?.keyboard as? LatinKeyboard)?.keyReleased()
        val distinctMultiTouch = mKeyboardSwitcher!!.hasDistinctMultitouch()
        val ic = currentInputConnection
        when {
            distinctMultiTouch && primaryCode == Keyboard.KEYCODE_SHIFT -> {
                if (mShiftKeyState.isChording) {
                    resetMultitouchShift()
                } else {
                    commitMultitouchShift()
                }
                mShiftKeyState.onRelease()
            }
            distinctMultiTouch && primaryCode == Keyboard.KEYCODE_MODE_CHANGE -> {
                if (mKeyboardSwitcher!!.isInChordingAutoModeSwitchState) {
                    changeKeyboardMode()
                }
                mSymbolKeyState.onRelease()
            }
            distinctMultiTouch && primaryCode == LatinKeyboardView.KEYCODE_CTRL_LEFT -> {
                if (mCtrlKeyState.isChording) setModCtrl(false)
                sendCtrlKey(ic, false, true)
                mCtrlKeyState.onRelease()
            }
            distinctMultiTouch && primaryCode == LatinKeyboardView.KEYCODE_ALT_LEFT -> {
                if (mAltKeyState.isChording) setModAlt(false)
                sendAltKey(ic, false, true)
                mAltKeyState.onRelease()
            }
            distinctMultiTouch && primaryCode == LatinKeyboardView.KEYCODE_META_LEFT -> {
                if (mMetaKeyState.isChording) setModMeta(false)
                sendMetaKey(ic, false, true)
                mMetaKeyState.onRelease()
            }
            distinctMultiTouch && primaryCode == LatinKeyboardView.KEYCODE_FN -> {
                if (mFnKeyState.isChording) setModFn(false)
                mFnKeyState.onRelease()
            }
        }
    }

    override fun onCancel() {
        // Empty implementation
    }

    // Helper methods for key handling
    private fun processMultiKey(primaryCode: Int): Boolean {
        if (mDeadAccentBuffer.composeBuffer.isNotEmpty()) {
            mDeadAccentBuffer.execute(primaryCode)
            mDeadAccentBuffer.clear()
            return true
        }
        if (mComposeMode) {
            mComposeMode = mComposeBuffer.execute(primaryCode)
            return true
        }
        return false
    }

    private fun handleBackspace() {
        val ic = currentInputConnection ?: return
        val deleteChar = mDeleteCount > DELETE_ACCELERATE_AT
        if (mPredicting) {
            val length = mComposing.length
            if (length > 0) {
                mComposing.delete(length - 1, length)
                mWord.deleteLast()
                ic.setComposingText(mComposing, 1)
                if (mComposing.isEmpty()) {
                    mPredicting = false
                }
                postUpdateSuggestions()
            } else {
                ic.deleteSurroundingText(1, 0)
            }
        } else {
            deleteSelection(ic)
        }
        postUpdateShiftKeyState()
        mJustRevertedSeparator = null
    }

    private fun deleteSelection(ic: InputConnection) {
        if (mDeleteCount > DELETE_ACCELERATE_AT) {
            ic.deleteSurroundingText(2, 0)
        } else {
            ic.deleteSurroundingText(1, 0)
        }
    }

    private fun handleCharacter(primaryCode: Int, keyCodes: IntArray?) {
        if (isAlphabet(primaryCode) && isPredictionOn() && !isCursorTouchingWord()) {
            if (!mPredicting) {
                mPredicting = true
                mComposing.setLength(0)
                mWord.reset()
            }
        }
        if (mPredicting) {
            if (isShiftCapsMode() && mComposing.isEmpty()) {
                mWord.setFirstCharCapitalized(true)
            }
            val code = if (isShiftCapsMode()) Character.toUpperCase(primaryCode) else primaryCode
            mComposing.append(code.toChar())
            mWord.add(code, keyCodes)
            val ic = currentInputConnection
            ic?.setComposingText(mComposing, 1)
            postUpdateSuggestions()
        } else {
            sendModifiableKeyChar(primaryCode.toChar())
        }
        updateShiftKeyState(currentInputEditorInfo)
    }

    private fun handleSeparator(primaryCode: Int) {
        var pickedDefault = false
        val ic = currentInputConnection
        if (mPredicting) {
            pickedDefault = pickDefaultSuggestion()
            if (!pickedDefault) {
                commitTyped(ic, true)
            }
        }

        if (mJustAddedAutoSpace && primaryCode == ASCII_SPACE) {
            return
        }

        sendModifiableKeyChar(primaryCode.toChar())

        if (pickedDefault && mBestWord != null) {
            TextEntryState.acceptedDefault(mBestWord)
        }
        TextEntryState.typedCharacter(primaryCode.toChar(), isWordSeparator(primaryCode))
        if (TextEntryState.state == TextEntryState.State.PUNCTUATION_AFTER_ACCEPTED &&
            primaryCode != ASCII_ENTER
        ) {
            swapPunctuationAndSpace()
        } else if (isPredictionOn() && primaryCode == ASCII_SPACE) {
            doubleSpace()
        }
        if (pickedDefault) {
            TextEntryState.backToAcceptedDefault(mBestWord)
        }
        mJustRevertedSeparator = null
    }

    private fun handleShift() {
        mKeyboardSwitcher?.toggleShift()
    }

    private fun handleShiftInternal(forceNormal: Boolean) {
        mKeyboardSwitcher?.handleShiftInternal(forceNormal)
    }

    private fun startMultitouchShift() {
        val oldState = shiftState
        if (oldState == Keyboard.SHIFT_OFF) {
            mSavedShiftState = oldState
            mKeyboardSwitcher?.setShiftState(Keyboard.SHIFT_ON)
        }
    }

    private fun commitMultitouchShift() {
        // Commit the shift state
    }

    private fun resetMultitouchShift() {
        mKeyboardSwitcher?.setShiftState(mSavedShiftState)
    }

    private fun resetShift() {
        handleShiftInternal(true)
    }

    private fun setModCtrl(value: Boolean) {
        mModCtrl = value
        mKeyboardSwitcher?.setCtrlIndicator(value)
    }

    private fun setModAlt(value: Boolean) {
        mModAlt = value
        mKeyboardSwitcher?.setAltIndicator(value)
    }

    private fun setModMeta(value: Boolean) {
        mModMeta = value
        mKeyboardSwitcher?.setMetaIndicator(value)
    }

    private fun setModFn(value: Boolean) {
        mModFn = value
        mKeyboardSwitcher?.setFnIndicator(value)
    }

    private fun sendTab() {
        val ic = currentInputConnection
        val tabHack = isConnectbot() && mConnectbotTabHack
        if (tabHack) {
            if (mModAlt) {
                ic?.commitText(27.toChar().toString(), 1)
            }
            ic?.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_CENTER))
            ic?.sendKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DPAD_CENTER))
            ic?.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_I))
            ic?.sendKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_I))
        } else {
            sendModifiedKeyDownUp(KeyEvent.KEYCODE_TAB)
        }
    }

    private fun sendEscape() {
        if (isConnectbot()) {
            sendKeyChar(27.toChar())
        } else {
            sendModifiedKeyDownUp(111)
        }
    }

    private fun sendSpecialKey(code: Int) {
        if (!isConnectbot()) {
            commitTyped(currentInputConnection, true)
            sendModifiedKeyDownUp(code)
            return
        }
        initEscapeSequences()
        val ic = currentInputConnection
        val ctrlseq = if (mConnectbotTabHack) CTRL_SEQUENCES?.get(code) else null
        val seq = ESC_SEQUENCES?.get(code)

        when {
            ctrlseq != null -> {
                if (mModAlt) ic?.commitText(27.toChar().toString(), 1)
                ic?.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_CENTER))
                ic?.sendKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DPAD_CENTER))
                ic?.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, ctrlseq))
                ic?.sendKeyEvent(KeyEvent(KeyEvent.ACTION_UP, ctrlseq))
            }
            seq != null -> {
                if (mModAlt) ic?.commitText(27.toChar().toString(), 1)
                ic?.commitText(27.toChar().toString(), 1)
                ic?.commitText(seq, 1)
            }
            else -> sendDownUpKeyEvents(code)
        }
        handleModifierKeysUp(false, false)
    }

    private fun initEscapeSequences() {
        if (ESC_SEQUENCES == null) {
            ESC_SEQUENCES = HashMap()
            CTRL_SEQUENCES = HashMap()
            ESC_SEQUENCES!![LatinKeyboardView.KEYCODE_HOME] = "[1~"
            ESC_SEQUENCES!![LatinKeyboardView.KEYCODE_END] = "[4~"
            ESC_SEQUENCES!![LatinKeyboardView.KEYCODE_PAGE_UP] = "[5~"
            ESC_SEQUENCES!![LatinKeyboardView.KEYCODE_PAGE_DOWN] = "[6~"
            ESC_SEQUENCES!![LatinKeyboardView.KEYCODE_FKEY_F1] = "OP"
            ESC_SEQUENCES!![LatinKeyboardView.KEYCODE_FKEY_F2] = "OQ"
            ESC_SEQUENCES!![LatinKeyboardView.KEYCODE_FKEY_F3] = "OR"
            ESC_SEQUENCES!![LatinKeyboardView.KEYCODE_FKEY_F4] = "OS"
            ESC_SEQUENCES!![LatinKeyboardView.KEYCODE_FKEY_F5] = "[15~"
            ESC_SEQUENCES!![LatinKeyboardView.KEYCODE_FKEY_F6] = "[17~"
            ESC_SEQUENCES!![LatinKeyboardView.KEYCODE_FKEY_F7] = "[18~"
            ESC_SEQUENCES!![LatinKeyboardView.KEYCODE_FKEY_F8] = "[19~"
            ESC_SEQUENCES!![LatinKeyboardView.KEYCODE_FKEY_F9] = "[20~"
            ESC_SEQUENCES!![LatinKeyboardView.KEYCODE_FKEY_F10] = "[21~"
            ESC_SEQUENCES!![LatinKeyboardView.KEYCODE_FKEY_F11] = "[23~"
            ESC_SEQUENCES!![LatinKeyboardView.KEYCODE_FKEY_F12] = "[24~"
            ESC_SEQUENCES!![LatinKeyboardView.KEYCODE_FORWARD_DEL] = "[3~"
            ESC_SEQUENCES!![LatinKeyboardView.KEYCODE_INSERT] = "[2~"

            CTRL_SEQUENCES!![LatinKeyboardView.KEYCODE_FKEY_F1] = KeyEvent.KEYCODE_1
            CTRL_SEQUENCES!![LatinKeyboardView.KEYCODE_FKEY_F2] = KeyEvent.KEYCODE_2
            CTRL_SEQUENCES!![LatinKeyboardView.KEYCODE_FKEY_F3] = KeyEvent.KEYCODE_3
            CTRL_SEQUENCES!![LatinKeyboardView.KEYCODE_FKEY_F4] = KeyEvent.KEYCODE_4
            CTRL_SEQUENCES!![LatinKeyboardView.KEYCODE_FKEY_F5] = KeyEvent.KEYCODE_5
            CTRL_SEQUENCES!![LatinKeyboardView.KEYCODE_FKEY_F6] = KeyEvent.KEYCODE_6
            CTRL_SEQUENCES!![LatinKeyboardView.KEYCODE_FKEY_F7] = KeyEvent.KEYCODE_7
            CTRL_SEQUENCES!![LatinKeyboardView.KEYCODE_FKEY_F8] = KeyEvent.KEYCODE_8
            CTRL_SEQUENCES!![LatinKeyboardView.KEYCODE_FKEY_F9] = KeyEvent.KEYCODE_9
            CTRL_SEQUENCES!![LatinKeyboardView.KEYCODE_FKEY_F10] = KeyEvent.KEYCODE_0
        }
    }

    fun sendModifiableKeyChar(ch: Char) {
        val modShift = isShiftMod()
        if ((modShift || mModCtrl || mModAlt || mModMeta) && ch.code in 1..126) {
            val ic = currentInputConnection
            if (isConnectbot()) {
                if (mModAlt) ic?.commitText(27.toChar().toString(), 1)
                if (mModCtrl) {
                    val code = ch.code and 31
                    if (code == 9) sendTab() else ic?.commitText(code.toChar().toString(), 1)
                } else {
                    ic?.commitText(ch.toString(), 1)
                }
                handleModifierKeysUp(false, false)
                return
            }
            sendModifiedKeyDownUp(getKeyCodeForChar(ch), modShift)
            return
        }
        sendKeyChar(ch)
    }

    private fun getKeyCodeForChar(ch: Char): Int {
        return when {
            ch in 'a'..'z' -> KeyEvent.KEYCODE_A + (ch - 'a')
            ch in 'A'..'Z' -> KeyEvent.KEYCODE_A + (ch - 'A')
            ch in '0'..'9' -> KeyEvent.KEYCODE_0 + (ch - '0')
            ch == ' ' -> KeyEvent.KEYCODE_SPACE
            ch == '\n' -> KeyEvent.KEYCODE_ENTER
            else -> 0
        }
    }

    private fun getMetaState(shifted: Boolean): Int {
        var meta = 0
        if (shifted) meta = meta or KeyEvent.META_SHIFT_ON or KeyEvent.META_SHIFT_LEFT_ON
        if (mModCtrl) meta = meta or KeyEvent.META_CTRL_ON or KeyEvent.META_CTRL_LEFT_ON
        if (mModAlt) meta = meta or KeyEvent.META_ALT_ON or KeyEvent.META_ALT_LEFT_ON
        if (mModMeta) meta = meta or KeyEvent.META_META_ON or KeyEvent.META_META_LEFT_ON
        return meta
    }

    private fun sendKeyDown(ic: InputConnection?, key: Int, meta: Int) {
        val now = System.currentTimeMillis()
        ic?.sendKeyEvent(KeyEvent(now, now, KeyEvent.ACTION_DOWN, key, 0, meta))
    }

    private fun sendKeyUp(ic: InputConnection?, key: Int, meta: Int) {
        val now = System.currentTimeMillis()
        ic?.sendKeyEvent(KeyEvent(now, now, KeyEvent.ACTION_UP, key, 0, meta))
    }

    private fun sendModifiedKeyDownUp(key: Int, shifted: Boolean = isShiftMod()) {
        val ic = currentInputConnection
        val meta = getMetaState(shifted)
        sendModifierKeysDown(shifted)
        sendKeyDown(ic, key, meta)
        sendKeyUp(ic, key, meta)
        sendModifierKeysUp(shifted)
    }

    private fun isShiftMod(): Boolean {
        if (mShiftKeyState.isChording) return true
        val kb = mKeyboardSwitcher?.getInputView()
        return kb?.isShiftAll == true
    }

    private fun sendShiftKey(ic: InputConnection?, isDown: Boolean) {
        val key = KeyEvent.KEYCODE_SHIFT_LEFT
        val meta = KeyEvent.META_SHIFT_ON or KeyEvent.META_SHIFT_LEFT_ON
        if (isDown) sendKeyDown(ic, key, meta) else sendKeyUp(ic, key, meta)
    }

    private fun sendCtrlKey(ic: InputConnection?, isDown: Boolean, chording: Boolean) {
        if (chording && sKeyboardSettings.chordingCtrlKey == 0) return
        val key = if (sKeyboardSettings.chordingCtrlKey == 0) KeyEvent.KEYCODE_CTRL_LEFT else sKeyboardSettings.chordingCtrlKey
        val meta = KeyEvent.META_CTRL_ON or KeyEvent.META_CTRL_LEFT_ON
        if (isDown) sendKeyDown(ic, key, meta) else sendKeyUp(ic, key, meta)
    }

    private fun sendAltKey(ic: InputConnection?, isDown: Boolean, chording: Boolean) {
        if (chording && sKeyboardSettings.chordingAltKey == 0) return
        val key = if (sKeyboardSettings.chordingAltKey == 0) KeyEvent.KEYCODE_ALT_LEFT else sKeyboardSettings.chordingAltKey
        val meta = KeyEvent.META_ALT_ON or KeyEvent.META_ALT_LEFT_ON
        if (isDown) sendKeyDown(ic, key, meta) else sendKeyUp(ic, key, meta)
    }

    private fun sendMetaKey(ic: InputConnection?, isDown: Boolean, chording: Boolean) {
        if (chording && sKeyboardSettings.chordingMetaKey == 0) return
        val key = if (sKeyboardSettings.chordingMetaKey == 0) KeyEvent.KEYCODE_META_LEFT else sKeyboardSettings.chordingMetaKey
        val meta = KeyEvent.META_META_ON or KeyEvent.META_META_LEFT_ON
        if (isDown) sendKeyDown(ic, key, meta) else sendKeyUp(ic, key, meta)
    }

    private fun sendModifierKeysDown(shifted: Boolean) {
        val ic = currentInputConnection
        if (shifted) sendShiftKey(ic, true)
        if (mModCtrl && (!mCtrlKeyState.isChording || sKeyboardSettings.chordingCtrlKey != 0)) {
            sendCtrlKey(ic, true, false)
        }
        if (mModAlt && (!mAltKeyState.isChording || sKeyboardSettings.chordingAltKey != 0)) {
            sendAltKey(ic, true, false)
        }
        if (mModMeta && (!mMetaKeyState.isChording || sKeyboardSettings.chordingMetaKey != 0)) {
            sendMetaKey(ic, true, false)
        }
    }

    private fun sendModifierKeysUp(shifted: Boolean) {
        handleModifierKeysUp(shifted, true)
    }

    private fun handleModifierKeysUp(shifted: Boolean, sendKey: Boolean) {
        val ic = currentInputConnection
        if (mModMeta && (!mMetaKeyState.isChording || sKeyboardSettings.chordingMetaKey != 0)) {
            if (sendKey) sendMetaKey(ic, false, false)
            if (!mMetaKeyState.isChording) setModMeta(false)
        }
        if (mModAlt && (!mAltKeyState.isChording || sKeyboardSettings.chordingAltKey != 0)) {
            if (sendKey) sendAltKey(ic, false, false)
            if (!mAltKeyState.isChording) setModAlt(false)
        }
        if (mModCtrl && (!mCtrlKeyState.isChording || sKeyboardSettings.chordingCtrlKey != 0)) {
            if (sendKey) sendCtrlKey(ic, false, false)
            if (!mCtrlKeyState.isChording) setModCtrl(false)
        }
        if (shifted) {
            if (sendKey) sendShiftKey(ic, false)
            val state = shiftState
            if (!(mShiftKeyState.isChording || state == Keyboard.SHIFT_LOCKED)) {
                resetShift()
            }
        }
    }

    private fun isConnectbot(): Boolean {
        val ei = currentInputEditorInfo ?: return false
        val pkg = ei.packageName ?: return false
        return (pkg.equals("org.connectbot", ignoreCase = true) ||
                pkg.equals("org.woltage.irssiconnectbot", ignoreCase = true) ||
                pkg.equals("com.pslib.connectbot", ignoreCase = true) ||
                pkg.equals("sk.vx.connectbot", ignoreCase = true)) && ei.inputType == 0
    }

    private fun isShowingOptionDialog(): Boolean {
        return mOptionsDialog?.isShowing == true
    }

    private fun onOptionKeyPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val intent = Intent(this, LatinIMESettings::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } else {
            if (!isShowingOptionDialog()) showOptionsMenu()
        }
    }

    private fun onOptionKeyLongPressed() {
        if (!isShowingOptionDialog()) showInputMethodPicker()
    }

    private fun showInputMethodPicker() {
        (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).showInputMethodPicker()
    }

    private fun showOptionsMenu() {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setIcon(R.drawable.ic_dialog_keyboard)
        builder.setNegativeButton(android.R.string.cancel, null)
        val itemSettings = getString(R.string.english_ime_settings)
        val itemInputMethod = getString(R.string.selectInputMethod)
        builder.setItems(arrayOf<CharSequence>(itemInputMethod, itemSettings)) { di, position ->
            di.dismiss()
            when (position) {
                POS_SETTINGS -> launchSettings()
                POS_METHOD -> (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).showInputMethodPicker()
            }
        }
        builder.setTitle(mResources!!.getString(R.string.english_ime_input_options))
        mOptionsDialog = builder.create()
        val window = mOptionsDialog!!.window
        val lp = window!!.attributes
        lp.token = mKeyboardSwitcher!!.getInputView()?.windowToken
        lp.type = WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG
        window.attributes = lp
        window.addFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
        mOptionsDialog!!.show()
    }

    private fun commitTyped(ic: InputConnection?, manual: Boolean) {
        if (mPredicting) {
            mPredicting = false
            if (mComposing.isNotEmpty()) {
                ic?.commitText(mComposing, 1)
                mCommittedLength = mComposing.length
                if (manual) {
                    TextEntryState.manualTyped(mComposing)
                } else {
                    TextEntryState.acceptedTyped(mComposing)
                }
                addToDictionaries(mComposing, AutoDictionary.FREQUENCY_FOR_TYPED)
            }
            updateSuggestions()
        }
    }

    private fun postUpdateShiftKeyState() {
        // Shift state update handled by updateShiftKeyState
    }

    private fun postUpdateSuggestions() {
        mHandler.removeMessages(MSG_UPDATE_SUGGESTIONS)
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_UPDATE_SUGGESTIONS), 100)
    }

    private fun postUpdateOldSuggestions() {
        mHandler.removeMessages(MSG_UPDATE_OLD_SUGGESTIONS)
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_UPDATE_OLD_SUGGESTIONS), 300)
    }

    private fun isAlphabet(code: Int): Boolean = Character.isLetter(code)

    private fun isWordSeparator(code: Int): Boolean {
        val separators = mWordSeparators
        return separators.contains(code.toChar().toString())
    }

    private fun isSentenceSeparator(code: Int): Boolean {
        return mSentenceSeparators.contains(code.toChar().toString())
    }

    private fun isCursorTouchingWord(): Boolean {
        val ic = currentInputConnection ?: return false
        val before = ic.getTextBeforeCursor(1, 0)
        val after = ic.getTextAfterCursor(1, 0)
        if (!TextUtils.isEmpty(before) && !isWordSeparator(before!![0].code)) return true
        if (!TextUtils.isEmpty(after) && !isWordSeparator(after!![0].code)) return true
        return false
    }

    private fun isCandidateStripVisible(): Boolean {
        return isPredictionOn()
    }

    private fun pickDefaultSuggestion(): Boolean {
        if (mBestWord != null && mBestWord!!.isNotEmpty()) {
            TextEntryState.acceptedDefault(mBestWord)
            mJustAccepted = true
            val ic = currentInputConnection
            ic?.commitText(mBestWord, 1)
            mPredicting = false
            mComposing.setLength(0)
            return true
        }
        return false
    }

    private fun swapPunctuationAndSpace() {
        val ic = currentInputConnection ?: return
        val lastTwo = ic.getTextBeforeCursor(2, 0) ?: return
        if (lastTwo.length == 2 && lastTwo[0] == ' ' && isSentenceSeparator(lastTwo[1].code)) {
            ic.beginBatchEdit()
            ic.deleteSurroundingText(2, 0)
            ic.commitText("${lastTwo[1]} ", 1)
            ic.endBatchEdit()
            updateShiftKeyState(currentInputEditorInfo)
            mJustAddedAutoSpace = true
        }
    }

    private fun doubleSpace() {
        if (mCorrectionMode == Suggest.CORRECTION_NONE) return
        val ic = currentInputConnection ?: return
        val lastThree = ic.getTextBeforeCursor(3, 0) ?: return
        if (lastThree.length == 3 &&
            Character.isLetterOrDigit(lastThree[0]) &&
            lastThree[1] == ' ' && lastThree[2] == ' '
        ) {
            ic.beginBatchEdit()
            ic.deleteSurroundingText(2, 0)
            ic.commitText(". ", 1)
            ic.endBatchEdit()
            updateShiftKeyState(currentInputEditorInfo)
            mJustAddedAutoSpace = true
        }
    }

    private fun addToDictionaries(word: CharSequence, frequency: Int) {
        mUserDictionary?.addWord(word.toString(), frequency)
    }

    private fun abortCorrection(force: Boolean) {
        if (force || TextEntryState.isCorrecting) {
            currentInputConnection?.finishComposingText()
            clearSuggestions()
        }
    }

    private fun clearSuggestions() {
        mCandidateView?.setSuggestions(null, false, false, false)
    }

    private fun setNextSuggestions() {
        mSuggestPuncList?.let {
            setSuggestions(it, false, false, false)
        }
    }

    private fun setSuggestions(
        suggestions: List<CharSequence>?,
        completions: Boolean,
        typedWordValid: Boolean,
        haveMinimalSuggestion: Boolean
    ) {
        mCandidateView?.setSuggestions(suggestions, completions, typedWordValid, haveMinimalSuggestion)
    }

    private fun setCandidatesViewShownInternal(shown: Boolean, needsInputViewShown: Boolean) {
        val visible = shown &&
                onEvaluateInputViewShown() &&
                mKeyboardSwitcher!!.getInputView() != null &&
                isPredictionOn() &&
                (!needsInputViewShown || mKeyboardSwitcher!!.getInputView()!!.isShown)
        if (visible) {
            if (mCandidateViewContainer == null) {
                onCreateCandidatesView()
                setNextSuggestions()
            }
        } else {
            if (mCandidateViewContainer != null) {
                removeCandidateViewContainer()
                commitTyped(currentInputConnection, true)
            }
        }
        super.setCandidatesViewShown(visible)
    }

    override fun setCandidatesViewShown(shown: Boolean) {
        setCandidatesViewShownInternal(shown, true)
    }

    private fun isShiftCapsMode(): Boolean {
        val view = mKeyboardSwitcher?.getInputView()
        return view?.isShiftCaps == true
    }

    fun changeKeyboardMode() {
        val switcher = mKeyboardSwitcher ?: return
        if (switcher.isAlphabetMode) {
            mSavedShiftState = shiftState
        }
        switcher.toggleSymbols()
        if (switcher.isAlphabetMode) {
            switcher.setShiftState(mSavedShiftState)
        }
        updateShiftKeyState(currentInputEditorInfo)
    }

    private fun updateCorrectionMode() {
        mHasDictionary = mSuggest?.hasMainDictionary() == true
        mAutoCorrectOn = (mAutoCorrectEnabled || mQuickFixes) && !mInputTypeNoAutoCorrect && mHasDictionary
        mCorrectionMode = when {
            mAutoCorrectOn && mAutoCorrectEnabled -> Suggest.CORRECTION_FULL
            mAutoCorrectOn -> Suggest.CORRECTION_BASIC
            else -> Suggest.CORRECTION_NONE
        }
        if (mBigramSuggestionEnabled && mAutoCorrectOn && mAutoCorrectEnabled) {
            mCorrectionMode = Suggest.CORRECTION_FULL_BIGRAM
        }
        if (suggestionsDisabled()) {
            mAutoCorrectOn = false
            mCorrectionMode = Suggest.CORRECTION_NONE
        }
        mSuggest?.setCorrectionMode(mCorrectionMode)
    }

    private fun loadSettings() {
        val sp = PreferenceManager.getDefaultSharedPreferences(this)
        mVibrateOn = sp.getBoolean(PREF_VIBRATE_ON, false)
        mVibrateLen = getPrefInt(sp, PREF_VIBRATE_LEN, resources.getString(R.string.vibrate_duration_ms))
        mSoundOn = sp.getBoolean(PREF_SOUND_ON, false)
        mPopupOn = sp.getBoolean(PREF_POPUP_ON, mResources!!.getBoolean(R.bool.default_popup_preview))
        mAutoCapPref = sp.getBoolean(PREF_AUTO_CAP, resources.getBoolean(R.bool.default_auto_cap))
        mQuickFixes = sp.getBoolean(PREF_QUICK_FIXES, true)
        mShowSuggestions = sp.getBoolean(PREF_SHOW_SUGGESTIONS, mResources!!.getBoolean(R.bool.default_suggestions))

        val voiceMode = sp.getString(PREF_VOICE_MODE, getString(R.string.voice_mode_main))
        val enableVoice = voiceMode != getString(R.string.voice_mode_off) && mEnableVoiceButton
        val voiceOnPrimary = voiceMode == getString(R.string.voice_mode_main)
        if (mKeyboardSwitcher != null && (enableVoice != mEnableVoice || voiceOnPrimary != mVoiceOnPrimary)) {
            mKeyboardSwitcher!!.setVoiceMode(enableVoice, voiceOnPrimary)
        }
        mEnableVoice = enableVoice
        mVoiceOnPrimary = voiceOnPrimary

        mAutoCorrectEnabled = sp.getBoolean(PREF_AUTO_COMPLETE, mResources!!.getBoolean(R.bool.enable_autocorrect)) && mShowSuggestions
        updateCorrectionMode()
        mLanguageSwitcher!!.loadLocales(sp)
        mAutoCapActive = mAutoCapPref && mLanguageSwitcher!!.allowAutoCap()
        mDeadKeysActive = mLanguageSwitcher!!.allowDeadKeys()
    }

    private fun vibrate() {
        if (!mVibrateOn) return
        vibrate(mVibrateLen)
    }

    fun vibrate(len: Int) {
        val v = getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        if (v != null) {
            v.vibrate(len.toLong())
            return
        }
        mKeyboardSwitcher?.getInputView()?.performHapticFeedback(
            HapticFeedbackConstants.KEYBOARD_TAP,
            HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
        )
    }

    private fun playKeyClick(primaryCode: Int) {
        if (mAudioManager == null) {
            mKeyboardSwitcher?.getInputView()?.let { updateRingerMode() }
        }
        if (mSoundOn && !mSilentMode) {
            val sound = when (primaryCode) {
                Keyboard.KEYCODE_DELETE -> AudioManager.FX_KEYPRESS_DELETE
                ASCII_ENTER -> AudioManager.FX_KEYPRESS_RETURN
                ASCII_SPACE -> AudioManager.FX_KEYPRESS_SPACEBAR
                else -> AudioManager.FX_KEYPRESS_STANDARD
            }
            mAudioManager?.playSoundEffect(sound, getKeyClickVolume())
        }
    }

    private fun getKeyClickVolume(): Float {
        if (mAudioManager == null) return 0.0f
        val method = sKeyboardSettings.keyClickMethod
        if (method == 0) return FX_VOLUME
        var targetVol = sKeyboardSettings.keyClickVolume
        if (method > 1) {
            val mediaMax = mAudioManager!!.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
            val mediaVol = mAudioManager!!.getStreamVolume(AudioManager.STREAM_MUSIC)
            val channelVol = mediaVol.toFloat() / mediaMax
            when (method) {
                2 -> targetVol *= channelVol
                3 -> {
                    if (channelVol == 0f) return 0.0f
                    targetVol = minOf(targetVol / channelVol, 1.0f)
                }
            }
        }
        return Math.pow(10.0, (FX_VOLUME_RANGE_DB * (targetVol - 1) / 20).toDouble()).toFloat()
    }

    fun onAutoCompletionStateChanged(isAutoCompletion: Boolean) {
        mKeyboardSwitcher?.onAutoCompletionStateChanged(isAutoCompletion)
    }

    override fun onEvaluateInputViewShown(): Boolean {
        val parent = super.onEvaluateInputViewShown()
        return mForceKeyboardOn || parent
    }

    override fun onEvaluateFullscreenMode(): Boolean {
        val dm = resources.displayMetrics
        val displayHeight = dm.heightPixels.toFloat()
        val dimen = resources.getDimension(R.dimen.max_height_for_fullscreen)
        return if (displayHeight > dimen || mFullscreenOverride || isConnectbot()) {
            false
        } else {
            super.onEvaluateFullscreenMode()
        }
    }

    override fun onComputeInsets(outInsets: Insets) {
        super.onComputeInsets(outInsets)
        if (!isFullscreenMode) {
            outInsets.contentTopInsets = outInsets.visibleTopInsets
        }
    }

    override fun hideWindow() {
        onAutoCompletionStateChanged(false)
        if (mOptionsDialog?.isShowing == true) {
            mOptionsDialog!!.dismiss()
            mOptionsDialog = null
        }
        hideEmojiPicker()
        mWordToSuggestions.clear()
        mWordHistory.clear()
        super.hideWindow()
        TextEntryState.endSession()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                if (event.repeatCount == 0) {
                    // First close emoji picker if showing
                    if (mEmojiPickerShowing) {
                        hideEmojiPicker()
                        return true
                    }
                    if (mKeyboardSwitcher?.getInputView() != null) {
                        if (mKeyboardSwitcher!!.getInputView()!!.handleBack()) return true
                    }
                }
            }
            KeyEvent.KEYCODE_VOLUME_UP -> {
                if (mVolUpAction != "none" && isKeyboardVisible()) return true
            }
            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                if (mVolDownAction != "none" && isKeyboardVisible()) return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_DPAD_DOWN,
            KeyEvent.KEYCODE_DPAD_UP,
            KeyEvent.KEYCODE_DPAD_LEFT,
            KeyEvent.KEYCODE_DPAD_RIGHT -> {
                val inputView = mKeyboardSwitcher?.getInputView()
                if (inputView?.isShown == true && inputView.shiftState == Keyboard.SHIFT_ON) {
                    val newEvent = KeyEvent(
                        event.downTime, event.eventTime,
                        event.action, event.keyCode,
                        event.repeatCount, event.deviceId,
                        event.scanCode,
                        KeyEvent.META_SHIFT_LEFT_ON or KeyEvent.META_SHIFT_ON
                    )
                    currentInputConnection?.sendKeyEvent(newEvent)
                    return true
                }
            }
            KeyEvent.KEYCODE_VOLUME_UP -> {
                if (mVolUpAction != "none" && isKeyboardVisible()) {
                    return doSwipeAction(mVolUpAction)
                }
            }
            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                if (mVolDownAction != "none" && isKeyboardVisible()) {
                    return doSwipeAction(mVolDownAction)
                }
            }
        }
        return super.onKeyUp(keyCode, event)
    }

    fun isKeyboardVisible(): Boolean {
        return mKeyboardSwitcher?.getInputView()?.isShown == true
    }

    // SharedPreferences.OnSharedPreferenceChangeListener implementation
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (sharedPreferences == null || key == null) return
        val res = resources
        var needReload = false

        when (key) {
            PREF_SELECTED_LANGUAGES -> {
                mLanguageSwitcher!!.loadLocales(sharedPreferences)
                mKeyboardSwitcher!!.setLanguageSwitcher(mLanguageSwitcher)
                needReload = true
            }
            PREF_REC_CORRECTION -> {
                mReCorrectionEnabled = sharedPreferences.getBoolean(PREF_REC_CORRECTION, res.getBoolean(R.bool.default_recorrection_enabled))
            }
            PREF_CONNECTBOT_TAB_HACK -> {
                mConnectbotTabHack = sharedPreferences.getBoolean(PREF_CONNECTBOT_TAB_HACK, res.getBoolean(R.bool.default_connectbot_tab_hack))
            }
            PREF_FULLSCREEN_OVERRIDE -> {
                mFullscreenOverride = sharedPreferences.getBoolean(PREF_FULLSCREEN_OVERRIDE, res.getBoolean(R.bool.default_fullscreen_override))
                needReload = true
            }
            PREF_FORCE_KEYBOARD_ON -> {
                mForceKeyboardOn = sharedPreferences.getBoolean(PREF_FORCE_KEYBOARD_ON, res.getBoolean(R.bool.default_force_keyboard_on))
                needReload = true
            }
            PREF_KEYBOARD_NOTIFICATION -> {
                mKeyboardNotification = sharedPreferences.getBoolean(PREF_KEYBOARD_NOTIFICATION, res.getBoolean(R.bool.default_keyboard_notification))
                setNotification(mKeyboardNotification)
            }
            PREF_SUGGESTIONS_IN_LANDSCAPE -> {
                mSuggestionsInLandscape = sharedPreferences.getBoolean(PREF_SUGGESTIONS_IN_LANDSCAPE, res.getBoolean(R.bool.default_suggestions_in_landscape))
                mSuggestionForceOff = false
                mSuggestionForceOn = false
                setCandidatesViewShown(isPredictionOn())
            }
            PREF_SHOW_SUGGESTIONS -> {
                mShowSuggestions = sharedPreferences.getBoolean(PREF_SHOW_SUGGESTIONS, res.getBoolean(R.bool.default_suggestions))
                mSuggestionForceOff = false
                mSuggestionForceOn = false
                needReload = true
            }
            PREF_HEIGHT_PORTRAIT -> {
                mHeightPortrait = getHeight(sharedPreferences, PREF_HEIGHT_PORTRAIT, res.getString(R.string.default_height_portrait))
                needReload = true
            }
            PREF_HEIGHT_LANDSCAPE -> {
                mHeightLandscape = getHeight(sharedPreferences, PREF_HEIGHT_LANDSCAPE, res.getString(R.string.default_height_landscape))
                needReload = true
            }
            PREF_HINT_MODE -> {
                sKeyboardSettings.hintMode = sharedPreferences.getString(PREF_HINT_MODE, res.getString(R.string.default_hint_mode))?.toIntOrNull() ?: 0
                needReload = true
            }
            PREF_LONGPRESS_TIMEOUT -> {
                sKeyboardSettings.longpressTimeout = getPrefInt(sharedPreferences, PREF_LONGPRESS_TIMEOUT, res.getString(R.string.default_long_press_duration))
            }
            PREF_RENDER_MODE -> {
                sKeyboardSettings.renderMode = getPrefInt(sharedPreferences, PREF_RENDER_MODE, res.getString(R.string.default_render_mode))
                needReload = true
            }
            PREF_SWIPE_UP -> mSwipeUpAction = sharedPreferences.getString(PREF_SWIPE_UP, res.getString(R.string.default_swipe_up)) ?: ""
            PREF_SWIPE_DOWN -> mSwipeDownAction = sharedPreferences.getString(PREF_SWIPE_DOWN, res.getString(R.string.default_swipe_down)) ?: ""
            PREF_SWIPE_LEFT -> mSwipeLeftAction = sharedPreferences.getString(PREF_SWIPE_LEFT, res.getString(R.string.default_swipe_left)) ?: ""
            PREF_SWIPE_RIGHT -> mSwipeRightAction = sharedPreferences.getString(PREF_SWIPE_RIGHT, res.getString(R.string.default_swipe_right)) ?: ""
            PREF_VOL_UP -> mVolUpAction = sharedPreferences.getString(PREF_VOL_UP, res.getString(R.string.default_vol_up)) ?: ""
            PREF_VOL_DOWN -> mVolDownAction = sharedPreferences.getString(PREF_VOL_DOWN, res.getString(R.string.default_vol_down)) ?: ""
            PREF_VIBRATE_LEN -> mVibrateLen = getPrefInt(sharedPreferences, PREF_VIBRATE_LEN, res.getString(R.string.vibrate_duration_ms))
        }

        updateKeyboardOptions()
        if (needReload) {
            mKeyboardSwitcher!!.makeKeyboards(true)
        }
    }

    private fun setNotification(visible: Boolean) {
        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (visible && mNotificationReceiver == null) {
            createNotificationChannel()
            mNotificationReceiver = NotificationReceiver(this)
            val pFilter = IntentFilter(NotificationReceiver.ACTION_SHOW)
            pFilter.addAction(NotificationReceiver.ACTION_SETTINGS)
            ContextCompat.registerReceiver(this, mNotificationReceiver, pFilter, ContextCompat.RECEIVER_EXPORTED)

            val notificationIntent = Intent(NotificationReceiver.ACTION_SHOW)
            val contentIntent = PendingIntent.getBroadcast(applicationContext, 1, notificationIntent, PendingIntent.FLAG_IMMUTABLE)

            val configIntent = Intent(NotificationReceiver.ACTION_SETTINGS)
            val configPendingIntent = PendingIntent.getBroadcast(applicationContext, 2, configIntent, PendingIntent.FLAG_IMMUTABLE)

            val title = "Show Hacker's Keyboard"
            val body = "Select this to open the keyboard. Disable in settings."

            val mBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.icon_hk_notification)
                .setColor(0xff220044.toInt())
                .setAutoCancel(false)
                .setTicker("Keyboard notification enabled.")
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(contentIntent)
                .setOngoing(true)
                .addAction(R.drawable.icon_hk_notification, getString(R.string.notification_action_settings), configPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            try {
                NotificationManagerCompat.from(this).notify(NOTIFICATION_ONGOING_ID, mBuilder.build())
            } catch (e: SecurityException) {
                // permission not held
            }
        } else if (mNotificationReceiver != null) {
            mNotificationManager.cancel(NOTIFICATION_ONGOING_ID)
            unregisterReceiver(mNotificationReceiver)
            mNotificationReceiver = null
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.notification_channel_name)
            val description = getString(R.string.notification_channel_description)
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance)
            channel.description = description
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun getCurrentWord(): WordComposer = mWord

    fun getPopupOn(): Boolean = mPopupOn

    fun addWordToDictionary(word: String): Boolean {
        mUserDictionary?.addWord(word, 128)
        postUpdateSuggestions()
        return true
    }

    fun pickSuggestionManually(index: Int, suggestion: CharSequence) {
        val ic = currentInputConnection ?: return
        if (mCompletionOn && mCompletions != null && index >= 0 && index < mCompletions!!.size) {
            val ci = mCompletions!![index]
            ic.commitCompletion(ci)
            if (mCandidateView != null) {
                mCandidateView!!.clear()
            }
            return
        }
        pickSuggestion(suggestion)
        TextEntryState.acceptedSuggestion(mComposing.toString(), suggestion)
        if (mAutoSpace && !isShowingPunctuationList()) {
            sendSpace()
            mJustAddedAutoSpace = true
        }
    }

    private fun pickSuggestion(suggestion: CharSequence) {
        val ic = currentInputConnection
        if (ic != null) {
            ic.commitText(suggestion, 1)
        }
        mPredicting = false
        mCommittedLength = suggestion.length
        mComposing.setLength(0)
        mWord.reset()
    }

    private fun isShowingPunctuationList(): Boolean {
        return mSuggestPuncList == mCandidateView?.suggestions
    }

    private fun sendSpace() {
        sendKeyChar(' ')
        updateShiftKeyState(currentInputEditorInfo)
    }

    private fun initSuggestPuncList() {
        mSuggestPuncList = ArrayList()
        var suggestPuncs = sKeyboardSettings.suggestedPunctuation
        val defaultPuncs = resources.getString(R.string.suggested_punctuations_default)
        if (suggestPuncs == defaultPuncs || suggestPuncs.isEmpty()) {
            suggestPuncs = resources.getString(R.string.suggested_punctuations)
        }
        for (i in suggestPuncs.indices) {
            mSuggestPuncList!!.add(suggestPuncs.subSequence(i, i + 1))
        }
        setNextSuggestions()
    }

    override fun dump(fd: FileDescriptor, fout: PrintWriter, args: Array<String>) {
        super.dump(fd, fout, args)
        val p = PrintWriterPrinter(fout)
        p.println("LatinIME state :")
        p.println("  Keyboard mode = " + mKeyboardSwitcher!!.keyboardMode)
        p.println("  mComposing=$mComposing")
        p.println("  mPredictionOnForMode=$mPredictionOnForMode")
        p.println("  mCorrectionMode=$mCorrectionMode")
        p.println("  mPredicting=$mPredicting")
        p.println("  mAutoCorrectOn=$mAutoCorrectOn")
        p.println("  mAutoSpace=$mAutoSpace")
        p.println("  mCompletionOn=$mCompletionOn")
        p.println("  TextEntryState.state=" + TextEntryState.state)
        p.println("  mSoundOn=$mSoundOn")
        p.println("  mVibrateOn=$mVibrateOn")
        p.println("  mPopupOn=$mPopupOn")
    }
}

private const val CPS_BUFFER_SIZE = 16
