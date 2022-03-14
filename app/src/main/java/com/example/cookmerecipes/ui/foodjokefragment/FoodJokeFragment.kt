package com.example.cookmerecipes.ui.foodjokefragment

import android.content.Intent
import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.cookmerecipes.R
import com.example.cookmerecipes.databinding.FragmentFoodJokeBinding
import com.example.cookmerecipes.util.Constants
import com.example.cookmerecipes.util.NetworkResult
import com.example.cookmerecipes.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FoodJokeFragment : Fragment() {

    private val mainViewModel by viewModels<MainViewModel>() //Either initialise viewmodel using delegates or you can also use viewmodelProviders thing

    private var _binding: FragmentFoodJokeBinding? = null
    private val binding get() = _binding!!

    private var foodJokeText=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFoodJokeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.mainViewModel = mainViewModel //binding.mainViewModel is the variable in the data binding layout

        mainViewModel.getFoodJoke(Constants.API_KEY)
        mainViewModel.foodJokeResponse.observe(viewLifecycleOwner,{ response->
            when(response){
                is NetworkResult.Success ->{
                    binding.tvFoodJoke.text=response.data?.text
                    foodJokeText = response.data?.text.toString()
                }
                is NetworkResult.Error ->{
                    loadDataFromCache()
                    Toast.makeText(requireContext(),response.message.toString(),Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading ->{
                    Log.d("FoodJokeFragment","Loading..")
                }
            }

        })

        setHasOptionsMenu(true)

        return binding.root
    }


    private fun loadDataFromCache(){
        lifecycleScope.launch {
            mainViewModel.readFoodJoke.observe(viewLifecycleOwner,{ dataBase->
                if(dataBase.isNotEmpty() && dataBase!=null){
                    binding.tvFoodJoke.text=dataBase[0].foodJoke.text
                    foodJokeText = dataBase[0].foodJoke.text.toString()
                }
            })
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.food_joke_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.shareJoke){
            val shareIntent=Intent().apply {
                this.action = Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_TEXT,foodJokeText )
                this.type = "text/plain"
            }
            startActivity(shareIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}