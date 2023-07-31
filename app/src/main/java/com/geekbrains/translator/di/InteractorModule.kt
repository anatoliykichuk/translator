package com.geekbrains.translator.di

import com.geekbrains.translator.data.inteactor.MainInteractor
import com.geekbrains.translator.data.model.DataModel
import com.geekbrains.translator.data.repository.IRepository
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