package com.profahad.mvvmex.data.api

import com.profahad.mvvmex.data.model.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("users/{userId}")
    suspend fun getUser(@Path("userId") id: String): User

}