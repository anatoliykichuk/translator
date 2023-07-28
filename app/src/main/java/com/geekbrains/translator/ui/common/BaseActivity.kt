package com.geekbrains.translator.ui.common

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity<T: AppState> : AppCompatActivity() {

    abstract val viewModel: BaseViewModel<T>

    abstract fun renderData(appState: T)
}