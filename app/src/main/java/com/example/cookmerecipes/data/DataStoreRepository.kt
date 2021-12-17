package com.example.cookmerecipes.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.cookmerecipes.util.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.PREFERENCES_NAME)
@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferenceKeys {
        val selectedMealType = stringPreferencesKey("mealType")
        val selectedMealTypeId = intPreferencesKey("mealTypeId") //for meal chip id

        val selectedDietType = stringPreferencesKey("dietType")
        val selectedDietTypeId = intPreferencesKey("dietTypeId") //for diet chip id

        val backOnline = booleanPreferencesKey("backOnline")
    }

    private val settingsDataStore = context.dataStore

    suspend fun saveMealAndDietType(
        selectedMealType: String,
        selectedMealTypeId: Int,
        selectedDietType: String,
        selectedDietTypeId: Int
    ) {

        settingsDataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedMealType] = selectedMealType
            preferences[PreferenceKeys.selectedMealTypeId] = selectedMealTypeId
            preferences[PreferenceKeys.selectedDietType] = selectedDietType
            preferences[PreferenceKeys.selectedDietTypeId] = selectedDietTypeId

        }


    }

    suspend fun saveBackOnline(backOnline:Boolean){
        settingsDataStore.edit { preferences ->
            preferences[PreferenceKeys.backOnline] = backOnline
        }
    }

    val readMealAndDietType: Flow<MealAndDietType> = settingsDataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        val selectedMealType: String = preferences[PreferenceKeys.selectedMealType]
            ?: "main course" //if selectedMealType key is empty then we return 'main course'
        val selectedMealTypeId: Int = preferences[PreferenceKeys.selectedMealTypeId] ?: 0
        val selectedDietType: String = preferences[PreferenceKeys.selectedDietType] ?: "gluten free"
        val selectedDietTypeId: Int = preferences[PreferenceKeys.selectedDietTypeId] ?: 0

        MealAndDietType(
            selectedMealType,
            selectedMealTypeId,
            selectedDietType,
            selectedDietTypeId
        )
    }

    val readOnline:Flow<Boolean> =settingsDataStore.data.catch { exception->
        if(exception is IOException){
            emit(emptyPreferences())
        }
        else{
            throw exception
        }

    }.map { preferences ->
        val backOnline =preferences[PreferenceKeys.backOnline] ?: false
        backOnline
    }

}

data class MealAndDietType(
    val selectedMealType: String,
    val selectedMealTypeId: Int, //chip id
    val selectedDietType: String,
    val selectedDietTypeId: Int
)