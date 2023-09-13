package com.geekbrains.translator.data.repository

import com.geekbrains.translator.ui.common.AppState

interface IRepositoryLocal<T> : IRepository<T> {

    suspend fun saveData(appState: AppState)
}