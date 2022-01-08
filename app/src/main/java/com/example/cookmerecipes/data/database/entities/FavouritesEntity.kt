package com.example.cookmerecipes.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cookmerecipes.data.model.ResultsItem
import com.example.cookmerecipes.util.Constants

@Entity(tableName = Constants.FAVOURITE_RECIPES_TABLE_NAME)
class FavouritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var result:ResultsItem
)