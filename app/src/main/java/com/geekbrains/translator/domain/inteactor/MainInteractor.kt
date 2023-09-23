package com.geekbrains.translator.domain.inteactor

import com.geekbrains.translator.data.repository.IRepository
import com.geekbrains.translator.data.repository.IRepositoryLocal

class MainInteractor(
    private val repositoryRemote: IRepository<List<com.geekbrains.model.data.DataModel>>,
    private val repositoryLocal: IRepositoryLocal<List<com.geekbrains.model.data.DataModel>>
) : IInteractor<com.geekbrains.model.AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): com.geekbrains.model.AppState {
        val data = com.geekbrains.model.AppState.Success(
            if (fromRemoteSource) {
                repositoryRemote
            } else {
                repositoryLocal
            }.getData(word)
        )

        if (fromRemoteSource) {
            repositoryLocal.saveData(data)
        }

        return data
    }
}