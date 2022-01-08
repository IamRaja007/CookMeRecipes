package com.example.cookmerecipes.util

class Constants {

    companion object {
        const val API_KEY = "697eebe7976e4a178dfa4800f9f0bf91"
        const val BASE_URL = "https://api.spoonacular.com/"
        const val BASE_IMAGE_URL = "https://spoonacular.com/cdn/ingredients_100x100/"

        //Room Database
        const val DATABASE_NAME = "recipes_database"
        const val RECIPES_TABLE_NAME = "recipes_table"
        const val FAVOURITE_RECIPES_TABLE_NAME = "favourite_recipes_table"

        //Bottom Sheet and Preferences
        const val DEFAULT_MEAL_TYPE = "main course"
        const val DEFAULT_DIET_TYPE = "gluten free"
        const val PREFERENCES_NAME = "User Preferences"
    }
}