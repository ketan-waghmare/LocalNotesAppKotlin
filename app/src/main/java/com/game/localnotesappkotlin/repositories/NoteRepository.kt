package com.game.localnotesappkotlin.repositories

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.game.localnotesappkotlin.database.NotesDao
import com.game.localnotesappkotlin.database.NotesDatabase
import com.game.localnotesappkotlin.database.NotesEntity
import javax.inject.Inject
import kotlin.coroutines.coroutineContext


class NoteRepository @Inject constructor(private val notesDao: NotesDao) {

    lateinit var allNotes : LiveData<List<NotesEntity>>

    suspend fun addNote(notesEntity: NotesEntity) {
        notesDao.insertNotes(notesEntity)
    }

    suspend fun updateNote(notesEntity: NotesEntity) {
        notesDao.updateNote(notesEntity)
    }

    suspend fun deleteNote(notesEntity: NotesEntity) {
        notesDao.delete(notesEntity)
    }

    fun getAllNotes(userId: String) : LiveData<List<NotesEntity>> {
       allNotes = notesDao.getAllNotes(userId)
        return allNotes
    }

}