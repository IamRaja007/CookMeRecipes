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
import com.example.cookmerecipes.databinding.FragmentInstructionsBinding
import com.example.cookmerecipes.databinding.FragmentOverviewBinding
import org.jsoup.Jsoup


class OverviewFragment : Fragment() {


    private var _binding: FragmentOverviewBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)

        val args = arguments
        val bundle = args?.getParcelable<ResultsItem>("recipeBundle")

        binding.ivOverviewImage.load(bundle?.image)
        binding.tvRecipeTitle.text = bundle?.title
        binding.tvLikesNumber.text = bundle?.aggregateLikes.toString()
        binding.tvReadyTime.text = bundle?.readyInMinutes.toString()
        bundle?.summary.let {
            val summary=Jsoup.parse(it).text()
            binding.tvSummary.text=summary
        }

        if(bundle?.vegetarian == true){
            binding.ivCheckVegetarian.backgroundTintList= ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.green))
            binding.tvVegetarian.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
        }
        if(bundle?.vegan == true){
            binding.ivCheckVegan.backgroundTintList= ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.green))
            binding.tvVegan.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
        }
        if(bundle?.glutenFree == true){
            binding.ivCheckGlutenFree.backgroundTintList= ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.green))
            binding.tvGlutenFree.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
        }
        if(bundle?.dairyFree == true){
            binding.ivCheckDairyFree.backgroundTintList= ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.green))
            binding.tvDairyFree.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
        }
        if(bundle?.veryHealthy == true){
            binding.ivCheckHealthy.backgroundTintList= ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.green))
            binding.tvHealth.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
        }
        if(bundle?.cheap == true){
            binding.ivCheckCheap.backgroundTintList= ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.green))
            binding.tvCheap.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}