package com.geekbrains.translator.data.source.local

import com.geekbrains.translator.data.model.DataModel
import com.geekbrains.translator.ui.common.AppState
import com.geekbrains.translator.ui.common.convertDataModelToEntity
import com.geekbrains.translator.ui.common.mapHistoryEntityToSearchResult

class RoomClient(private val historyDao: HistoryDao) : IDataSourceLocal<List<DataModel>> {
    override suspend fun getData(word: String): List<DataModel> {
        return mapHistoryEntityToSearchResult(historyDao.getAll())
    }

    override suspend fun saveData(appState: AppState) {
        convertDataModelToEntity(appState)?.let {
            historyDao.insert(it)
        }
    }
}