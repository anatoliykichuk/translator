package com.geekbrains.repository

import com.geekbrains.model.AppState
import com.geekbrains.model.dto.SearchResultDto
import com.geekbrains.repository.source.local.IDataSourceLocal

class RepositoryLocal(
    val dataSource: IDataSourceLocal<List<SearchResultDto>>
) : IRepositoryLocal<List<SearchResultDto>> {
    override suspend fun getData(word: String): List<SearchResultDto> {
        return dataSource.getData(word)
    }

    override suspend fun saveData(appState: AppState) {
        dataSource.saveData(appState)
    }
}