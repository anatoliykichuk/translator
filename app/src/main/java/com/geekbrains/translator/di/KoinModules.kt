package com.geekbrains.translator.di

import androidx.room.Room
import com.geekbrains.model.data.DataModel
import com.geekbrains.translator.domain.inteactor.HistoryInteractor
import com.geekbrains.translator.domain.inteactor.MainInteractor
import com.geekbrains.translator.ui.view.main.MainViewModel
import com.geekbrains.translator.ui.view.pages.history.HistoryViewModel
import org.koin.dsl.module

val application = module {
    single { Room.databaseBuilder(get(), com.geekbrains.repository.source.local.HistoryDatabase::class.java, "HistoryDB").build() }
    single { get<com.geekbrains.repository.source.local.HistoryDatabase>().historyDao() }
    single<com.geekbrains.repository.IRepositoryLocal<List<DataModel>>> {
        com.geekbrains.repository.RepositoryLocal(
            com.geekbrains.repository.source.local.RoomClient(get())
        )
    }
    single<com.geekbrains.repository.IRepository<List<DataModel>>> {
        com.geekbrains.repository.RepositoryRemote(
            com.geekbrains.repository.source.remote.RetrofitClient()
        )
    }
}

val mainScreen = module {
    factory { MainInteractor(get(), get()) }
    factory { MainViewModel(get()) }
}

val historyScreen = module {
    factory { HistoryInteractor(get(), get()) }
    factory { HistoryViewModel(get()) }
}