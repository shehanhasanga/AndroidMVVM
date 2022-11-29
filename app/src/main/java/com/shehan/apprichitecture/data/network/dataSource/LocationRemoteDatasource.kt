package com.shehan.apprichitecture.data.network.dataSource

import com.shehan.apprichitecture.data.network.FoodRecipesApi
import com.shehan.apprichitecture.models.FoodRecipe
import com.shehan.apprichitecture.models.location.LocationResponse
import com.shehan.apprichitecture.models.login.AuthResponse
import com.shehan.apprichitecture.models.login.LoginUser
import retrofit2.Response
import javax.inject.Inject

class LocationRemoteDatasource @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi
) {
    suspend fun getLocations( token: String,  clinicId:String ): Response<List<LocationResponse>> {
        println(token)
        return foodRecipesApi.getLocations(token, clinicId)
    }
}