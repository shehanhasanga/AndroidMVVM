package com.shehan.apprichitecture.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shehan.apprichitecture.data.database.converters.RecipesTypeConverter
import com.shehan.apprichitecture.data.database.dao.RecipesDao
import com.shehan.apprichitecture.data.database.entities.FavoritesEntity
import com.shehan.apprichitecture.data.database.entities.FoodJokeEntity
import com.shehan.apprichitecture.data.database.entities.RecipesEntity

@Database(
    entities = [RecipesEntity::class, FavoritesEntity::class, FoodJokeEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RecipesTypeConverter::class)
abstract class RecipesDatabase: RoomDatabase() {

    abstract fun recipesDao(): RecipesDao

}