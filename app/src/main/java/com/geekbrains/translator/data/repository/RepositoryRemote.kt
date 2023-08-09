package com.geekbrains.translator.data.repository

import com.geekbrains.translator.data.model.DataModel
import com.geekbrains.translator.data.source.IDataSource

class RepositoryRemote(
    val dataSource: IDataSource<List<DataModel>>
) : IRepository<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return dataSource.getData(word)
    }
}