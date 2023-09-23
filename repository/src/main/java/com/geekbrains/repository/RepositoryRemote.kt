package com.geekbrains.repository

import com.geekbrains.model.data.DataModel
import com.geekbrains.repository.source.IDataSource

class RepositoryRemote(
    val dataSource: IDataSource<List<DataModel>>
) : IRepository<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return dataSource.getData(word)
    }
}