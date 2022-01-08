package com.example.cookmerecipes.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.datastore.preferences.protobuf.Internal
import androidx.recyclerview.widget.RecyclerView
import com.example.cookmerecipes.adapters.FavouriteRecipesRecyclerAdapter
import com.example.cookmerecipes.data.database.entities.FavouritesEntity

class FavouriteRecipesBindings {

    companion object {

        @BindingAdapter("viewVisibility","setData",requireAll = false)
        @JvmStatic
        fun setDataAndViewVisibility(
            view: View,
            favouritesRecipeEntityList: List<FavouritesEntity>?, //viewVisibilty variable initialises this one
            mAdapter:FavouriteRecipesRecyclerAdapter?  //setData variable initialises this one
        ){
            if (favouritesRecipeEntityList.isNullOrEmpty()){
                when(view){

                    is LinearLayout ->{
                        view.visibility=View.VISIBLE
                    }
                    is RecyclerView ->{
                        view.visibility=View.GONE
                    }
                }
            }else{
                when(view){
                    
                    is LinearLayout ->{
                        view.visibility=View.GONE
                    }
                    is RecyclerView ->{
                        view.visibility=View.VISIBLE
                        mAdapter?.setData(favouritesRecipeEntityList)
                    }
                }
            }
        }
    }
}