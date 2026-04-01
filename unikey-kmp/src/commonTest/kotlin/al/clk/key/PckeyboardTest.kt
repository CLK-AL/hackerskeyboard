package al.clk.key

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ModifierKeyStateTest {

    @Test
    fun testInitialState() {
        val state = ModifierKeyState()
        assertFalse(state.isChording)
    }

    @Test
    fun testPressRelease() {
        val state = ModifierKeyState()
        state.onPress()
        assertFalse(state.isChording)
        state.onRelease()
        assertFalse(state.isChording)
    }

    @Test
    fun testChording() {
        val state = ModifierKeyState()
        state.onPress()
        state.onOtherKeyPressed()
        assertTrue(state.isChording)
    }

    @Test
    fun testNotChordingAfterRelease() {
        val state = ModifierKeyState()
        state.onPress()
        state.onOtherKeyPressed()
        assertTrue(state.isChording)
        state.onRelease()
        assertFalse(state.isChording)
    }

    @Test
    fun testOtherKeyPressedWithoutModifier() {
        val state = ModifierKeyState()
        state.onOtherKeyPressed()
        assertFalse(state.isChording)
    }
}

class WordComposerTest {

    @Test
    fun testEmpty() {
        val composer = WordComposer()
        assertEquals(0, composer.size())
        assertNull(composer.typedWord)
    }

    @Test
    fun testAddCharacter() {
        val composer = WordComposer()
        composer.add('a'.code, intArrayOf('a'.code, 's'.code, 'w'.code))
        assertEquals(1, composer.size())
        assertEquals("a", composer.typedWord.toString())
    }

    @Test
    fun testDeleteLast() {
        val composer = WordComposer()
        composer.add('h'.code, intArrayOf('h'.code))
        composer.add('i'.code, intArrayOf('i'.code))
        assertEquals(2, composer.size())
        composer.deleteLast()
        assertEquals(1, composer.size())
        assertEquals("h", composer.typedWord.toString())
    }

    @Test
    fun testReset() {
        val composer = WordComposer()
        composer.add('h'.code, intArrayOf('h'.code))
        composer.add('e'.code, intArrayOf('e'.code))
        composer.add('l'.code, intArrayOf('l'.code))
        composer.add('l'.code, intArrayOf('l'.code))
        composer.add('o'.code, intArrayOf('o'.code))
        assertEquals("hello", composer.typedWord.toString())
        composer.reset()
        assertEquals(0, composer.size())
        assertNull(composer.typedWord)
    }

    @Test
    fun testCapsTracking() {
        val composer = WordComposer()
        composer.add('H'.code, intArrayOf('H'.code))
        composer.add('e'.code, intArrayOf('e'.code))
        composer.add('l'.code, intArrayOf('l'.code))
        composer.add('l'.code, intArrayOf('l'.code))
        composer.add('o'.code, intArrayOf('o'.code))
        assertFalse(composer.isAllUpperCase)
        assertFalse(composer.isMostlyCaps())
    }

    @Test
    fun testAllUpperCase() {
        val composer = WordComposer()
        composer.add('H'.code, intArrayOf('H'.code))
        composer.add('I'.code, intArrayOf('I'.code))
        assertTrue(composer.isAllUpperCase)
    }

    @Test
    fun testPreferredWord() {
        val composer = WordComposer()
        composer.add('h'.code, intArrayOf('h'.code))
        composer.add('l'.code, intArrayOf('l'.code))
        assertEquals("hl", composer.typedWord.toString())
        assertEquals("hl", composer.getPreferredWord().toString())
        composer.setPreferredWord("hello")
        assertEquals("hello", composer.getPreferredWord())
    }

    @Test
    fun testCopyConstructor() {
        val original = WordComposer()
        original.add('h'.code, intArrayOf('h'.code))
        original.add('i'.code, intArrayOf('i'.code))
        original.setPreferredWord("hi there")

        val copy = WordComposer(original)
        assertEquals(2, copy.size())
        assertEquals("hi", copy.typedWord.toString())
        assertEquals("hi there", copy.getPreferredWord())
    }
}

class SwipeTrackerTest {

    @Test
    fun testInitialVelocity() {
        val tracker = SwipeTracker()
        assertEquals(0f, tracker.xVelocity)
        assertEquals(0f, tracker.yVelocity)
    }

    @Test
    fun testAddPoints() {
        val tracker = SwipeTracker()
        tracker.onTouchDown()
        tracker.addPoint(0f, 0f, 0)
        tracker.addPoint(100f, 0f, 100)
        tracker.computeCurrentVelocity(1000)
        assertTrue(tracker.xVelocity > 0)
        assertEquals(0f, tracker.yVelocity, 0.001f)
    }

    @Test
    fun testVerticalSwipe() {
        val tracker = SwipeTracker()
        tracker.onTouchDown()
        tracker.addPoint(0f, 0f, 0)
        tracker.addPoint(0f, 100f, 100)
        tracker.computeCurrentVelocity(1000)
        assertEquals(0f, tracker.xVelocity, 0.001f)
        assertTrue(tracker.yVelocity > 0)
    }

    @Test
    fun testMaxVelocity() {
        val tracker = SwipeTracker()
        tracker.onTouchDown()
        tracker.addPoint(0f, 0f, 0)
        tracker.addPoint(1000f, 0f, 10) // Very fast swipe
        tracker.computeCurrentVelocity(1000, 500f)
        assertEquals(500f, tracker.xVelocity, 0.001f)
    }

    @Test
    fun testRingBufferClear() {
        val buffer = SwipeTracker.EventRingBuffer(4)
        buffer.add(1f, 2f, 100)
        buffer.add(3f, 4f, 200)
        assertEquals(2, buffer.size())
        buffer.clear()
        assertEquals(0, buffer.size())
    }
}

class TextEntryStateTest {

    @Test
    fun testNewSession() {
        TextEntryState.newSession()
        assertEquals(TextEntryState.State.START, TextEntryState.getState())
    }

    @Test
    fun testTypedCharacter() {
        TextEntryState.newSession()
        TextEntryState.typedCharacter('a', false)
        assertEquals(TextEntryState.State.IN_WORD, TextEntryState.getState())
    }

    @Test
    fun testTypedSpace() {
        TextEntryState.newSession()
        TextEntryState.typedCharacter('a', false)
        TextEntryState.typedCharacter(' ', false)
        assertEquals(TextEntryState.State.START, TextEntryState.getState())
    }

    @Test
    fun testAcceptedDefault() {
        TextEntryState.newSession()
        TextEntryState.typedCharacter('h', false)
        TextEntryState.typedCharacter('e', false)
        TextEntryState.acceptedDefault("he", "hello")
        assertEquals(TextEntryState.State.ACCEPTED_DEFAULT, TextEntryState.getState())
    }

    @Test
    fun testBackspace() {
        TextEntryState.newSession()
        TextEntryState.typedCharacter('h', false)
        TextEntryState.acceptedDefault("h", "hello")
        TextEntryState.backspace()
        assertEquals(TextEntryState.State.UNDO_COMMIT, TextEntryState.getState())
    }

    @Test
    fun testCorrecting() {
        TextEntryState.newSession()
        TextEntryState.selectedForCorrection()
        assertTrue(TextEntryState.isCorrecting())
    }

    @Test
    fun testStats() {
        TextEntryState.newSession()
        TextEntryState.acceptedDefault("helo", "hello")
        val stats = TextEntryState.getStats()
        assertEquals(1, stats.autoSuggestCount)
        assertEquals(4, stats.typedChars)
        assertEquals(5, stats.actualChars)
    }
}
