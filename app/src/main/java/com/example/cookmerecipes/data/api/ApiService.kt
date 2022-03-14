package com.example.cookmerecipes.data.api

import com.example.cookmerecipes.data.model.FoodJokeResponse
import com.example.cookmerecipes.data.model.RecipeSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiService {

    @GET("recipes/complexSearch")
    suspend fun getRecipes(@QueryMap queries: Map<String, String>): Response<RecipeSearchResponse>

    @GET("recipes/complexSearch")
    suspend fun searchRecipe(@QueryMap searchQuery: Map<String, String>): Response<RecipeSearchResponse>

    @GET("food/jokes/random")
    suspend fun getFoodJoke(
        @Query("apiKey") apiKey:String
    ): Response<FoodJokeResponse>
}