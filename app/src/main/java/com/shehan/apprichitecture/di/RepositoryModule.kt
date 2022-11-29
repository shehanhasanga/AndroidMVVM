package com.shehan.apprichitecture.di

import com.shehan.apprichitecture.data.database.datasource.UserLocalDatasource
import com.shehan.apprichitecture.data.network.dataSource.LocationRemoteDatasource
import com.shehan.apprichitecture.data.network.dataSource.UserRemoteDatasource
import com.shehan.apprichitecture.data.repositories.LocationRepository
import com.shehan.apprichitecture.data.repositories.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideUserRepository(
        userRemoteDatasource: UserRemoteDatasource,
        userLocalDatasource: UserLocalDatasource
    ): UserRepository {
        return UserRepository(
            userRemoteDatasource,
            userLocalDatasource,
            Dispatchers.IO
        )
    }

    @Singleton
    @Provides
    fun provideLocationRepository(
        locationRemoteDatasource: LocationRemoteDatasource
    ): LocationRepository {
        return LocationRepository(
            locationRemoteDatasource,
            Dispatchers.IO
        )
    }



}