package com.geekbrains.translator.data.source

import com.geekbrains.translator.data.model.DataModel
import io.reactivex.rxjava3.core.Observable

class RoomClient : IDataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> {
        TODO("Not yet implemented")
    }
}