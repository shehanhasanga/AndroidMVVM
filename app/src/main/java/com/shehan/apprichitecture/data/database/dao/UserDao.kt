package com.shehan.apprichitecture.data.database.dao

import androidx.room.*
import com.shehan.apprichitecture.data.database.entities.FoodJokeEntity
import com.shehan.apprichitecture.data.database.entities.RecipesEntity
import com.shehan.apprichitecture.data.database.entities.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM users WHERE isLogged = 1 LIMIT 1")
    fun getActiveUsers(): Flow<List<User>>


}