package org.dimomite.brbuttonsystem.domain.common

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.subjects.BehaviorSubject

class BehaviorDataModifier<D> constructor(private val subj: BehaviorSubject<DataContainer<D>>) : DataModifier<D> {
    constructor(provider: BehaviorDataProvider<D>) : this(provider.subj)

    override fun modifyData(patch: Function<D, D>): Single<Unit> {
        return subj.value.exec(object : DataContainer.Visitor<D, Single<Unit>> {
            override fun visitOk(v: DataContainer.Ok<D>): Single<Unit> {
                val current: D = v.data
                val next: D = patch.apply(current)
                subj.onNext(DataContainer.Ok(next))
                return Single.just(Unit)
            }

            override fun visitPending(v: DataContainer.Pending<D>): Single<Unit> = Single.error(IllegalStateException("Cannot modify Pending value"))
            override fun visitError(v: DataContainer.Error<D>): Single<Unit> = Single.error(IllegalStateException("Cannot modify Error value"))
        })
    }

}
