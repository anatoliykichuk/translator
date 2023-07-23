package com.geekbrains.translator.ui.presenter

import com.geekbrains.translator.ui.common.AppState
import com.geekbrains.translator.ui.common.IView

interface IPresenter<T : AppState, V : IView> {
    fun attachView(view: V)
    fun detachView(view: V)
    fun getData(word: String, isOnline: Boolean)
}