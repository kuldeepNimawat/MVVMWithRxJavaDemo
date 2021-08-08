package com.kuldeep.mvvmwithrxjavademo.data.repository

import com.kuldeep.mvvmwithrxjavademo.data.api.ApiHelper
import com.kuldeep.mvvmwithrxjavademo.data.model.User
import io.reactivex.Single

class MainRepository(private val apiHelper: ApiHelper) {
    fun getUser(): Single<List<User>> {
        return apiHelper.getUser()
    }
}