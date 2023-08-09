package com.geekbrains.translator.data.source

import com.geekbrains.translator.data.model.DataModel

class DataSourceRemote(
    private val remoteProvider: RetrofitClient = RetrofitClient()
) : IDataSource<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> = remoteProvider.getData(word)
}