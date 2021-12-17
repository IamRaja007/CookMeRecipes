package com.example.cookmerecipes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import androidx.viewpager.widget.PagerAdapter
import com.example.cookmerecipes.R
import com.example.cookmerecipes.adapters.ViewPagerAdapter
import com.example.cookmerecipes.data.model.IngredientsItem
import com.example.cookmerecipes.ui.ingredientsfragment.IngredientFragment
import com.example.cookmerecipes.ui.instructionsfragment.InstructionsFragment
import com.example.cookmerecipes.ui.overviewfragment.OverviewFragment
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    private val args by navArgs<DetailsActivityArgs>()

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

        viewPager.adapter=adapter
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}