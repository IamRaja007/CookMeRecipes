package com.example.cookmerecipes.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.example.cookmerecipes.data.Repository
import com.example.cookmerecipes.data.database.RecipesEntity
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

    val readRecipes:LiveData<List<RecipesEntity>> =repository.local.readDatabase().asLiveData()

    private fun insertRecipes(recipesEntity: RecipesEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertRecipes(recipesEntity)
        }
    }

    /** Retrofit */
    var recipesResponse: MutableLiveData<NetworkResult<RecipeSearchResponse>> = MutableLiveData()

    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)
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

    private fun offlineCacheRecipes(foodRecipe: RecipeSearchResponse) {
        val recipesEntity=RecipesEntity(foodRecipe)
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