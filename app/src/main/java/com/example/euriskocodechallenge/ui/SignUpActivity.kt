package com.example.euriskocodechallenge.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import com.example.euriskocodechallenge.R
import com.example.euriskocodechallenge.model.User
import com.example.euriskocodechallenge.databinding.ActivitySignUpBinding
import com.example.euriskocodechallenge.ui.home.HomeActivity
import com.example.euriskocodechallenge.utils.Constants
import com.example.euriskocodechallenge.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * This activity is responsible for registering new users
 * and inserting them into the Room Database Table [User]
 */
@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var binding: ActivitySignUpBinding
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        //Check if fields are valid, then insert new user
        //We can also add Check if User exist with same email address
        binding.btnSignup.setOnClickListener {
            if (validateFields() && validatePass()) {
                val user = User(
                    userId = 0L,
                    email = binding.emailEt.text?.trim().toString(),
                    fName = binding.fNameEt.text?.trim().toString(),
                    lName = binding.lNameEt.text?.trim().toString(),
                    password = binding.passEt.text?.trim().toString(),
                    application.getDrawable(R.drawable.ic_account_circle)!!.toBitmap()
                )
                userViewModel.insertUser(user)
            }
        }
        observeViewModel()
    }

    //This function handles all the LiveData to be observed
    private fun observeViewModel() {
        //On Successful Login, Redirect to HomeActivity and setUserLoggedIn in DataStore
        userViewModel.fetchUser().observe(this) {
            Toast.makeText(this, "Welcome ${it.fName} ${it.lName}", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, HomeActivity::class.java))
        }
        //- Can Be Removed - Checks if user inserted successfully
        userViewModel.fetchInsertedId().observe(this) {
            if (it != -1L && it != 0L) {
                Toast.makeText(this, "Registration Successful", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Insert Failed", Toast.LENGTH_LONG).show()
            }
        }
    }

    //Check if passwords match
    private fun validatePass(): Boolean {
        return when {
            binding.passEt.text.toString() != binding.confirmPassEt.text.toString() -> {
                Toast.makeText(this, "Passwords Don't Match", Toast.LENGTH_LONG).show()
                false
            }
            else -> {
                true
            }
        }
    }

    //Check if fields are empty or null
    private fun validateFields(): Boolean {
        var isValid = true
        when {
            binding.emailEt.text.isNullOrEmpty() -> {
                binding.emailEt.error = Constants.EMPTY_FIELD
                isValid = false
            }
            !binding.emailEt.text!!.trim().toString().matches(Constants.EMAIL_REGEX.toRegex()) -> {
                binding.emailEt.error = Constants.INVALID_EMAIL
                isValid = false
            }
        }
        when {
            binding.fNameEt.text.isNullOrEmpty() -> {
                binding.fNameEt.error = Constants.EMPTY_FIELD
                isValid = false
            }
            !binding.fNameEt.text!!.trim().toString()
                .matches(Constants.WHITE_SPACE_REGEX.toRegex()) -> {
                binding.fNameEt.error = Constants.CONTAINS_WHITESPACE
                isValid = false
            }
        }
        when {
            binding.lNameEt.text.isNullOrEmpty() -> {
                binding.lNameEt.error = Constants.EMPTY_FIELD
                isValid = false
            }
            !binding.lNameEt.text!!.trim().toString()
                .matches(Constants.WHITE_SPACE_REGEX.toRegex()) -> {
                binding.lNameEt.error = Constants.CONTAINS_WHITESPACE
                isValid = false
            }
        }
        when {
            binding.passEt.text.isNullOrEmpty() -> {
                binding.passEt.error = Constants.EMPTY_FIELD
                isValid = false
            }
            binding.passEt.text!!.trim().length < 7 -> {
                binding.passEt.error = Constants.TOO_SHORT
                isValid = false
            }
        }
        when {
            binding.confirmPassEt.text.isNullOrEmpty() -> {
                binding.confirmPassEt.error = Constants.EMPTY_FIELD
                isValid = false
            }
        }
        return isValid
    }

}