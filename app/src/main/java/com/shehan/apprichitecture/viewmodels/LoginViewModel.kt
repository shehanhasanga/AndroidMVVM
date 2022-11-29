package com.shehan.apprichitecture.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.shehan.apprichitecture.data.Repository
import com.shehan.apprichitecture.data.database.entities.User
import com.shehan.apprichitecture.data.repositories.UserRepository
import com.shehan.apprichitecture.models.FoodRecipe
import com.shehan.apprichitecture.models.login.AuthResponse
import com.shehan.apprichitecture.models.login.LoginUser
import com.shehan.apprichitecture.util.NetworkListener
import com.shehan.apprichitecture.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: UserRepository,
    application: Application
) : AndroidViewModel(application) {
    private lateinit var networkListener: NetworkListener
    var networkStatus = false
    var loginResponse: MutableLiveData<NetworkResult<AuthResponse>> = MutableLiveData()
    var usersData: LiveData<List<User>> = repository.readUsers().asLiveData()


    fun saveUserDataIntoCashe (user :User) {
        repository.setUser(user)
    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun loginUser(user: LoginUser) {
        viewModelScope.launch{
            loginResponse.value = NetworkResult.Loading()
            if (hasInternetConnection()) {
                try {
                    val response = repository.loginUser(user)
                    loginResponse.value = response

                }catch (e: Exception){
                    println(e)
                    loginResponse.value = NetworkResult.Error("Recipes not found.")
                }
            } else {
                loginResponse.value = NetworkResult.Error("No Internet Connection.")
            }
        }

    }

    private fun handleFoodRecipesResponse(response: Response<AuthResponse>): NetworkResult<AuthResponse> {
        when {
            response.isSuccessful -> {
                val foodRecipes = response.body()
                return NetworkResult.Success(foodRecipes!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

}