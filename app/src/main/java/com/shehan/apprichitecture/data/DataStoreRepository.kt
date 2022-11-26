package com.shehan.apprichitecture.data

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.emptyPreferences
import androidx.datastore.preferences.preferencesKey

import com.shehan.apprichitecture.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.shehan.apprichitecture.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.shehan.apprichitecture.util.Constants.Companion.PREFERENCES_BACK_ONLINE
import com.shehan.apprichitecture.util.Constants.Companion.PREFERENCES_DIET_TYPE
import com.shehan.apprichitecture.util.Constants.Companion.PREFERENCES_DIET_TYPE_ID
import com.shehan.apprichitecture.util.Constants.Companion.PREFERENCES_MEAL_TYPE
import com.shehan.apprichitecture.util.Constants.Companion.PREFERENCES_MEAL_TYPE_ID
import com.shehan.apprichitecture.util.Constants.Companion.PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.util.prefs.Preferences
import javax.inject.Inject

@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(
    @ApplicationContext private val context: Context,

    ) {
    private object PreferenceKeys{
        val selectedMealType  = preferencesKey<String>(PREFERENCES_MEAL_TYPE)
        val selectedMealTypeId =  preferencesKey<Int>(PREFERENCES_MEAL_TYPE_ID)
        val selectedDietType = preferencesKey<String>(PREFERENCES_DIET_TYPE)
        val selectedDietTypeId = preferencesKey<Int>(PREFERENCES_DIET_TYPE_ID)
        val backtoOnline = preferencesKey<Boolean>(PREFERENCES_BACK_ONLINE)

    }
    private val dataStore : DataStore<androidx.datastore.preferences.Preferences> = context.createDataStore(
        name = PREFERENCES_NAME
    )

    suspend fun saveBackOnline(status: Boolean){
        dataStore.edit { prefs ->
            prefs[PreferenceKeys.backtoOnline] = status
        }
    }

    val readBackOnline : Flow<Boolean> = dataStore.data
        .catch { exception ->
            if(exception is IOException){
                emit(emptyPreferences())
            }else{
                throw  exception
            }
        }
        .map { preferences ->
            val status = preferences[PreferenceKeys.backtoOnline] ?:false
            status

        }

    suspend fun saveMealAndDietType(mealType:String, mealTypeId: Int, dietType:String, dietTypeId: Int){
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedMealType] = mealType
            preferences[PreferenceKeys.selectedMealTypeId] = mealTypeId
            preferences[PreferenceKeys.selectedDietType] = dietType
            preferences[PreferenceKeys.selectedDietTypeId] = dietTypeId

            println("saved data +++++++++++++++++++")
            println(mealTypeId)

        }

    }

    val readMealAndDietType : Flow<MealAndDietType> = dataStore.data
        .catch { exception ->
            if(exception is IOException){
                emit(emptyPreferences())
            }else{
                throw  exception
            }
        }
        .map { preferences ->
            val selectedType = preferences[PreferenceKeys.selectedMealType] ?: "main course";
            val selectedTypeId = preferences[PreferenceKeys.selectedMealTypeId] ?: 0
            val selectedDietType = preferences[PreferenceKeys.selectedDietType] ?: "gluten free"
            val selectedDietTypeId = preferences[PreferenceKeys.selectedDietTypeId] ?: 0
            MealAndDietType(
                selectedType,
                selectedTypeId,
                selectedDietType,
                selectedDietTypeId
            )


        }
}

data class MealAndDietType(
    val selectedMealType: String,
    val selectedMealTypeId: Int,
    val selectedDietType: String,
    val selectedDietTypeId: Int
) {

}