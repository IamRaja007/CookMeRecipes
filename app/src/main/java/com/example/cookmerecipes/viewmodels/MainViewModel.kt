package com.example.cookmerecipes.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.example.cookmerecipes.data.Repository
import com.example.cookmerecipes.data.database.entities.FavouritesEntity
import com.example.cookmerecipes.data.database.entities.RecipesEntity
import com.example.cookmerecipes.data.model.RecipeSearchResponse
import com.example.cookmerecipes.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    /** Room */

    val readRecipesFromDatabase:LiveData<List<RecipesEntity>> =repository.local.readRecipes().asLiveData()
    val readFavouriteRecipesFromDatabase:LiveData<List<FavouritesEntity>> =repository.local.readFavouriteRecipes().asLiveData()


    private fun insertRecipes(recipesEntity: RecipesEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertRecipes(recipesEntity)
        }
    }

     fun insertFavouriteRecipes(favouritesEntity: FavouritesEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertFavouriteRecipes(favouritesEntity)
        }
    }

    fun deleteFavouriteRecipe(favouritesEntity: FavouritesEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteFavouriteRecipe(favouritesEntity)
        }
    }

    fun deleteAllFavouriteRecipes(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteAllFavouriteRecipes()
        }
    }

    /** Retrofit */
    var recipesResponse: MutableLiveData<NetworkResult<RecipeSearchResponse>> = MutableLiveData()
    var searchedRecipeResponse: MutableLiveData<NetworkResult<RecipeSearchResponse>> = MutableLiveData()

    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)
    }

    fun searchRecipes(searchQuery:Map<String, String>) = viewModelScope.launch {
        getSearchRecipeSafeCall(searchQuery)
    }

    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {
        recipesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getRecipes(queries)
                recipesResponse.value = handleFoodRecipesResponse(response)

                val foodRecipe=recipesResponse.value!!.data
                if(foodRecipe!=null){
                    offlineCacheRecipes(foodRecipe)
                }
            } catch (e: Exception) {
                recipesResponse.value = NetworkResult.Error(message = "Recipes not found")
            }
        } else {
            recipesResponse.value = NetworkResult.Error(message = "No Internet Connection")
        }
    }

    private suspend fun getSearchRecipeSafeCall(searchQuery: Map<String, String>) {
        searchedRecipeResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.searchRecipe(searchQuery)
                searchedRecipeResponse.value = handleFoodRecipesResponse(response)

                // We dont want to cache our searched recipes
                
            } catch (e: Exception) {
                searchedRecipeResponse.value = NetworkResult.Error(message = "Recipes not found")
            }
        } else {
            searchedRecipeResponse.value = NetworkResult.Error(message = "No Internet Connection")
        }
    }

    private fun offlineCacheRecipes(foodRecipe: RecipeSearchResponse) {
        val recipesEntity= RecipesEntity(foodRecipe)
        insertRecipes(recipesEntity)
    }

    private fun handleFoodRecipesResponse(response: Response<RecipeSearchResponse>): NetworkResult<RecipeSearchResponse>? {
        when {
            response.message().toString()
                .contains("timeout") -> { //We need to know the api responses and modify accordingly
                return NetworkResult.Error(message = "Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error(message = "API key limited")
            }
            response.body()!!.results.isNullOrEmpty() -> {
                return NetworkResult.Error(message = "Recipes not found")
            }
            response.isSuccessful -> {
                val foodRecipes = response.body()
                return NetworkResult.Success(foodRecipes!!)
            }
            else -> {
                return NetworkResult.Error(message = response.message())
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager =
            getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}