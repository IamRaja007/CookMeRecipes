package com.example.cookmerecipes.data.model

import com.google.gson.annotations.SerializedName

data class FoodJokeResponse(

	@field:SerializedName("text")
	val text: String? = null
)
