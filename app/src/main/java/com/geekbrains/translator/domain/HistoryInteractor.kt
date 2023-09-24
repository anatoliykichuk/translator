package com.geekbrains.translator.domain

import com.geekbrains.core.domain.IInteractor
import com.geekbrains.model.AppState
import com.geekbrains.model.dto.SearchResultDto
import com.geekbrains.repository.IRepository
import com.geekbrains.repository.IRepositoryLocal
import com.geekbrains.repository.mapSearchResultToResult

class HistoryInteractor(
    private val repositoryRemote: IRepository<List<SearchResultDto>>,
    private val repositoryLocal: IRepositoryLocal<List<SearchResultDto>>
) : IInteractor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        return AppState.Success(
            mapSearchResultToResult(
                if (fromRemoteSource) {
                    repositoryRemote
                } else {
                    repositoryLocal
                }.getData(word)
            )
        )
    }
}