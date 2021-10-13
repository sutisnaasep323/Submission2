package com.example.submission2.api

import com.example.submission2.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    @Headers("Authorization: token (YOUR API KEY)")
    fun getSearchUsers(
        @Query("q") query: String
    ):Call<UserResponse>
}