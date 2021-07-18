package org.dimomite.brbuttonsystem.domain.common

import org.dimomite.brbuttonsystem.domain.common.DataContainer.*
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.junit.Assert.*
import org.junit.Test

class DataContainerTest {
    private class ErrorDataContainerMatcher(private val expectedErrorMessage: String) : BaseMatcher<DataContainer<*>>() {
        override fun describeTo(description: Description?) {
            description?.appendText("expected error message: \"$expectedErrorMessage\"")
        }

        override fun matches(item: Any?): Boolean {
            return item is Error && expectedErrorMessage.equals(item.er)
        }
    }

    private fun errMatch(er: String) = ErrorDataContainerMatcher(er)

    private fun isTrueBoolContainer(c: DataContainer<Boolean>) = c is Ok && c.data
    private fun isPending(c: DataContainer<*>) = c is Pending

    @Test
    fun testOneContainerTypeResolution() {
        assertEquals("Wrong result type for Ok input", ContainerType.Ok, resolveContainersType(Ok(10)))
        assertEquals("Wrong result type for Pending input", ContainerType.Pending, resolveContainersType(Pending))
        assertEquals("Wrong result type for Error input", ContainerType.Error, resolveContainersType(Error("Errr")))
    }

    @Test
    fun testTwoContainersTypeResolution() {
        assertEquals("Wrong result type for two Ok input", ContainerType.Ok, resolveContainersType(Ok(10), Ok(20)))
        assertEquals("Wrong result type for Pending and Ok input", ContainerType.Pending, resolveContainersType(Pending, Ok(10)))
        assertEquals("Wrong result type for Pending input", ContainerType.Pending, resolveContainersType(Pending, Pending))
        assertEquals("Wrong result type for Error and Ok input", ContainerType.Error, resolveContainersType(Error("Errr"), Ok(10)))
        assertEquals("Wrong result type for Error and Pending input", ContainerType.Error, resolveContainersType(Error("Errr"), Pending))
    }

    @Test
    fun testThreeContainersTypeResolution() {
        assertEquals("Wrong result type for two Ok input", ContainerType.Ok, resolveContainersType(Ok(10), Ok(20), Ok(30)))
        assertEquals("Wrong result type for Pending and Ok input", ContainerType.Pending, resolveContainersType(Pending, Ok(10), Ok(20)))
        assertEquals("Wrong result type for Pending input", ContainerType.Pending, resolveContainersType(Pending, Pending, Pending))
        assertEquals("Wrong result type for Error, Pending and Ok input", ContainerType.Error, resolveContainersType(Error("Errr"), Ok(10), Pending))
    }

    @Test
    fun testComposeSingleDataContainer() {
        assertTrue("Wrong result for single Ok input", isTrueBoolContainer(composeDataContainer(Ok(10))))
        assertTrue("Wrong result type for single Pending input", isPending(composeDataContainer(Pending)))
        assertThat("Wrong result type for single Error input", composeDataContainer(Error("er1")), errMatch("{er1}"))
    }

    @Test
    fun testComposerTwoDataContainers() {
        assertTrue("Wrong result for two Ok input", isTrueBoolContainer(composeDataContainer(Ok(10), Ok(20))))
        assertTrue("Wrong result for two Pending input", isPending(composeDataContainer(Pending, Pending)))
        assertTrue("Wrong result for Pending and Ok input", isPending(composeDataContainer(Pending, Ok(10))))
        assertTrue("Wrong result for Pending and Ok input", isPending(composeDataContainer(Ok(10), Pending)))
        assertThat("Wrong result for two Error input", composeDataContainer(Error("er1"), Error("er2")), errMatch("{er1}, {er2}"))
        assertThat("Wrong result for two Error input", composeDataContainer(Error("er2"), Error("er1")), errMatch("{er2}, {er1}"))
        assertThat("Wrong result for Error and Pending input", composeDataContainer(Error("er1"), Pending), errMatch("{er1}"))
        assertThat("Wrong result for Pending and Error input", composeDataContainer(Pending, Error("er1")), errMatch("{er1}"))
        assertThat("Wrong result for Error and Ok input", composeDataContainer(Error("er1"), Ok(10)), errMatch("{er1}"))
        assertThat("Wrong result for OK and Error input", composeDataContainer(Ok(10), Error("er1")), errMatch("{er1}"))
    }

    @Test
    fun testComposeThreeDataContainers() {
        assertTrue("Wrong result for three Ok input", isTrueBoolContainer(composeDataContainer(Ok(10), Ok(20), Ok(30))))
        assertTrue("Wrong result for three Pending input", isPending(composeDataContainer(Pending, Pending, Pending)))
        assertThat("Wrong result for Error, Pending and Ok input", composeDataContainer(Error("er1"), Pending, Ok(10)), errMatch("{er1}"))
        assertThat("Wrong result for three Error input", composeDataContainer(Error("er1"), Error("er2"), Error("er3")), errMatch("{er1}, {er2}, {er3}"))
        assertThat("Wrong result for three Error input", composeDataContainer(Error("er3"), Error("er2"), Error("er1")), errMatch("{er3}, {er2}, {er1}"))
    }

}
