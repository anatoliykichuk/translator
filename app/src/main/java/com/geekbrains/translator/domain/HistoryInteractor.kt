package com.geekbrains.translator.domain

import com.geekbrains.core.domain.IInteractor
import com.geekbrains.model.AppState
import com.geekbrains.model.data.DataModel
import com.geekbrains.repository.IRepository
import com.geekbrains.repository.IRepositoryLocal

class HistoryInteractor(
    private val repositoryRemote: IRepository<List<DataModel>>,
    private val repositoryLocal: IRepositoryLocal<List<DataModel>>
) : IInteractor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        return AppState.Success(
            if (fromRemoteSource) {
                repositoryRemote
            } else {
                repositoryLocal
            }.getData(word)
        )
    }
}