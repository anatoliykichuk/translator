package com.geekbrains.translator.ui.view.pages.history

import androidx.lifecycle.LiveData
import com.geekbrains.translator.domain.inteactor.HistoryInteractor
import com.geekbrains.translator.ui.common.BaseViewModel
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val interactor: HistoryInteractor
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
        _livedata.postValue(
            com.geekbrains.repository.parseLocalSearchResults(
                interactor.getData(word, isOnline)
            )
        )
}