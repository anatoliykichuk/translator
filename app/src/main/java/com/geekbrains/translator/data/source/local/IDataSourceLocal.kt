package com.geekbrains.translator.data.source.local

import com.geekbrains.translator.data.source.IDataSource

interface IDataSourceLocal<T> : IDataSource<T> {

    suspend fun saveData(appState: com.geekbrains.model.AppState)
}