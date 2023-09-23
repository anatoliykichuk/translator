package com.geekbrains.translator.ui.view.main

import androidx.lifecycle.LiveData
import com.geekbrains.translator.data.parseOnlineSearchResults
import com.geekbrains.translator.domain.inteactor.MainInteractor
import com.geekbrains.translator.ui.common.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val interactor: MainInteractor,
) : BaseViewModel<com.geekbrains.model.AppState>() {

    private val liveData: LiveData<com.geekbrains.model.AppState> = _livedata

    fun subscribe(): LiveData<com.geekbrains.model.AppState> {
        return liveData
    }

    override fun getData(word: String, isOnline: Boolean) {
        _livedata.value = com.geekbrains.model.AppState.Loading(null)
        cancelJob()

        viewModelCoroutineScope.launch { startInteractor(word, isOnline) }
    }

    override fun handleError(error: Throwable) {
        _livedata.postValue(com.geekbrains.model.AppState.Error(error))
    }

    override fun onCleared() {
        _livedata.value = com.geekbrains.model.AppState.Success(null)
        super.onCleared()
    }

    private suspend fun startInteractor(word: String, isOnline: Boolean) =
        withContext(Dispatchers.IO) {
            _livedata.postValue(
                parseOnlineSearchResults(
                    interactor.getData(word, isOnline)
                )
            )
        }
}