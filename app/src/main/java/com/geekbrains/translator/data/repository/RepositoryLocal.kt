package com.geekbrains.translator.data.repository

import com.geekbrains.translator.data.model.DataModel
import com.geekbrains.translator.data.source.IDataSource
import io.reactivex.rxjava3.core.Observable

class RepositoryLocal(
    val dataSource: IDataSource<List<DataModel>>
) : IRepository<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> {
        TODO("Not yet implemented")
    }
}