package com.geekbrains.translator.data.inteactor

import io.reactivex.rxjava3.core.Observable

interface IInteractor<T : Any> {
    fun getData(word: String, fromRemoteSource: Boolean): Observable<T>
}