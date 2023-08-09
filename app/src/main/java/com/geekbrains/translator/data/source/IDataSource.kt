package com.geekbrains.translator.data.source

interface IDataSource<T : Any> {
    suspend fun getData(word: String): T
}