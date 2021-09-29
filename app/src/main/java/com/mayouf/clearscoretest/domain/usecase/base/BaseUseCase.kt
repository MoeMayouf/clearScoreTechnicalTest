package com.mayouf.clearscoretest.domain.usecase.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseUseCase {

    protected var lastDisposable: Disposable? = null
    protected var compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun disposeLast() {
        lastDisposable?.let {
            if (!it.isDisposed) it.dispose()
        }
    }

    fun disposeCompositeDisposable() {
        compositeDisposable.clear()
    }

}