package com.geekbrains.translator.data.repository

import com.geekbrains.translator.data.source.IDataSource

class RepositoryRemote(
    val dataSource: IDataSource<List<com.geekbrains.model.data.DataModel>>
) : IRepository<List<com.geekbrains.model.data.DataModel>> {

    override suspend fun getData(word: String): List<com.geekbrains.model.data.DataModel> {
        return dataSource.getData(word)
    }
}