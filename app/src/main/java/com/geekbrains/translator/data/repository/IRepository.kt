package com.geekbrains.translator.data.repository

interface IRepository<T> {
    suspend fun getData(word: String): T
}