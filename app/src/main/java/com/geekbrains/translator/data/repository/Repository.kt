package com.geekbrains.translator.data.repository

import com.geekbrains.translator.data.model.DataModel
import com.geekbrains.translator.data.source.IDataSource
import io.reactivex.rxjava3.core.Observable

class Repository(
    private val dataSource: IDataSource<List<DataModel>>
) : IRepository<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> {
        return dataSource.getData(word)
    }
}