package com.shehan.apprichitecture.data.repositories

import com.shehan.apprichitecture.data.database.LocalDataSource
import com.shehan.apprichitecture.data.database.datasource.UserLocalDatasource
import com.shehan.apprichitecture.data.database.entities.RecipesEntity
import com.shehan.apprichitecture.data.database.entities.User
import com.shehan.apprichitecture.data.network.RemoteDataSource
import com.shehan.apprichitecture.data.network.dataSource.UserRemoteDatasource
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


class UserRepository @Inject constructor(
    private val userRemoteDatasource: UserRemoteDatasource,
    private val userLocalDatasource: UserLocalDatasource,
    private val defaultDispatcher: CoroutineDispatcher
){
    private val latestNewsMutex = Mutex()
    private  var _loggedUser: User? = null

    public val loggedUser get() = _loggedUser

    fun setUser(user: User) {
        _loggedUser = user
    }

    fun readUsers(): Flow<List<User>> {
        return userLocalDatasource.readUsers()
    }

    suspend fun loginUser(user: LoginUser): NetworkResult<AuthResponse> {
        try {
            val response =  userRemoteDatasource.loginUser(user)
            if(response.isSuccessful){
                val userRes = response.body()
                val user = userRes?.let { convertAuthResponseToUser(it) }
                if(user != null){
                    userLocalDatasource.insertUser(user)
                    _loggedUser = user
                }

            }
            return handleResponse(response)
        }catch (ex: Exception){
            throw Exception(ex)
        }
    }

    private fun handleResponse(response: Response<AuthResponse>) : NetworkResult<AuthResponse>{
        when {
            response.isSuccessful -> {
                val user = response.body()
                return NetworkResult.Success(user!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    private fun convertAuthResponseToUser(res :AuthResponse) : User? {
        val resUser = res
        if (resUser != null) {
            return  User(
                resUser.UserID,resUser.ClinicID,null,null,resUser.FirstName,resUser.LastName,resUser.DateOfBirth,resUser.Email,true,resUser.Token,null,resUser.UserRoleID)
        }else{
            return null
        }

    }
}