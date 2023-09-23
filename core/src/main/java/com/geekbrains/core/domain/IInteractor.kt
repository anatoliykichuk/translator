package com.geekbrains.core.domain

interface IInteractor<T : Any> {
    suspend fun getData(word: String, fromRemoteSource: Boolean): T
}