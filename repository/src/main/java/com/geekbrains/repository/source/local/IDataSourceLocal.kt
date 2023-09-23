package com.geekbrains.repository.source.local

import com.geekbrains.model.AppState
import com.geekbrains.repository.source.IDataSource

interface IDataSourceLocal<T> : IDataSource<T> {

    suspend fun saveData(appState: AppState)
}