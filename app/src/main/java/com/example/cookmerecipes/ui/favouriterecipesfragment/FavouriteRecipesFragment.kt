package com.example.cookmerecipes.ui.favouriterecipesfragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookmerecipes.R
import com.example.cookmerecipes.adapters.FavouriteRecipesRecyclerAdapter
import com.example.cookmerecipes.databinding.FragmentFavouriteRecipesBinding
import com.example.cookmerecipes.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favourite_recipes.view.*

@AndroidEntryPoint
class FavouriteRecipesFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()

    private val mAdapter: FavouriteRecipesRecyclerAdapter by lazy {
        FavouriteRecipesRecyclerAdapter(requireActivity(),mainViewModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private var _binding:FragmentFavouriteRecipesBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentFavouriteRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel=mainViewModel
        binding.mAdapter=mAdapter

        setHasOptionsMenu(true)

        setUpRecyclerView(binding.rvFavouriteRecipes)

        mainViewModel.readFavouriteRecipesFromDatabase.observe(viewLifecycleOwner,
            { favouritesEntityList ->
                mAdapter.setData(favouritesEntityList)
            })
        return binding.root
    }

    private fun setUpRecyclerView(recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = mAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
        mAdapter.clearContextualActionMode()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favourites_recipe_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.deleteAllFavMenuRecipes){
            mainViewModel.deleteAllFavouriteRecipes()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showSnackbar(message:String){
        Snackbar.make(
            binding.root,
            "All favourite recipes removed.",
            Snackbar.LENGTH_SHORT
        ).setAction("Okay"){}.show()
    }
}