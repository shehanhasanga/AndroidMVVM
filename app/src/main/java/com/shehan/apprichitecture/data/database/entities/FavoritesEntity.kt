package com.shehan.apprichitecture.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shehan.apprichitecture.models.Result
import com.shehan.apprichitecture.util.Constants.Companion.FAVORITE_RECIPES_TABLE

@Entity(tableName = FAVORITE_RECIPES_TABLE)
class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: Result
)