package com.geekbrains.translator.domain.inteactor

import com.geekbrains.translator.data.model.DataModel
import com.geekbrains.translator.data.repository.IRepository
import com.geekbrains.translator.data.repository.IRepositoryLocal
import com.geekbrains.translator.ui.common.AppState

class MainInteractor(
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