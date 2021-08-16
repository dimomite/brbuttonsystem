package org.dimomite.brbuttonsystem.domain.common

import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.subjects.BehaviorSubject

class BehaviorDataProvider<D> constructor(
    initialValue: DataContainer<D>,
    strategy: BackpressureStrategy = BackpressureStrategy.LATEST
) : DataProvider<D> {
    val subj: BehaviorSubject<DataContainer<D>> = BehaviorSubject.createDefault(initialValue)
    private val flow: Flowable<DataContainer<D>> = subj.toFlowable(strategy).distinctUntilChanged().replay(1).refCount()

    override fun outFlow(): Flowable<DataContainer<D>> = flow

}
