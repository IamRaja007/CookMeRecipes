package com.example.cookmerecipes.data.database

import androidx.room.*
import com.example.cookmerecipes.data.database.entities.FavouritesEntity
import com.example.cookmerecipes.data.database.entities.FoodJokeEntity
import com.example.cookmerecipes.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipesEntity: RecipesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteRecipes(favouritesEntity: FavouritesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodJoke(foodJoke: FoodJokeEntity)

    @Query("SELECT * FROM recipes_table ORDER BY id ASC")
    fun readRecipes():Flow<List<RecipesEntity>>

    @Query("SELECT * FROM favourite_recipes_table ORDER BY id ASC")
    fun readFavouriteRecipes():Flow<List<FavouritesEntity>>

    @Query("SELECT * FROM food_joke_table ORDER BY id ASC")
    fun readFoodJoke():Flow<List<FoodJokeEntity>>

    @Delete
    suspend fun deleteFavouriteRecipe(favouritesEntity: FavouritesEntity)

    @Query("DELETE FROM favourite_recipes_table")
    suspend fun deleteAllFavouriteRecipes()
}