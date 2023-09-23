package com.geekbrains.repository

import com.geekbrains.model.AppState
import com.geekbrains.model.data.DataModel
import com.geekbrains.repository.source.local.IDataSourceLocal

class RepositoryLocal(
    val dataSource: IDataSourceLocal<List<DataModel>>
) : IRepositoryLocal<List<DataModel>> {
    override suspend fun getData(word: String): List<DataModel> {
        return dataSource.getData(word)
    }

    override suspend fun saveData(appState: AppState) {
        dataSource.saveData(appState)
    }
}