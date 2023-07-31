package com.geekbrains.translator.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class ViewModelFactory @Inject constructor(
    private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(viewModelClass: Class<T>): T {
        val creator = viewModels[viewModelClass] ?: viewModels.asIterable()
            .firstOrNull {
                viewModelClass.isAssignableFrom(it.key)
            }?.value ?: throw IllegalArgumentException("unknown view model class $viewModelClass")

        return try {
            creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}