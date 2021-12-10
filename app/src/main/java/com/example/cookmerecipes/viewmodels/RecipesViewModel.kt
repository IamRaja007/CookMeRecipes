package com.example.cookmerecipes.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.cookmerecipes.util.Constants

class RecipesViewModel(application: Application) : AndroidViewModel(application) {

    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        queries["number"] = "50"
        queries["apiKey"] = Constants.API_KEY
        queries["type"] = "Snack"
        queries["diet"] = "vegan"
        queries["addRecipeInformation"] = "true"
        queries["fillIngredients"] = "true"

        return queries

    }
}