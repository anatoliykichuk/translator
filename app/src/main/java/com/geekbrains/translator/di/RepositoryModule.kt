package com.geekbrains.translator.di

import com.geekbrains.translator.data.model.DataModel
import com.geekbrains.translator.data.repository.IRepository
import com.geekbrains.translator.data.repository.Repository
import com.geekbrains.translator.data.source.IDataSource
import com.geekbrains.translator.data.source.RetrofitClient
import com.geekbrains.translator.data.source.RoomClient
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    @Named(REPOSITORY_NAME_REMOTE)
    internal fun provideRepositoryRemote(
        @Named(REPOSITORY_NAME_REMOTE) dataSourceRemote: IDataSource<List<DataModel>>
    ): IRepository<List<DataModel>> = Repository(dataSourceRemote)

    @Provides
    @Singleton
    @Named(REPOSITORY_NAME_LOCAL)
    internal fun providesRepositoryLocal(
        @Named(REPOSITORY_NAME_LOCAL) dataSourceLocal: IDataSource<List<DataModel>>
    ): IRepository<List<DataModel>> = Repository(dataSourceLocal)

    @Provides
    @Singleton
    @Named(REPOSITORY_NAME_REMOTE)
    internal fun provideDataSourceRemote(): IDataSource<List<DataModel>> = RetrofitClient()

    @Provides
    @Singleton
    @Named(REPOSITORY_NAME_LOCAL)
    internal fun provideDataSourceLocal(): IDataSource<List<DataModel>> = RoomClient()
}