package com.example.cookmerecipes.util

class Constants {

    companion object {
        const val API_KEY = "697eebe7976e4a178dfa4800f9f0bf91"
        const val BASE_URL = "https://api.spoonacular.com/"

        //Room Database
        const val DATABASE_NAME = "recipes_database"
        const val TABLE_NAME = "recipes_table"

        //Bottom Sheet and Preferences
        const val DEFAULT_MEAL_TYPE = "main course"
        const val DEFAULT_DIET_TYPE = "gluten free"
        const val PREFERENCES_NAME = "User Preferences"
    }
}