package com.geekbrains.translator.ui.pages.history

import androidx.lifecycle.LiveData
import com.geekbrains.core.ui.BaseViewModel
import com.geekbrains.model.AppState
import com.geekbrains.repository.parseLocalSearchResults
import com.geekbrains.translator.domain.HistoryInteractor
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val interactor: HistoryInteractor
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
        _livedata.postValue(
            parseLocalSearchResults(
                interactor.getData(word, isOnline)
            )
        )
}