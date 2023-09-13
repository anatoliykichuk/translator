package com.geekbrains.translator.ui.view.main

import androidx.lifecycle.LiveData
import com.geekbrains.translator.domain.inteactor.MainInteractor
import com.geekbrains.translator.ui.common.AppState
import com.geekbrains.translator.ui.common.BaseViewModel
import com.geekbrains.translator.ui.common.parseOnlineSearchResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val interactor: MainInteractor,
) : BaseViewModel<AppState>() {

    private val liveData: LiveData<AppState> = _livedata

    fun subscribe(): LiveData<AppState> {
        return liveData
    }

    override fun getData(word: String, isOnline: Boolean) {
        _livedata.value = AppState.Loading(null)
        cancelJob()

        viewModelCoroutineScope.launch { startInteractor(word, isOnline) }
    }

    override fun handleError(error: Throwable) {
        _livedata.postValue(AppState.Error(error))
    }

    override fun onCleared() {
        _livedata.value = AppState.Success(null)
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