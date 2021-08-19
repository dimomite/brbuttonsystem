package org.dimomite.brbuttonsystem.domain.common

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.subjects.BehaviorSubject
import org.dimomite.brbuttonsystem.domain.channels.ChannelDataHandler
import org.dimomite.brbuttonsystem.domain.channels.DataChannel

class BehaviorDataModifier<D> constructor(private val subj: BehaviorSubject<DataChannel<D>>) : DataModifier<D> {
    constructor(provider: BehaviorDataProvider<D>) : this(provider.subj)

    override fun modifyData(patch: Function<D, D>): Single<Unit> {
        return subj.value.execOnData(object : ChannelDataHandler<D, Single<Unit>> {
            override fun onData(data: D): Single<Unit> {
                val next = patch.apply(data)
                subj.onNext(DataChannel.create(next))
                return Single.just(Unit)
            }

            override fun onNothing(): Single<Unit> = Single.error(IllegalStateException(""))
        })
    }

}
