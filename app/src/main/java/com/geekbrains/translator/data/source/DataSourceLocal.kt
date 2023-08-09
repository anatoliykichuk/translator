package com.geekbrains.translator.data.source

import com.geekbrains.translator.data.model.DataModel

class DataSourceLocal(
    private val localProvider: RoomClient = RoomClient()
) : IDataSource<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> = localProvider.getData(word)
}