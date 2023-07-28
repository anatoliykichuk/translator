package com.geekbrains.translator.ui.view.main

import androidx.lifecycle.LiveData
import com.geekbrains.translator.data.inteactor.MainInteractor
import com.geekbrains.translator.data.repository.RepositoryLocal
import com.geekbrains.translator.data.repository.RepositoryRemote
import com.geekbrains.translator.data.source.DataSourceLocal
import com.geekbrains.translator.data.source.DataSourceRemote
import com.geekbrains.translator.ui.common.AppState
import com.geekbrains.translator.ui.common.BaseViewModel
import io.reactivex.rxjava3.observers.DisposableObserver

class MainViewModel(
    private val interactor: MainInteractor = MainInteractor(
        RepositoryRemote(DataSourceRemote()),
        RepositoryLocal(DataSourceLocal())
    )
) : BaseViewModel<AppState>() {

    private var appState: AppState? = null

    override fun getData(word: String, isOnline: Boolean): LiveData<AppState> {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe { liveDataForViewForObserve.value = AppState.Loading(null) }
                .subscribeWith(getObserver())
        )
        return super.getData(word, isOnline)
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