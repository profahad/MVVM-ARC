package com.profahad.mvvmex.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("avatar")
    val image: String,
    @SerializedName("email")
    val userEmail: String,
    @SerializedName("id")
    val userId: String,
    @SerializedName("name")
    val userName: String


) {
    override fun toString(): String {
        return "{'image':'$image', 'userEmail':'$userEmail', 'userId':'$userId', 'userName':'$userName'}"
    }
}