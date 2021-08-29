package com.android.githubinfo.model

import com.google.gson.annotations.SerializedName

data class ProjectInfo(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String = "",
    @SerializedName("description")
    var description: String = "",
    @SerializedName("html_url")
    var url: String,
    @SerializedName("language")
    var language: String = "",
    @SerializedName("watchers_count")
    var watchers_count: Int = 0,
    @SerializedName("forks_count")
    var forks_count: Int = 0
)