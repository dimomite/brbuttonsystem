package org.dimomite.brbuttonsystem.domain.common

interface DataRepository<R> {
    fun provider(): DataProvider<R>
    fun modifier(): DataModifier<R>
}
