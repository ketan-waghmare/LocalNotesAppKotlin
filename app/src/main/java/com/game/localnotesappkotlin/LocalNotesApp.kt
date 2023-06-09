package com.game.localnotesappkotlin

import android.app.Application
import androidx.room.Room
import com.game.localnotesappkotlin.database.NotesDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LocalNotesApp : Application() {

    val db by lazy {
        NotesDatabase.getInstance(this)
    }
}