package com.geekbrains.translator.domain.inteactor

interface IInteractor<T : Any> {
    suspend fun getData(word: String, fromRemoteSource: Boolean): T
}