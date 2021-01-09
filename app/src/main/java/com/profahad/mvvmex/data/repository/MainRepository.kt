package com.profahad.mvvmex.data.repository

import com.profahad.mvvmex.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getUsers() = apiHelper.getUsers()

    suspend fun getUser(id: String) = apiHelper.getUser(id)

}