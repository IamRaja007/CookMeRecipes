package com.example.cookmerecipes.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView
import com.example.cookmerecipes.data.database.entities.FoodJokeEntity
import com.example.cookmerecipes.data.database.entities.RecipesEntity
import com.example.cookmerecipes.data.model.FoodJokeResponse
import com.example.cookmerecipes.data.model.RecipeSearchResponse
import com.example.cookmerecipes.util.NetworkResult
import com.google.android.material.card.MaterialCardView

class FoodJokeBindings {

    companion object {

        @BindingAdapter("readApiResponseFoodJoke", "readDatabaseFoodJoke", requireAll = false)
        @JvmStatic
        fun setCardAndProgressVisibility(
            view: View,
            apiResponse: NetworkResult<FoodJokeResponse>?,
            dataBase: List<FoodJokeEntity>?
        ) {
            when (apiResponse) {
                is NetworkResult.Loading -> {
                    when (view) {
//                        is ProgressBar -> {
//                            view.visibility = View.VISIBLE
//                        }
                        is LottieAnimationView -> {
                            view.visibility = View.VISIBLE
                            view.animate()
                        }
                        is MaterialCardView -> {
                            view.visibility = View.GONE
                        }

                        is ImageView -> {
                            view.visibility = View.GONE
                        }
                    }
                }

                is NetworkResult.Error -> {
                    when (view) {
//                        is ProgressBar -> {
//                            view.visibility = View.GONE
//                        }
                        is LottieAnimationView -> {
                            view.visibility = View.GONE
//                            view.animate()
                        }
                        is MaterialCardView -> {
                            view.visibility = View.VISIBLE

                            if (dataBase != null) { //For data base empty case
                                if (dataBase.isEmpty()) {
                                    view.visibility = View.GONE
                                }
                            }
                        }

                        is ImageView -> {
                            view.visibility = View.VISIBLE

                            if (dataBase != null) { //For data base empty case
                                if (dataBase.isEmpty()) {
                                    view.visibility = View.GONE
                                }
                            }
                        }
                    }
                }

                is NetworkResult.Success -> {
                    when (view) {
//                        is ProgressBar ->{
//                            view.visibility = View.GONE
//                        }

                        is LottieAnimationView -> {
                            view.visibility = View.GONE
                        }
                        is MaterialCardView -> {
                            view.visibility = View.VISIBLE
                        }

                        is ImageView -> {
                            view.visibility = View.VISIBLE
                        }

                    }
                }
            }
        }

        @BindingAdapter(
            "readApiResponseForError",
            "readDatabaseForError",
            requireAll = true
        ) //requireAll true means an instruction to compiler to display an error if we use only one of them, we need to use both of them
        @JvmStatic
        fun showErrorText(
            view: View,
            apiResponse: NetworkResult<FoodJokeResponse>?,
            database: List<FoodJokeEntity>?
        ) {
            if (database != null) {
                if (database.isEmpty()) {
                    view.visibility = View.VISIBLE

                    if (view is TextView) {
                        if (apiResponse != null) {
                            view.text = apiResponse.message.toString()
                        }
                    }
                }

            }

            if (apiResponse is NetworkResult.Success) {
                view.visibility = View.GONE
            }
        }
    }

}