package com.geekbrains.translator.data

import com.geekbrains.translator.data.source.local.HistoryEntity

fun mapHistoryEntityToSearchResult(list: List<HistoryEntity>): List<com.geekbrains.model.data.DataModel> {
    val searchResult = ArrayList<com.geekbrains.model.data.DataModel>()

    if (!list.isNullOrEmpty()) {
        for (entity in list) {
            searchResult.add(com.geekbrains.model.data.DataModel(entity.word, null))
        }
    }

    return searchResult
}

fun convertDataModelSuccessToEntity(appState: com.geekbrains.model.AppState): HistoryEntity? {
    return when (appState) {
        is com.geekbrains.model.AppState.Success -> {
            val searchResult = appState.data

            if (searchResult.isNullOrEmpty() || searchResult[0].text.isNullOrEmpty()) {
                null
            } else {
                HistoryEntity(searchResult[0].text!!, null)
            }
        }
        else -> null
    }
}

fun parseLocalSearchResults(appState: com.geekbrains.model.AppState): com.geekbrains.model.AppState {
    return com.geekbrains.model.AppState.Success(mapResult(appState, false))
}

fun parseOnlineSearchResults(appState: com.geekbrains.model.AppState): com.geekbrains.model.AppState {
    return com.geekbrains.model.AppState.Success(mapResult(appState, true))
}

private fun mapResult(
    appState: com.geekbrains.model.AppState,
    isOnline: Boolean
): List<com.geekbrains.model.data.DataModel> {
    val newSearchResults = arrayListOf<com.geekbrains.model.data.DataModel>()

    when (appState) {
        is com.geekbrains.model.AppState.Success -> {
            getSuccessResultData(appState, isOnline, newSearchResults)
        }
        else -> {
            // Nothing to do
        }
    }

    return newSearchResults
}

private fun getSuccessResultData(
    appState: com.geekbrains.model.AppState.Success,
    isOnline: Boolean,
    newDataModels: ArrayList<com.geekbrains.model.data.DataModel>
) {
    val dataModels: List<com.geekbrains.model.data.DataModel> = appState.data as List<com.geekbrains.model.data.DataModel>
    if (dataModels.isNotEmpty()) {
        if (isOnline) {
            for (searchResult in dataModels) {
                parseOnlineResult(searchResult, newDataModels)
            }
        } else {
            for (searchResult in dataModels) {
                newDataModels.add(
                    com.geekbrains.model.data.DataModel(
                        searchResult.text,
                        arrayListOf()
                    )
                )
            }
        }
    }
}

private fun parseOnlineResult(dataModel: com.geekbrains.model.data.DataModel, newDataModels: ArrayList<com.geekbrains.model.data.DataModel>) {
    if (!dataModel.text.isNullOrBlank() && !dataModel.meanings.isNullOrEmpty()) {
        val newMeanings = arrayListOf<com.geekbrains.model.data.Meanings>()

        dataModel.meanings?.let {
            for (meaning in dataModel.meanings!!) {
                if (meaning.translation != null && !meaning.translation!!.text.isNullOrBlank()) {
                    newMeanings.add(
                        com.geekbrains.model.data.Meanings(
                            meaning.translation,
                            meaning.imageUrl
                        )
                    )
                }
            }
        }

        if (newMeanings.isNotEmpty()) {
            newDataModels.add(com.geekbrains.model.data.DataModel(dataModel.text, newMeanings))
        }
    }
}

fun parseSearchResults(state: com.geekbrains.model.AppState): com.geekbrains.model.AppState {
    val newSearchResults = arrayListOf<com.geekbrains.model.data.DataModel>()
    when (state) {
        is com.geekbrains.model.AppState.Success -> {
            val searchResults = state.data
            if (!searchResults.isNullOrEmpty()) {
                for (searchResult in searchResults) {
                    parseResult(searchResult, newSearchResults)
                }
            }
        }

        else -> {}
    }

    return com.geekbrains.model.AppState.Success(newSearchResults)
}

private fun parseResult(dataModel: com.geekbrains.model.data.DataModel, newDataModels: ArrayList<com.geekbrains.model.data.DataModel>) {
    if (!dataModel.text.isNullOrBlank() && !dataModel.meanings.isNullOrEmpty()) {
        val newMeanings = arrayListOf<com.geekbrains.model.data.Meanings>()

        for (meaning in dataModel.meanings!!) {
            if (meaning.translation != null && !meaning.translation!!.text.isNullOrBlank()) {
                newMeanings.add(
                    com.geekbrains.model.data.Meanings(
                        meaning.translation,
                        meaning.imageUrl
                    )
                )
            }
        }

        if (newMeanings.isNotEmpty()) {
            newDataModels.add(com.geekbrains.model.data.DataModel(dataModel.text, newMeanings))
        }
    }
}

fun convertMeaningsToString(meanings: List<com.geekbrains.model.data.Meanings>): String {
    var meaningsSeparatedByComma = String()
    for ((index, meaning) in meanings.withIndex()) {
        meaningsSeparatedByComma += if (index + 1 != meanings.size) {
            String.format("%s%s", meaning.translation?.text, ", ")
        } else {
            meaning.translation?.text
        }
    }
    return meaningsSeparatedByComma
}