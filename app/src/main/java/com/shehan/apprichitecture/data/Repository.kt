package com.shehan.apprichitecture.data

import com.shehan.apprichitecture.data.database.LocalDataSource
import com.shehan.apprichitecture.data.network.RemoteDataSource
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource,
    localDataSource: LocalDataSource
) {

    val remote = remoteDataSource
    val local = localDataSource

}