package com.example.cookmerecipes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cookmerecipes.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController:NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme) //We changed manifest theme to use SplashScreen Style, so it will show first. But when the Main Activity runs, it will switch back to our appTheme
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController=findNavController(R.id.HostFragment)
        val appBarConfiguration=AppBarConfiguration(setOf(
            R.id.recipesFragment,
            R.id.favouriteRecipesFragment,
            R.id.foodJokeFragment
        ))

        val bottomNavigationView : BottomNavigationView = binding.bottomNavigationView

        bottomNavigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController,appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}