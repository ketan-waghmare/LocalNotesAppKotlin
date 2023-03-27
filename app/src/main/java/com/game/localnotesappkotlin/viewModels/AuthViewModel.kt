package com.game.localnotesappkotlin.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.game.localnotesappkotlin.database.NotesDatabase
import com.game.localnotesappkotlin.database.NotesEntity
import com.game.localnotesappkotlin.database.UserEntity
import com.game.localnotesappkotlin.repositories.AuthRepository
import com.game.localnotesappkotlin.repositories.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    var allUserList : LiveData<List<UserEntity>> = authRepository.userEntityFromDB


    fun addUser(userEntity: UserEntity) {
        viewModelScope.launch {
            authRepository?.addUser(userEntity)
        }
    }

}