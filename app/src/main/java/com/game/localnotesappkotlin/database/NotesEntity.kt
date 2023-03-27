package com.game.localnotesappkotlin.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes-table")
data class NotesEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val userId : String = "",
    val noteTitle : String = "",
    val noteDescription : String = ""
)