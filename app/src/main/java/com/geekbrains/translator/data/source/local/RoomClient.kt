package com.geekbrains.translator.data.source.local

import com.geekbrains.translator.data.convertDataModelSuccessToEntity
import com.geekbrains.translator.data.mapHistoryEntityToSearchResult

class RoomClient(private val historyDao: HistoryDao) : IDataSourceLocal<List<com.geekbrains.model.data.DataModel>> {
    override suspend fun getData(word: String): List<com.geekbrains.model.data.DataModel> {
        return if (word.isEmpty()) {
            mapHistoryEntityToSearchResult(historyDao.getAll())
        } else {
            mapHistoryEntityToSearchResult(
                listOf(historyDao.getDataByWord(word))
            )
        }
    }

    override suspend fun saveData(appState: com.geekbrains.model.AppState) {
        convertDataModelSuccessToEntity(appState)?.let {
            historyDao.insert(it)
        }
    }
}