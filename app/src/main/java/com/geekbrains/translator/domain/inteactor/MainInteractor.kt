package com.geekbrains.translator.domain.inteactor

import com.geekbrains.model.data.DataModel

class MainInteractor(
    private val repositoryRemote: com.geekbrains.repository.IRepository<List<DataModel>>,
    private val repositoryLocal: com.geekbrains.repository.IRepositoryLocal<List<DataModel>>
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