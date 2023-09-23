package com.geekbrains.repository

import com.geekbrains.model.AppState

interface IRepositoryLocal<T> : IRepository<T> {

    suspend fun saveData(appState: AppState)
}