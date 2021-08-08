package com.kuldeep.mvvmwithrxjavademo.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kuldeep.mvvmwithrxjavademo.data.model.User
import com.kuldeep.mvvmwithrxjavademo.data.repository.MainRepository
import com.kuldeep.mvvmwithrxjavademo.utils.Resource
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val mainRepository: MainRepository) :ViewModel(){
    private val users= MutableLiveData<Resource<List<User>>>()
    private val compositeDisposable = CompositeDisposable()
    init {
        fetchUser()
    }

    private fun fetchUser(){
        users.postValue(Resource.loading(null))
        compositeDisposable.add(
            mainRepository.getUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ userlist->
                    users.postValue(Resource.success(userlist))
                }, { throwable ->
                    users.postValue(Resource.error("Something went wrong",null))
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun getUser():LiveData<Resource<List<User>>>{
        return users
    }
}