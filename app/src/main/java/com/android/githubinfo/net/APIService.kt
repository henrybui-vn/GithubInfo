package com.android.githubinfo.net

import com.android.githubinfo.model.ProjectInfo
import com.android.githubinfo.net.model.UserInfoResponse
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    @GET
    fun getUserInfo(@Url url: String): Observable<UserInfoResponse>

    @GET
    fun getProjectsInfo(@Url url: String): Observable<List<ProjectInfo>>
}