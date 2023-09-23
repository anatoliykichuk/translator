package com.geekbrains.translator.di

import androidx.room.Room
import com.geekbrains.translator.data.repository.IRepository
import com.geekbrains.translator.data.repository.IRepositoryLocal
import com.geekbrains.translator.data.repository.RepositoryLocal
import com.geekbrains.translator.data.repository.RepositoryRemote
import com.geekbrains.translator.data.source.local.HistoryDatabase
import com.geekbrains.translator.data.source.local.RoomClient
import com.geekbrains.translator.data.source.remote.RetrofitClient
import com.geekbrains.translator.domain.inteactor.HistoryInteractor
import com.geekbrains.translator.domain.inteactor.MainInteractor
import com.geekbrains.translator.ui.view.main.MainViewModel
import com.geekbrains.translator.ui.view.pages.history.HistoryViewModel
import org.koin.dsl.module

val application = module {
    single { Room.databaseBuilder(get(), HistoryDatabase::class.java, "HistoryDB").build() }
    single { get<HistoryDatabase>().historyDao() }
    single<IRepositoryLocal<List<com.geekbrains.model.data.DataModel>>> { RepositoryLocal(RoomClient(get())) }
    single<IRepository<List<com.geekbrains.model.data.DataModel>>> { RepositoryRemote(RetrofitClient()) }
}

val mainScreen = module {
    factory { MainInteractor(get(), get()) }
    factory { MainViewModel(get()) }
}

val historyScreen = module {
    factory { HistoryInteractor(get(), get()) }
    factory { HistoryViewModel(get()) }
}