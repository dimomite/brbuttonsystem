package org.dimomite.brbuttonsystem.domain.common

import org.dimomite.brbuttonsystem.domain.common.PendingProgress.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PendingProgressCombinerTest {

    @Test
    fun testInProgressUniqueCombine() {
        val p1 = InProgress.instance()
        val p2 = InProgress.instance()
        val p3 = FlagProgress.instance(true)
        val p4 = FlagProgress.instance(false)
        val p5 = FlagProgress.instance(true)

        val type = 123L

        val uniqueResult = combinePendingProgress(type, p1, p2, p1, p3, p4, p5, p1, unique = true)
        assertTrue("Result is not a SyntheticComposite", uniqueResult is SyntheticComposite)
        uniqueResult as SyntheticComposite
        assertEquals("Wrong result type", type, uniqueResult.type())
        val content = uniqueResult.children
        assertEquals("Wrong number of items in result", 3, content.size)
        assertTrue("Result does not contain InProgress instance", content.contains(p1))
        assertTrue("Result does not contain FlagProgress-ready instance", content.contains(p3))
        assertTrue("Result does not contain FlagProgress-non-ready instance", content.contains(p4))
    }

    @Test
    fun testInProgressNonUniqueCombine() {
        val p1 = InProgress.instance()
        val p2 = InProgress.instance()
        val p3 = FlagProgress.instance(true)
        val p4 = FlagProgress.instance(false)
        val p5 = FlagProgress.instance(true)

        val type = 123L

        val nonUniqueResult = combinePendingProgress(type, p1, p2, p1, p3, p4, p5, p1, unique = false)
        assertTrue("Result is not a SyntheticComposite", nonUniqueResult is SyntheticComposite)
        nonUniqueResult as SyntheticComposite
        assertEquals("Wrong result type", type, nonUniqueResult.type())
        val content = nonUniqueResult.children
        assertEquals("Wrong number of items in result", 7, content.size)
        assertEquals("Wrong value in result at position 0", p1, content[0])
        assertEquals("Wrong value in result at position 1", p2, content[1])
        assertEquals("Wrong value in result at position 2", p1, content[2])
        assertEquals("Wrong value in result at position 3", p3, content[3])
        assertEquals("Wrong value in result at position 4", p4, content[4])
        assertEquals("Wrong value in result at position 5", p5, content[5])
        assertEquals("Wrong value in result at position 6", p1, content[6])
    }


}