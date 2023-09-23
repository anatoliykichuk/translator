package com.geekbrains.translator.data.repository

import com.geekbrains.translator.data.source.local.IDataSourceLocal

class RepositoryLocal(
    val dataSource: IDataSourceLocal<List<com.geekbrains.model.data.DataModel>>
) : IRepositoryLocal<List<com.geekbrains.model.data.DataModel>> {
    override suspend fun getData(word: String): List<com.geekbrains.model.data.DataModel> {
        return dataSource.getData(word)
    }

    override suspend fun saveData(appState: com.geekbrains.model.AppState) {
        dataSource.saveData(appState)
    }
}