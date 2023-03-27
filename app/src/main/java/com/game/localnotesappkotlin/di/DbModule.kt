package com.game.localnotesappkotlin.di

import android.content.Context
import androidx.room.Room
import com.game.localnotesappkotlin.database.NotesDatabase
import com.game.localnotesappkotlin.database.NotesEntity
import com.game.localnotesappkotlin.utils.Constants.NOTE_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DbModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, NotesDatabase::class.java, NOTE_DATABASE
    )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()


    @Provides
    @Singleton
    fun provideDao(db : NotesDatabase) = db.notesDao()

    @Provides
    fun provideEntity() = NotesEntity()
}