package com.android.githubinfo.ui

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.githubinfo.model.ProjectInfo
import com.android.githubinfo.net.APIService
import com.android.githubinfo.net.model.UserInfoResponse
import com.android.githubinfo.utils.BASE_URL
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.java.KoinJavaComponent

class GithubInfoViewModel : ViewModel() {
    val userInfoData = MutableLiveData<UserInfoResponse>()
    val userInfoErrorData = MutableLiveData<String>()
    val projectsInfoData = MutableLiveData<List<ProjectInfo>>()
    val projectsInfoErrorData = MutableLiveData<String>()
    val apiService: APIService by KoinJavaComponent.inject(APIService::class.java)

    @SuppressLint("CheckResult")
    fun loadUserInfo(user: String) {
        val url = "/users/$user"
        apiService.getUserInfo(url).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    handleUserInfoResponse(result)
                },
                { error ->
                    println(error.message)
                    handleUserInfoError(error.message ?: "Error")
                }
            )
    }

    @SuppressLint("CheckResult")
    fun loadProjectsInfo(projectsRepo: String) {
        apiService.getProjectsInfo(projectsRepo.replace(BASE_URL, "")).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    handleProjectsInfoResponse(result)
                },
                { error ->
                    println(error.message)
                    handleProjectsInfoError(error.message ?: "Error")
                }
            )
    }

    private fun handleUserInfoResponse(userInfo: UserInfoResponse) {
        userInfoData.postValue(userInfo)
    }

    private fun handleUserInfoError(message: String) {
        userInfoErrorData.postValue(message)
    }

    private fun handleProjectsInfoResponse(projects: List<ProjectInfo>) {
        projectsInfoData.postValue(projects)
    }

    private fun handleProjectsInfoError(message: String) {
        projectsInfoErrorData.postValue(message)
    }
}