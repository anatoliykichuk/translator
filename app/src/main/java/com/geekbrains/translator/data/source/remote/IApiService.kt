package com.geekbrains.translator.data.source.remote

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface IApiService {

    @GET("words/search")
    fun searchAsync(@Query("search") wordToSearch: String): Deferred<List<com.geekbrains.model.data.DataModel>>
}