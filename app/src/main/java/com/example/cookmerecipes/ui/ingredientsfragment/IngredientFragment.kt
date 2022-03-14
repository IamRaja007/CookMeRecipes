package com.example.cookmerecipes.ui.ingredientsfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cookmerecipes.R
import com.example.cookmerecipes.adapters.IngredientsRecyclerAdapter
import com.example.cookmerecipes.data.model.ExtendedIngredientsItem
import com.example.cookmerecipes.data.model.ResultsItem
import com.example.cookmerecipes.databinding.FragmentIngredientBinding


class IngredientFragment : Fragment() {

    private var _binding:FragmentIngredientBinding?=null
    private val binding get() = _binding!!

    private val mAdapter:IngredientsRecyclerAdapter by lazy {
        IngredientsRecyclerAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentIngredientBinding.inflate(inflater, container, false)

        val args = arguments
        val bundle = args?.getParcelable<ResultsItem>("recipeBundle")

        setUpRecyclerView()

        bundle?.extendedIngredients?.let {
            mAdapter.setData(it)
        }
        return binding.root
    }

    private fun setUpRecyclerView(){
        binding.rvIngredients.adapter=mAdapter
        binding.rvIngredients.layoutManager=LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}