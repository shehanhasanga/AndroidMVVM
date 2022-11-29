package com.shehan.apprichitecture

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.foody.viewmodels.MainViewModel
import com.shehan.apprichitecture.data.database.entities.User
import com.shehan.apprichitecture.databinding.ActivityLoginBinding
import com.shehan.apprichitecture.databinding.ActivityMainBinding
import com.shehan.apprichitecture.models.login.LoginUser
import com.shehan.apprichitecture.util.NetworkListener
import com.shehan.apprichitecture.util.NetworkResult
import com.shehan.apprichitecture.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private var showPassword = false
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var networkListener: NetworkListener

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        supportActionBar?.hide()
        loginViewModel = ViewModelProvider(this@LoginActivity)[LoginViewModel::class.java]
        binding = ActivityLoginBinding.inflate(layoutInflater)
        binding.passwordshow.setOnClickListener{view ->
            showPassword = !showPassword
            if(showPassword){
                binding.passwordshow.setImageResource(R.drawable.ic_baseline_visibility_off_24)
            }else{
                binding.passwordshow.setImageResource(R.drawable.ic_eye)
            }

        }
        lifecycleScope.launchWhenStarted {
            networkListener = NetworkListener()
            networkListener.checkNetworkAvailability(applicationContext)
                .collect { status ->
                    Log.d("NetworkListener", status.toString())
                    loginViewModel.networkStatus = status
                }
        }

        var textWatcher = object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if(binding.password.text.length > 0 && binding.username.text.length > 0){
                    binding.loginbtn.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.backenablecolor))
                }else{
                    binding.loginbtn.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.red))
                }
            }

        }

        binding.password.addTextChangedListener(textWatcher)
        binding.username.addTextChangedListener(textWatcher)
        binding.loginbtn.setOnClickListener{view ->
            val loginUser = LoginUser(binding.username.text.toString(),binding.password.text.toString())
            loginViewModel.loginUser(loginUser)

        }
        loginViewModel.usersData.observe(this@LoginActivity){userList ->
            if(!userList.isEmpty()){
                val savedUser: User = userList.get(0)
                loginViewModel.saveUserDataIntoCashe(savedUser)
                println(savedUser.token)
                if(savedUser != null && savedUser.token != null){
                    val intend = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intend)
                }
            }

        }
        loginViewModel.loginResponse.observe(this@LoginActivity) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    println("this is loaded")
                    println(response.data?.Token)
                    Toast.makeText(
                        this@LoginActivity,
                        response.data?.Token.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
//                    val intend = Intent(this@LoginActivity, MainActivity::class.java)
//                    startActivity(intend)
                }
                is NetworkResult.Error -> {
                    println("this is loaded +++++")
                    Toast.makeText(
                        this@LoginActivity,
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    println("this is loadinng")
                }
            }
        }
        setContentView(binding.root)
    }


}