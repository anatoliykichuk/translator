package com.geekbrains.repository.source.remote

import com.geekbrains.model.data.DataModel
import com.geekbrains.repository.source.IDataSource
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient : IDataSource<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return getService(BaseInterceptor.interceptor).searchAsync(word).await()
    }

    private fun getService(interceptor: Interceptor): IApiService {
        return createRetrofit(interceptor).create(IApiService::class.java)
    }

    private fun createRetrofit(interceptor: Interceptor): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(createHttpClient(interceptor))
            .build()
    }

    private fun createHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    companion object {
        private const val BASE_URL = "https://dictionary.skyeng.ru/api/public/v1/"
    }
}