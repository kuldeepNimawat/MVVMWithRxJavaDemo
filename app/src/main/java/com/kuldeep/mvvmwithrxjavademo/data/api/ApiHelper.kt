package com.kuldeep.mvvmwithrxjavademo.data.api

class ApiHelper(private val apiService: ApiService) {
    fun getUser()=apiService.getUser()
}