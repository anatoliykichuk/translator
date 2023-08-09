package com.geekbrains.translator.data.inteactor

interface IInteractor<T : Any> {
    suspend fun getData(word: String, fromRemoteSource: Boolean): T
}