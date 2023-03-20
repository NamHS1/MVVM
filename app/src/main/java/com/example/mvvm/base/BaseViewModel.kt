package com.example.mvvm.base

import androidx.lifecycle.ViewModel
import com.example.mvvm.extension.track
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

abstract class BaseViewModel : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    fun getCompositeDisposable() = compositeDisposable

    inline fun <T> Observable<T>.fetchData(
        crossinline success: (T) -> Unit,
        crossinline error: (Throwable) -> Unit
    ) {
        subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { data ->
                    success(data)
                },
                { throwable ->
                    error(throwable)
                }
            ).track(getCompositeDisposable())
    }

    abstract fun observeViewModel()

    override fun onCleared() = compositeDisposable.dispose()
}