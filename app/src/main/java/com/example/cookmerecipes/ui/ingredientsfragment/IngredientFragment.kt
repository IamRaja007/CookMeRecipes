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
import kotlinx.android.synthetic.main.fragment_ingredient.view.*


class IngredientFragment : Fragment() {

    private val mAdapter:IngredientsRecyclerAdapter by lazy {
        IngredientsRecyclerAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_ingredient, container, false)

        val args = arguments
        val bundle = args?.getParcelable<ResultsItem>("recipeBundle")

        setUpRecyclerView(view)

        bundle?.extendedIngredients?.let {
            mAdapter.setData(it)
        }
        return view
    }

    private fun setUpRecyclerView(view:View){
        view.rvIngredients.adapter=mAdapter
        view.rvIngredients.layoutManager=LinearLayoutManager(requireContext())
    }

}