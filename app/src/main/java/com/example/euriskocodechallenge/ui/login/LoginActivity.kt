package com.example.euriskocodechallenge.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.euriskocodechallenge.common.Utilityfunctions
import com.example.euriskocodechallenge.databinding.ActivityLoginBinding
import com.example.euriskocodechallenge.ui.home.HomeActivity
import com.example.euriskocodechallenge.utils.Constants
import com.example.euriskocodechallenge.ui.home.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * This is the main activity user is represented with
 *
 * User can login if already registered, or alternatively click on signup
 * to be redirected to [SignUpActivity]
 *
 */
@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        //Check if user already LoggedIn if true then redirect to HomeActivity
        userViewModel.isLoggedIn()
        setContentView(binding.root)
        supportActionBar?.hide()
        implementListeners()
        initObservers()
    }


    private fun implementListeners() {
        //Redirect to SignUp Activity
        binding.hyperSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        /*
        Check credentials if they match the user's info in the DB
        Then return user value as LiveData which is observed using fetchUser()
        */
        binding.btnLogin.setOnClickListener {
            if (validateFields()) {
                val uEmail = binding.emailEditText.text?.trim().toString()
                val uPass = binding.passEditText.text?.trim().toString()
                userViewModel.loginUser(uEmail, uPass)
            }
        }
    }

    //This function handles all the LiveData to be observed
    private fun initObservers() {
        //Display Toast Message if an error occurs
        userViewModel.fetchError().observe(this) {
            Utilityfunctions.showtoast(this, it)
        }
        //Get Fetched User after successful login
        //Redirect the user to HomeActivity and setUserLoggedIn in DataStore
        userViewModel.fetchUser().observe(this) {
            Utilityfunctions.showtoast(this, "Welcome ${it.fName} ${it.lName}")
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }


    //Check that fields are not empty or null
    private fun validateFields(): Boolean {
        var isValid = true
        when {
            binding.emailEditText.text.isNullOrEmpty() -> {
                binding.emailEditText.error = Constants.EMPTY_FIELD
                isValid = false
            }
            !binding.emailEditText.text?.trim().toString()
                .matches(Constants.EMAIL_REGEX.toRegex()) -> {
                binding.emailEditText.error = Constants.INVALID_EMAIL
            }

        }
        when {
            binding.passEditText.text.isNullOrEmpty() -> {
                binding.passEditText.error = Constants.EMPTY_FIELD
                isValid = false
            }
        }
        return isValid
    }


}