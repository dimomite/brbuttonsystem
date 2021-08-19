package org.dimomite.brbuttonsystem.domain.common

import io.reactivex.rxjava3.core.Flowable
import org.dimomite.brbuttonsystem.domain.channels.DataChannel

interface DataProvider<R> {
    fun outFlow(): Flowable<DataChannel<R>>
}