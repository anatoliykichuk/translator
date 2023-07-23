package com.geekbrains.translator.data.source

import com.geekbrains.translator.data.model.DataModel
import io.reactivex.rxjava3.core.Observable

class DataSourceRemote(
    private val remoteProvider: RetrofitClient = RetrofitClient()
) : IDataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> = remoteProvider.getData(word)
}