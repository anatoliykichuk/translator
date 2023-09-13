package com.geekbrains.translator.data.repository

import com.geekbrains.translator.data.model.DataModel
import com.geekbrains.translator.data.source.local.IDataSourceLocal
import com.geekbrains.translator.ui.common.AppState

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