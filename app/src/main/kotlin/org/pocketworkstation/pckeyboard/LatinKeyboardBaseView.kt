/*
 * Copyright (C) 2010 The Android Open Source Project
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

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Message
import android.os.SystemClock
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.GestureDetector
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import java.lang.reflect.Method
import java.util.WeakHashMap

/**
 * A view that renders a virtual [LatinKeyboard]. It handles rendering of keys and
 * detecting key presses and touch movements.
 *
 * TODO: References to LatinKeyboard in this class should be replaced with ones to its base class.
 *
 * @attr ref R.styleable.LatinKeyboardBaseView_keyBackground
 * @attr ref R.styleable.LatinKeyboardBaseView_keyPreviewLayout
 * @attr ref R.styleable.LatinKeyboardBaseView_keyPreviewOffset
 * @attr ref R.styleable.LatinKeyboardBaseView_labelTextSize
 * @attr ref R.styleable.LatinKeyboardBaseView_keyTextSize
 * @attr ref R.styleable.LatinKeyboardBaseView_keyTextColor
 * @attr ref R.styleable.LatinKeyboardBaseView_verticalCorrection
 * @attr ref R.styleable.LatinKeyboardBaseView_popupLayout
 */
open class LatinKeyboardBaseView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = R.attr.keyboardViewStyle
) : View(context, attrs, defStyle), PointerTracker.UIProxy {

    interface OnKeyboardActionListener {
        /**
         * Called when the user presses a key. This is sent before the
         * [onKey] is called. For keys that repeat, this is only
         * called once.
         *
         * @param primaryCode the unicode of the key being pressed. If the touch is
         *            not on a valid key, the value will be zero.
         */
        fun onPress(primaryCode: Int)

        /**
         * Called when the user releases a key. This is sent after the
         * [onKey] is called. For keys that repeat, this is only
         * called once.
         *
         * @param primaryCode the code of the key that was released
         */
        fun onRelease(primaryCode: Int)

        /**
         * Send a key press to the listener.
         *
         * @param primaryCode this is the key that was pressed
         * @param keyCodes the codes for all the possible alternative keys with
         *            the primary code being the first. If the primary key
         *            code is a single character such as an alphabet or
         *            number or symbol, the alternatives will include other
         *            characters that may be on the same key or adjacent
         *            keys. These codes are useful to correct for
         *            accidental presses of a key adjacent to the intended
         *            key.
         * @param x x-coordinate pixel of touched event. If onKey is not called by onTouchEvent,
         *            the value should be NOT_A_TOUCH_COORDINATE.
         * @param y y-coordinate pixel of touched event. If onKey is not called by onTouchEvent,
         *            the value should be NOT_A_TOUCH_COORDINATE.
         */
        fun onKey(primaryCode: Int, keyCodes: IntArray, x: Int, y: Int)

        /**
         * Sends a sequence of characters to the listener.
         *
         * @param text the sequence of characters to be displayed.
         */
        fun onText(text: CharSequence)

        /**
         * Called when user released a finger outside any key.
         */
        fun onCancel()

        /**
         * Called when the user quickly moves the finger from right to left.
         */
        fun swipeLeft(): Boolean

        /**
         * Called when the user quickly moves the finger from left to right.
         */
        fun swipeRight(): Boolean

        /**
         * Called when the user quickly moves the finger from up to down.
         */
        fun swipeDown(): Boolean

        /**
         * Called when the user quickly moves the finger from down to up.
         */
        fun swipeUp(): Boolean
    }

    // Timing constants
    private val mKeyRepeatInterval: Int

    // XML attribute
    private var mKeyTextSize: Float = 0f
    private var mLabelScale: Float = 1.0f
    private var mKeyTextColor: Int = 0
    private var mKeyHintColor: Int = 0
    private var mKeyCursorColor: Int = 0
    private var mInvertSymbols: Boolean = false
    private var mRecolorSymbols: Boolean = false
    private var mKeyTextStyle: Typeface = Typeface.DEFAULT
    private var mLabelTextSize: Float = 0f
    private var mSymbolColorScheme: Int = 0
    private var mShadowColor: Int = 0
    private var mShadowRadius: Float = 0f
    private var mKeyBackground: Drawable? = null
    private var mBackgroundAlpha: Int = 0
    private var mBackgroundDimAmount: Float = 0f
    private var mKeyHysteresisDistance: Float = 0f
    private var mVerticalCorrection: Float = 0f
    @JvmField protected var mPreviewOffset: Int = 0
    @JvmField protected var mPreviewHeight: Int = 0
    @JvmField protected var mPopupLayout: Int = 0

    // Main keyboard
    private var mKeyboard: Keyboard? = null
    private var mKeys: Array<Keyboard.Key>? = null
    // TODO this attribute should be gotten from Keyboard.
    private var mKeyboardVerticalGap: Int = 0

    // Key preview popup
    @JvmField protected var mPreviewText: TextView? = null
    @JvmField protected var mPreviewPopup: PopupWindow? = null
    @JvmField protected var mPreviewTextSizeLarge: Int = 0
    @JvmField protected var mOffsetInWindow: IntArray? = null
    @JvmField protected var mOldPreviewKeyIndex: Int = NOT_A_KEY
    @JvmField protected var mShowPreview: Boolean = true
    @JvmField protected var mShowTouchPoints: Boolean = true
    @JvmField protected var mPopupPreviewOffsetX: Int = 0
    @JvmField protected var mPopupPreviewOffsetY: Int = 0
    @JvmField protected var mWindowY: Int = 0
    @JvmField protected var mPopupPreviewDisplayedY: Int = 0
    @JvmField protected val mDelayBeforePreview: Int
    @JvmField protected val mDelayBeforeSpacePreview: Int
    @JvmField protected val mDelayAfterPreview: Int

    // Popup mini keyboard
    @JvmField protected var mMiniKeyboardPopup: PopupWindow? = null
    @JvmField protected var mMiniKeyboard: LatinKeyboardBaseView? = null
    @JvmField protected var mMiniKeyboardContainer: View? = null
    @JvmField protected var mMiniKeyboardParent: View? = null
    @JvmField protected var mMiniKeyboardVisible: Boolean = false
    @JvmField protected val mMiniKeyboardCacheMain: WeakHashMap<Keyboard.Key, Keyboard> = WeakHashMap()
    @JvmField protected val mMiniKeyboardCacheShift: WeakHashMap<Keyboard.Key, Keyboard> = WeakHashMap()
    @JvmField protected val mMiniKeyboardCacheCaps: WeakHashMap<Keyboard.Key, Keyboard> = WeakHashMap()
    @JvmField protected var mMiniKeyboardOriginX: Int = 0
    @JvmField protected var mMiniKeyboardOriginY: Int = 0
    @JvmField protected var mMiniKeyboardPopupTime: Long = 0
    @JvmField protected var mWindowOffset: IntArray? = null
    @JvmField protected val mMiniKeyboardSlideAllowance: Float
    @JvmField protected var mMiniKeyboardTrackerId: Int = 0

    /** Listener for [OnKeyboardActionListener]. */
    private var mKeyboardActionListener: OnKeyboardActionListener? = null

    private val mPointerTrackers: ArrayList<PointerTracker> = ArrayList()
    private var mIgnoreMove: Boolean = false

    // TODO: Let the PointerTracker class manage this pointer queue
    private val mPointerQueue: PointerQueue = PointerQueue()

    private val mHasDistinctMultitouch: Boolean
    private var mOldPointerCount: Int = 1

    @JvmField protected var mKeyDetector: KeyDetector = ProximityKeyDetector()

    // Swipe gesture detector
    private var mGestureDetector: GestureDetector? = null
    private val mSwipeTracker: SwipeTracker = SwipeTracker()
    private val mSwipeThreshold: Int
    private val mDisambiguateSwipe: Boolean

    // Drawing
    /** Whether the keyboard bitmap needs to be redrawn before it's blitted. **/
    private var mDrawPending: Boolean = false
    /** The dirty region in the keyboard bitmap */
    private val mDirtyRect: Rect = Rect()
    /** The keyboard bitmap for faster updates */
    private var mBuffer: Bitmap? = null
    /** Notes if the keyboard just changed, so that we could possibly reallocate the mBuffer. */
    private var mKeyboardChanged: Boolean = false
    private var mInvalidatedKey: Keyboard.Key? = null
    /** The canvas for the above mutable keyboard bitmap */
    private var mCanvas: Canvas? = null
    private val mPaint: Paint
    private val mPaintHint: Paint
    private val mPadding: Rect
    private val mClipRegion: Rect = Rect(0, 0, 0, 0)
    private var mViewWidth: Int = 0
    // This map caches key label text height in pixel as value and key label text size as map key.
    private val mTextHeightCache: HashMap<Int, Int> = HashMap()
    // Distance from horizontal center of the key, proportional to key label text height.
    private val KEY_LABEL_VERTICAL_ADJUSTMENT_FACTOR: Float = 0.55f
    private val KEY_LABEL_HEIGHT_REFERENCE_CHAR: String = "H"

    private val mInvertingColorFilter: ColorMatrixColorFilter = ColorMatrixColorFilter(INVERTING_MATRIX)

    private val mHandler: UIHandler = UIHandler()

    inner class UIHandler : Handler() {
        private var mInKeyRepeat: Boolean = false

        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MSG_POPUP_PREVIEW -> showKey(msg.arg1, msg.obj as PointerTracker)
                MSG_DISMISS_PREVIEW -> mPreviewPopup?.dismiss()
                MSG_REPEAT_KEY -> {
                    val tracker = msg.obj as PointerTracker
                    tracker.repeatKey(msg.arg1)
                    startKeyRepeatTimer(mKeyRepeatInterval.toLong(), msg.arg1, tracker)
                }
                MSG_LONGPRESS_KEY -> {
                    val tracker = msg.obj as PointerTracker
                    openPopupIfRequired(msg.arg1, tracker)
                }
            }
        }

        fun popupPreview(delay: Long, keyIndex: Int, tracker: PointerTracker) {
            removeMessages(MSG_POPUP_PREVIEW)
            if (mPreviewPopup?.isShowing == true && mPreviewText?.visibility == VISIBLE) {
                // Show right away, if it's already visible and finger is moving around
                showKey(keyIndex, tracker)
            } else {
                sendMessageDelayed(obtainMessage(MSG_POPUP_PREVIEW, keyIndex, 0, tracker), delay)
            }
        }

        fun cancelPopupPreview() {
            removeMessages(MSG_POPUP_PREVIEW)
        }

        fun dismissPreview(delay: Long) {
            if (mPreviewPopup?.isShowing == true) {
                sendMessageDelayed(obtainMessage(MSG_DISMISS_PREVIEW), delay)
            }
        }

        fun cancelDismissPreview() {
            removeMessages(MSG_DISMISS_PREVIEW)
        }

        fun startKeyRepeatTimer(delay: Long, keyIndex: Int, tracker: PointerTracker) {
            mInKeyRepeat = true
            sendMessageDelayed(obtainMessage(MSG_REPEAT_KEY, keyIndex, 0, tracker), delay)
        }

        fun cancelKeyRepeatTimer() {
            mInKeyRepeat = false
            removeMessages(MSG_REPEAT_KEY)
        }

        fun isInKeyRepeat(): Boolean = mInKeyRepeat

        fun startLongPressTimer(delay: Long, keyIndex: Int, tracker: PointerTracker) {
            removeMessages(MSG_LONGPRESS_KEY)
            sendMessageDelayed(obtainMessage(MSG_LONGPRESS_KEY, keyIndex, 0, tracker), delay)
        }

        fun cancelLongPressTimer() {
            removeMessages(MSG_LONGPRESS_KEY)
        }

        fun cancelKeyTimers() {
            cancelKeyRepeatTimer()
            cancelLongPressTimer()
        }

        fun cancelAllMessages() {
            cancelKeyTimers()
            cancelPopupPreview()
            cancelDismissPreview()
        }

        companion object {
            private const val MSG_POPUP_PREVIEW = 1
            private const val MSG_DISMISS_PREVIEW = 2
            private const val MSG_REPEAT_KEY = 3
            private const val MSG_LONGPRESS_KEY = 4
        }
    }

    class PointerQueue {
        private val mQueue: MutableList<PointerTracker> = mutableListOf()

        fun add(tracker: PointerTracker) {
            mQueue.add(tracker)
        }

        fun lastIndexOf(tracker: PointerTracker): Int {
            for (index in mQueue.indices.reversed()) {
                if (mQueue[index] === tracker) return index
            }
            return -1
        }

        fun releaseAllPointersOlderThan(tracker: PointerTracker, eventTime: Long) {
            var oldestPos = 0
            while (mQueue.isNotEmpty()) {
                val t = mQueue[oldestPos]
                if (t === tracker) break
                if (t.isModifier) {
                    oldestPos++
                } else {
                    t.onUpEvent(t.lastX, t.lastY, eventTime)
                    t.setAlreadyProcessed()
                    mQueue.removeAt(oldestPos)
                }
                if (mQueue.isEmpty()) return
            }
        }

        fun releaseAllPointersExcept(tracker: PointerTracker?, eventTime: Long) {
            for (t in mQueue) {
                if (t === tracker) continue
                t.onUpEvent(t.lastX, t.lastY, eventTime)
                t.setAlreadyProcessed()
            }
            mQueue.clear()
            if (tracker != null) mQueue.add(tracker)
        }

        fun remove(tracker: PointerTracker) {
            mQueue.remove(tracker)
        }

        fun isInSlidingKeyInput(): Boolean {
            for (tracker in mQueue) {
                if (tracker.isInSlidingKeyInput) return true
            }
            return false
        }
    }

    init {
        if (!isInEditMode) Log.i(TAG, "Creating new LatinKeyboardBaseView $this")
        setRenderModeIfPossible(LatinIME.sKeyboardSettings.renderMode)

        val a = context.obtainStyledAttributes(
            attrs, R.styleable.LatinKeyboardBaseView, defStyle, R.style.LatinKeyboardBaseView
        )

        val n = a.indexCount
        for (i in 0 until n) {
            val attr = a.getIndex(i)
            when (attr) {
                R.styleable.LatinKeyboardBaseView_keyBackground ->
                    mKeyBackground = a.getDrawable(attr)
                R.styleable.LatinKeyboardBaseView_keyHysteresisDistance ->
                    mKeyHysteresisDistance = a.getDimensionPixelOffset(attr, 0).toFloat()
                R.styleable.LatinKeyboardBaseView_verticalCorrection ->
                    mVerticalCorrection = a.getDimensionPixelOffset(attr, 0).toFloat()
                R.styleable.LatinKeyboardBaseView_keyTextSize ->
                    mKeyTextSize = a.getDimensionPixelSize(attr, 18).toFloat()
                R.styleable.LatinKeyboardBaseView_keyTextColor ->
                    mKeyTextColor = a.getColor(attr, 0xFF000000.toInt())
                R.styleable.LatinKeyboardBaseView_keyHintColor ->
                    mKeyHintColor = a.getColor(attr, 0xFFBBBBBB.toInt())
                R.styleable.LatinKeyboardBaseView_keyCursorColor ->
                    mKeyCursorColor = a.getColor(attr, 0xFF000000.toInt())
                R.styleable.LatinKeyboardBaseView_invertSymbols ->
                    mInvertSymbols = a.getBoolean(attr, false)
                R.styleable.LatinKeyboardBaseView_recolorSymbols ->
                    mRecolorSymbols = a.getBoolean(attr, false)
                R.styleable.LatinKeyboardBaseView_labelTextSize ->
                    mLabelTextSize = a.getDimensionPixelSize(attr, 14).toFloat()
                R.styleable.LatinKeyboardBaseView_shadowColor ->
                    mShadowColor = a.getColor(attr, 0)
                R.styleable.LatinKeyboardBaseView_shadowRadius ->
                    mShadowRadius = a.getFloat(attr, 0f)
                R.styleable.LatinKeyboardBaseView_backgroundDimAmount ->
                    mBackgroundDimAmount = a.getFloat(attr, 0.5f)
                R.styleable.LatinKeyboardBaseView_backgroundAlpha ->
                    mBackgroundAlpha = a.getInteger(attr, 255)
                R.styleable.LatinKeyboardBaseView_keyTextStyle -> {
                    val textStyle = a.getInt(attr, 0)
                    mKeyTextStyle = when (textStyle) {
                        0 -> Typeface.DEFAULT
                        1 -> Typeface.DEFAULT_BOLD
                        else -> Typeface.defaultFromStyle(textStyle)
                    }
                }
                R.styleable.LatinKeyboardBaseView_symbolColorScheme ->
                    mSymbolColorScheme = a.getInt(attr, 0)
            }
        }
        a.recycle()

        val res = resources

        mShowPreview = false
        mDelayBeforePreview = res.getInteger(R.integer.config_delay_before_preview)
        mDelayBeforeSpacePreview = res.getInteger(R.integer.config_delay_before_space_preview)
        mDelayAfterPreview = res.getInteger(R.integer.config_delay_after_preview)

        mPopupLayout = 0

        mPaint = Paint().apply {
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
            alpha = 255
        }

        mPaintHint = Paint().apply {
            isAntiAlias = true
            textAlign = Paint.Align.RIGHT
            alpha = 255
            typeface = Typeface.DEFAULT_BOLD
        }

        mPadding = Rect(0, 0, 0, 0)
        mKeyBackground?.getPadding(mPadding)

        mSwipeThreshold = (300 * res.displayMetrics.density).toInt()
        // TODO: Refer frameworks/base/core/res/res/values/config.xml
        // TODO(klausw): turn off mDisambiguateSwipe if no swipe actions are set?
        mDisambiguateSwipe = res.getBoolean(R.bool.config_swipeDisambiguation)
        mMiniKeyboardSlideAllowance = res.getDimension(R.dimen.mini_keyboard_slide_allowance)

        val listener = object : GestureDetector.SimpleOnGestureListener() {
            override fun onFling(
                me1: MotionEvent?,
                me2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                if (me1 == null) return false
                val absX = Math.abs(velocityX)
                val absY = Math.abs(velocityY)
                val deltaX = me2.x - me1.x
                val deltaY = me2.y - me1.y
                mSwipeTracker.computeCurrentVelocity(1000)
                val endingVelocityX = mSwipeTracker.xVelocity
                val endingVelocityY = mSwipeTracker.yVelocity
                // Calculate swipe distance threshold based on screen width & height,
                // taking the smaller distance.
                val travelX = width / 3
                val travelY = height / 3
                val travelMin = Math.min(travelX, travelY)

                if (velocityX > mSwipeThreshold && absY < absX && deltaX > travelMin) {
                    if (mDisambiguateSwipe && endingVelocityX >= velocityX / 4) {
                        if (swipeRight()) return true
                    }
                } else if (velocityX < -mSwipeThreshold && absY < absX && deltaX < -travelMin) {
                    if (mDisambiguateSwipe && endingVelocityX <= velocityX / 4) {
                        if (swipeLeft()) return true
                    }
                } else if (velocityY < -mSwipeThreshold && absX < absY && deltaY < -travelMin) {
                    if (mDisambiguateSwipe && endingVelocityY <= velocityY / 4) {
                        if (swipeUp()) return true
                    }
                } else if (velocityY > mSwipeThreshold && absX < absY / 2 && deltaY > travelMin) {
                    if (mDisambiguateSwipe && endingVelocityY >= velocityY / 4) {
                        if (swipeDown()) return true
                    }
                }
                return false
            }
        }

        val ignoreMultitouch = true
        mGestureDetector = GestureDetector(getContext(), listener, null, ignoreMultitouch)
        mGestureDetector?.setIsLongpressEnabled(false)

        mHasDistinctMultitouch = context.packageManager
            .hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_DISTINCT)
        mKeyRepeatInterval = res.getInteger(R.integer.config_key_repeat_interval)
    }

    private fun showHints7Bit(): Boolean = LatinIME.sKeyboardSettings.hintMode >= 1

    private fun showHintsAll(): Boolean = LatinIME.sKeyboardSettings.hintMode >= 2

    fun setOnKeyboardActionListener(listener: OnKeyboardActionListener?) {
        mKeyboardActionListener = listener
        for (tracker in mPointerTrackers) {
            tracker.setOnKeyboardActionListener(listener)
        }
    }

    /**
     * Returns the [OnKeyboardActionListener] object.
     * @return the listener attached to this keyboard
     */
    protected fun getOnKeyboardActionListener(): OnKeyboardActionListener? = mKeyboardActionListener

    /**
     * Attaches a keyboard to this view. The keyboard can be switched at any time and the
     * view will re-layout itself to accommodate the keyboard.
     * @see Keyboard
     * @see getKeyboard
     * @param keyboard the keyboard to display in this view
     */
    fun setKeyboard(keyboard: Keyboard?) {
        if (mKeyboard != null) {
            dismissKeyPreview()
        }
        // Remove any pending messages, except dismissing preview
        mHandler.cancelKeyTimers()
        mHandler.cancelPopupPreview()
        mKeyboard = keyboard
        // Disable correctionX and correctionY, it doesn't seem to work as intended.
        mKeys = mKeyDetector.setKeyboard(keyboard, 0, 0)
        mKeyboardVerticalGap = resources.getDimension(R.dimen.key_bottom_gap).toInt()
        for (tracker in mPointerTrackers) {
            tracker.setKeyboard(mKeys, mKeyHysteresisDistance)
        }
        mLabelScale = LatinIME.sKeyboardSettings.labelScalePref
        requestLayout()
        // Hint to reallocate the buffer if the size changed
        mKeyboardChanged = true
        invalidateAllKeys()
        keyboard?.let { computeProximityThreshold(it) }
        mMiniKeyboardCacheMain.clear()
        mMiniKeyboardCacheShift.clear()
        mMiniKeyboardCacheCaps.clear()
        setRenderModeIfPossible(LatinIME.sKeyboardSettings.renderMode)
        mIgnoreMove = true
    }

    /**
     * Returns the current keyboard being displayed by this view.
     * @return the currently attached keyboard
     * @see setKeyboard
     */
    fun getKeyboard(): Keyboard? = mKeyboard

    /**
     * Return whether the device has distinct multi-touch panel.
     * @return true if the device has distinct multi-touch panel.
     */
    fun hasDistinctMultitouch(): Boolean = mHasDistinctMultitouch

    /**
     * Sets the state of the shift key of the keyboard, if any.
     * @param shiftState whether or not to enable the state of the shift key
     * @return true if the shift key state changed, false if there was no change
     */
    fun setShiftState(shiftState: Int): Boolean {
        if (mKeyboard != null) {
            if (mKeyboard!!.setShiftState(shiftState)) {
                // The whole keyboard probably needs to be redrawn
                invalidateAllKeys()
                return true
            }
        }
        return false
    }

    fun setCtrlIndicator(active: Boolean) {
        mKeyboard?.let { invalidateKey(it.setCtrlIndicator(active)) }
    }

    fun setAltIndicator(active: Boolean) {
        mKeyboard?.let { invalidateKey(it.setAltIndicator(active)) }
    }

    fun setMetaIndicator(active: Boolean) {
        mKeyboard?.let { invalidateKey(it.setMetaIndicator(active)) }
    }

    /**
     * Returns the state of the shift key of the keyboard, if any.
     * @return true if the shift is in a pressed state, false otherwise. If there is
     * no shift key on the keyboard or there is no keyboard attached, it returns false.
     */
    fun getShiftState(): Int = mKeyboard?.getShiftState() ?: Keyboard.SHIFT_OFF

    fun isShiftCaps(): Boolean = getShiftState() != Keyboard.SHIFT_OFF

    fun isShiftAll(): Boolean {
        val state = getShiftState()
        return if (LatinIME.sKeyboardSettings.shiftLockModifiers) {
            state == Keyboard.SHIFT_ON || state == Keyboard.SHIFT_LOCKED
        } else {
            state == Keyboard.SHIFT_ON
        }
    }

    /**
     * Enables or disables the key feedback popup. This is a popup that shows a magnified
     * version of the depressed key. By default the preview is enabled.
     * @param previewEnabled whether or not to enable the key feedback popup
     * @see isPreviewEnabled
     */
    fun setPreviewEnabled(previewEnabled: Boolean) {
        mShowPreview = previewEnabled
    }

    /**
     * Returns the enabled state of the key feedback popup.
     * @return whether or not the key feedback popup is enabled
     * @see setPreviewEnabled
     */
    fun isPreviewEnabled(): Boolean = mShowPreview

    private fun isBlackSym(): Boolean = mSymbolColorScheme == 1

    fun setPopupParent(v: View?) {
        mMiniKeyboardParent = v
    }

    fun setPopupOffset(x: Int, y: Int) {
        mPopupPreviewOffsetX = x
        mPopupPreviewOffsetY = y
        mPreviewPopup?.dismiss()
    }

    /**
     * When enabled, calls to [OnKeyboardActionListener.onKey] will include key
     * codes for adjacent keys.  When disabled, only the primary key code will be
     * reported.
     * @param enabled whether or not the proximity correction is enabled
     */
    fun setProximityCorrectionEnabled(enabled: Boolean) {
        mKeyDetector.setProximityCorrectionEnabled(enabled)
    }

    /**
     * Returns true if proximity correction is enabled.
     */
    fun isProximityCorrectionEnabled(): Boolean = mKeyDetector.isProximityCorrectionEnabled

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // Round up a little
        if (mKeyboard == null) {
            setMeasuredDimension(paddingLeft + paddingRight, paddingTop + paddingBottom)
        } else {
            var width = MeasureSpec.getSize(widthMeasureSpec)
            Log.i(TAG, "onMeasure width=$width")

            // workaround for those small "popup" keyboards. There
            // should be a proper fix specifying the correct width in
            // the layout (wrap_content rather than match_parent), but
            // in which layout is this set?
            val oldWidth = mKeyboard!!.minWidth
            if (width > oldWidth) {
                Log.i(TAG, "Reducing width from $width to $oldWidth")
                width = oldWidth
            }

            setMeasuredDimension(width, mKeyboard!!.height + paddingTop + paddingBottom)
        }
    }

    /**
     * Compute the average distance between adjacent keys (horizontally and vertically)
     * and square it to get the proximity threshold. We use a square here and in computing
     * the touch distance from a key's center to avoid taking a square root.
     * @param keyboard
     */
    private fun computeProximityThreshold(keyboard: Keyboard) {
        val keys = mKeys ?: return
        val length = keys.size
        var dimensionSum = 0
        for (i in 0 until length) {
            val key = keys[i]
            dimensionSum += Math.min(key.width, key.height + mKeyboardVerticalGap) + key.gap
        }
        if (dimensionSum < 0 || length == 0) return
        mKeyDetector.setProximityThreshold((dimensionSum * 1.4f / length).toInt())
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        Log.i(TAG, "onSizeChanged, w=$w, h=$h")
        mViewWidth = w
        // Release the buffer, if any and it will be reallocated on the next draw
        mBuffer = null
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mCanvas = canvas
        if (mDrawPending || mBuffer == null || mKeyboardChanged) {
            onBufferDraw(canvas)
        }
        mBuffer?.let { canvas.drawBitmap(it, 0f, 0f, null) }
    }

    private fun drawDeadKeyLabel(canvas: Canvas, hint: String, x: Int, baseline: Float, paint: Paint) {
        val c = hint[0]
        val accent = DeadAccentSequence.getSpacing(c)
        canvas.drawText(Keyboard.DEAD_KEY_PLACEHOLDER_STRING, x.toFloat(), baseline, paint)
        canvas.drawText(accent, x.toFloat(), baseline, paint)
    }

    private fun getLabelHeight(paint: Paint, labelSize: Int): Int {
        val labelHeightValue = mTextHeightCache[labelSize]
        return if (labelHeightValue != null) {
            labelHeightValue
        } else {
            val textBounds = Rect()
            paint.getTextBounds(KEY_LABEL_HEIGHT_REFERENCE_CHAR, 0, 1, textBounds)
            val labelHeight = textBounds.height()
            mTextHeightCache[labelSize] = labelHeight
            labelHeight
        }
    }

    private fun onBufferDraw(canvas: Canvas) {
        if (mKeyboardChanged) {
            mKeyboard?.setKeyboardWidth(mViewWidth)
            invalidateAllKeys()
            mKeyboardChanged = false
        }
        canvas.getClipBounds(mDirtyRect)

        if (mKeyboard == null) return

        val paint = mPaint
        val paintHint = mPaintHint
        paintHint.color = mKeyHintColor
        val keyBackground = mKeyBackground ?: return
        val clipRegion = mClipRegion
        val padding = mPadding
        val kbdPaddingLeft = paddingLeft
        val kbdPaddingTop = paddingTop
        val keys = mKeys ?: return
        val invalidKey = mInvalidatedKey

        var iconColorFilter: android.graphics.ColorFilter? = null
        var shadowColorFilter: android.graphics.ColorFilter? = null
        if (mInvertSymbols) {
            iconColorFilter = mInvertingColorFilter
        } else if (mRecolorSymbols) {
            iconColorFilter = PorterDuffColorFilter(mKeyTextColor, PorterDuff.Mode.SRC_ATOP)
            shadowColorFilter = PorterDuffColorFilter(mShadowColor, PorterDuff.Mode.SRC_ATOP)
        }

        var drawSingleKey = false
        if (invalidKey != null && canvas.getClipBounds(clipRegion)) {
            // Is clipRegion completely contained within the invalidated key?
            if (invalidKey.x + kbdPaddingLeft - 1 <= clipRegion.left &&
                invalidKey.y + kbdPaddingTop - 1 <= clipRegion.top &&
                invalidKey.x + invalidKey.width + kbdPaddingLeft + 1 >= clipRegion.right &&
                invalidKey.y + invalidKey.height + kbdPaddingTop + 1 >= clipRegion.bottom
            ) {
                drawSingleKey = true
            }
        }

        val keyCount = keys.size

        // Scale the key labels based on the median key size.
        val keyWidths = mutableListOf<Int>()
        val keyHeights = mutableListOf<Int>()
        for (i in 0 until keyCount) {
            val key = keys[i]
            keyWidths.add(key.width)
            keyHeights.add(key.height)
        }
        keyWidths.sort()
        keyHeights.sort()
        val medianKeyWidth = keyWidths[keyCount / 2]
        val medianKeyHeight = keyHeights[keyCount / 2]
        // Use 60% of the smaller of width or height. This is kind of arbitrary.
        mKeyTextSize = Math.min(medianKeyHeight * 6 / 10, medianKeyWidth * 6 / 10).toFloat()
        mLabelTextSize = mKeyTextSize * 3 / 4

        for (i in 0 until keyCount) {
            val key = keys[i]
            if (drawSingleKey && invalidKey !== key) {
                continue
            }
            if (!mDirtyRect.intersects(
                    key.x + kbdPaddingLeft,
                    key.y + kbdPaddingTop,
                    key.x + key.width + kbdPaddingLeft,
                    key.y + key.height + kbdPaddingTop
                )
            ) {
                continue
            }
            paint.color = if (key.isCursor) mKeyCursorColor else mKeyTextColor

            val drawableState = key.currentDrawableState
            keyBackground.state = drawableState

            // Switch the character to uppercase if shift is pressed
            val label = key.caseLabel

            var yscale = 1.0f
            val bounds = keyBackground.bounds
            if (key.width != bounds.right || key.height != bounds.bottom) {
                val minHeight = keyBackground.minimumHeight
                if (minHeight > key.height) {
                    yscale = key.height.toFloat() / minHeight
                    keyBackground.setBounds(0, 0, key.width, minHeight)
                } else {
                    keyBackground.setBounds(0, 0, key.width, key.height)
                }
            }
            canvas.translate((key.x + kbdPaddingLeft).toFloat(), (key.y + kbdPaddingTop).toFloat())
            if (yscale != 1.0f) {
                canvas.save()
                canvas.scale(1.0f, yscale)
            }
            if (mBackgroundAlpha != 255) {
                keyBackground.alpha = mBackgroundAlpha
            }
            keyBackground.draw(canvas)
            if (yscale != 1.0f) canvas.restore()

            var shouldDrawIcon = true
            if (label != null) {
                // For characters, use large font. For labels like "Done", use small font.
                val labelSize: Int
                if (label.length > 1 && key.codes.size < 2) {
                    labelSize = (mLabelTextSize * mLabelScale).toInt()
                    paint.typeface = Typeface.DEFAULT
                } else {
                    labelSize = (mKeyTextSize * mLabelScale).toInt()
                    paint.typeface = mKeyTextStyle
                }
                paint.isFakeBoldText = key.isCursor
                paint.textSize = labelSize.toFloat()

                val labelHeight = getLabelHeight(paint, labelSize)

                // Draw a drop shadow for the text
                paint.setShadowLayer(mShadowRadius, 0f, 0f, mShadowColor)

                // Draw hint label (if present) behind the main key
                val hint = key.getHintLabel(showHints7Bit(), showHintsAll())
                if (hint.isNotEmpty() && !(key.isShifted && key.shiftLabel != null && hint[0] == key.shiftLabel!![0])) {
                    val hintTextSize = (mKeyTextSize * 0.6 * mLabelScale).toInt()
                    paintHint.textSize = hintTextSize.toFloat()

                    val hintLabelHeight = getLabelHeight(paintHint, hintTextSize)
                    val x = key.width - padding.right
                    val baseline = (padding.top + hintLabelHeight * 12 / 10).toFloat()
                    if (Character.getType(hint[0]).toByte().toInt() == Character.NON_SPACING_MARK.toInt()) {
                        drawDeadKeyLabel(canvas, hint, x, baseline, paintHint)
                    } else {
                        canvas.drawText(hint, x.toFloat(), baseline, paintHint)
                    }
                }

                // Draw alternate hint label (if present) behind the main key
                val altHint = key.getAltHintLabel(showHints7Bit(), showHintsAll())
                if (altHint.isNotEmpty()) {
                    val hintTextSize = (mKeyTextSize * 0.6 * mLabelScale).toInt()
                    paintHint.textSize = hintTextSize.toFloat()

                    val hintLabelHeight = getLabelHeight(paintHint, hintTextSize)
                    val x = key.width - padding.right
                    val baseline = (padding.top + hintLabelHeight * (if (hint.isEmpty()) 12 else 26) / 10).toFloat()
                    if (Character.getType(altHint[0]).toByte().toInt() == Character.NON_SPACING_MARK.toInt()) {
                        drawDeadKeyLabel(canvas, altHint, x, baseline, paintHint)
                    } else {
                        canvas.drawText(altHint, x.toFloat(), baseline, paintHint)
                    }
                }

                // Draw main key label
                val centerX = (key.width + padding.left - padding.right) / 2
                val centerY = (key.height + padding.top - padding.bottom) / 2
                val baseline = centerY + labelHeight * KEY_LABEL_VERTICAL_ADJUSTMENT_FACTOR
                if (key.isDeadKey) {
                    drawDeadKeyLabel(canvas, label, centerX, baseline, paint)
                } else {
                    canvas.drawText(label, centerX.toFloat(), baseline, paint)
                }
                if (key.isCursor) {
                    // poor man's bold - FIXME
                    // Turn off drop shadow
                    paint.setShadowLayer(0f, 0f, 0f, 0)

                    canvas.drawText(label, centerX + 0.5f, baseline, paint)
                    canvas.drawText(label, centerX - 0.5f, baseline, paint)
                    canvas.drawText(label, centerX.toFloat(), baseline + 0.5f, paint)
                    canvas.drawText(label, centerX.toFloat(), baseline - 0.5f, paint)
                }

                // Turn off drop shadow
                paint.setShadowLayer(0f, 0f, 0f, 0)

                // Usually don't draw icon if label is not null, but we draw icon for the number
                // hint and popup hint.
                shouldDrawIcon = shouldDrawLabelAndIcon(key)
            }
            val icon = key.icon
            if (icon != null && shouldDrawIcon) {
                // Special handing for the upper-right number hint icons
                val drawableWidth: Int
                val drawableHeight: Int
                val drawableX: Int
                val drawableY: Int
                if (shouldDrawIconFully(key)) {
                    drawableWidth = key.width
                    drawableHeight = key.height
                    drawableX = 0
                    drawableY = NUMBER_HINT_VERTICAL_ADJUSTMENT_PIXEL
                } else {
                    drawableWidth = icon.intrinsicWidth
                    drawableHeight = icon.intrinsicHeight
                    drawableX = (key.width + padding.left - padding.right - drawableWidth) / 2
                    drawableY = (key.height + padding.top - padding.bottom - drawableHeight) / 2
                }
                canvas.translate(drawableX.toFloat(), drawableY.toFloat())
                icon.setBounds(0, 0, drawableWidth, drawableHeight)

                if (iconColorFilter != null) {
                    // Re-color the icon to match the theme, and draw a shadow for it manually.
                    if (shadowColorFilter != null && mShadowRadius > 0) {
                        val shadowBlur = BlurMaskFilter(mShadowRadius, BlurMaskFilter.Blur.OUTER)
                        val blurPaint = Paint()
                        blurPaint.maskFilter = shadowBlur
                        val tmpIcon = Bitmap.createBitmap(key.width, key.height, Bitmap.Config.ARGB_8888)
                        val tmpCanvas = Canvas(tmpIcon)
                        icon.draw(tmpCanvas)
                        val offsets = IntArray(2)
                        val shadowBitmap = tmpIcon.extractAlpha(blurPaint, offsets)
                        val shadowPaint = Paint()
                        shadowPaint.colorFilter = shadowColorFilter
                        canvas.drawBitmap(shadowBitmap, offsets[0].toFloat(), offsets[1].toFloat(), shadowPaint)
                    }
                    icon.colorFilter = iconColorFilter
                    icon.draw(canvas)
                    icon.colorFilter = null
                } else {
                    icon.draw(canvas)
                }
                canvas.translate(-drawableX.toFloat(), -drawableY.toFloat())
            }
            canvas.translate((-key.x - kbdPaddingLeft).toFloat(), (-key.y - kbdPaddingTop).toFloat())
        }
        mInvalidatedKey = null
        // Overlay a dark rectangle to dim the keyboard
        if (mMiniKeyboardVisible) {
            paint.color = ((mBackgroundDimAmount * 0xFF).toInt()) shl 24
            canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
        }

        if (LatinIME.sKeyboardSettings.showTouchPos || DEBUG) {
            if (LatinIME.sKeyboardSettings.showTouchPos || mShowTouchPoints) {
                for (tracker in mPointerTrackers) {
                    val startX = tracker.startX
                    val startY = tracker.startY
                    val lastX = tracker.lastX
                    val lastY = tracker.lastY
                    paint.alpha = 128
                    paint.color = 0xFFFF0000.toInt()
                    canvas.drawCircle(startX.toFloat(), startY.toFloat(), 3f, paint)
                    canvas.drawLine(startX.toFloat(), startY.toFloat(), lastX.toFloat(), lastY.toFloat(), paint)
                    paint.color = 0xFF0000FF.toInt()
                    canvas.drawCircle(lastX.toFloat(), lastY.toFloat(), 3f, paint)
                    paint.color = 0xFF00FF00.toInt()
                    canvas.drawCircle(((startX + lastX) / 2).toFloat(), ((startY + lastY) / 2).toFloat(), 2f, paint)
                }
            }
        }

        mDrawPending = false
        mDirtyRect.setEmpty()
    }

    // TODO: clean up this method.
    private fun dismissKeyPreview() {
        for (tracker in mPointerTrackers) {
            tracker.updateKey(NOT_A_KEY)
        }
        showPreview(NOT_A_KEY, null)
    }

    fun showPreview(keyIndex: Int, tracker: PointerTracker?) {
        val oldKeyIndex = mOldPreviewKeyIndex
        mOldPreviewKeyIndex = keyIndex
        val isLanguageSwitchEnabled = (mKeyboard is LatinKeyboard) &&
            (mKeyboard as LatinKeyboard).isLanguageSwitchEnabled
        // We should re-draw popup preview when 1) we need to hide the preview, 2) we will show
        // the space key preview and 3) pointer moves off the space key to other letter key, we
        // should hide the preview of the previous key.
        val hidePreviewOrShowSpaceKeyPreview = (tracker == null) ||
            tracker.isSpaceKey(keyIndex) || tracker.isSpaceKey(oldKeyIndex)
        // If key changed and preview is on or the key is space (language switch is enabled)
        if (oldKeyIndex != keyIndex &&
            (mShowPreview || (hidePreviewOrShowSpaceKeyPreview && isLanguageSwitchEnabled))
        ) {
            if (keyIndex == NOT_A_KEY) {
                mHandler.cancelPopupPreview()
                mHandler.dismissPreview(mDelayAfterPreview.toLong())
            } else if (tracker != null) {
                val delay = if (mShowPreview) mDelayBeforePreview else mDelayBeforeSpacePreview
                mHandler.popupPreview(delay.toLong(), keyIndex, tracker)
            }
        }
    }

    private fun showKey(keyIndex: Int, tracker: PointerTracker) {
        val key = tracker.getKey(keyIndex) ?: return
        // Should not draw hint icon in key preview
        val icon = key.icon
        if (icon != null && !shouldDrawLabelAndIcon(key)) {
            mPreviewText?.setCompoundDrawables(
                null, null, null,
                key.iconPreview ?: icon
            )
            mPreviewText?.text = null
        } else {
            mPreviewText?.setCompoundDrawables(null, null, null, null)
            mPreviewText?.text = key.caseLabel
            if (key.label != null && key.label!!.length > 1 && key.codes.size < 2) {
                mPreviewText?.setTextSize(TypedValue.COMPLEX_UNIT_PX, mKeyTextSize)
                mPreviewText?.typeface = Typeface.DEFAULT_BOLD
            } else {
                mPreviewText?.setTextSize(TypedValue.COMPLEX_UNIT_PX, mPreviewTextSizeLarge.toFloat())
                mPreviewText?.typeface = mKeyTextStyle
            }
        }
        mPreviewText?.measure(
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        )
        val popupWidth = Math.max(
            mPreviewText?.measuredWidth ?: 0,
            key.width + (mPreviewText?.paddingLeft ?: 0) + (mPreviewText?.paddingRight ?: 0)
        )
        val popupHeight = mPreviewHeight
        val lp = mPreviewText?.layoutParams
        if (lp != null) {
            lp.width = popupWidth
            lp.height = popupHeight
        }

        var popupPreviewX = key.x - (popupWidth - key.width) / 2
        var popupPreviewY = key.y - popupHeight + mPreviewOffset

        mHandler.cancelDismissPreview()
        if (mOffsetInWindow == null) {
            mOffsetInWindow = IntArray(2)
            getLocationInWindow(mOffsetInWindow)
            mOffsetInWindow!![0] += mPopupPreviewOffsetX
            mOffsetInWindow!![1] += mPopupPreviewOffsetY
            val windowLocation = IntArray(2)
            getLocationOnScreen(windowLocation)
            mWindowY = windowLocation[1]
        }
        // Set the preview background state.
        // Retrieve and cache the popup keyboard if any.
        val hasPopup = (getLongPressKeyboard(key) != null)
        // Set background manually, the StateListDrawable doesn't work.
        mPreviewText?.setBackgroundDrawable(
            resources.getDrawable(
                if (hasPopup) R.drawable.keyboard_key_feedback_more_background
                else R.drawable.keyboard_key_feedback_background
            )
        )
        popupPreviewX += mOffsetInWindow!![0]
        popupPreviewY += mOffsetInWindow!![1]

        // If the popup cannot be shown above the key, put it on the side
        if (popupPreviewY + mWindowY < 0) {
            // If the key you're pressing is on the left side of the keyboard, show the popup on
            // the right, offset by enough to see at least one key to the left/right.
            if (key.x + key.width <= width / 2) {
                popupPreviewX += (key.width * 2.5).toInt()
            } else {
                popupPreviewX -= (key.width * 2.5).toInt()
            }
            popupPreviewY += popupHeight
        }

        if (mPreviewPopup?.isShowing == true) {
            mPreviewPopup?.update(popupPreviewX, popupPreviewY, popupWidth, popupHeight)
        } else {
            mPreviewPopup?.width = popupWidth
            mPreviewPopup?.height = popupHeight
            mPreviewPopup?.showAtLocation(
                mMiniKeyboardParent, Gravity.NO_GRAVITY,
                popupPreviewX, popupPreviewY
            )
        }
        // Record popup preview position to display mini-keyboard later at the same position
        mPopupPreviewDisplayedY = popupPreviewY
        mPreviewText?.visibility = VISIBLE
    }

    /**
     * Requests a redraw of the entire keyboard. Calling [invalidate] is not sufficient
     * because the keyboard renders the keys to an off-screen buffer and an invalidate() only
     * draws the cached buffer.
     * @see invalidateKey
     */
    fun invalidateAllKeys() {
        mDirtyRect.union(0, 0, width, height)
        mDrawPending = true
        invalidate()
    }

    /**
     * Invalidates a key so that it will be redrawn on the next repaint. Use this method if only
     * one key is changing it's content. Any changes that affect the position or size of the key
     * may not be honored.
     * @param key key in the attached [Keyboard].
     * @see invalidateAllKeys
     */
    fun invalidateKey(key: Keyboard.Key?) {
        if (key == null) return
        mInvalidatedKey = key
        mDirtyRect.union(
            key.x + paddingLeft, key.y + paddingTop,
            key.x + key.width + paddingLeft, key.y + key.height + paddingTop
        )
        invalidate(
            key.x + paddingLeft, key.y + paddingTop,
            key.x + key.width + paddingLeft, key.y + key.height + paddingTop
        )
    }

    private fun openPopupIfRequired(keyIndex: Int, tracker: PointerTracker): Boolean {
        // Check if we have a popup layout specified first.
        if (mPopupLayout == 0) {
            return false
        }

        val popupKey = tracker.getKey(keyIndex) ?: return false
        if (tracker.isInSlidingKeyInput) return false
        val result = onLongPress(popupKey)
        if (result) {
            dismissKeyPreview()
            mMiniKeyboardTrackerId = tracker.mPointerId
            // Mark this tracker "already processed" and remove it from the pointer queue
            tracker.setAlreadyProcessed()
            mPointerQueue.remove(tracker)
        }
        return result
    }

    private fun inflateMiniKeyboardContainer() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val container = inflater.inflate(mPopupLayout, null)

        mMiniKeyboard = container.findViewById<View>(R.id.LatinKeyboardBaseView) as LatinKeyboardBaseView
        mMiniKeyboard?.setOnKeyboardActionListener(object : OnKeyboardActionListener {
            override fun onKey(primaryCode: Int, keyCodes: IntArray, x: Int, y: Int) {
                mKeyboardActionListener?.onKey(primaryCode, keyCodes, x, y)
                dismissPopupKeyboard()
            }

            override fun onText(text: CharSequence) {
                mKeyboardActionListener?.onText(text)
                dismissPopupKeyboard()
            }

            override fun onCancel() {
                mKeyboardActionListener?.onCancel()
                dismissPopupKeyboard()
            }

            override fun swipeLeft(): Boolean = false
            override fun swipeRight(): Boolean = false
            override fun swipeUp(): Boolean = false
            override fun swipeDown(): Boolean = false
            override fun onPress(primaryCode: Int) {
                mKeyboardActionListener?.onPress(primaryCode)
            }
            override fun onRelease(primaryCode: Int) {
                mKeyboardActionListener?.onRelease(primaryCode)
            }
        })
        // Override default ProximityKeyDetector.
        mMiniKeyboard?.mKeyDetector = MiniKeyboardKeyDetector(mMiniKeyboardSlideAllowance)
        // Remove gesture detector on mini-keyboard
        mMiniKeyboard?.mGestureDetector = null

        mMiniKeyboard?.setPopupParent(this)

        mMiniKeyboardContainer = container
    }

    private fun getLongPressKeyboard(popupKey: Keyboard.Key): Keyboard? {
        val cache: WeakHashMap<Keyboard.Key, Keyboard> = when {
            popupKey.isDistinctCaps -> mMiniKeyboardCacheCaps
            popupKey.isShifted -> mMiniKeyboardCacheShift
            else -> mMiniKeyboardCacheMain
        }
        var kbd = cache[popupKey]
        if (kbd == null) {
            kbd = popupKey.getPopupKeyboard(context, paddingLeft + paddingRight)
            if (kbd != null) cache[popupKey] = kbd
        }
        return kbd
    }

    /**
     * Called when a key is long pressed. By default this will open any popup keyboard associated
     * with this key through the attributes popupLayout and popupCharacters.
     * @param popupKey the key that was long pressed
     * @return true if the long press is handled, false otherwise. Subclasses should call the
     * method on the base class if the subclass doesn't wish to handle the call.
     */
    protected open fun onLongPress(popupKey: Keyboard.Key): Boolean {
        if (mPopupLayout == 0) return false // No popups wanted

        val kbd = getLongPressKeyboard(popupKey) ?: return false

        if (mMiniKeyboardContainer == null) {
            inflateMiniKeyboardContainer()
        }
        if (mMiniKeyboard == null) return false
        mMiniKeyboard?.setKeyboard(kbd)
        mMiniKeyboardContainer?.measure(
            MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST),
            MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST)
        )

        if (mWindowOffset == null) {
            mWindowOffset = IntArray(2)
            getLocationInWindow(mWindowOffset)
        }

        // Get width of a key in the mini popup keyboard = "miniKeyWidth".
        // On the other hand, "popupKey.width" is width of the pressed key on the main keyboard.
        val miniKeys = mMiniKeyboard?.getKeyboard()?.keys ?: emptyList()
        val miniKeyWidth = if (miniKeys.isNotEmpty()) miniKeys[0].width else 0

        var popupX = popupKey.x + mWindowOffset!![0]
        popupX += paddingLeft
        if (shouldAlignLeftmost(popupKey)) {
            popupX += popupKey.width - miniKeyWidth  // adjustment for a) described above
            popupX -= mMiniKeyboardContainer!!.paddingLeft
        } else {
            popupX += miniKeyWidth  // adjustment for b) described above
            popupX -= mMiniKeyboardContainer!!.measuredWidth
            popupX += mMiniKeyboardContainer!!.paddingRight
        }
        var popupY = popupKey.y + mWindowOffset!![1]
        popupY += paddingTop
        popupY -= mMiniKeyboardContainer!!.measuredHeight
        popupY += mMiniKeyboardContainer!!.paddingBottom
        val x = popupX
        val y = if (mShowPreview && isOneRowKeys(miniKeys)) mPopupPreviewDisplayedY else popupY

        var adjustedX = x
        if (x < 0) {
            adjustedX = 0
        } else if (x > (measuredWidth - mMiniKeyboardContainer!!.measuredWidth)) {
            adjustedX = measuredWidth - mMiniKeyboardContainer!!.measuredWidth
        }
        mMiniKeyboardOriginX = adjustedX + mMiniKeyboardContainer!!.paddingLeft - mWindowOffset!![0]
        mMiniKeyboardOriginY = y + mMiniKeyboardContainer!!.paddingTop - mWindowOffset!![1]
        mMiniKeyboard?.setPopupOffset(adjustedX, y)
        mMiniKeyboard?.setShiftState(getShiftState())
        // Mini keyboard needs no pop-up key preview displayed.
        mMiniKeyboard?.setPreviewEnabled(false)
        mMiniKeyboardPopup?.contentView = mMiniKeyboardContainer
        mMiniKeyboardPopup?.width = mMiniKeyboardContainer!!.measuredWidth
        mMiniKeyboardPopup?.height = mMiniKeyboardContainer!!.measuredHeight
        mMiniKeyboardPopup?.showAtLocation(this, Gravity.NO_GRAVITY, adjustedX, y)
        mMiniKeyboardVisible = true

        // Inject down event on the key to mini keyboard.
        val eventTime = SystemClock.uptimeMillis()
        mMiniKeyboardPopupTime = eventTime
        val downEvent = generateMiniKeyboardMotionEvent(
            MotionEvent.ACTION_DOWN,
            popupKey.x + popupKey.width / 2,
            popupKey.y + popupKey.height / 2,
            eventTime
        )
        mMiniKeyboard?.onTouchEvent(downEvent)
        downEvent.recycle()

        invalidateAllKeys()
        return true
    }

    private fun shouldDrawIconFully(key: Keyboard.Key): Boolean {
        return isNumberAtEdgeOfPopupChars(key) || isLatinF1Key(key) ||
            LatinKeyboard.hasPuncOrSmileysPopup(key)
    }

    private fun shouldDrawLabelAndIcon(key: Keyboard.Key): Boolean {
        return isNonMicLatinF1Key(key) || LatinKeyboard.hasPuncOrSmileysPopup(key)
    }

    private fun shouldAlignLeftmost(key: Keyboard.Key): Boolean = !key.popupReversed

    private fun isLatinF1Key(key: Keyboard.Key): Boolean =
        (mKeyboard is LatinKeyboard) && (mKeyboard as LatinKeyboard).isF1Key(key)

    private fun isNonMicLatinF1Key(key: Keyboard.Key): Boolean =
        isLatinF1Key(key) && key.label != null

    private fun generateMiniKeyboardMotionEvent(action: Int, x: Int, y: Int, eventTime: Long): MotionEvent {
        return MotionEvent.obtain(
            mMiniKeyboardPopupTime, eventTime, action,
            (x - mMiniKeyboardOriginX).toFloat(), (y - mMiniKeyboardOriginY).toFloat(), 0
        )
    }

    /*package*/ open fun enableSlideKeyHack(): Boolean = false

    private fun getPointerTracker(id: Int): PointerTracker {
        val pointers = mPointerTrackers
        val keys = mKeys
        val listener = mKeyboardActionListener

        // Create pointer trackers until we can get 'id+1'-th tracker, if needed.
        for (i in pointers.size..id) {
            val tracker = PointerTracker(i, mHandler, mKeyDetector, this, resources, enableSlideKeyHack())
            if (keys != null) tracker.setKeyboard(keys, mKeyHysteresisDistance)
            if (listener != null) tracker.setOnKeyboardActionListener(listener)
            pointers.add(tracker)
        }

        return pointers[id]
    }

    fun isInSlidingKeyInput(): Boolean {
        return if (mMiniKeyboardVisible) {
            mMiniKeyboard?.isInSlidingKeyInput() ?: false
        } else {
            mPointerQueue.isInSlidingKeyInput()
        }
    }

    fun getPointerCount(): Int = mOldPointerCount

    override fun onTouchEvent(me: MotionEvent): Boolean = onTouchEvent(me, false)

    fun onTouchEvent(me: MotionEvent, continuing: Boolean): Boolean {
        val action = me.actionMasked
        val pointerCount = me.pointerCount
        val oldPointerCount = mOldPointerCount
        mOldPointerCount = pointerCount

        // TODO: cleanup this code into a multi-touch to single-touch event converter class?
        // If the device does not have distinct multi-touch support panel, ignore all multi-touch
        // events except a transition from/to single-touch.
        if (!mHasDistinctMultitouch && pointerCount > 1 && oldPointerCount > 1) {
            return true
        }

        // Track the last few movements to look for spurious swipes.
        mSwipeTracker.addMovement(me)

        // Gesture detector must be enabled only when mini-keyboard is not on the screen.
        if (!mMiniKeyboardVisible && mGestureDetector != null && mGestureDetector!!.onTouchEvent(me)) {
            dismissKeyPreview()
            mHandler.cancelKeyTimers()
            return true
        }

        val eventTime = me.eventTime
        val index = me.actionIndex
        val id = me.getPointerId(index)
        val x = me.getX(index).toInt()
        val y = me.getY(index).toInt()

        // Needs to be called after the gesture detector gets a turn, as it may have
        // displayed the mini keyboard
        if (mMiniKeyboardVisible) {
            val miniKeyboardPointerIndex = me.findPointerIndex(mMiniKeyboardTrackerId)
            if (miniKeyboardPointerIndex >= 0 && miniKeyboardPointerIndex < pointerCount) {
                val miniKeyboardX = me.getX(miniKeyboardPointerIndex).toInt()
                val miniKeyboardY = me.getY(miniKeyboardPointerIndex).toInt()
                val translated = generateMiniKeyboardMotionEvent(
                    action, miniKeyboardX, miniKeyboardY, eventTime
                )
                mMiniKeyboard?.onTouchEvent(translated)
                translated.recycle()
            }
            return true
        }

        if (mHandler.isInKeyRepeat()) {
            // It will keep being in the key repeating mode while the key is being pressed.
            if (action == MotionEvent.ACTION_MOVE) {
                return true
            }
            val tracker = getPointerTracker(id)
            // Key repeating timer will be canceled if 2 or more keys are in action, and current
            // event (UP or DOWN) is non-modifier key.
            if (pointerCount > 1 && !tracker.isModifier) {
                mHandler.cancelKeyRepeatTimer()
            }
            // Up event will pass through.
        }

        // TODO: cleanup this code into a multi-touch to single-touch event converter class?
        // Translate mutli-touch event to single-touch events on the device that has no distinct
        // multi-touch panel.
        if (!mHasDistinctMultitouch) {
            // Use only main (id=0) pointer tracker.
            val tracker = getPointerTracker(0)
            when {
                pointerCount == 1 && oldPointerCount == 2 -> {
                    // Multi-touch to single touch transition.
                    // Send a down event for the latest pointer.
                    tracker.onDownEvent(x, y, eventTime)
                }
                pointerCount == 2 && oldPointerCount == 1 -> {
                    // Single-touch to multi-touch transition.
                    // Send an up event for the last pointer.
                    tracker.onUpEvent(tracker.lastX, tracker.lastY, eventTime)
                }
                pointerCount == 1 && oldPointerCount == 1 -> {
                    tracker.onTouchEvent(action, x, y, eventTime)
                }
                else -> {
                    Log.w(TAG, "Unknown touch panel behavior: pointer count is $pointerCount (old $oldPointerCount)")
                }
            }
            if (continuing) tracker.setSlidingKeyInputState(true)
            return true
        }

        if (action == MotionEvent.ACTION_MOVE) {
            if (!mIgnoreMove) {
                for (i in 0 until pointerCount) {
                    val tracker = getPointerTracker(me.getPointerId(i))
                    tracker.onMoveEvent(me.getX(i).toInt(), me.getY(i).toInt(), eventTime)
                }
            }
        } else {
            val tracker = getPointerTracker(id)
            when (action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                    mIgnoreMove = false
                    onDownEvent(tracker, x, y, eventTime)
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                    mIgnoreMove = false
                    onUpEvent(tracker, x, y, eventTime)
                }
                MotionEvent.ACTION_CANCEL -> {
                    onCancelEvent(tracker, x, y, eventTime)
                }
            }
            if (continuing) tracker.setSlidingKeyInputState(true)
        }

        return true
    }

    private fun onDownEvent(tracker: PointerTracker, x: Int, y: Int, eventTime: Long) {
        if (tracker.isOnModifierKey(x, y)) {
            // Before processing a down event of modifier key, all pointers already being tracked
            // should be released.
            mPointerQueue.releaseAllPointersExcept(null, eventTime)
        }
        tracker.onDownEvent(x, y, eventTime)
        mPointerQueue.add(tracker)
    }

    private fun onUpEvent(tracker: PointerTracker, x: Int, y: Int, eventTime: Long) {
        if (tracker.isModifier) {
            // Before processing an up event of modifier key, all pointers already being tracked
            // should be released.
            mPointerQueue.releaseAllPointersExcept(tracker, eventTime)
        } else {
            val index = mPointerQueue.lastIndexOf(tracker)
            if (index >= 0) {
                mPointerQueue.releaseAllPointersOlderThan(tracker, eventTime)
            } else {
                Log.w(TAG, "onUpEvent: corresponding down event not found for pointer ${tracker.mPointerId}")
            }
        }
        tracker.onUpEvent(x, y, eventTime)
        mPointerQueue.remove(tracker)
    }

    private fun onCancelEvent(tracker: PointerTracker, x: Int, y: Int, eventTime: Long) {
        tracker.onCancelEvent(x, y, eventTime)
        mPointerQueue.remove(tracker)
    }

    protected open fun swipeRight(): Boolean = mKeyboardActionListener?.swipeRight() ?: false

    protected open fun swipeLeft(): Boolean = mKeyboardActionListener?.swipeLeft() ?: false

    /*package*/ open fun swipeUp(): Boolean = mKeyboardActionListener?.swipeUp() ?: false

    protected open fun swipeDown(): Boolean = mKeyboardActionListener?.swipeDown() ?: false

    fun closing() {
        Log.i(TAG, "closing $this")
        mPreviewPopup?.dismiss()
        mHandler.cancelAllMessages()

        dismissPopupKeyboard()

        mMiniKeyboardCacheMain.clear()
        mMiniKeyboardCacheShift.clear()
        mMiniKeyboardCacheCaps.clear()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        closing()
    }

    protected fun popupKeyboardIsShowing(): Boolean = mMiniKeyboardPopup?.isShowing == true

    protected open fun dismissPopupKeyboard() {
        mMiniKeyboardPopup?.let { popup ->
            if (popup.isShowing) {
                popup.dismiss()
            }
            mMiniKeyboardVisible = false
            mPointerQueue.releaseAllPointersExcept(null, 0) // https://github.com/klausw/hackerskeyboard/issues/477
            invalidateAllKeys()
        }
    }

    fun handleBack(): Boolean {
        if (mMiniKeyboardPopup?.isShowing == true) {
            dismissPopupKeyboard()
            return true
        }
        return false
    }

    private fun setRenderModeIfPossible(mode: Int) {
        if (sSetRenderMode != null && mode != sPrevRenderMode) {
            try {
                sSetRenderMode!!.invoke(this, mode, null)
                sPrevRenderMode = mode
                Log.i(TAG, "render mode set to ${LatinIME.sKeyboardSettings.renderMode}")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        private const val TAG = "HK/LatinKbdBaseView"
        private const val DEBUG = false

        const val NOT_A_TOUCH_COORDINATE = -1

        // Miscellaneous constants
        @JvmField internal val NOT_A_KEY = -1
        private const val NUMBER_HINT_VERTICAL_ADJUSTMENT_PIXEL = -1

        private val INVERTING_MATRIX = floatArrayOf(
            -1f, 0f, 0f, 0f, 255f, // Red
            0f, -1f, 0f, 0f, 255f, // Green
            0f, 0f, -1f, 0f, 255f, // Blue
            0f, 0f, 0f, 1f, 0f     // Alpha
        )

        @JvmStatic
        var sSetRenderMode: Method? = null
        private var sPrevRenderMode = -1

        init {
            initCompatibility()
        }

        @JvmStatic
        fun initCompatibility() {
            try {
                sSetRenderMode = View::class.java.getMethod("setLayerType", Int::class.javaPrimitiveType, Paint::class.java)
                Log.i(TAG, "setRenderMode is supported")
            } catch (e: SecurityException) {
                Log.w(TAG, "unexpected SecurityException", e)
            } catch (e: NoSuchMethodException) {
                // ignore, not supported by API level pre-Honeycomb
                Log.i(TAG, "ignoring render mode, not supported")
            }
        }

        private fun isOneRowKeys(keys: List<Keyboard.Key>): Boolean {
            if (keys.isEmpty()) return false
            val edgeFlags = keys[0].edgeFlags
            // HACK: The first key of mini keyboard which was inflated from xml and has multiple rows,
            // does not have both top and bottom edge flags on at the same time.  On the other hand,
            // the first key of mini keyboard that was created with popupCharacters must have both top
            // and bottom edge flags on.
            return (edgeFlags and Keyboard.EDGE_TOP) != 0 && (edgeFlags and Keyboard.EDGE_BOTTOM) != 0
        }

        private fun isNumberAtEdgeOfPopupChars(key: Keyboard.Key): Boolean =
            isNumberAtLeftmostPopupChar(key) || isNumberAtRightmostPopupChar(key)

        @JvmStatic
        internal fun isNumberAtLeftmostPopupChar(key: Keyboard.Key): Boolean {
            if (key.popupCharacters != null && key.popupCharacters!!.isNotEmpty() &&
                isAsciiDigit(key.popupCharacters!![0])
            ) {
                return true
            }
            return false
        }

        @JvmStatic
        internal fun isNumberAtRightmostPopupChar(key: Keyboard.Key): Boolean {
            if (key.popupCharacters != null && key.popupCharacters!!.isNotEmpty() &&
                isAsciiDigit(key.popupCharacters!![key.popupCharacters!!.length - 1])
            ) {
                return true
            }
            return false
        }

        private fun isAsciiDigit(c: Char): Boolean = (c.code < 0x80) && Character.isDigit(c)
    }
}
