package com.android.githubinfo.net.model

import com.google.gson.annotations.SerializedName

data class UserInfoResponse(
    @SerializedName("id") var id: Int?,
    @SerializedName("login") var login: String,
    @SerializedName("avatar_url") var avatar_url: String,
    @SerializedName("repos_url") var repos_url: String = "",
    @SerializedName("html_url") var html_url: String,
    @SerializedName("name") var name: String,
    @SerializedName("company") var company: String?,
    @SerializedName("blog") var blog: String?,
    @SerializedName("location") var location: String?,
    @SerializedName("email") var email: String,
    @SerializedName("bio") var bio: String?,
    @SerializedName("twitter_username") var twitter_username: String?,
    @SerializedName("message") var message: String = ""
)