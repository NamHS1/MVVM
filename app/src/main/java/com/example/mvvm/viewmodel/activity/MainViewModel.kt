@file:Suppress("UNUSED_EXPRESSION")

package com.example.mvvm.viewmodel.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvm.base.BaseViewModel
import com.example.mvvm.view.Data
import io.reactivex.Observable

class MainViewModel : BaseViewModel() {

    private var _liveData = MutableLiveData<Data>()
    val liveData: LiveData<Data> = _liveData

    override fun observeViewModel() {
        Observable.just(Data("Hello")).fetchData(
            success = {
                _liveData.value = it
            },
            error = {
                _liveData.value = Data(null, it)
            })
    }
}