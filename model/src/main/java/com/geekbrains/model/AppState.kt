package com.geekbrains.model

sealed class AppState {
    data class Success(val data: List<com.geekbrains.model.data.DataModel>?) : AppState()
    data class Error(val error: Throwable) : AppState()
    data class Loading(val progress: Int?) : AppState()
}
