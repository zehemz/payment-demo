package com.lucasbais.paymentdemo.datasource.interactors

import io.reactivex.observers.DisposableObserver


abstract class DefaultDisposableObserver<T> : DisposableObserver<T>() {
    override fun onNext(t: T) {}

    override fun onComplete() {}

    override fun onError(e: Throwable) {}
}