package com.kuldeep.mvvmwithrxjavademo.data.api

import com.kuldeep.mvvmwithrxjavademo.data.model.User
import com.rx2androidnetworking.Rx2ANRequest
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Single

class ApiServiceImp : ApiService {
    override fun getUser() : Single<List<User>> {
       return Rx2AndroidNetworking.get("https://5e510330f2c0d300147c034c.mockapi.io/users")
           .build()
           .getObjectListSingle(User::class.java)
    }
}