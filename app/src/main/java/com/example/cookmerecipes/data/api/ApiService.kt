package com.example.cookmerecipes.data.api

import com.example.cookmerecipes.data.model.RecipeSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {

    @GET("recipes/complexSearch")
    suspend fun getRecipes(@QueryMap queries:Map<String,String>):Response<RecipeSearchResponse>
}