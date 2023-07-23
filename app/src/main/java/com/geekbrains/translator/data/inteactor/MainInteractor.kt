package com.geekbrains.translator.data.inteactor

import com.geekbrains.translator.data.model.DataModel
import com.geekbrains.translator.data.repository.IRepository
import com.geekbrains.translator.ui.common.AppState
import io.reactivex.rxjava3.core.Observable

class MainInteractor(
    private val repositoryRemote: IRepository<List<DataModel>>,
    private val repositoryLocal: IRepository<List<DataModel>>
) : IInteractor<AppState> {

    override fun getData(word: String, fromRemoteSource: Boolean): Observable<AppState> {
        return if (fromRemoteSource) {
            repositoryRemote.getData(word).map { AppState.Success(it) }
        } else {
            repositoryLocal.getData(word).map { AppState.Success(it) }
        }
    }
}