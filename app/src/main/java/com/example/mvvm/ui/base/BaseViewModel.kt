package com.example.mvvm.ui.base

import androidx.lifecycle.ViewModel
import com.example.mvvm.data.enum.State
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

abstract class BaseViewModel : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun Disposable.track() = compositeDisposable.add(this)

    inline fun <T> Observable<T>.fetchData(
        crossinline success: (T) -> Unit,
        crossinline error: (Throwable) -> Unit,
        crossinline state: (State) -> Unit
    ) {
        subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                state(State.LOADING)
            }
            .subscribe(
                { data ->
                    state(State.SUCCESS)
                    success(data)
                },
                { throwable ->
                    state(State.ERROR)
                    error(throwable)
                }
            ).track()
    }

    override fun onCleared() = compositeDisposable.dispose()
}