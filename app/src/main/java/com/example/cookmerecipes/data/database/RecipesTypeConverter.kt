package com.example.cookmerecipes.data.database

import androidx.room.TypeConverter
import com.example.cookmerecipes.data.model.RecipeSearchResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/*
    We cannot store complex objects in our database directly, We need to convert them
    to acceptable types first and then store in the database.
    So, with this converter we will convert our RecipeSearchResponse to a JSON or a string
    when we write to the database, and we again convert this to RecipeSearchResponse when
    we retrieve it from the Database.
 */
class RecipesTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun recipeSearchResponseToString(recipeSearchResponse: RecipeSearchResponse): String {
        return gson.toJson(recipeSearchResponse)
    }

    @TypeConverter
    fun stringToRecipeSearchResponse(stringData: String): RecipeSearchResponse {
        val listType = object : TypeToken<RecipeSearchResponse>() {}.type
        return gson.fromJson(stringData, listType)
    }
}