package com.geekbrains.translator.data.source.local

import com.geekbrains.translator.data.convertDataModelSuccessToEntity
import com.geekbrains.translator.data.mapHistoryEntityToSearchResult
import com.geekbrains.translator.data.model.DataModel
import com.geekbrains.translator.ui.common.AppState

class RoomClient(private val historyDao: HistoryDao) : IDataSourceLocal<List<DataModel>> {
    override suspend fun getData(word: String): List<DataModel> {
        return if (word.isEmpty()) {
            mapHistoryEntityToSearchResult(historyDao.getAll())
        } else {
            mapHistoryEntityToSearchResult(
                listOf(historyDao.getDataByWord(word))
            )
        }
    }

    override suspend fun saveData(appState: AppState) {
        convertDataModelSuccessToEntity(appState)?.let {
            historyDao.insert(it)
        }
    }
}