package com.geekbrains.translator.data.source.local

import com.geekbrains.translator.data.source.IDataSource
import com.geekbrains.translator.ui.common.AppState

interface IDataSourceLocal<T> : IDataSource<T> {

    suspend fun saveData(appState: AppState)
}