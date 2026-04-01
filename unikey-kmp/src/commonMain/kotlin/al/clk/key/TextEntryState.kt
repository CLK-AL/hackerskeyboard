/*
 * Copyright (C) 2008 The Android Open Source Project
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

package al.clk.key

/**
 * Tracks the state of text entry for the input method.
 * Platform-independent version without logging.
 */
object TextEntryState {

    private var backspaceCount = 0
    private var autoSuggestCount = 0
    private var autoSuggestUndoneCount = 0
    private var manualSuggestCount = 0
    private var wordNotInDictionaryCount = 0
    private var sessionCount = 0
    private var typedChars = 0
    private var actualChars = 0

    enum class State {
        UNKNOWN,
        START,
        IN_WORD,
        ACCEPTED_DEFAULT,
        PICKED_SUGGESTION,
        PUNCTUATION_AFTER_WORD,
        PUNCTUATION_AFTER_ACCEPTED,
        SPACE_AFTER_ACCEPTED,
        SPACE_AFTER_PICKED,
        UNDO_COMMIT,
        CORRECTING,
        PICKED_CORRECTION
    }

    private var state = State.UNKNOWN

    fun newSession() {
        sessionCount++
        autoSuggestCount = 0
        backspaceCount = 0
        autoSuggestUndoneCount = 0
        manualSuggestCount = 0
        wordNotInDictionaryCount = 0
        typedChars = 0
        actualChars = 0
        state = State.START
    }

    fun endSession() {
        // Statistics available via getStats()
    }

    /**
     * Get session statistics
     */
    fun getStats(): SessionStats = SessionStats(
        backspaceCount = backspaceCount,
        autoSuggestCount = autoSuggestCount,
        autoSuggestUndoneCount = autoSuggestUndoneCount,
        manualSuggestCount = manualSuggestCount,
        wordNotInDictionaryCount = wordNotInDictionaryCount,
        typedChars = typedChars,
        actualChars = actualChars
    )

    fun acceptedDefault(typedWord: CharSequence?, actualWord: CharSequence) {
        if (typedWord == null) return
        if (typedWord.toString() != actualWord.toString()) {
            autoSuggestCount++
        }
        typedChars += typedWord.length
        actualChars += actualWord.length
        state = State.ACCEPTED_DEFAULT
    }

    // State.ACCEPTED_DEFAULT will be changed to other sub-states
    // and should be restored back to State.ACCEPTED_DEFAULT after processing
    fun backToAcceptedDefault(typedWord: CharSequence?) {
        if (typedWord == null) return
        when (state) {
            State.SPACE_AFTER_ACCEPTED,
            State.PUNCTUATION_AFTER_ACCEPTED,
            State.IN_WORD -> state = State.ACCEPTED_DEFAULT
            else -> { /* do nothing */ }
        }
    }

    fun manualTyped(typedWord: CharSequence?) {
        state = State.START
    }

    fun acceptedTyped(typedWord: CharSequence?) {
        wordNotInDictionaryCount++
        state = State.PICKED_SUGGESTION
    }

    fun acceptedSuggestion(typedWord: CharSequence, actualWord: CharSequence) {
        manualSuggestCount++
        val oldState = state
        if (typedWord.toString() == actualWord.toString()) {
            acceptedTyped(typedWord)
        }
        state = if (oldState == State.CORRECTING || oldState == State.PICKED_CORRECTION) {
            State.PICKED_CORRECTION
        } else {
            State.PICKED_SUGGESTION
        }
    }

    fun selectedForCorrection() {
        state = State.CORRECTING
    }

    fun typedCharacter(c: Char, isSeparator: Boolean) {
        val isSpace = c == ' '
        when (state) {
            State.IN_WORD -> {
                if (isSpace || isSeparator) {
                    state = State.START
                }
            }
            State.ACCEPTED_DEFAULT, State.SPACE_AFTER_PICKED -> {
                state = when {
                    isSpace -> State.SPACE_AFTER_ACCEPTED
                    isSeparator -> State.PUNCTUATION_AFTER_ACCEPTED
                    else -> State.IN_WORD
                }
            }
            State.PICKED_SUGGESTION, State.PICKED_CORRECTION -> {
                state = when {
                    isSpace -> State.SPACE_AFTER_PICKED
                    isSeparator -> State.PUNCTUATION_AFTER_ACCEPTED
                    else -> State.IN_WORD
                }
            }
            State.START, State.UNKNOWN, State.SPACE_AFTER_ACCEPTED,
            State.PUNCTUATION_AFTER_ACCEPTED, State.PUNCTUATION_AFTER_WORD -> {
                state = if (!isSpace && !isSeparator) State.IN_WORD else State.START
            }
            State.UNDO_COMMIT -> {
                state = if (isSpace || isSeparator) State.ACCEPTED_DEFAULT else State.IN_WORD
            }
            State.CORRECTING -> {
                state = State.START
            }
        }
    }

    fun backspace() {
        if (state == State.ACCEPTED_DEFAULT) {
            state = State.UNDO_COMMIT
            autoSuggestUndoneCount++
        } else if (state == State.UNDO_COMMIT) {
            state = State.IN_WORD
        }
        backspaceCount++
    }

    fun reset() {
        state = State.START
    }

    fun getState(): State = state

    fun isCorrecting(): Boolean = state == State.CORRECTING || state == State.PICKED_CORRECTION
}

/**
 * Session statistics data class
 */
data class SessionStats(
    val backspaceCount: Int,
    val autoSuggestCount: Int,
    val autoSuggestUndoneCount: Int,
    val manualSuggestCount: Int,
    val wordNotInDictionaryCount: Int,
    val typedChars: Int,
    val actualChars: Int
) {
    val savedCharacterRatio: Float
        get() = if (actualChars > 0) (actualChars - typedChars).toFloat() / actualChars else 0f
}
