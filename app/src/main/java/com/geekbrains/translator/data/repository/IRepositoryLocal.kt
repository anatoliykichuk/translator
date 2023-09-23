package com.geekbrains.translator.data.repository

interface IRepositoryLocal<T> : IRepository<T> {

    suspend fun saveData(appState: com.geekbrains.model.AppState)
}