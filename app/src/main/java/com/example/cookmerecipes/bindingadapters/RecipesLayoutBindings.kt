package com.example.cookmerecipes.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.cookmerecipes.data.database.entities.RecipesEntity
import com.example.cookmerecipes.data.model.RecipeSearchResponse
import com.example.cookmerecipes.util.NetworkResult

class RecipesLayoutBindings {

    companion object {

        @BindingAdapter(
            "readApiResponse",
            "readDatabase",
            requireAll = true
        ) //requireAll true means an instruction to compiler to display an error if we use only one of them, we need to use both of them
        @JvmStatic
        fun showErrorImage(
            imageView: ImageView,
            apiResponse: NetworkResult<RecipeSearchResponse>?,
            database: List<RecipesEntity>?
        ) {
            if (apiResponse is NetworkResult.Error && database.isNullOrEmpty()) {
                imageView.visibility = View.VISIBLE
            } else if (apiResponse is NetworkResult.Loading) {
                imageView.visibility = View.GONE
            } else if (apiResponse is NetworkResult.Success) {
                imageView.visibility = View.GONE
            }
        }

        @BindingAdapter(
            "readApiResponse2",
            "readDatabase2",
            requireAll = true
        ) //requireAll true means an instruction to compiler to display an error if we use only one of them, we need to use both of them
        @JvmStatic
        fun showErrorText(
            textView: TextView,
            apiResponse: NetworkResult<RecipeSearchResponse>?,
            database: List<RecipesEntity>?
        ) {
            if (apiResponse is NetworkResult.Error && database.isNullOrEmpty()) {
                textView.visibility = View.VISIBLE
                textView.text = apiResponse.message.toString()
            } else if (apiResponse is NetworkResult.Loading) {
                textView.visibility = View.GONE
            } else if (apiResponse is NetworkResult.Success) {
                textView.visibility = View.GONE
            }
        }
    }
}