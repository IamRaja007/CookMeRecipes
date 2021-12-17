package com.example.cookmerecipes.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.cookmerecipes.data.DataStoreRepository
import com.example.cookmerecipes.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {

    var mealType = Constants.DEFAULT_MEAL_TYPE
    var dietType = Constants.DEFAULT_DIET_TYPE

    var networkStatus = false
    var backOnline = false

    val readMealAndDietType = dataStoreRepository.readMealAndDietType
    val readBackOnline = dataStoreRepository.readOnline.asLiveData()

    fun saveMealAndDietType(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveMealAndDietType(mealType, mealTypeId, dietType, dietTypeId)
        }

    fun saveBackOnline(backOnline: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        dataStoreRepository.saveBackOnline(backOnline)
    }

    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        viewModelScope.launch {
            readMealAndDietType.collect { value ->
                mealType = value.selectedMealType
                dietType = value.selectedDietType
            }
        }

        queries["number"] = "50"
        queries["apiKey"] = Constants.API_KEY
        queries["type"] = mealType
        queries["diet"] = dietType
        queries["addRecipeInformation"] = "true"
        queries["fillIngredients"] = "true"

        return queries
    }

    fun applySearchQueries(searchQuery: String): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        queries["query"] = searchQuery
        queries["number"] = "50"
        queries["apiKey"] = Constants.API_KEY
        queries["addRecipeInformation"] = "true"
        queries["fillIngredients"] = "true"

        return queries
    }

    fun showNetworkStatus() {
        if (!networkStatus) {
            Toast.makeText(getApplication(), "No Internet Connection.", Toast.LENGTH_SHORT).show()
            saveBackOnline(true)
        } else if(networkStatus){
            if(backOnline){
                Toast.makeText(getApplication(), "Woohoo! We are back online.", Toast.LENGTH_SHORT).show()
                saveBackOnline(false)
            }
        }
    }

}