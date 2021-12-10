package com.example.cookmerecipes.data

import com.example.cookmerecipes.data.database.RecipesDao
import com.example.cookmerecipes.data.database.RecipesDatabase
import com.example.cookmerecipes.data.database.RecipesEntity
import com.example.cookmerecipes.data.model.RecipeSearchResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDao:RecipesDao
) {

    fun readDatabase():Flow<List<RecipesEntity>>{
        return recipesDao.readRecipes()
    }

    suspend fun insertRecipes(recipesEntity: RecipesEntity){
        recipesDao.insertRecipes(recipesEntity)
    }
}