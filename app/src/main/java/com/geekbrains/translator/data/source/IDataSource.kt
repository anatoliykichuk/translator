package com.geekbrains.translator.data.source

interface IDataSource<T> {
    suspend fun getData(word: String): T
}