package com.geekbrains.translator.ui.view.main

import androidx.lifecycle.LiveData
import com.geekbrains.translator.data.inteactor.MainInteractor
import com.geekbrains.translator.ui.common.AppState
import com.geekbrains.translator.ui.common.BaseViewModel
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableObserver
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val interactor: MainInteractor
) : BaseViewModel<AppState>() {

    private var appState: AppState? = null

    fun subscribe(): LiveData<AppState> {
        return liveDataForViewForObserve
    }

    override fun getData(word: String, isOnline: Boolean): LiveData<AppState> {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe { doOnSubscribe() }
                .subscribeWith(getObserver())
        )
        return super.getData(word, isOnline)
    }

    private fun doOnSubscribe(): (Disposable) -> Unit = {
        liveDataForViewForObserve.value = AppState.Loading(null)
    }

    private fun getObserver(): DisposableObserver<AppState> {
        return object : DisposableObserver<AppState>() {
            override fun onNext(state: AppState) {
                appState = state
                liveDataForViewForObserve.value = state
            }

            override fun onError(e: Throwable) {
                liveDataForViewForObserve.value = AppState.Error(e)
            }

            override fun onComplete() {}
        }
    }
}