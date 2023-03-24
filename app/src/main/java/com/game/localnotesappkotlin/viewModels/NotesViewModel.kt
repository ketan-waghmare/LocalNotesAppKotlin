package com.game.localnotesappkotlin.viewModels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.game.localnotesappkotlin.LocalNotesApp
import com.game.localnotesappkotlin.database.NotesDao
import com.game.localnotesappkotlin.database.NotesDatabase
import com.game.localnotesappkotlin.database.NotesEntity
import com.game.localnotesappkotlin.repositories.NoteRepository
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private var notesRepository : NoteRepository?= null
    var allNotes : LiveData<List<NotesEntity>>
    var userId : String?

    init {
        val dao = NotesDatabase.getInstance(application).notesDao()
        notesRepository = NoteRepository(dao)
        val sharedPref = application?.getSharedPreferences("MyApp", Context.MODE_PRIVATE)
        userId = sharedPref?.getString("userId", "")
        allNotes = notesRepository?.getAllNotes(userId!!)!!
    }

    fun addNote(notesEntity: NotesEntity) {
        viewModelScope.launch {
            notesRepository?.addNote(notesEntity)
        }
    }

    fun updateNote(notesEntity: NotesEntity) {
        viewModelScope.launch {
            notesRepository?.updateNote(notesEntity)
        }
    }

    fun deleteNote(notesEntity: NotesEntity) {
        viewModelScope.launch {
            notesRepository?.deleteNote(notesEntity)
        }
    }
}