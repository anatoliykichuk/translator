package com.geekbrains.repository.source.local

import com.geekbrains.model.AppState
import com.geekbrains.model.data.DataModel
import com.geekbrains.repository.convertDataModelSuccessToEntity
import com.geekbrains.repository.mapHistoryEntityToSearchResult

class RoomClient(private val historyDao: HistoryDao) :
    IDataSourceLocal<List<DataModel>> {
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