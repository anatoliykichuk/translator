package com.geekbrains.translator.data.source

import com.geekbrains.translator.data.model.DataModel
import io.reactivex.rxjava3.core.Observable

class DataSourceLocal(
    private val localProvider: RoomClient = RoomClient()
) : IDataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> = localProvider.getData(word)
}