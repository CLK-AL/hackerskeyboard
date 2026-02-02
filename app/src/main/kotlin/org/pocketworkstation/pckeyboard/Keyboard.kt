/*
 * Copyright (C) 2008-2009 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.pocketworkstation.pckeyboard

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.content.res.XmlResourceParser
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.util.Xml
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.util.StringTokenizer

/**
 * Loads an XML description of a keyboard and stores the attributes of the keys.
 */
open class Keyboard {
    companion object {
        const val TAG = "Keyboard"

        const val DEAD_KEY_PLACEHOLDER = 0x25cc.toChar()
        @JvmField
        val DEAD_KEY_PLACEHOLDER_STRING = DEAD_KEY_PLACEHOLDER.toString()

        private const val TAG_KEYBOARD = "Keyboard"
        private const val TAG_ROW = "Row"
        private const val TAG_KEY = "Key"

        const val EDGE_LEFT = 0x01
        const val EDGE_RIGHT = 0x02
        const val EDGE_TOP = 0x04
        const val EDGE_BOTTOM = 0x08

        const val KEYCODE_SHIFT = -1
        const val KEYCODE_MODE_CHANGE = -2
        const val KEYCODE_CANCEL = -3
        const val KEYCODE_DONE = -4
        const val KEYCODE_DELETE = -5
        const val KEYCODE_ALT_SYM = -6

        const val DEFAULT_LAYOUT_ROWS = 4
        const val DEFAULT_LAYOUT_COLUMNS = 10

        const val POPUP_ADD_SHIFT = 1
        const val POPUP_ADD_CASE = 2
        const val POPUP_ADD_SELF = 4
        const val POPUP_DISABLE = 256
        const val POPUP_AUTOREPEAT = 512

        const val SHIFT_OFF = 0
        const val SHIFT_ON = 1
        const val SHIFT_LOCKED = 2
        const val SHIFT_CAPS = 3
        const val SHIFT_CAPS_LOCKED = 4

        private const val SEARCH_DISTANCE = 1.8f

        @JvmStatic
        fun getDimensionOrFraction(a: TypedArray, index: Int, base: Int, defValue: Float): Float {
            val value = a.peekValue(index) ?: return defValue
            return when (value.type) {
                TypedValue.TYPE_DIMENSION -> a.getDimensionPixelOffset(index, defValue.toInt()).toFloat()
                TypedValue.TYPE_FRACTION -> a.getFraction(index, base, base, defValue)
                else -> defValue
            }
        }
    }

    private var mDefaultHorizontalGap = 0f
    private var mHorizontalPad = 0f
    private var mVerticalPad = 0f
    private var mDefaultWidth = 0f
    var mDefaultHeight = 0
        protected set
    private var mDefaultVerticalGap = 0
    private var mShiftState = SHIFT_OFF
    private var mShiftKey: Key? = null
    private var mAltKey: Key? = null
    private var mCtrlKey: Key? = null
    private var mMetaKey: Key? = null
    private var mShiftKeyIndex = -1
    private var mTotalHeight = 0
    private var mTotalWidth = 0
    private var mKeys: MutableList<Key> = ArrayList()
    private var mModifierKeys: MutableList<Key> = ArrayList()
    var mDisplayWidth = 0
        private set
    private var mDisplayHeight = 0
    private var mKeyboardHeight = 0
    private var mKeyboardMode = 0
    private var mUseExtension = false

    var mLayoutRows = 0
    var mLayoutColumns = 0
    var mRowCount = 1
    var mExtensionRowCount = 0

    private var mCellWidth = 0
    private var mCellHeight = 0
    private var mGridNeighbors: Array<IntArray?>? = null
    private var mProximityThreshold = 0

    /**
     * Container for keys in the keyboard.
     */
    open class Row {
        var defaultWidth = 0f
        var defaultHeight = 0
        var defaultHorizontalGap = 0f
        var verticalGap = 0
        var mode = 0
        var extension = false
        var parent: Keyboard

        constructor(parent: Keyboard) {
            this.parent = parent
        }

        constructor(res: Resources, parent: Keyboard, parser: XmlResourceParser) {
            this.parent = parent
            var a = res.obtainAttributes(Xml.asAttributeSet(parser), R.styleable.Keyboard)
            defaultWidth = getDimensionOrFraction(a, R.styleable.Keyboard_keyWidth, parent.mDisplayWidth, parent.mDefaultWidth)
            defaultHeight = getDimensionOrFraction(a, R.styleable.Keyboard_keyHeight, parent.mDisplayHeight, parent.mDefaultHeight.toFloat()).toInt()
            defaultHorizontalGap = getDimensionOrFraction(a, R.styleable.Keyboard_horizontalGap, parent.mDisplayWidth, parent.mDefaultHorizontalGap)
            verticalGap = getDimensionOrFraction(a, R.styleable.Keyboard_verticalGap, parent.mDisplayHeight, parent.mDefaultVerticalGap.toFloat()).toInt()
            a.recycle()

            a = res.obtainAttributes(Xml.asAttributeSet(parser), R.styleable.Keyboard_Row)
            mode = a.getResourceId(R.styleable.Keyboard_Row_keyboardMode, 0)
            extension = a.getBoolean(R.styleable.Keyboard_Row_extension, false)

            if (parent.mLayoutRows >= 5 || extension) {
                val isTop = extension || parent.mRowCount - parent.mExtensionRowCount <= 0
                val topScale = LatinIME.sKeyboardSettings.topRowScale
                val scale = if (isTop) topScale else 1.0f + (1.0f - topScale) / (parent.mLayoutRows - 1)
                defaultHeight = (defaultHeight * scale).toInt()
            }
            a.recycle()
        }
    }

    /**
     * Class for describing the position and characteristics of a single key.
     */
    open class Key {
        var codes: IntArray? = null
        var label: CharSequence? = null
        var shiftLabel: CharSequence? = null
        var capsLabel: CharSequence? = null
        var icon: Drawable? = null
        var iconPreview: Drawable? = null
        var width = 0
        private var realWidth = 0f
        var height = 0
        var gap = 0
        var realGap = 0f
        var sticky = false
        var x = 0
        var realX = 0f
        var y = 0
        var pressed = false
        var on = false
        var locked = false
        var text: CharSequence? = null
        var popupCharacters: CharSequence? = null
        var popupReversed = false
        var isCursor = false
        var hint: String? = null
        var altHint: String? = null
        var edgeFlags = 0
        var modifier = false
        private var keyboard: Keyboard
        var popupResId = 0
        var repeatable = false
        private var isSimpleUppercase = false
        private var isDistinctUppercase = false

        companion object {
            private val KEY_STATE_NORMAL_ON = intArrayOf(android.R.attr.state_checkable, android.R.attr.state_checked)
            private val KEY_STATE_PRESSED_ON = intArrayOf(android.R.attr.state_pressed, android.R.attr.state_checkable, android.R.attr.state_checked)
            private val KEY_STATE_NORMAL_LOCK = intArrayOf(android.R.attr.state_active, android.R.attr.state_checkable, android.R.attr.state_checked)
            private val KEY_STATE_PRESSED_LOCK = intArrayOf(android.R.attr.state_active, android.R.attr.state_pressed, android.R.attr.state_checkable, android.R.attr.state_checked)
            private val KEY_STATE_NORMAL_OFF = intArrayOf(android.R.attr.state_checkable)
            private val KEY_STATE_PRESSED_OFF = intArrayOf(android.R.attr.state_pressed, android.R.attr.state_checkable)
            private val KEY_STATE_NORMAL = intArrayOf()
            private val KEY_STATE_PRESSED = intArrayOf(android.R.attr.state_pressed)

            private fun is7BitAscii(c: Char): Boolean {
                if (c in 'A'..'Z' || c in 'a'..'z') return false
                return c.code in 32..126
            }
        }

        constructor(parent: Row) {
            keyboard = parent.parent
            height = parent.defaultHeight
            width = parent.defaultWidth.toInt()
            realWidth = parent.defaultWidth
            gap = parent.defaultHorizontalGap.toInt()
            realGap = parent.defaultHorizontalGap
        }

        constructor(res: Resources, parent: Row, x: Int, y: Int, parser: XmlResourceParser) : this(parent) {
            this.x = x
            this.y = y

            var a = res.obtainAttributes(Xml.asAttributeSet(parser), R.styleable.Keyboard)
            realWidth = getDimensionOrFraction(a, R.styleable.Keyboard_keyWidth, keyboard.mDisplayWidth, parent.defaultWidth)
            var realHeight = getDimensionOrFraction(a, R.styleable.Keyboard_keyHeight, keyboard.mDisplayHeight, parent.defaultHeight.toFloat())
            realHeight -= parent.parent.mVerticalPad
            height = realHeight.toInt()
            this.y += (parent.parent.mVerticalPad / 2).toInt()
            realGap = getDimensionOrFraction(a, R.styleable.Keyboard_horizontalGap, keyboard.mDisplayWidth, parent.defaultHorizontalGap)
            realGap += parent.parent.mHorizontalPad
            realWidth -= parent.parent.mHorizontalPad
            width = realWidth.toInt()
            gap = realGap.toInt()
            a.recycle()

            a = res.obtainAttributes(Xml.asAttributeSet(parser), R.styleable.Keyboard_Key)
            this.realX = this.x + realGap - parent.parent.mHorizontalPad / 2
            this.x = this.realX.toInt()

            val codesValue = TypedValue()
            a.getValue(R.styleable.Keyboard_Key_codes, codesValue)
            codes = when (codesValue.type) {
                TypedValue.TYPE_INT_DEC, TypedValue.TYPE_INT_HEX -> intArrayOf(codesValue.data)
                TypedValue.TYPE_STRING -> parseCSV(codesValue.string.toString())
                else -> null
            }

            iconPreview = a.getDrawable(R.styleable.Keyboard_Key_iconPreview)?.apply {
                setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            }
            popupCharacters = a.getText(R.styleable.Keyboard_Key_popupCharacters)
            popupResId = a.getResourceId(R.styleable.Keyboard_Key_popupKeyboard, 0)
            repeatable = a.getBoolean(R.styleable.Keyboard_Key_isRepeatable, false)
            modifier = a.getBoolean(R.styleable.Keyboard_Key_isModifier, false)
            sticky = a.getBoolean(R.styleable.Keyboard_Key_isSticky, false)
            isCursor = a.getBoolean(R.styleable.Keyboard_Key_isCursor, false)

            icon = a.getDrawable(R.styleable.Keyboard_Key_keyIcon)?.apply {
                setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            }
            label = a.getText(R.styleable.Keyboard_Key_keyLabel)
            shiftLabel = a.getText(R.styleable.Keyboard_Key_shiftLabel)?.takeIf { it.isNotEmpty() }
            capsLabel = a.getText(R.styleable.Keyboard_Key_capsLabel)?.takeIf { it.isNotEmpty() }
            text = a.getText(R.styleable.Keyboard_Key_keyOutputText)

            if (codes == null && !TextUtils.isEmpty(label)) {
                codes = getFromString(label!!)
                if (codes != null && codes!!.size == 1) {
                    val locale = LatinIME.sKeyboardSettings.inputLocale
                    val upperLabel = label.toString().uppercase(locale)
                    if (shiftLabel == null) {
                        if (upperLabel != label.toString() && upperLabel.length == 1) {
                            shiftLabel = upperLabel
                            isSimpleUppercase = true
                        }
                    } else {
                        when {
                            capsLabel != null -> isDistinctUppercase = true
                            upperLabel == shiftLabel.toString() -> isSimpleUppercase = true
                            upperLabel.length == 1 -> {
                                capsLabel = upperLabel
                                isDistinctUppercase = true
                            }
                        }
                    }
                }
                if ((LatinIME.sKeyboardSettings.popupKeyboardFlags and POPUP_DISABLE) != 0) {
                    popupCharacters = null
                    popupResId = 0
                }
                if ((LatinIME.sKeyboardSettings.popupKeyboardFlags and POPUP_AUTOREPEAT) != 0) {
                    repeatable = true
                }
            }
            a.recycle()
        }

        fun isDistinctCaps(): Boolean = isDistinctUppercase && keyboard.isShiftCaps()

        fun isShifted(): Boolean = keyboard.isShifted(isSimpleUppercase)

        fun getPrimaryCode(isShiftCaps: Boolean, isShifted: Boolean): Int {
            if (isDistinctUppercase && isShiftCaps) {
                return capsLabel!![0].code
            }
            return if (isShifted && shiftLabel != null) {
                if (shiftLabel!![0] == DEAD_KEY_PLACEHOLDER && shiftLabel!!.length >= 2) {
                    shiftLabel!![1].code
                } else {
                    shiftLabel!![0].code
                }
            } else {
                codes!![0]
            }
        }

        fun getPrimaryCode(): Int = getPrimaryCode(keyboard.isShiftCaps(), keyboard.isShifted(isSimpleUppercase))

        fun isDeadKey(): Boolean {
            if (codes == null || codes!!.isEmpty()) return false
            return Character.getType(codes!![0]) == Character.NON_SPACING_MARK.toInt()
        }

        fun getFromString(str: CharSequence): IntArray {
            return if (str.length > 1) {
                if (str[0] == DEAD_KEY_PLACEHOLDER && str.length >= 2) {
                    intArrayOf(str[1].code)
                } else {
                    text = str
                    intArrayOf(0)
                }
            } else {
                intArrayOf(str[0].code)
            }
        }

        fun getCaseLabel(): String? {
            if (isDistinctUppercase && keyboard.isShiftCaps()) {
                return capsLabel.toString()
            }
            val isShifted = keyboard.isShifted(isSimpleUppercase)
            return if (isShifted && shiftLabel != null) {
                shiftLabel.toString()
            } else {
                label?.toString()
            }
        }

        private fun getPopupKeyboardContent(isShiftCaps: Boolean, isShifted: Boolean, addExtra: Boolean): String {
            var mainChar = getPrimaryCode(false, false)
            var shiftChar = getPrimaryCode(false, true)
            var capsChar = getPrimaryCode(true, true)

            if (shiftChar == mainChar) shiftChar = 0
            if (capsChar == shiftChar || capsChar == mainChar) capsChar = 0

            val popupLen = popupCharacters?.length ?: 0
            val popup = StringBuilder(popupLen)
            for (i in 0 until popupLen) {
                var c = popupCharacters!![i]
                if (isShifted || isShiftCaps) {
                    val upper = c.toString().uppercase(LatinIME.sKeyboardSettings.inputLocale)
                    if (upper.length == 1) c = upper[0]
                }
                if (c.code == mainChar || c.code == shiftChar || c.code == capsChar) continue
                popup.append(c)
            }

            if (addExtra) {
                val extra = StringBuilder(3 + popup.length)
                val flags = LatinIME.sKeyboardSettings.popupKeyboardFlags
                if ((flags and POPUP_ADD_SELF) != 0) {
                    when {
                        isDistinctUppercase && isShiftCaps -> if (capsChar > 0) { extra.append(capsChar.toChar()); capsChar = 0 }
                        isShifted -> if (shiftChar > 0) { extra.append(shiftChar.toChar()); shiftChar = 0 }
                        else -> if (mainChar > 0) { extra.append(mainChar.toChar()); mainChar = 0 }
                    }
                }
                if ((flags and POPUP_ADD_CASE) != 0) {
                    when {
                        isDistinctUppercase && isShiftCaps -> {
                            if (mainChar > 0) { extra.append(mainChar.toChar()); mainChar = 0 }
                            if (shiftChar > 0) { extra.append(shiftChar.toChar()); shiftChar = 0 }
                        }
                        isShifted -> {
                            if (mainChar > 0) { extra.append(mainChar.toChar()); mainChar = 0 }
                            if (capsChar > 0) { extra.append(capsChar.toChar()); capsChar = 0 }
                        }
                        else -> {
                            if (shiftChar > 0) { extra.append(shiftChar.toChar()); shiftChar = 0 }
                            if (capsChar > 0) { extra.append(capsChar.toChar()); capsChar = 0 }
                        }
                    }
                }
                if (!isSimpleUppercase && (flags and POPUP_ADD_SHIFT) != 0) {
                    if (isShifted) {
                        if (mainChar > 0) extra.append(mainChar.toChar())
                    } else {
                        if (shiftChar > 0) extra.append(shiftChar.toChar())
                    }
                }
                extra.append(popup)
                return extra.toString()
            }
            return popup.toString()
        }

        fun getPopupKeyboard(context: Context, padding: Int): Keyboard? {
            if (popupCharacters == null) {
                return if (popupResId != 0) {
                    Keyboard(context, keyboard.mDefaultHeight, popupResId)
                } else {
                    if (modifier) null else null
                }
            }
            if ((LatinIME.sKeyboardSettings.popupKeyboardFlags and POPUP_DISABLE) != 0) return null

            val popup = getPopupKeyboardContent(keyboard.isShiftCaps(), keyboard.isShifted(isSimpleUppercase), true)
            return if (popup.isNotEmpty()) {
                var resId = popupResId
                if (resId == 0) resId = R.xml.kbd_popup_template
                Keyboard(context, keyboard.mDefaultHeight, resId, popup, popupReversed, -1, padding)
            } else {
                null
            }
        }

        fun getHintLabel(wantAscii: Boolean, wantAll: Boolean): String {
            if (hint == null) {
                hint = ""
                if (shiftLabel != null && !isSimpleUppercase) {
                    val c = shiftLabel!![0]
                    if (wantAll || (wantAscii && is7BitAscii(c))) {
                        hint = c.toString()
                    }
                }
            }
            return hint!!
        }

        fun getAltHintLabel(wantAscii: Boolean, wantAll: Boolean): String {
            if (altHint == null) {
                altHint = ""
                val popup = getPopupKeyboardContent(false, false, false)
                if (popup.isNotEmpty()) {
                    val c = popup[0]
                    if (wantAll || (wantAscii && is7BitAscii(c))) {
                        altHint = c.toString()
                    }
                }
            }
            return altHint!!
        }

        fun onPressed() { pressed = !pressed }
        fun onReleased(inside: Boolean) { pressed = !pressed }

        fun parseCSV(value: String): IntArray {
            var count = 0
            var lastIndex = 0
            if (value.isNotEmpty()) {
                count++
                while (value.indexOf(",", lastIndex + 1).also { lastIndex = it } > 0) {
                    count++
                }
            }
            val values = IntArray(count)
            count = 0
            val st = StringTokenizer(value, ",")
            while (st.hasMoreTokens()) {
                try {
                    values[count++] = st.nextToken().toInt()
                } catch (e: NumberFormatException) {
                    Log.e(TAG, "Error parsing keycodes $value")
                }
            }
            return values
        }

        fun isInside(x: Int, y: Int): Boolean {
            val leftEdge = (edgeFlags and EDGE_LEFT) > 0
            val rightEdge = (edgeFlags and EDGE_RIGHT) > 0
            val topEdge = (edgeFlags and EDGE_TOP) > 0
            val bottomEdge = (edgeFlags and EDGE_BOTTOM) > 0
            return (x >= this.x || (leftEdge && x <= this.x + width)) &&
                    (x < this.x + width || (rightEdge && x >= this.x)) &&
                    (y >= this.y || (topEdge && y <= this.y + height)) &&
                    (y < this.y + height || (bottomEdge && y >= this.y))
        }

        fun squaredDistanceFrom(x: Int, y: Int): Int {
            val xDist = this.x + width / 2 - x
            val yDist = this.y + height / 2 - y
            return xDist * xDist + yDist * yDist
        }

        fun getCurrentDrawableState(): IntArray {
            return when {
                locked -> if (pressed) KEY_STATE_PRESSED_LOCK else KEY_STATE_NORMAL_LOCK
                on -> if (pressed) KEY_STATE_PRESSED_ON else KEY_STATE_NORMAL_ON
                sticky -> if (pressed) KEY_STATE_PRESSED_OFF else KEY_STATE_NORMAL_OFF
                pressed -> KEY_STATE_PRESSED
                else -> KEY_STATE_NORMAL
            }
        }

        override fun toString(): String {
            val code = if (codes != null && codes!!.isNotEmpty()) codes!![0] else 0
            val edges = (if ((edgeFlags and EDGE_LEFT) != 0) "L" else "-") +
                    (if ((edgeFlags and EDGE_RIGHT) != 0) "R" else "-") +
                    (if ((edgeFlags and EDGE_TOP) != 0) "T" else "-") +
                    (if ((edgeFlags and EDGE_BOTTOM) != 0) "B" else "-")
            return "Key(label=$label" +
                    (shiftLabel?.let { " shift=$it" } ?: "") +
                    (capsLabel?.let { " caps=$it" } ?: "") +
                    (text?.let { " text=$it" } ?: "") +
                    " code=$code" +
                    (if (code <= 0 || Character.isWhitespace(code)) "" else ":'${code.toChar()}'") +
                    " x=$x..${x + width} y=$y..${y + height}" +
                    " edges=$edges" +
                    (popupCharacters?.let { " pop=$it" } ?: "") +
                    " res=$popupResId)"
        }
    }

    constructor(context: Context, defaultHeight: Int, xmlLayoutResId: Int) : this(context, defaultHeight, xmlLayoutResId, 0)

    constructor(context: Context, defaultHeight: Int, xmlLayoutResId: Int, modeId: Int) : this(context, defaultHeight, xmlLayoutResId, modeId, 0f)

    constructor(context: Context, defaultHeight: Int, xmlLayoutResId: Int, modeId: Int, kbHeightPercent: Float) {
        val dm = context.resources.displayMetrics
        mDisplayWidth = dm.widthPixels
        mDisplayHeight = dm.heightPixels
        Log.v(TAG, "keyboard's display metrics: $dm, mDisplayWidth=$mDisplayWidth")

        mDefaultHorizontalGap = 0f
        mDefaultWidth = mDisplayWidth / 10f
        mDefaultVerticalGap = 0
        mDefaultHeight = defaultHeight
        mKeyboardHeight = (mDisplayHeight * kbHeightPercent / 100).toInt()
        mKeys = ArrayList()
        mModifierKeys = ArrayList()
        mKeyboardMode = modeId
        mUseExtension = LatinIME.sKeyboardSettings.useExtension
        loadKeyboard(context, context.resources.getXml(xmlLayoutResId))
        setEdgeFlags()
        fixAltChars(LatinIME.sKeyboardSettings.inputLocale)
    }

    private constructor(context: Context, defaultHeight: Int, layoutTemplateResId: Int,
                        characters: CharSequence, reversed: Boolean, columns: Int, horizontalPadding: Int)
            : this(context, defaultHeight, layoutTemplateResId) {
        var x = 0
        var y = 0
        var column = 0
        mTotalWidth = 0

        val row = Row(this).apply {
            this.defaultHeight = mDefaultHeight
            this.defaultWidth = mDefaultWidth
            this.defaultHorizontalGap = mDefaultHorizontalGap
            this.verticalGap = mDefaultVerticalGap
        }
        val maxColumns = if (columns == -1) Int.MAX_VALUE else columns
        mLayoutRows = 1
        val start = if (reversed) characters.length - 1 else 0
        val end = if (reversed) -1 else characters.length
        val step = if (reversed) -1 else 1

        var i = start
        while (i != end) {
            val c = characters[i]
            if (column >= maxColumns || x + mDefaultWidth + horizontalPadding > mDisplayWidth) {
                x = 0
                y += mDefaultVerticalGap + mDefaultHeight
                column = 0
                ++mLayoutRows
            }
            val key = Key(row).apply {
                this.x = x
                this.realX = x.toFloat()
                this.y = y
                this.label = c.toString()
                this.codes = getFromString(this.label!!)
            }
            column++
            x += key.width + key.gap
            mKeys.add(key)
            if (x > mTotalWidth) mTotalWidth = x
            i += step
        }
        mTotalHeight = y + mDefaultHeight
        mLayoutColumns = if (columns == -1) column else maxColumns
        setEdgeFlags()
    }

    private fun setEdgeFlags() {
        if (mRowCount == 0) mRowCount = 1
        var row = 0
        var prevKey: Key? = null
        var rowFlags = 0
        for (key in mKeys) {
            var keyFlags = 0
            if (prevKey == null || key.x <= prevKey.x) {
                prevKey?.let { it.edgeFlags = it.edgeFlags or EDGE_RIGHT }
                rowFlags = 0
                if (row == 0) rowFlags = rowFlags or EDGE_TOP
                if (row == mRowCount - 1) rowFlags = rowFlags or EDGE_BOTTOM
                ++row
                keyFlags = keyFlags or EDGE_LEFT
            }
            key.edgeFlags = rowFlags or keyFlags
            prevKey = key
        }
        prevKey?.let { it.edgeFlags = it.edgeFlags or EDGE_RIGHT }
    }

    private fun fixAltChars(locale: java.util.Locale?) {
        val loc = locale ?: java.util.Locale.getDefault()
        val mainKeys = HashSet<Char>()
        for (key in mKeys) {
            if (key.label != null && !key.modifier && key.label!!.length == 1) {
                mainKeys.add(key.label!![0])
            }
        }
        for (key in mKeys) {
            if (key.popupCharacters == null) continue
            var popupLen = key.popupCharacters!!.length
            if (popupLen == 0) continue
            if (key.x >= mTotalWidth / 2) key.popupReversed = true

            val needUpcase = key.label != null && key.label!!.length == 1 && Character.isUpperCase(key.label!![0])
            if (needUpcase) {
                key.popupCharacters = key.popupCharacters.toString().uppercase(loc)
                popupLen = key.popupCharacters!!.length
            }

            val newPopup = StringBuilder(popupLen)
            for (i in 0 until popupLen) {
                val c = key.popupCharacters!![i]
                if (Character.isDigit(c) && mainKeys.contains(c)) continue
                if ((key.edgeFlags and EDGE_TOP) == 0 && Character.isDigit(c)) continue
                newPopup.append(c)
            }
            key.popupCharacters = newPopup.toString()
        }
    }

    fun getKeys(): List<Key> = mKeys
    fun getModifierKeys(): List<Key> = mModifierKeys
    protected fun getHorizontalGap(): Int = mDefaultHorizontalGap.toInt()
    protected fun setHorizontalGap(gap: Int) { mDefaultHorizontalGap = gap.toFloat() }
    protected fun getVerticalGap(): Int = mDefaultVerticalGap
    protected fun setVerticalGap(gap: Int) { mDefaultVerticalGap = gap }
    protected fun getKeyHeight(): Int = mDefaultHeight
    protected fun setKeyHeight(height: Int) { mDefaultHeight = height }
    protected fun getKeyWidth(): Int = mDefaultWidth.toInt()
    protected fun setKeyWidth(width: Int) { mDefaultWidth = width.toFloat() }
    fun getHeight(): Int = mTotalHeight
    fun getScreenHeight(): Int = mDisplayHeight
    fun getMinWidth(): Int = mTotalWidth

    fun setShiftState(shiftState: Int, updateKey: Boolean = true): Boolean {
        if (updateKey && mShiftKey != null) {
            mShiftKey!!.on = shiftState != SHIFT_OFF
        }
        if (mShiftState != shiftState) {
            mShiftState = shiftState
            return true
        }
        return false
    }

    fun setCtrlIndicator(active: Boolean): Key? {
        mCtrlKey?.on = active
        return mCtrlKey
    }

    fun setAltIndicator(active: Boolean): Key? {
        mAltKey?.on = active
        return mAltKey
    }

    fun setMetaIndicator(active: Boolean): Key? {
        mMetaKey?.on = active
        return mMetaKey
    }

    fun isShiftCaps(): Boolean = mShiftState == SHIFT_CAPS || mShiftState == SHIFT_CAPS_LOCKED

    fun isShifted(applyCaps: Boolean): Boolean {
        return if (applyCaps) {
            mShiftState != SHIFT_OFF
        } else {
            mShiftState == SHIFT_ON || mShiftState == SHIFT_LOCKED
        }
    }

    fun getShiftState(): Int = mShiftState
    fun getShiftKeyIndex(): Int = mShiftKeyIndex

    private fun computeNearestNeighbors() {
        mCellWidth = (getMinWidth() + mLayoutColumns - 1) / mLayoutColumns
        mCellHeight = (getHeight() + mLayoutRows - 1) / mLayoutRows
        mGridNeighbors = arrayOfNulls(mLayoutColumns * mLayoutRows)
        val indices = IntArray(mKeys.size)
        val gridWidth = mLayoutColumns * mCellWidth
        val gridHeight = mLayoutRows * mCellHeight
        var x = 0
        while (x < gridWidth) {
            var y = 0
            while (y < gridHeight) {
                var count = 0
                for (i in mKeys.indices) {
                    val key = mKeys[i]
                    val isSpace = key.codes != null && key.codes!!.isNotEmpty() && key.codes!![0] == LatinIME.ASCII_SPACE
                    if (key.squaredDistanceFrom(x, y) < mProximityThreshold ||
                        key.squaredDistanceFrom(x + mCellWidth - 1, y) < mProximityThreshold ||
                        key.squaredDistanceFrom(x + mCellWidth - 1, y + mCellHeight - 1) < mProximityThreshold ||
                        key.squaredDistanceFrom(x, y + mCellHeight - 1) < mProximityThreshold ||
                        (isSpace && !(x + mCellWidth - 1 < key.x || x > key.x + key.width ||
                                y + mCellHeight - 1 < key.y || y > key.y + key.height))) {
                        indices[count++] = i
                    }
                }
                val cell = IntArray(count)
                System.arraycopy(indices, 0, cell, 0, count)
                mGridNeighbors!![(y / mCellHeight) * mLayoutColumns + (x / mCellWidth)] = cell
                y += mCellHeight
            }
            x += mCellWidth
        }
    }

    fun getNearestKeys(x: Int, y: Int): IntArray {
        if (mGridNeighbors == null) computeNearestNeighbors()
        if (x >= 0 && x < getMinWidth() && y >= 0 && y < getHeight()) {
            val index = (y / mCellHeight) * mLayoutColumns + (x / mCellWidth)
            if (index < mLayoutRows * mLayoutColumns) {
                return mGridNeighbors!![index] ?: IntArray(0)
            }
        }
        return IntArray(0)
    }

    protected open fun createRowFromXml(res: Resources, parser: XmlResourceParser): Row = Row(res, this, parser)
    protected open fun createKeyFromXml(res: Resources, parent: Row, x: Int, y: Int, parser: XmlResourceParser): Key = Key(res, parent, x, y, parser)

    private fun loadKeyboard(context: Context, parser: XmlResourceParser) {
        var inKey = false
        var inRow = false
        var x = 0f
        var y = 0
        var key: Key? = null
        var currentRow: Row? = null
        val res = context.resources
        var skipRow = false
        mRowCount = 0

        try {
            var event: Int
            var prevKey: Key? = null
            while (parser.next().also { event = it } != XmlResourceParser.END_DOCUMENT) {
                if (event == XmlResourceParser.START_TAG) {
                    when (parser.name) {
                        TAG_ROW -> {
                            inRow = true
                            x = 0f
                            currentRow = createRowFromXml(res, parser)
                            skipRow = currentRow.mode != 0 && currentRow.mode != mKeyboardMode
                            if (currentRow.extension) {
                                if (mUseExtension) {
                                    ++mExtensionRowCount
                                } else {
                                    skipRow = true
                                }
                            }
                            if (skipRow) {
                                skipToEndOfRow(parser)
                                inRow = false
                            }
                        }
                        TAG_KEY -> {
                            inKey = true
                            key = createKeyFromXml(res, currentRow!!, x.toInt(), y, parser)
                            key.realX = x
                            if (key.codes == null) {
                                prevKey?.let { it.width += key.width }
                            } else {
                                mKeys.add(key)
                                prevKey = key
                                when {
                                    key.codes!![0] == KEYCODE_SHIFT -> {
                                        if (mShiftKeyIndex == -1) {
                                            mShiftKey = key
                                            mShiftKeyIndex = mKeys.size - 1
                                        }
                                        mModifierKeys.add(key)
                                    }
                                    key.codes!![0] == KEYCODE_ALT_SYM -> mModifierKeys.add(key)
                                    key.codes!![0] == LatinKeyboardView.KEYCODE_CTRL_LEFT -> mCtrlKey = key
                                    key.codes!![0] == LatinKeyboardView.KEYCODE_ALT_LEFT -> mAltKey = key
                                    key.codes!![0] == LatinKeyboardView.KEYCODE_META_LEFT -> mMetaKey = key
                                }
                            }
                        }
                        TAG_KEYBOARD -> parseKeyboardAttributes(res, parser)
                    }
                } else if (event == XmlResourceParser.END_TAG) {
                    when {
                        inKey -> {
                            inKey = false
                            x += key!!.realGap + key.realWidth
                            if (x > mTotalWidth) mTotalWidth = x.toInt()
                        }
                        inRow -> {
                            inRow = false
                            y += currentRow!!.verticalGap + currentRow.defaultHeight
                            mRowCount++
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Parse error: $e")
            e.printStackTrace()
        }
        mTotalHeight = y - mDefaultVerticalGap
    }

    fun setKeyboardWidth(newWidth: Int) {
        Log.i(TAG, "setKeyboardWidth newWidth=$newWidth, mTotalWidth=$mTotalWidth")
        if (newWidth <= 0) return
        if (mTotalWidth <= newWidth) return
        val scale = newWidth.toFloat() / mDisplayWidth
        Log.i("PCKeyboard", "Rescaling keyboard: $mTotalWidth => $newWidth")
        for (key in mKeys) {
            key.x = (key.realX * scale).toInt()
        }
        mTotalWidth = newWidth
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun skipToEndOfRow(parser: XmlResourceParser) {
        var event: Int
        while (parser.next().also { event = it } != XmlResourceParser.END_DOCUMENT) {
            if (event == XmlResourceParser.END_TAG && parser.name == TAG_ROW) break
        }
    }

    private fun parseKeyboardAttributes(res: Resources, parser: XmlResourceParser) {
        val a = res.obtainAttributes(Xml.asAttributeSet(parser), R.styleable.Keyboard)
        mDefaultWidth = getDimensionOrFraction(a, R.styleable.Keyboard_keyWidth, mDisplayWidth, mDisplayWidth / 10f)
        mDefaultHeight = getDimensionOrFraction(a, R.styleable.Keyboard_keyHeight, mDisplayHeight, mDefaultHeight.toFloat()).toInt()
        mDefaultHorizontalGap = getDimensionOrFraction(a, R.styleable.Keyboard_horizontalGap, mDisplayWidth, 0f)
        mDefaultVerticalGap = getDimensionOrFraction(a, R.styleable.Keyboard_verticalGap, mDisplayHeight, 0f).toInt()
        mHorizontalPad = getDimensionOrFraction(a, R.styleable.Keyboard_horizontalPad, mDisplayWidth, res.getDimension(R.dimen.key_horizontal_pad))
        mVerticalPad = getDimensionOrFraction(a, R.styleable.Keyboard_verticalPad, mDisplayHeight, res.getDimension(R.dimen.key_vertical_pad))
        mLayoutRows = a.getInteger(R.styleable.Keyboard_layoutRows, DEFAULT_LAYOUT_ROWS)
        mLayoutColumns = a.getInteger(R.styleable.Keyboard_layoutColumns, DEFAULT_LAYOUT_COLUMNS)
        if (mDefaultHeight == 0 && mKeyboardHeight > 0 && mLayoutRows > 0) {
            mDefaultHeight = mKeyboardHeight / mLayoutRows
        }
        mProximityThreshold = (mDefaultWidth * SEARCH_DISTANCE).toInt()
        mProximityThreshold = mProximityThreshold * mProximityThreshold
        a.recycle()
    }

    override fun toString(): String {
        return "Keyboard(${mLayoutColumns}x$mLayoutRows keys=${mKeys.size} rowCount=$mRowCount mode=$mKeyboardMode size=${mTotalWidth}x$mTotalHeight)"
    }
}
