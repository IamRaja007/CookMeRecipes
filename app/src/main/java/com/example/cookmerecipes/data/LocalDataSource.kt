package com.example.cookmerecipes.data

import com.example.cookmerecipes.data.database.RecipesDao
import com.example.cookmerecipes.data.database.entities.FavouritesEntity
import com.example.cookmerecipes.data.database.entities.FoodJokeEntity
import com.example.cookmerecipes.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDao:RecipesDao
) {

    fun readRecipes():Flow<List<RecipesEntity>>{
        return recipesDao.readRecipes()
    }

    fun readFavouriteRecipes():Flow<List<FavouritesEntity>>{
        return recipesDao.readFavouriteRecipes()
    }

    fun readFoodJoke():Flow<List<FoodJokeEntity>>{
        return recipesDao.readFoodJoke()
    }

    suspend fun insertRecipes(recipesEntity: RecipesEntity){
        recipesDao.insertRecipes(recipesEntity)
    }

    suspend fun insertFavouriteRecipes(favouritesEntity: FavouritesEntity){
        recipesDao.insertFavouriteRecipes(favouritesEntity)
    }

    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity){
        recipesDao.insertFoodJoke(foodJokeEntity)
    }

    suspend fun deleteFavouriteRecipe(favouritesEntity: FavouritesEntity){
        recipesDao.deleteFavouriteRecipe(favouritesEntity)
    }

    suspend fun deleteAllFavouriteRecipes(){
        recipesDao.deleteAllFavouriteRecipes()
    }
}