package com.geekbrains.translator.ui.common

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.geekbrains.translator.data.inteactor.IInteractor

abstract class BaseActivity<T: AppState, I: IInteractor<T>> : AppCompatActivity() {

    abstract val viewModel: BaseViewModel<T>

    protected var isNetworkAvailable: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        isNetworkAvailable = isOnline(applicationContext)
    }

    override fun onResume() {
        super.onResume()
        isNetworkAvailable = isOnline(applicationContext)
    }

    abstract fun renderData(appState: T)
}