package com.shehan.apprichitecture.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shehan.apprichitecture.models.FoodRecipe
import com.shehan.apprichitecture.util.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    var foodRecipe: FoodRecipe
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}