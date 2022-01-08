package com.example.cookmerecipes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import androidx.viewpager.widget.PagerAdapter
import com.example.cookmerecipes.R
import com.example.cookmerecipes.adapters.ViewPagerAdapter
import com.example.cookmerecipes.data.database.entities.FavouritesEntity
import com.example.cookmerecipes.data.model.IngredientsItem
import com.example.cookmerecipes.ui.ingredientsfragment.IngredientFragment
import com.example.cookmerecipes.ui.instructionsfragment.InstructionsFragment
import com.example.cookmerecipes.ui.overviewfragment.OverviewFragment
import com.example.cookmerecipes.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_details.*
import java.lang.Exception

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private val args by navArgs<DetailsActivityArgs>()
    private val mainViewModel:MainViewModel by viewModels()

    private var recipeSaved=false
    private var savedRecipeId=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments=ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientFragment())
        fragments.add(InstructionsFragment())

        val titles =ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")

        val bundleResult=Bundle()
        bundleResult.putParcelable("recipeBundle",args.recipeResult)

        val adapter=ViewPagerAdapter(bundleResult,fragments,titles,supportFragmentManager)

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
        }else if(item.itemId == R.id.saveToFavouritesMenu && !recipeSaved){
            saveToFavourites(item)
        }else if(item.itemId == R.id.saveToFavouritesMenu && recipeSaved){
            removeFromFavourites(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveToFavourites(item: MenuItem) {
        val favouriteEntity=FavouritesEntity(0,args.recipeResult) //Id can be put anything, because ultimately it will get autogenerated. So no worries!
        mainViewModel.insertFavouriteRecipes(favouriteEntity)

        changeMenuItemColor(item,R.color.yellow)
        showSnackBar("Recipe added to Favourites.")
        recipeSaved=true
    }

    private fun removeFromFavourites(item:MenuItem){
        val favouritesEntity=FavouritesEntity(savedRecipeId,args.recipeResult)
        mainViewModel.deleteFavouriteRecipe(favouritesEntity)
        changeMenuItemColor(item,R.color.white)
        showSnackBar("Removed from Favourites.")
        recipeSaved=false
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(findViewById(R.id.CLDetailsLayout),message,Snackbar.LENGTH_SHORT).setAction("Okay"){}.show()
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon.setTint(ContextCompat.getColor(this,color))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu,menu)
        val menuItem=menu?.findItem(R.id.saveToFavouritesMenu)
        checkSavedRecipes(menuItem!!)
        return true
    }

    private fun checkSavedRecipes(menuItem: MenuItem) {
        mainViewModel.readFavouriteRecipesFromDatabase.observe(this,{ favEntity->
            try {
                for(savedRecipe in favEntity){
                    if(savedRecipe.result.id == args.recipeResult.id){
                        changeMenuItemColor(menuItem,R.color.yellow)
                        savedRecipeId=savedRecipe.id
                        recipeSaved=true
                        println("HEREEEE")
                    }else{
                        changeMenuItemColor(menuItem,R.color.white)
                    }
                }
            }catch (e:Exception){
                Log.d("DetailsActivity",e.message.toString())
            }
        })
    }
}