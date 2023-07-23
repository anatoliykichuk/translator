package com.geekbrains.translator.ui.presenter

import com.geekbrains.translator.data.inteactor.MainInteractor
import com.geekbrains.translator.data.repository.RepositoryLocal
import com.geekbrains.translator.data.repository.RepositoryRemote
import com.geekbrains.translator.data.source.DataSourceLocal
import com.geekbrains.translator.data.source.DataSourceRemote
import com.geekbrains.translator.ui.common.AppState
import com.geekbrains.translator.ui.common.IView
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver

class MainPresenter<T : AppState, V : IView>(
    private val interactor: MainInteractor = MainInteractor(
        RepositoryRemote(DataSourceRemote()),
        RepositoryLocal(DataSourceLocal())
    ),
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable(),
    protected val schedulerProvider: SchedulerProvider = SchedulerProvider()

) : IPresenter<T, V> {

    private var currentView: V? = null

    override fun attachView(view: V) {
        if (view != currentView) {
            currentView = view
        }
    }

    override fun detachView(view: V) {
        compositeDisposable.clear()

        if (view == currentView) {
            currentView = null
        }
    }

    override fun getData(word: String, isOnline: Boolean) {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe { currentView?.renderData(AppState.Loading(null)) }
                .subscribeWith(getObserver())
        )
    }

    private fun getObserver(): DisposableObserver<AppState> {
        return object : DisposableObserver<AppState>() {
            override fun onNext(appState: AppState) {
                currentView?.renderData(appState)
            }

            override fun onError(e: Throwable) {
                currentView?.renderData(AppState.Error(e))
            }

            override fun onComplete() { }
        }
    }
}