package com.example.cookmerecipes.ui.recipesfragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cookmerecipes.R
import com.example.cookmerecipes.viewmodels.MainViewModel
import com.example.cookmerecipes.adapters.RecipesRecyclerAdapter
import com.example.cookmerecipes.databinding.FragmentRecipesBinding
import com.example.cookmerecipes.util.NetworkListener
import com.example.cookmerecipes.util.NetworkResult
import com.example.cookmerecipes.util.observeOnce
import com.example.cookmerecipes.viewmodels.RecipesViewModel
import com.google.gson.internal.bind.TreeTypeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RecipesFragment : Fragment(), SearchView.OnQueryTextListener {

    private val args by navArgs<RecipesFragmentArgs>()

    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    private val mAdapter by lazy {
        RecipesRecyclerAdapter()
    }

    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipesViewModel: RecipesViewModel

    private lateinit var networkListener: NetworkListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner =
            viewLifecycleOwner //We have to do this, because in the recipesFragment xml layout, we are going to use LiveData variables
        binding.mainViewModel = mainViewModel

        setHasOptionsMenu(true)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setUpRecyclerView()

        recipesViewModel.readBackOnline.observe(viewLifecycleOwner, {
            recipesViewModel.backOnline = it
        })

        lifecycleScope.launch { //use launchWhenStarted in crashes
            networkListener = NetworkListener()
            networkListener.checkNetworkAvailability(requireContext()).collect { status ->
                recipesViewModel.networkStatus = status
                recipesViewModel.showNetworkStatus()
                readDatabase()
            }
        }


        binding.recipesFab.setOnClickListener {
            if (recipesViewModel.networkStatus) {
                findNavController().navigate(R.id.action_recipesFragment_to_recipesBottomSheet)
            } else {
                recipesViewModel.showNetworkStatus()
            }

        }
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readRecipesFromDatabase.observeOnce(this@RecipesFragment, { database -> //if Using lifecycle owner as viewLifecycleOwner, app was crashing
                if (database.isNotEmpty() && !args.backFromBottomSheet) {
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
        binding.shimmerRv.showShimmer()
    }

    private fun hideShimmer() {
        binding.shimmerRv.hideShimmer()
    }

    private fun requestApiData() {
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        println(recipesViewModel.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner) { response ->

            when (response) {
                is NetworkResult.Success -> {
                    hideShimmer()
//                    hideNoInternetVisual()

                    println(response.data)
                    response.data?.let {
                        mAdapter.setData(it)
                    }
                }

                is NetworkResult.Error -> {
                    hideShimmer()
                    loadDataFromCache() //If we receive error, we show the previous state of the data
//                    if (response.message == "No Internet Connection") {
//                        showNoInternetVisual()
//                    } else {
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
//                    }
                }

                is NetworkResult.Loading -> {
                    showShimmer()
                }
            }

        }
    }

    private fun searchApiData(searchQuery: String) {
        showShimmer()
        mainViewModel.searchRecipes(recipesViewModel.applySearchQueries(searchQuery))
        mainViewModel.searchedRecipeResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmer()
                    val foodRecipe = response.data
                    foodRecipe?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmer()
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    showShimmer()
                }
            }

        })
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readRecipesFromDatabase.observe(viewLifecycleOwner, { database ->
                if (database.isNotEmpty()) {
                    mAdapter.setData(database[0].foodRecipe)
                }
            })
        }
    }

    private fun setUpRecyclerView() {
        binding.shimmerRv.adapter = mAdapter
        binding.shimmerRv.layoutManager = LinearLayoutManager(requireContext())
        showShimmer()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recipes_menu, menu)

        val search = menu.findItem(R.id.menu_Search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query!=null){
            searchApiData(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

//    fun showNoInternetVisual() {
//        binding.LLerror.visibility = View.VISIBLE
//    }
//
//    fun hideNoInternetVisual() {
//        binding.LLerror.visibility = View.GONE
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}