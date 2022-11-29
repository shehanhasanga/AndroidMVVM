package com.shehan.apprichitecture.data.network

import com.shehan.apprichitecture.models.FoodJoke
import com.shehan.apprichitecture.models.FoodRecipe
import com.shehan.apprichitecture.models.location.LocationResponse
import com.shehan.apprichitecture.models.login.AuthResponse
import com.shehan.apprichitecture.models.login.LoginUser
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface FoodRecipesApi {

    @GET("/recipes/complexSearch")
    suspend fun getRecipes(
        @QueryMap queries: Map<String, String>
    ): Response<FoodRecipe>

    @GET("/recipes/complexSearch")
    suspend fun searchRecipes(
        @QueryMap searchQuery: Map<String, String>
    ): Response<FoodRecipe>

    @GET("food/jokes/random")
    suspend fun getFoodJoke(
        @Query("apiKey") apiKey: String
    ): Response<FoodJoke>

    @POST("/ErgoFlexWebAPIForKOT/authenticatebyusernamepassword")
    suspend fun authUser(@Body user: LoginUser) : Response<AuthResponse>

    @POST("/ErgoFlexWebAPIForKOT/api/v1/getlocationsbyclinicid/{CLINIC_ID}")
    suspend fun getLocations(@Header("Authorization") token : String, @Path("CLINIC_ID") clinicId : String) : Response<List<LocationResponse>>


}