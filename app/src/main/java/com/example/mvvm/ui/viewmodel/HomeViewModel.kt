package com.example.mvvm.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvm.data.enum.State
import com.example.mvvm.ui.base.BaseViewModel
import com.example.mvvm.data.model.home.NowPlaying
import com.example.mvvm.data.model.home.Popular
import com.example.mvvm.data.model.home.UpComing
import com.example.mvvm.data.usecase.MovieUseCase
import com.example.mvvm.util.Event

class HomeViewModel(
    private val moviesUseCase: MovieUseCase = MovieUseCase(),
    private var _moviesNowPlaying: MutableLiveData<Event<NowPlaying>> = MutableLiveData<Event<NowPlaying>>(),
    private var _moviesUpComing: MutableLiveData<Event<UpComing>> = MutableLiveData<Event<UpComing>>(),
    private var _moviesPopular: MutableLiveData<Event<Popular>> = MutableLiveData<Event<Popular>>(),
    private var _stateNowPlaying: MutableLiveData<Event<State>> = MutableLiveData<Event<State>>(),
    private var _stateUpComing: MutableLiveData<Event<State>> = MutableLiveData<Event<State>>(),
    private var _statePopular: MutableLiveData<Event<State>> = MutableLiveData<Event<State>>(),
) : BaseViewModel() {

    val moviesNowPlaying: LiveData<Event<NowPlaying>>
        get() = _moviesNowPlaying
    val moviesUpComing: LiveData<Event<UpComing>>
        get() = _moviesUpComing
    val moviesPopular: LiveData<Event<Popular>>
        get() = _moviesPopular

    val stateNowPlaying: LiveData<Event<State>>
        get() = _stateNowPlaying
    val stateUpComing: LiveData<Event<State>>
        get() = _stateUpComing
    val statePopular: LiveData<Event<State>>
        get() = _statePopular

    init {
        getMoviesNowPlaying()
        getMoviesPopular()
        getMoviesUpComing()
    }

    fun getMoviesNowPlaying() {
        moviesUseCase.apply {
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
        moviesUseCase.apply {
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
        moviesUseCase.getMoviesUpComing(page = page).fetchData(
            success = {
                _moviesUpComing.value = Event(it)
            },
            error = {},
            state = {
                _stateUpComing.value = Event(it)
            }
        )
    }
}