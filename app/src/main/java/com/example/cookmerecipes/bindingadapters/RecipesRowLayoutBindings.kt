package com.example.cookmerecipes.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.example.cookmerecipes.R

class RecipesRowLayoutBindings {

    companion object {
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
    }
}