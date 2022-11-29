package com.shehan.apprichitecture.data.network.dataSource

import com.shehan.apprichitecture.data.network.FoodRecipesApi
import com.shehan.apprichitecture.models.FoodRecipe
import com.shehan.apprichitecture.models.login.AuthResponse
import com.shehan.apprichitecture.models.login.LoginUser
import retrofit2.Response
import javax.inject.Inject

class UserRemoteDatasource @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi
) {
    suspend fun loginUser(user: LoginUser): Response<AuthResponse> {
        return foodRecipesApi.authUser(user)
    }
}