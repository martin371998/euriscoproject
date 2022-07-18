package com.example.euriskocodechallenge.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.euriskocodechallenge.R
import com.example.euriskocodechallenge.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.newsFragment, R.id.moreFragment))

        navController.addOnDestinationChangedListener {_,destination,_ ->
            when (destination.id){
                R.id.newsFragment -> {
                    binding.bottomNav.visibility = VISIBLE
                }
                R.id.moreFragment -> {
                    binding.bottomNav.visibility = VISIBLE
                }
                else -> binding.bottomNav.visibility = GONE
            }
        }

        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNav.setupWithNavController(navController)

    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment)
        return navController.navigateUp() ||super.onSupportNavigateUp()
    }
}