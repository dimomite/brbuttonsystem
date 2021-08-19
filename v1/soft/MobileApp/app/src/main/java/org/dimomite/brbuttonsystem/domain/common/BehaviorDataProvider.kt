package org.dimomite.brbuttonsystem.domain.common

import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import org.dimomite.brbuttonsystem.domain.channels.DataChannel

class BehaviorDataProvider<D> constructor(
    initialValue: DataChannel<D>,
    strategy: BackpressureStrategy = BackpressureStrategy.LATEST
) : DataProvider<D> {
    val subj: BehaviorSubject<DataChannel<D>> = BehaviorSubject.createDefault(initialValue)
    private val flow: Flowable<DataChannel<D>> = subj.toFlowable(strategy).distinctUntilChanged().replay(1).refCount()

    override fun outFlow(): Flowable<DataChannel<D>> = flow

}
