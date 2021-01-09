package com.profahad.mvvmex.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.profahad.mvvmex.R
import com.profahad.mvvmex.data.api.ApiHelper
import com.profahad.mvvmex.data.api.RetrofitBuilder
import com.profahad.mvvmex.data.model.User
import com.profahad.mvvmex.ui.base.ViewModelFactory
import com.profahad.mvvmex.ui.main.adapter.MainAdapter
import com.profahad.mvvmex.ui.main.listeners.RecyclerViewItemClickListener
import com.profahad.mvvmex.ui.main.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

import com.profahad.mvvmex.utils.Status.LOADING
import com.profahad.mvvmex.utils.Status.ERROR
import com.profahad.mvvmex.utils.Status.SUCCESS

class MainActivity : AppCompatActivity(), RecyclerViewItemClickListener {


    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
        setupUI()
        setupObservers()
    }


    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(
                ApiHelper(
                    RetrofitBuilder.apiService
                )
            )
        ).get(MainViewModel::class.java)
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(this, arrayListOf())
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getUsers().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    SUCCESS -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        resource.data?.let { users ->
                            retrieveList(users)
                        }
                    }
                    ERROR -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun getUserInfo(id: String) {
        viewModel.getUser(id).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    SUCCESS -> {
                        progressBar.visibility = View.GONE
                        resource.data?.let { user ->
                            Toast.makeText(this, user.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                    ERROR -> {
                        progressBar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    LOADING -> {
                        progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun retrieveList(users: List<User>) {
        adapter.apply {
            addUsers(users)
            notifyDataSetChanged()
        }
    }

    override fun onItemClicked(user: User) {
        getUserInfo(user.userId)
    }

}