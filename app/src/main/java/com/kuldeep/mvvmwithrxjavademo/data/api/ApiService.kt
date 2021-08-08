package com.kuldeep.mvvmwithrxjavademo.data.api

import com.kuldeep.mvvmwithrxjavademo.data.model.User
import io.reactivex.Single

interface ApiService {
    fun getUser(): Single<List<User>>
}