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
            return item is Error<*> && expectedErrorMessage.equals(item.er)
        }
    }

    private fun errMatch(er: String) = ErrorDataContainerMatcher(er)

    private fun isTrueBoolContainer(c: DataContainer<Boolean>) = c is Ok && c.data
    private fun isPending(c: DataContainer<*>) = c is Pending

    @Test
    fun testOneContainerTypeResolution() {
        assertEquals("Wrong result type for Ok input", ContainerType.Ok, resolveContainersType(Ok(10)))
        assertEquals("Wrong result type for Pending input", ContainerType.Pending, resolveContainersType(Pending()))
        assertEquals("Wrong result type for Error input", ContainerType.Error, resolveContainersType(Error("Errr")))
    }

    @Test
    fun testTwoContainersTypeResolution() {
        assertEquals("Wrong result type for two Ok input", ContainerType.Ok, resolveContainersType(Ok(10), Ok(20)))
        assertEquals("Wrong result type for Pending and Ok input", ContainerType.Pending, resolveContainersType(Pending(), Ok(10)))
        assertEquals("Wrong result type for Pending input", ContainerType.Pending, resolveContainersType(Pending(), Pending()))
        assertEquals("Wrong result type for Error and Ok input", ContainerType.Error, resolveContainersType(Error("Errr"), Ok(10)))
        assertEquals("Wrong result type for Error and Pending input", ContainerType.Error, resolveContainersType(Error("Errr"), Pending()))
    }

    @Test
    fun testThreeContainersTypeResolution() {
        assertEquals("Wrong result type for two Ok input", ContainerType.Ok, resolveContainersType(Ok(10), Ok(20), Ok(30)))
        assertEquals("Wrong result type for Pending and Ok input", ContainerType.Pending, resolveContainersType(Pending(), Ok(10), Ok(20)))
        assertEquals("Wrong result type for Pending input", ContainerType.Pending, resolveContainersType(Pending(), Pending(), Pending()))
        assertEquals("Wrong result type for Error, Pending and Ok input", ContainerType.Error, resolveContainersType(Error("Errr"), Ok(10), Pending()))
    }

    @Test
    fun testComposeSingleDataContainer() {
        assertTrue("Wrong result for single Ok input", isTrueBoolContainer(composeDataContainer(Ok(10))))
        assertTrue("Wrong result type for single Pending input", isPending(composeDataContainer(Pending<Int>())))
        assertThat("Wrong result type for single Error input", composeDataContainer(Error<Int>("er1")), errMatch("{er1}"))
    }

    @Test
    fun testComposerTwoDataContainers() {
        assertTrue("Wrong result for two Ok input", isTrueBoolContainer(composeDataContainer(Ok(10), Ok(20))))
        assertTrue("Wrong result for two Pending input", isPending(composeDataContainer(Pending<Int>(), Pending<Int>())))
        assertTrue("Wrong result for Pending and Ok input", isPending(composeDataContainer(Pending<Int>(), Ok(10))))
        assertTrue("Wrong result for Pending and Ok input", isPending(composeDataContainer(Ok(10), Pending<String>())))
        assertThat("Wrong result for two Error input", composeDataContainer(Error<Int>("er1"), Error<String>("er2")), errMatch("{er1}, {er2}"))
        assertThat("Wrong result for two Error input", composeDataContainer(Error<Int>("er2"), Error<String>("er1")), errMatch("{er2}, {er1}"))
        assertThat("Wrong result for Error and Pending input", composeDataContainer(Error<Int>("er1"), Pending<Int>()), errMatch("{er1}"))
        assertThat("Wrong result for Pending and Error input", composeDataContainer(Pending<Int>(), Error<Double>("er1")), errMatch("{er1}"))
        assertThat("Wrong result for Error and Ok input", composeDataContainer(Error<Int>("er1"), Ok(10)), errMatch("{er1}"))
        assertThat("Wrong result for OK and Error input", composeDataContainer(Ok(10), Error<Double>("er1")), errMatch("{er1}"))
    }

    @Test
    fun testComposeThreeDataContainers() {
        assertTrue("Wrong result for three Ok input", isTrueBoolContainer(composeDataContainer(Ok(10), Ok(20), Ok(30))))
        assertTrue("Wrong result for three Pending input", isPending(composeDataContainer(Pending<Int>(), Pending<String>(), Pending<Double>())))
        assertThat("Wrong result for Error, Pending and Ok input", composeDataContainer(Error<String>("er1"), Pending<Int>(), Ok(10)), errMatch("{er1}"))
        assertThat("Wrong result for three Error input", composeDataContainer(Error<Int>("er1"), Error<String>("er2"), Error<Double>("er3")), errMatch("{er1}, {er2}, {er3}"))
        assertThat("Wrong result for three Error input", composeDataContainer(Error<Int>("er3"), Error<String>("er2"), Error<Double>("er1")), errMatch("{er3}, {er2}, {er1}"))
    }

    @Test
    fun testDataContainerDiffTypeEquals() {
        assertFalse("Ok and Pending containers are equal", Ok(10).equals(Pending<Int>()))
        assertFalse("Pending and Ok containers are equal", Pending<Int>().equals(Ok(10)))

        assertFalse("Ok and Error containers are equal", Ok(10).equals(Error<Int>("abc")))
        assertFalse("Error and Ok containers are equal", Error<Int>("abc").equals(Ok(10)))

        assertFalse("Pending and Error containers are equal", Pending<Int>().equals(Error<Int>("abc")))
        assertFalse("Error and Pending containers are equal", Error<Int>("abc").equals(Pending<Int>()))
    }

    @Test
    fun testOkContainersEquality() {
        val c10 = Ok(10)
        val c20 = Ok(20)
        val c10_2 = Ok(10)

        assertTrue("Ok containers are not equal", c10.equals(c10_2))
        assertTrue("Ok containers are not equal", c10_2.equals(c10))
        assertEquals("Ok containers hash codes are not equal", c10.hashCode(), c10_2.hashCode())

        assertTrue("Self Ok containers are not equal", c10.equals(c10))
        assertEquals("Self Ok containers hash code are not equal", c10.hashCode(), c10.hashCode())

        assertFalse("Ok containers 10 and 20 are equal", c10.equals(c20))
        assertFalse("Ok containers 20 and 10 are equal", c20.equals(c10))

        val c10L = Ok<Long>(10L)
        assertFalse("Ok containers 10 and 10L are equal", c10.equals(c10L))
        assertFalse("Ok containers 10L and 10 are equal", c10L.equals(c10))

        val c10Str = Ok<String>("10")
        assertFalse("Ok containers 10 and \"10\" are equal", c10.equals(c10Str))
        assertFalse("Ok containers \"10\" and 10 are equal", c10Str.equals(c10))
    }

    @Test
    fun testPendingContainersEquality() {
        val cInt = Pending<Int>()
        val cInt2 = Pending<Int>()
        val cLong = Pending<Long>()
        val cString = Pending<String>()

        assertTrue("Pending Int containers are not equal", cInt.equals(cInt2))
        assertTrue("Pending Int containers are not equal", cInt2.equals(cInt))
        assertEquals("Pending Int containers hash code not equal", cInt.hashCode(), cInt2.hashCode())

        assertTrue("Pending Int containers are not self equal", cInt.equals(cInt))
        assertEquals("Pending Int containers hash codes are not self equal", cInt.hashCode(), cInt.hashCode())

        assertTrue("Pending Int and Long containers are not equal", cInt.equals(cLong))
        assertTrue("Pending Long and Int containers are not equal", cLong.equals(cInt))
        assertEquals("Pending Int and Long containers hash codes are not equal", cInt.hashCode(), cLong.hashCode())

        assertTrue("Pending Int and String containers are not equal", cInt.equals(cString))
        assertTrue("Pending String and Int containers are not equal", cString.equals(cInt))
        assertEquals("Pending Int and String containers hash codes are not equal", cInt.hashCode(), cString.hashCode())
    }

    @Test
    fun testErrorContainersEquality() {
        val cInt = Error<Int>("er1")
        val cInt2 = Error<Int>("er" + "1")
        val cStr1 = Error<String>("er1")
        val cStr2 = Error<String>("er2")

        assertTrue("Error Int containers are not equal", cInt.equals(cInt2))
        assertTrue("Error Int containers are not equal", cInt2.equals(cInt))
        assertEquals("Error Int containers hash codes are not equal", cInt.hashCode(), cInt2.hashCode())

        assertTrue("Error Int container is not self-equal", cInt.equals(cInt))

        assertFalse("Error String containers are equals", cStr1.equals(cStr2))
        assertFalse("Error String containers are equals", cStr2.equals(cStr1))

        assertTrue("Error Int and String containers with same message are not equal", cInt.equals(cStr1))
        assertTrue("Error String and Int containers with same message are not equal", cStr1.equals(cInt))
        assertEquals("Error Int and String containers hash codes with same message are not equal", cInt.hashCode(), cStr1.hashCode())
    }

}
