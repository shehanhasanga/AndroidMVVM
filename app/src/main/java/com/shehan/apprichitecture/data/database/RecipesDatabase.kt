package com.shehan.apprichitecture.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shehan.apprichitecture.data.database.converters.RecipesTypeConverter
import com.shehan.apprichitecture.data.database.dao.RecipesDao
import com.shehan.apprichitecture.data.database.dao.UserDao
import com.shehan.apprichitecture.data.database.entities.FavoritesEntity
import com.shehan.apprichitecture.data.database.entities.FoodJokeEntity
import com.shehan.apprichitecture.data.database.entities.RecipesEntity
import com.shehan.apprichitecture.data.database.entities.User

@Database(
    entities = [RecipesEntity::class, FavoritesEntity::class, FoodJokeEntity::class, User::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RecipesTypeConverter::class)
abstract class RecipesDatabase: RoomDatabase() {

    abstract fun recipesDao(): RecipesDao
    abstract fun userDao(): UserDao

}