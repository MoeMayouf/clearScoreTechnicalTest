package com.mayouf.clearscoretest.domain.usecase.base

import com.mayouf.clearscoretest.domain.utils.ThreadScheduler
import io.reactivex.Single
import io.reactivex.disposables.Disposable

abstract class SingleUseCase<T>(private val threadScheduler: ThreadScheduler) : BaseUseCase() {

    internal abstract fun createSingleUseCase(): Single<T>

    fun execute(
        onLoading: ((t: Disposable) -> Unit),
        onSuccess: ((t: T) -> Unit),
        onError: ((t: Throwable) -> Unit),
        onFinished: () -> Unit = {}
    ): Single<T> {
        disposeLast()
        lastDisposable = createSingleUseCase()
            .doOnSubscribe(onLoading)
            .subscribeOn(threadScheduler.io())
            .observeOn(threadScheduler.main())
            .doAfterTerminate(onFinished)
            .subscribe(onSuccess, onError)

        lastDisposable?.let {
            compositeDisposable.add(it)
        }
        return createSingleUseCase()
    }
}