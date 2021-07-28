package org.dimomite.brbuttonsystem.domain.common

import org.dimomite.brbuttonsystem.domain.common.PendingProgress.*
import org.junit.Assert.*
import org.junit.Test

class PendingProgressTest {

    @Test
    fun testInProgress() {
        val pp1 = InProgress.instance()
        val pp2 = InProgress.instance()

        assertSame("InProgress instances are not the same", pp1, pp2)
        assertEquals("InProgress instances are not equal", pp1, pp2)
        assertEquals("InProgress instances are not equal", pp2, pp1)
        assertEquals("InProgress instances hash codes are not equal", pp1.hashCode(), pp2.hashCode())

        assertEquals("InProgress instances are not self equal", pp1, pp1)
    }

    @Test
    fun testFlagProgress() {
        val ready1 = FlagProgress.instance(true)
        val ready2 = FlagProgress.instance(true)
        val notReady1 = FlagProgress.instance(false)
        val notReady2 = FlagProgress.instance(false)

        assertSame("FlagProgress ready instances are not the same", ready1, ready2)
        assertEquals("FlagProgress ready instances are not equal", ready1, ready2)
        assertEquals("FlagProgress ready instances are not equal", ready2, ready1)
        assertEquals("FlagProgress ready instances hash codes are not equal", ready1.hashCode(), ready2.hashCode())

        assertSame("FlagProgress not ready instances are not the same", notReady1, notReady2)
        assertEquals("FlagProgress not ready instances are not equal", notReady1, notReady2)
        assertEquals("FlagProgress not ready instances are not equal", notReady2, notReady1)
        assertEquals("FlagProgress not ready instances hash codes are not equal", notReady1.hashCode(), notReady2.hashCode())

        assertTrue("FlagProgress ready flag is not true", (ready1 as FlagProgress).ready)
        assertFalse("FlagProgress not ready flag is not true", (notReady1 as FlagProgress).ready)

        assertEquals("FlagProgress ready instances are not self equal", ready1, ready1)
        assertEquals("FlagProgress not ready instances are not self equal", notReady1, notReady1)
    }

    @Test
    fun testToMaxProgress() {
        val p0 = ToMaxProgress(progress = 0, max = 2)
        val p02 = ToMaxProgress(progress = 0, max = 2)
        val p1 = ToMaxProgress(progress = 1, max = 2)

        assertNotSame("ToMaxProgress instances are the same", p0, p02)
        assertEquals("ToMaxProgress instances are not equal", p0, p02)
        assertEquals("ToMaxProgress instances are not equal", p02, p0)
        assertEquals("ToMaxProgress instances hash codes are not equal", p0.hashCode(), p02.hashCode())

        assertEquals("ToMaxProgress instances are not self equal", p0, p0)

        assertNotEquals("ToMaxProgress instances are not equal", p0, p1)
        assertNotEquals("ToMaxProgress instances are not equal", p1, p0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testCompositeProgressId0Check() {
        CompositeProgress(0, emptyArray())
    }

    @Test(expected = IllegalArgumentException::class)
    fun testCompositeProgressId1Check() {
        CompositeProgress(1, emptyArray())
    }

    @Test(expected = IllegalArgumentException::class)
    fun testCompositeProgressId2Check() {
        CompositeProgress(2, emptyArray())
    }

    @Test(expected = IllegalArgumentException::class)
    fun testCompositeProgressNegativeIdCheck() {
        CompositeProgress(-1, emptyArray())
    }

    @Test
    fun testCompositeProgress() {
        val p = CompositeProgress(3L, emptyArray())
        assertNotNull("CompositeProgress instance is null", p)
    }


    @Test(expected = IllegalArgumentException::class)
    fun testSyntheticCompositeId0Check() {
        SyntheticComposite(0, emptyArray())
    }

    @Test(expected = IllegalArgumentException::class)
    fun testSyntheticCompositeId1Check() {
        SyntheticComposite(1, emptyArray())
    }

    @Test(expected = IllegalArgumentException::class)
    fun testSyntheticCompositeId2Check() {
        SyntheticComposite(2, emptyArray())
    }

    @Test(expected = IllegalArgumentException::class)
    fun testSyntheticCompositeNegativeIdCheck() {
        SyntheticComposite(-1, emptyArray())
    }

    @Test
    fun testSyntheticComposite() {
        val p = SyntheticComposite(3L, emptyArray())
        assertNotNull("CompositeProgress instance is null", p)
    }
}
