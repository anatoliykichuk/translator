package com.geekbrains.translator.di.koin

import com.geekbrains.translator.data.inteactor.MainInteractor
import com.geekbrains.translator.data.model.DataModel
import com.geekbrains.translator.data.repository.IRepository
import com.geekbrains.translator.data.repository.Repository
import com.geekbrains.translator.data.source.RetrofitClient
import com.geekbrains.translator.data.source.RoomClient
import com.geekbrains.translator.di.REPOSITORY_NAME_LOCAL
import com.geekbrains.translator.di.REPOSITORY_NAME_REMOTE
import com.geekbrains.translator.ui.view.main.MainViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val application = module {
    single<IRepository<List<DataModel>>>(named(REPOSITORY_NAME_REMOTE)) {
        Repository(RetrofitClient())
    }

    single<IRepository<List<DataModel>>>(named(REPOSITORY_NAME_LOCAL)) {
        Repository(RoomClient())
    }
}

val mainScreen = module {
    factory {
        MainInteractor(
            get(named(REPOSITORY_NAME_REMOTE)), get(named(REPOSITORY_NAME_LOCAL))
        )
    }

    factory {
        MainViewModel(get())
    }
}