package com.example.cookmerecipes.data.api

import com.example.cookmerecipes.data.model.FoodJokeResponse
import com.example.cookmerecipes.data.model.RecipeSearchResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {
   suspend fun getRecipes(queries:Map<String,String>):Response<RecipeSearchResponse>{
        return apiService.getRecipes(queries)
    }

    suspend fun searchRecipe(searchQuery:Map<String,String>):Response<RecipeSearchResponse>{
        return apiService.searchRecipe(searchQuery)
    }

    suspend fun getFoodJoke(apiKey:String):Response<FoodJokeResponse>{
        return apiService.getFoodJoke(apiKey)
    }
}