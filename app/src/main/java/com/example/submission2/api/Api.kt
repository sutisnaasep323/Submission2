package com.example.submission2.api

import com.example.submission2.model.DetailUserResponse
import com.example.submission2.model.UserItem
import com.example.submission2.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    @Headers("Authorization: token (KEY API)")
    fun getSearchUsers(
        @Query("q") query: String
    ):Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token (KEY API)")
    fun getDetailUser (
        @Path("username") username: String?
    ):Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token (KEY API)")
    fun getFollowers (
        @Path("username") username: String
    ):Call<ArrayList<UserItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token (KEY API)")
    fun getFollowing (
        @Path("username") username: String
    ):Call<ArrayList<UserItem>>
}