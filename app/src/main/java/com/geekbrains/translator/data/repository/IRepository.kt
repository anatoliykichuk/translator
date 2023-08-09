package com.geekbrains.translator.data.repository

interface IRepository<T : Any> {
    suspend fun getData(word: String): T
}