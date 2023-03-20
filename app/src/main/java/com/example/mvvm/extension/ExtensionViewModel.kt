package com.example.mvvm.extension

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun Disposable.track(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}