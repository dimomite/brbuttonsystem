package org.dimomite.brbuttonsystem.presentation

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

open class DisposingViewModel : ViewModel() {
    private val subscribers = CompositeDisposable()

    override fun onCleared() {
        subscribers.clear()
        super.onCleared()
    }

    /**
     * For Single<> streams.
     * Provided Disposable will be cleared at the end of ViewModel lifecycle.
     */
    protected fun event(ev: DisposingViewModel.() -> Disposable) {
        subscribers.add(ev())
    }
}