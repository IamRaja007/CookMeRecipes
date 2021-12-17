package com.example.cookmerecipes.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(
    private val resultBundle: Bundle, //To pass the data from DetailsActivity to the other fragments inside that activity
    private val fragments: ArrayList<Fragment>,
    private val titles: ArrayList<String>,
    fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        fragments[position].arguments = resultBundle //With this, we will pass all result from recipe to other fragments in the viewpager
        return fragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
}