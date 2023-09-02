package com.geekbrains.translator.ui.view.main

import androidx.lifecycle.LiveData
import com.geekbrains.translator.domain.inteactor.MainInteractor
import com.geekbrains.translator.ui.common.AppState
import com.geekbrains.translator.ui.common.BaseViewModel
import com.geekbrains.translator.ui.common.parseSearchResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val interactor: MainInteractor) : BaseViewModel<AppState>() {

    private var appState: AppState? = null

    private val liveDataForViewForObserve: LiveData<AppState> = _livedata

    fun subscribe(): LiveData<AppState> {
        return liveDataForViewForObserve
    }

    override fun getData(word: String, isOnline: Boolean) {
        _livedata.value = AppState.Loading(null)
        cancelJob()

        viewModelCoroutineScope.launch { startInteractor(word, isOnline) }
    }

    private suspend fun startInteractor(word: String, isOnline: Boolean) =
        withContext(Dispatchers.IO) {
            _livedata.postValue(
                parseSearchResults(
                    interactor.getData(word, isOnline)
                )
            )
        }

    override fun handleError(error: Throwable) {
        _livedata.postValue(AppState.Error(error))
    }

    override fun onCleared() {
        _livedata.value = AppState.Success(null)
        super.onCleared()
    }
}