package com.example.cookmerecipes.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class RecipeSearchResponse(

	@field:SerializedName("results")
	val results: List<ResultsItem?>? = null
)

@Parcelize
data class ExtendedIngredientsItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("amount")
	val amount: Double? = null,

	@field:SerializedName("original")
	val original: String? = null,

	@field:SerializedName("consistency")
	val consistency: String? = null,

	@field:SerializedName("unit")
	val unit: String? = null,

):Parcelable

data class IngredientsItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("localizedName")
	val localizedName: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

@Parcelize
data class ResultsItem(

	@field:SerializedName("sustainable")
	val sustainable: Boolean? = null,

	@field:SerializedName("glutenFree")
	val glutenFree: Boolean? = null,

	@field:SerializedName("healthScore")
	val healthScore: Double? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("diets")
	val diets: List<String?>? = null,

	@field:SerializedName("aggregateLikes")
	val aggregateLikes: Int? = null,

	@field:SerializedName("readyInMinutes")
	val readyInMinutes: Int? = null,

	@field:SerializedName("sourceUrl")
	val sourceUrl: String? = null,

	@field:SerializedName("dairyFree")
	val dairyFree: Boolean? = null,

	@field:SerializedName("servings")
	val servings: Int? = null,

	@field:SerializedName("vegetarian")
	val vegetarian: Boolean? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("summary")
	val summary: String? = null,

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("veryHealthy")
	val veryHealthy: Boolean? = null,

	@field:SerializedName("vegan")
	val vegan: Boolean? = null,

	@field:SerializedName("cheap")
	val cheap: Boolean? = null,

	@field:SerializedName("extendedIngredients")
	val extendedIngredients: List<ExtendedIngredientsItem?>? = null,

	@field:SerializedName("dishTypes")
	val dishTypes: List<String?>? = null,

	@field:SerializedName("gaps")
	val gaps: String? = null,

	@field:SerializedName("license")
	val license: String? = null,

	@field:SerializedName("missedIngredientCount")
	val missedIngredientCount: Int? = null,

//	@field:SerializedName("occasions")
//	val occasions: List<Any?>? = null,

	@field:SerializedName("pricePerServing")
	val pricePerServing: Double? = null,

	@field:SerializedName("sourceName")
	val sourceName: String? = null,

	@field:SerializedName("spoonacularSourceUrl")
	val spoonacularSourceUrl: String? = null
):Parcelable


