package com.game.localnotesappkotlin.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user-table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userName : String = "",
    val email : String = "",
    val password : String = ""
)
