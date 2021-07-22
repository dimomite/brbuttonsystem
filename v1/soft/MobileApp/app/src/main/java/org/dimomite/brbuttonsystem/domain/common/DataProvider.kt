package org.dimomite.brbuttonsystem.domain.common

import io.reactivex.rxjava3.core.Flowable

interface DataProvider<R> {
    fun outFlow(): Flowable<DataContainer<R>>
}