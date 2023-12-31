package com.geekbrains.translator.di

import androidx.room.Room
import com.geekbrains.model.dto.SearchResultDto
import com.geekbrains.repository.IRepository
import com.geekbrains.repository.IRepositoryLocal
import com.geekbrains.repository.RepositoryLocal
import com.geekbrains.repository.RepositoryRemote
import com.geekbrains.repository.source.local.HistoryDatabase
import com.geekbrains.repository.source.local.RoomClient
import com.geekbrains.repository.source.remote.RetrofitClient
import com.geekbrains.translator.domain.HistoryInteractor
import com.geekbrains.translator.domain.MainInteractor
import com.geekbrains.translator.ui.main.MainActivity
import com.geekbrains.translator.ui.main.MainViewModel
import com.geekbrains.translator.ui.pages.history.HistoryActivity
import com.geekbrains.translator.ui.pages.history.HistoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val application = module {
    single { Room.databaseBuilder(get(), HistoryDatabase::class.java, "HistoryDB").build() }
    single { get<HistoryDatabase>().historyDao() }
    single<IRepositoryLocal<List<SearchResultDto>>> {
        RepositoryLocal(RoomClient(get()))
    }
    single<IRepository<List<SearchResultDto>>> {
        RepositoryRemote(RetrofitClient())
    }
}

val mainScreen = module {
    scope(named<MainActivity>()) {
        scoped { MainInteractor(get(), get()) }
        viewModel { MainViewModel(get()) }
    }
}

val historyScreen = module {
    scope(named<HistoryActivity>()) {
        scoped { HistoryInteractor(get(), get()) }
        viewModel { HistoryViewModel(get()) }
    }
}
