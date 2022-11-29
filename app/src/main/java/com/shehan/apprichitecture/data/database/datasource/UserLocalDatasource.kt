package com.shehan.apprichitecture.data.database.datasource

import com.shehan.apprichitecture.data.database.dao.RecipesDao
import com.shehan.apprichitecture.data.database.dao.UserDao
import com.shehan.apprichitecture.data.database.entities.FoodJokeEntity
import com.shehan.apprichitecture.data.database.entities.RecipesEntity
import com.shehan.apprichitecture.data.database.entities.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserLocalDatasource@Inject constructor(
    private val userDao: UserDao
) {
    fun readUsers(): Flow<List<User>> {
        return userDao.getActiveUsers()
    }

    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }

}