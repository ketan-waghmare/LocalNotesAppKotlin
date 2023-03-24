package com.game.localnotesappkotlin.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.game.localnotesappkotlin.database.NotesDatabase
import com.game.localnotesappkotlin.database.NotesEntity
import com.game.localnotesappkotlin.database.UserEntity
import com.game.localnotesappkotlin.repositories.AuthRepository
import com.game.localnotesappkotlin.repositories.NoteRepository
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private var authRepository : AuthRepository
    var allUserList : LiveData<List<UserEntity>>

    init {
        val dao = NotesDatabase.getInstance(application).notesDao()
        authRepository = AuthRepository(dao)
        allUserList  = authRepository.userEntityFromDB
    }

    fun addUser(userEntity: UserEntity) {
        viewModelScope.launch {
            authRepository?.addUser(userEntity)
        }
    }

    fun getUserIdByName(name : String) {
        viewModelScope.launch {
            authRepository?.getUserIdFromDB(name)
        }
    }


}