package com.geekbrains.translator.data.source

import com.geekbrains.translator.data.model.DataModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface IApiService {

    @GET("word/search")
    fun searchAsync(@Query("search") wordToSearch: String): Deferred<List<DataModel>>
}