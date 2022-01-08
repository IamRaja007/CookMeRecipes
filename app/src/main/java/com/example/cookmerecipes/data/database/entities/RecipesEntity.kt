package com.example.cookmerecipes.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cookmerecipes.data.model.RecipeSearchResponse
import com.example.cookmerecipes.util.Constants

@Entity(tableName = Constants.RECIPES_TABLE_NAME)
class RecipesEntity(
    var foodRecipe:RecipeSearchResponse
) {
    @PrimaryKey(autoGenerate = false)
    var id:Int=0 //We will have only one entry in the database, whenever we refresh we will
                // replace the previous data with the new one

}