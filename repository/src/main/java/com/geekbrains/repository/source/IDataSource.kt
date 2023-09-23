package com.geekbrains.repository.source

interface IDataSource<T> {
    suspend fun getData(word: String): T
}