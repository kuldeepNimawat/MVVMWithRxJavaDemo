package com.kuldeep.mvvmwithrxjavademo.ui.main.view

import androidx.lifecycle.Observer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kuldeep.mvvmwithrxjavademo.R
import com.kuldeep.mvvmwithrxjavademo.data.api.ApiHelper
import com.kuldeep.mvvmwithrxjavademo.data.api.ApiServiceImp
import com.kuldeep.mvvmwithrxjavademo.data.model.User
import com.kuldeep.mvvmwithrxjavademo.ui.base.ViewModelFactory
import com.kuldeep.mvvmwithrxjavademo.ui.main.adapter.MainAdapter
import com.kuldeep.mvvmwithrxjavademo.ui.main.viewmodel.MainViewModel
import com.kuldeep.mvvmwithrxjavademo.utils.Status
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        mainViewModel = ViewModelProviders.of(this,ViewModelFactory(ApiHelper(ApiServiceImp()))).get(MainViewModel::class.java)
        setupObserver()
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf())
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private fun setupObserver() {
        mainViewModel.getUser().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data?.let { users -> renderList(users) }
                    recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(users: List<User>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }
}