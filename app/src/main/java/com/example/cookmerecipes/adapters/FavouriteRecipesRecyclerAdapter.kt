package com.example.cookmerecipes.adapters

import android.view.*
import androidx.appcompat.view.menu.MenuView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cookmerecipes.R
import com.example.cookmerecipes.data.database.entities.FavouritesEntity
import com.example.cookmerecipes.databinding.ItemRowFavRecipeBinding
import com.example.cookmerecipes.databinding.ItemRowRecipeBinding
import com.example.cookmerecipes.ui.favouriterecipesfragment.FavouriteRecipesFragmentDirections
import com.example.cookmerecipes.util.RecipesDiffUtil
import com.example.cookmerecipes.viewmodels.MainViewModel
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar

class FavouriteRecipesRecyclerAdapter(
    private val requireActivity: FragmentActivity, //This parameter is required to start the contextual action mode
    private val mainViewModel: MainViewModel
) : RecyclerView.Adapter<FavouriteRecipesRecyclerAdapter.MyViewHolder>(), ActionMode.Callback {

    private var multiSelection = false
    private var selectedRecipes = arrayListOf<FavouritesEntity>()
    private var myViewHolders = arrayListOf<MyViewHolder>()
    private lateinit var mActionMode: ActionMode

    private lateinit var rootView: View

    private var favouritesRecipes = emptyList<FavouritesEntity>()

    class MyViewHolder(private val binding: ItemRowFavRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favouritesEntity: FavouritesEntity) {
            binding.favouritesEntity = favouritesEntity
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemRowFavRecipeBinding.inflate(layoutInflater, parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        myViewHolders.add(holder)
        rootView = holder.itemView.rootView
        val selectedRecipe = favouritesRecipes[position]

        holder.bind(selectedRecipe)

        /**
         * Single Click Listener
         */
        val CLfavRecipes=holder.itemView.findViewById<ConstraintLayout>(R.id.CLfavRecipes)
        CLfavRecipes.setOnClickListener {
            if (multiSelection) {
                applySelection(holder, selectedRecipe)
            } else {
                val action =
                    FavouriteRecipesFragmentDirections.actionFavouriteRecipesFragmentToDetailsActivity(
                        selectedRecipe.result
                    )
                holder.itemView.findNavController().navigate(action)
            }

        }

        /**
         * Long Click Listener
         */
        CLfavRecipes.setOnLongClickListener {
            if (!multiSelection) {
                multiSelection = true
                requireActivity.startActionMode(this)
                applySelection(holder, selectedRecipe)
                true
            } else {
                multiSelection = false
                false
            }

        }
    }

    override fun getItemCount(): Int {
        return favouritesRecipes.size
    }

    private fun applySelection(holder: MyViewHolder, currentRecipe: FavouritesEntity) {
        if (selectedRecipes.contains(currentRecipe)) {
            selectedRecipes.remove(currentRecipe)
            changeRecipeStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
            setActionModeTitle()
        } else {
            selectedRecipes.add(currentRecipe)
            changeRecipeStyle(holder, R.color.cardBackgroundLightOpacityColor, R.color.purple_700)
            setActionModeTitle()
        }

    }

    private fun changeRecipeStyle(holder: MyViewHolder, backgroundColor: Int, strokeColor: Int) {
        val CLfavRecipes=holder.itemView.findViewById<ConstraintLayout>(R.id.CLfavRecipes)
        CLfavRecipes.setBackgroundColor(
            ContextCompat.getColor(requireActivity, backgroundColor)
        )

        val cardViewFavRecipe=holder.itemView.findViewById<MaterialCardView>(R.id.cardViewFavRecipe)

        cardViewFavRecipe.strokeColor =
            ContextCompat.getColor(requireActivity, strokeColor)
    }

    fun setData(newFavouritesRecipes: List<FavouritesEntity>) {
        val favRecipesDiffUtil = RecipesDiffUtil(favouritesRecipes, newFavouritesRecipes)
        val diffUtilResult = DiffUtil.calculateDiff(favRecipesDiffUtil)

        favouritesRecipes = newFavouritesRecipes

        diffUtilResult.dispatchUpdatesTo(this)
    }

    private fun applyStatusBarColor(color: Int) {
        requireActivity.window.statusBarColor = ContextCompat.getColor(requireActivity, color)
    }

    private fun setActionModeTitle() {
        when (selectedRecipes.size) {
            0 -> {
                mActionMode.finish()
            }

            1 -> {
                mActionMode.title = "${selectedRecipes.size} item selected"
            }
            else -> {
                mActionMode.title = "${selectedRecipes.size} items selected"
            }
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(
            rootView,
            message,
            Snackbar.LENGTH_SHORT,
        ).setAction("Okay") {}.show()
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.favourites_contextual_menu, menu)
        mActionMode = mode!!
        applyStatusBarColor(R.color.contextualStatusBarColor)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        if (item?.itemId == R.id.deleteFavRecipesMenu) {
            selectedRecipes.forEach {
                mainViewModel.deleteFavouriteRecipe(it)
            }

            if (selectedRecipes.size > 1) {
                showSnackbar("${selectedRecipes.size} recipes removed.")
            } else {
                showSnackbar("${selectedRecipes.size} recipe removed.")
            }

            multiSelection = false
            selectedRecipes.clear()
            mode?.finish()
        }
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        myViewHolders.forEach { holder ->
            changeRecipeStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
        }
        multiSelection = false
        selectedRecipes.clear()
        applyStatusBarColor(R.color.statusBarColor)
    }

    fun clearContextualActionMode(){
        if(this::mActionMode.isInitialized){
            mActionMode.finish()
        }
    }
}