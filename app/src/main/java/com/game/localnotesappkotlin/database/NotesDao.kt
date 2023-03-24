package com.game.localnotesappkotlin.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NotesDao {

    // adding a new entry to our database.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(userEntity: UserEntity)

    @Query("select * from `user-table`")
    fun getAllUsers(): LiveData<List<UserEntity>>

    @Query("select * from `user-table` where userName = :userName")
    fun getUserIdByName(userName: String): LiveData<UserEntity>

    @Insert
    suspend fun insertNotes(notesEntity: NotesEntity)

    @Update
    suspend fun updateNote(notesEntity: NotesEntity)

    @Delete
    suspend fun delete(notesEntity: NotesEntity)

    @Query("select * from `notes-table` where userId = :userId order by id desc")
    fun getAllNotes(userId: String): LiveData<List<NotesEntity>>


}