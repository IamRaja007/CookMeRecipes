package com.example.cookmerecipes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.cookmerecipes.R
import com.example.cookmerecipes.data.model.ExtendedIngredientsItem
import com.example.cookmerecipes.util.Constants
import com.example.cookmerecipes.util.RecipesDiffUtil
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
        val ivIngredient=holder.itemView.findViewById<ImageView>(R.id.ivIngredient)
        ivIngredient.load(Constants.BASE_IMAGE_URL + ingredientsList[position]?.image){
            crossfade(600)
            error(R.drawable.ic_launcher_background)
        }
        val tvIngredientName=holder.itemView.findViewById<TextView>(R.id.tvIngredientName)
        tvIngredientName.text= ingredientsList[position]?.name?.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }

        val tvIngredientAmount=holder.itemView.findViewById<TextView>(R.id.tvIngredientAmount)
        tvIngredientAmount.text=ingredientsList[position]?.amount.toString()

        val tvIngredientUnit=holder.itemView.findViewById<TextView>(R.id.tvIngredientUnit)
        tvIngredientUnit.text=ingredientsList[position]?.unit

        val tvConsistency=holder.itemView.findViewById<TextView>(R.id.tvConsistency)
        tvConsistency.text=ingredientsList[position]?.consistency

        val tvIngredientOriginal=holder.itemView.findViewById<TextView>(R.id.tvIngredientOriginal)
        tvIngredientOriginal.text=ingredientsList[position]?.original
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