package com.shehan.apprichitecture.data.repositories

import com.shehan.apprichitecture.data.database.LocalDataSource
import com.shehan.apprichitecture.data.database.datasource.UserLocalDatasource
import com.shehan.apprichitecture.data.database.entities.RecipesEntity
import com.shehan.apprichitecture.data.database.entities.User
import com.shehan.apprichitecture.data.network.RemoteDataSource
import com.shehan.apprichitecture.data.network.dataSource.LocationRemoteDatasource
import com.shehan.apprichitecture.data.network.dataSource.UserRemoteDatasource
import com.shehan.apprichitecture.models.location.LocationResponse
import com.shehan.apprichitecture.models.login.AuthResponse
import com.shehan.apprichitecture.models.login.LoginUser
import com.shehan.apprichitecture.util.NetworkResult
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject


class LocationRepository @Inject constructor(
    private val localDatasource: LocationRemoteDatasource,
    private val defaultDispatcher: CoroutineDispatcher
){


    suspend fun getLocations(token: String,  clinicId:String): Response<List<LocationResponse>> {
        return localDatasource.getLocations(token, clinicId)
    }


}