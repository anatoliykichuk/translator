package com.geekbrains.translator.data.source

import com.geekbrains.translator.data.model.DataModel

class RoomClient : IDataSource<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        TODO("Not yet implemented")
    }
}