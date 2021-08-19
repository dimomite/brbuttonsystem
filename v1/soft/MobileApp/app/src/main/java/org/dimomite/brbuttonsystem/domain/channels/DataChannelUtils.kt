package org.dimomite.brbuttonsystem.domain.channels

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.FlowableTransformer
import io.reactivex.rxjava3.functions.Function

fun <D, R> mapDataChannelData(dc: DataChannel<D>, mapper: Function<D, R>): DataChannel<R> {
    val dataWrap: DataWrap<D> = dc.data
    val mapped: DataWrap<R> = dataWrap.execR(object : ChannelDataHandler<D, DataWrap<R>> {
        override fun onData(data: D): DataWrap<R> = DataWrap.Ok<R>(mapper.apply(data))
        override fun onNothing(): DataWrap<R> = DataWrap.None.ins()
    })

    return DataChannel(mapped, dc.progress, dc.unhandledStates)
}

class OkOnlyPassingTransformer<T> : FlowableTransformer<DataChannel<T>, T> {
    override fun apply(upstream: Flowable<DataChannel<T>>?): @NonNull Flowable<T>? {
        return upstream?.flatMap {
            it.execOnData(object : ChannelDataHandler<T, Flowable<T>> {
                override fun onData(data: T): Flowable<T> = Flowable.just(data)
                override fun onNothing(): Flowable<T> = Flowable.empty()
            })
        }
    }
}
