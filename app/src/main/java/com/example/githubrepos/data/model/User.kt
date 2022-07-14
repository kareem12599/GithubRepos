package com.example.githubrepos.data.model

data class User(
    val name: String,
    val full_name: String,
    val owner: Owner,
    val private: Boolean,
    val visibility: String,
)

data class Owner(val avatar_url: String)