package com.example.submission2.model

data class DetailUserResponse(
    val login: String,
    val avatar_url: String,
    val name: String,
    val followers: Int,
    val following: Int,
    val public_repos: Int,
    val location: String,
    val company: String
)
