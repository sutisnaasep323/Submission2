package com.example.submission2.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorite_user")
data class FavoriteUser(
    val login: String,
    val url: String,
    val avatar_url: String,
    @PrimaryKey
    val id: Int
): Serializable
