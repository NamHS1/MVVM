package com.example.mvvm.ui.viewmodel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvm.data.enumtype.State
import com.example.mvvm.ui.base.BaseViewModel
import com.example.mvvm.data.model.home.NowPlaying
import com.example.mvvm.data.model.home.Popular
import com.example.mvvm.data.model.home.UpComing
import com.example.mvvm.usecase.MovieUseCase
import com.example.mvvm.util.Constant
import com.example.mvvm.util.Event
import kotlinx.coroutines.*

class HomeViewModel : BaseViewModel() {

    private var _moviesNowPlaying: MutableLiveData<Event<NowPlaying>> =
        MutableLiveData<Event<NowPlaying>>()
    val moviesNowPlaying: LiveData<Event<NowPlaying>>
        get() = _moviesNowPlaying

    private var _moviesUpComing: MutableLiveData<Event<UpComing>> =
        MutableLiveData<Event<UpComing>>()
    val moviesUpComing: LiveData<Event<UpComing>>
        get() = _moviesUpComing

    private var _moviesPopular: MutableLiveData<Event<Popular>> = MutableLiveData<Event<Popular>>()
    val moviesPopular: LiveData<Event<Popular>>
        get() = _moviesPopular

    private var _stateNowPlaying: MutableLiveData<Event<State>> = MutableLiveData<Event<State>>()
    val stateNowPlaying: LiveData<Event<State>>
        get() = _stateNowPlaying

    private var _stateUpComing: MutableLiveData<Event<State>> = MutableLiveData<Event<State>>()
    val stateUpComing: LiveData<Event<State>>
        get() = _stateUpComing

    private var _statePopular: MutableLiveData<Event<State>> = MutableLiveData<Event<State>>()
    val statePopular: LiveData<Event<State>>
        get() = _statePopular

    init {
        getMoviesNowPlaying()
        getMoviesPopular()
        getMoviesUpComing()
    }

    private var _autoScroll: MutableLiveData<Event<Int>> = MutableLiveData<Event<Int>>()
    val autoScroll: LiveData<Event<Int>>
        get() = _autoScroll

    private var indexAutoScroll: Int = 0

    private val handleAutoScroll = Handler(Looper.getMainLooper())

    private var hasBeenHandled = false

    private val runnableAutoScroll =  Runnable {
        _moviesUpComing.value?.peekContent()?.movies?.size?.let {
            if((indexAutoScroll == it - 1)) {
                indexAutoScroll = 0
            } else {
                indexAutoScroll++
            }
            _autoScroll.value = Event(indexAutoScroll)
        }
    }

    fun setIndexAutoScroll(index: Int) {
        this.indexAutoScroll = index
    }

    fun getMoviesNowPlaying() {
        MovieUseCase.apply {
            val nowPlaying: NowPlaying? = _moviesNowPlaying.value?.peekContent()
            getMoviesNowPlaying(page = getPageNowPlaying(nowPlaying)).fetchData(
                success = {
                    if (it.page < it.totalPage) {
                        _stateNowPlaying.value = Event(State.LOAD_MORE)
                    }

                    _moviesNowPlaying.postValue(Event(it))
                },
                error = {},
                state = {
                    _stateNowPlaying.value = Event(it)
                }
            )
        }
    }

    fun getMoviesPopular() {
        MovieUseCase.apply {
            val popular: Popular? = _moviesPopular.value?.peekContent()
            getMoviesPopular(page = getPagePopular(popular)).fetchData(
                success = {
                    if (it.page < it.totalPage) {
                        _statePopular.value = Event(State.LOAD_MORE)
                    }
                    _moviesPopular.postValue(Event(it))
                },
                error = {},
                state = {
                    _statePopular.value = Event(it)
                }
            )
        }
    }

    fun getMoviesUpComing(page: Int = 1) {
        MovieUseCase.getMoviesUpComing(page = page).fetchData(
            success = {
                _moviesUpComing.value = Event(it)
                startAutoScroll()
            },
            error = {},
            state = {
                _stateUpComing.value = Event(it)
            }
        )
    }

    fun cancelAutoScroll() {
        handleAutoScroll.removeCallbacks(runnableAutoScroll)
        hasBeenHandled = false
    }

    fun startAutoScroll() {
        if (!hasBeenHandled) {
            handleAutoScroll.postDelayed(runnableAutoScroll, Constant.DELAY_TIME)
            hasBeenHandled = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        cancelAutoScroll()
    }
}