package com.example.githubrepos.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repos")
data class Repo(
    @PrimaryKey(autoGenerate = false) var id: Long,
    var name: String?,
    var full_name: String?,
    var owner: Owner?,
    var private: Boolean?,
    var visibility: String?,
    var description: String?,
    var html_url: String?

) {
    constructor() : this(0, "", "", Owner(""), false, " ", " ", "")
}

data class Owner(val avatar_url: String)