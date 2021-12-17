package com.example.cookmerecipes.ui.overviewfragment

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import coil.load
import com.example.cookmerecipes.R
import com.example.cookmerecipes.data.model.ResultsItem
import kotlinx.android.synthetic.main.fragment_overview.view.*
import org.jsoup.Jsoup


class OverviewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val mView = inflater.inflate(R.layout.fragment_overview, container, false)

        val args = arguments
        val bundle = args?.getParcelable<ResultsItem>("recipeBundle")

        mView.ivOverviewImage.load(bundle?.image)
        mView.tvRecipeTitle.text = bundle?.title
        mView.tvLikesNumber.text = bundle?.aggregateLikes.toString()
        mView.tvReadyTime.text = bundle?.readyInMinutes.toString()
        bundle?.summary.let {
            val summary=Jsoup.parse(it).text()
            mView.tvSummary.text=summary
        }

        if(bundle?.vegetarian == true){
            mView.ivCheckVegetarian.backgroundTintList= ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.green))
            mView.tvVegetarian.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
        }
        if(bundle?.vegan == true){
            mView.ivCheckVegan.backgroundTintList= ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.green))
            mView.tvVegan.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
        }
        if(bundle?.glutenFree == true){
            mView.ivCheckGlutenFree.backgroundTintList= ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.green))
            mView.tvGlutenFree.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
        }
        if(bundle?.dairyFree == true){
            mView.ivCheckDairyFree.backgroundTintList= ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.green))
            mView.tvDairyFree.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
        }
        if(bundle?.veryHealthy == true){
            mView.ivCheckHealthy.backgroundTintList= ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.green))
            mView.tvHealth.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
        }
        if(bundle?.cheap == true){
            mView.ivCheckCheap.backgroundTintList= ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.green))
            mView.tvCheap.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
        }

        return mView
    }
}