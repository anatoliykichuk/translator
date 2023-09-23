package com.geekbrains.repository

interface IRepository<T> {
    suspend fun getData(word: String): T
}