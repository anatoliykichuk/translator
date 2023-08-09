package com.geekbrains.translator.di.dagger

import com.geekbrains.translator.data.inteactor.MainInteractor
import com.geekbrains.translator.data.model.DataModel
import com.geekbrains.translator.data.repository.IRepository
import com.geekbrains.translator.di.REPOSITORY_NAME_LOCAL
import com.geekbrains.translator.di.REPOSITORY_NAME_REMOTE
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class InteractorModule {

    @Provides
    internal fun provideInteractor(
        @Named(REPOSITORY_NAME_REMOTE) repositoryRemote: IRepository<List<DataModel>>,
        @Named(REPOSITORY_NAME_LOCAL) repositoryLocal: IRepository<List<DataModel>>
    ) = MainInteractor(repositoryRemote, repositoryLocal)
}