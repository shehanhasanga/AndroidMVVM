package com.shehan.apprichitecture

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.shehan.apprichitecture.adapters.LocationAdapter
import com.shehan.apprichitecture.adapters.RecipesAdapter
import com.shehan.apprichitecture.data.database.entities.User
import com.shehan.apprichitecture.databinding.ActivityLocationBinding
import com.shehan.apprichitecture.models.location.LocationResponse
import com.shehan.apprichitecture.util.NetworkResult
import com.shehan.apprichitecture.viewmodels.LocationViewModel
import com.shehan.apprichitecture.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLocationBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var locationViewModel: LocationViewModel
    private var userData : User? = null
    private val mAdapter by lazy { LocationAdapter() }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationBinding.inflate(layoutInflater)
        locationViewModel = ViewModelProvider(this@LocationActivity)[LocationViewModel::class.java]
        userData = locationViewModel.userData
        setContentView(binding.root)
        setupRecyclerView()
        binding.swipeRefresh.setOnRefreshListener {
            retrieveLocations()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun retrieveLocations() {
        println("location data ++++++++++++++++++++")
        println(userData?.token)
        println(userData?.clinicID)
        if(userData != null){
            println("getting location data ++++++++++++++++")
            locationViewModel.getLocations(userData!!.token.toString(), userData!!.clinicID.toString())
        }


        locationViewModel.locationResponse.observe(this@LocationActivity){res ->
            when (res) {
                is NetworkResult.Success -> {
                    println("this is loaded")
                    println(res.data?.get(0)?.ClinicID)
                    Toast.makeText(
                        this@LocationActivity,
                        res.data?.size.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    res.data?.let { mAdapter.setData(it) }
                    hideSwipeRefresh()
//                    val intend = Intent(this@LoginActivity, MainActivity::class.java)
//                    startActivity(intend)
                }
                is NetworkResult.Error -> {
                    println("this is loaded +++++")
//                    println(res)

                    Toast.makeText(
                        this@LocationActivity,
                        res.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    println("this is loadinng")
                }
            }

        }
    }

    private fun setupRecyclerView() {
        binding.rvLocations.adapter = mAdapter
        binding.rvLocations.layoutManager = LinearLayoutManager(this@LocationActivity)

    }

    private fun hideSwipeRefresh() {
        if(binding.swipeRefresh != null){
            binding.swipeRefresh.isRefreshing = false
        }
    }


}