package com.game.localnotesappkotlin.repositories

import androidx.lifecycle.LiveData
import com.game.localnotesappkotlin.database.NotesDao
import com.game.localnotesappkotlin.database.UserEntity
import javax.inject.Inject

class AuthRepository @Inject constructor(private val notesDao: NotesDao) {

    val userEntityFromDB : LiveData<List<UserEntity>> = notesDao.getAllUsers()

    suspend fun addUser(userEntity: UserEntity) {
        notesDao.insertUser(userEntity)
    }

    fun getUserIdFromDB(name: String) {
        notesDao.getUserIdByName(name)
    }

}