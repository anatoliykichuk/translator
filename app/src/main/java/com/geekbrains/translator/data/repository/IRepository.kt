package com.geekbrains.translator.data.repository

import io.reactivex.rxjava3.core.Observable

interface IRepository<T : Any> {
    fun getData(word: String): Observable<T>
}