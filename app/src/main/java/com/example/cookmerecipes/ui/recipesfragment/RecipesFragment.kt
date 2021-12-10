package com.example.cookmerecipes.ui.recipesfragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cookmerecipes.viewmodels.MainViewModel
import com.example.cookmerecipes.R
import com.example.cookmerecipes.adapters.RecipesRecyclerAdapter
import com.example.cookmerecipes.util.Constants
import com.example.cookmerecipes.util.NetworkResult
import com.example.cookmerecipes.util.observeOnce
import com.example.cookmerecipes.viewmodels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_recipes.view.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private lateinit var mView: View

    private val mAdapter by lazy {
        RecipesRecyclerAdapter()
    }

    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipesViewModel: RecipesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_recipes, container, false)

        setUpRecyclerView()
        readDatabase()

        return mView
    }

    private fun readDatabase() {
        lifecycleScope.launch{
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner, { database ->
                if (database.isNotEmpty()) {
                    Log.d("RecipeFragment", "ReadDatabase called")
                    mAdapter.setData(database[0].foodRecipe)
                    hideShimmer()
                } else {
                    Log.d("RecipeFragment", "requestAPIdata called")
                    requestApiData()
                }
            })
        }
    }

    private fun showShimmer() {
        mView.shimmerRv.showShimmer()
    }

    private fun hideShimmer() {
        mView.shimmerRv.hideShimmer()
    }

    private fun requestApiData() {
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner) { response ->

            when (response) {
                is NetworkResult.Success -> {
                    hideShimmer()
                    hideNoInternetVisual()

                    response.data?.let {
                        mAdapter.setData(it)
                    }
                }

                is NetworkResult.Error -> {
                    hideShimmer()
                    loadDataFromCache() //If we receive error, we show the previous state of the data
                    if (response.message == "No Internet Connection") {
                        showNoInternetVisual()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            response.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                is NetworkResult.Loading -> {
                    showShimmer()
                }
            }

        }
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observe(viewLifecycleOwner, { database ->
                if (database.isNotEmpty()) {
                    mAdapter.setData(database[0].foodRecipe)
                }
            })
        }
    }

    private fun setUpRecyclerView() {
        mView.shimmerRv.adapter = mAdapter
        mView.shimmerRv.layoutManager = LinearLayoutManager(requireContext())
        showShimmer()
    }

    fun showNoInternetVisual() {
        mView.LLerror.visibility = View.VISIBLE
    }

    fun hideNoInternetVisual() {
        mView.LLerror.visibility = View.GONE
    }
}