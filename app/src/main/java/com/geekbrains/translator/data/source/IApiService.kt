package com.geekbrains.translator.data.source

import com.geekbrains.translator.data.model.DataModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface IApiService {

    @GET("word/search")
    fun search(@Query("search") wordToSearch: String): Observable<List<DataModel>>
}