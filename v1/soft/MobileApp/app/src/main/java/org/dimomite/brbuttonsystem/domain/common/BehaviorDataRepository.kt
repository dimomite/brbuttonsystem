package org.dimomite.brbuttonsystem.domain.common

import androidx.annotation.CallSuper
import io.reactivex.rxjava3.disposables.CompositeDisposable
import timber.log.Timber

/**
 * Override [onDataUpdated] to do extra actions on data change.
 */
open class BehaviorDataRepository<D>(val initialValue: DataContainer<D>) : DataRepository<D> {
    private val localProvider = BehaviorDataProvider<D>(initialValue)
    private val localModifier = BehaviorDataModifier<D>(localProvider)

    override fun provider(): DataProvider<D> = localProvider
    override fun modifier(): DataModifier<D> = localModifier

    private val subs = CompositeDisposable()

    init {
        this.init()
    }

    /**
     * This method is called in response to value update in local [io.reactivex.rxjava3.subjects.BehaviorSubject].
     * This means that call [DataModifier.modifyData] may not cause invocation of this method if value was not changed
     * (or if [equals] behaves not correctly).
     */
    open fun onDataUpdated(newData: DataContainer<D>) {
    }

    @CallSuper
    open fun init() {
        subs.add(provider().outFlow().subscribe({ this.onDataUpdated(it) }, { Timber.w("Error in data flow: $it") }))
    }

    @CallSuper
    open fun release() {
        subs.clear()
    }

}
