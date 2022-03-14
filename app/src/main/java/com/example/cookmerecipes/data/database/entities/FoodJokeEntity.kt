package com.example.cookmerecipes.data.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cookmerecipes.data.model.FoodJokeResponse
import com.example.cookmerecipes.util.Constants

@Entity(tableName = Constants.FOOD_JOKE_TABLE_NAME)
class FoodJokeEntity(
    @Embedded
    var foodJoke:FoodJokeResponse  //We dont need type converters for foodJokeResponse because it is already in string form. So we are adding Embedded annotation
) {
    @PrimaryKey(autoGenerate = false)
    var id:Int=0

}