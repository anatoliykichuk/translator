package com.geekbrains.repository

import com.geekbrains.model.dto.SearchResultDto
import com.geekbrains.repository.source.IDataSource

class RepositoryRemote(
    val dataSource: IDataSource<List<SearchResultDto>>
) : IRepository<List<SearchResultDto>> {

    override suspend fun getData(word: String): List<SearchResultDto> {
        return dataSource.getData(word)
    }
}