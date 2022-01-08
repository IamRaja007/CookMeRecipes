package com.example.cookmerecipes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.cookmerecipes.R
import com.example.cookmerecipes.data.model.ExtendedIngredientsItem
import com.example.cookmerecipes.util.Constants
import com.example.cookmerecipes.util.RecipesDiffUtil
import kotlinx.android.synthetic.main.item_row_ingredients.view.*
import java.util.*

class IngredientsRecyclerAdapter :
    RecyclerView.Adapter<IngredientsRecyclerAdapter.IngredientsViewHolder>() {

    private var ingredientsList = emptyList<ExtendedIngredientsItem?>()

    class IngredientsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        return IngredientsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_row_ingredients, parent, false)
        )

    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        holder.itemView.ivIngredient.load(Constants.BASE_IMAGE_URL + ingredientsList[position]?.image){
            crossfade(600)
            error(R.drawable.ic_launcher_background)
        }
        holder.itemView.tvIngredientName.text= ingredientsList[position]?.name?.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
        holder.itemView.tvIngredientAmount.text=ingredientsList[position]?.amount.toString()
        holder.itemView.tvIngredientUnit.text=ingredientsList[position]?.unit
        holder.itemView.tvConsistency.text=ingredientsList[position]?.consistency
        holder.itemView.tvIngredientOriginal.text=ingredientsList[position]?.original
    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }

    fun setData(newIngredientsList:List<ExtendedIngredientsItem?>){
        val ingredientsDiffUtil=RecipesDiffUtil(ingredientsList,newIngredientsList)
        val diffUtilResult=DiffUtil.calculateDiff(ingredientsDiffUtil)
        ingredientsList=newIngredientsList
        diffUtilResult.dispatchUpdatesTo(this)
    }
}