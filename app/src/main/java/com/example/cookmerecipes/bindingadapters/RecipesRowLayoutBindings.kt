package com.example.cookmerecipes.bindingadapters

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.example.cookmerecipes.R
import com.example.cookmerecipes.data.model.ResultsItem
import com.example.cookmerecipes.ui.recipesfragment.RecipesFragmentDirections
import org.jsoup.Jsoup
import java.lang.Exception

class RecipesRowLayoutBindings {

    companion object {
        @BindingAdapter("onRecipeClickListener")
        @JvmStatic
        fun onRecipeClickListener(recipeRowLayout:ConstraintLayout,result: ResultsItem){
            recipeRowLayout.setOnClickListener {
                try {
                    val action =RecipesFragmentDirections.actionRecipesFragmentToDetailsActivity(result)

                    recipeRowLayout.findNavController().navigate(action)
                }
                catch (exception:Exception){
                    Log.d("onRecipeClickListener",exception.toString())

                }
            }
        }
        @BindingAdapter("setTheNumberOfLikes")
        @JvmStatic
        fun setNumberOfLikes(textView: TextView, likes: Int) {
            textView.text = likes.toString()
        }

        @BindingAdapter("setRecipeTime")
        @JvmStatic
        fun setRecipeTime(textView: TextView, time: Int) {
            textView.text = time.toString()
        }

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl: String) {
            imageView.load(imageUrl){
                crossfade(600)
                error(R.drawable.ic_launcher_background)
            }
        }

        @BindingAdapter("applyVeganOrNotColor")
        @JvmStatic
        fun applyVeganOrNotColor(view: View, isVegan: Boolean) {
            if (isVegan) {
                when (view) {
                    is TextView -> {
                        view.setTextColor(
                            ContextCompat.getColor(view.context, R.color.green)
                        )
                    }
                    is ImageView -> {
                        view.setColorFilter(
                            ContextCompat.getColor(view.context, R.color.green)
                        )
                    }
                }
            }
        }

        @BindingAdapter("parseHtml")
        @JvmStatic
        fun parseHtml(textView: TextView,description:String?){
            if(description!=null){
                val desc=Jsoup.parse(description).text()
                textView.text = desc
            }

        }
    }
}