package com.shehan.apprichitecture.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.shehan.apprichitecture.data.repositories.LocationRepository
import com.shehan.apprichitecture.data.repositories.UserRepository
import com.shehan.apprichitecture.models.location.LocationResponse
import com.shehan.apprichitecture.models.login.AuthResponse
import com.shehan.apprichitecture.models.login.LoginUser
import com.shehan.apprichitecture.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val userRepository: UserRepository,
    application: Application
) : AndroidViewModel(application){

    var locationResponse: MutableLiveData<NetworkResult<List<LocationResponse>>> = MutableLiveData()
    val userData = userRepository.loggedUser

    @RequiresApi(Build.VERSION_CODES.M)
    fun getLocations(token:String, clinicId: String) {
        viewModelScope.launch{
            locationResponse.value = NetworkResult.Loading()
            if (hasInternetConnection()) {
                try {
                    val res = locationRepository.getLocations("Bearer " + token, clinicId)
                    locationResponse.value = handleFoodRecipesResponse(res)
                } catch (ex: Exception){
                    println(ex)
                    locationResponse.value = NetworkResult.Error("locations not found.")
                }

            }else {
                locationResponse.value = NetworkResult.Error("No Internet Connection.")
            }

        }

    }

    private fun handleFoodRecipesResponse(response: Response<List<LocationResponse>>): NetworkResult<List<LocationResponse>> {

        when {
            response.isSuccessful -> {
                val locations = response.body()
                return NetworkResult.Success(locations!!)
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