package com.shehan.apprichitecture.di

import android.content.Context
import androidx.room.Room
import com.shehan.apprichitecture.data.database.RecipesDatabase
import com.shehan.apprichitecture.util.Constants.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        RecipesDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideDao(database: RecipesDatabase) = database.recipesDao()

    @Singleton
    @Provides
    fun provideUserDao(database: RecipesDatabase) = database.userDao()

}