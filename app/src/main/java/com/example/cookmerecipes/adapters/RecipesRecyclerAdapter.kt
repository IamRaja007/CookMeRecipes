package com.example.cookmerecipes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cookmerecipes.data.model.RecipeSearchResponse
import com.example.cookmerecipes.data.model.ResultsItem
import com.example.cookmerecipes.databinding.ItemRowRecipeBinding
import com.example.cookmerecipes.util.RecipesDiffUtil

class RecipesRecyclerAdapter : RecyclerView.Adapter<RecipesRecyclerAdapter.RecipesViewHolder>() {

    private var recipeList = emptyList<ResultsItem>()

    class RecipesViewHolder(private val binding: ItemRowRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(resultsItem: ResultsItem){
                binding.result=resultsItem  //this 'result' variable is inside ItemRowRecipe xml inside data tag
                binding.executePendingBindings() //Updates layout whenever there is change in our data
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        val layoutInflater=LayoutInflater.from(parent.context)
        val binding=ItemRowRecipeBinding.inflate(layoutInflater,parent,false)

        return RecipesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
       val itemResult=recipeList[position]

        holder.bind(itemResult)
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    fun setData(data:RecipeSearchResponse){
        val diffUtils=RecipesDiffUtil(recipeList,(data.results as List<ResultsItem>?)!!)
        val diffUtilResult=DiffUtil.calculateDiff(diffUtils)
        recipeList= (data.results as List<ResultsItem>?)!!
        diffUtilResult.dispatchUpdatesTo(this)

    }
}