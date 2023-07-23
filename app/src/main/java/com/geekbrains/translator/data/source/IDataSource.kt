package com.geekbrains.translator.data.source

import io.reactivex.rxjava3.core.Observable

interface IDataSource<T : Any> {
    fun getData(word: String): Observable<T>
}