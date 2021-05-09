package com.example.mvvm.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.R
import com.example.mvvm.data.api.ApiHelper
import com.example.mvvm.data.api.ApiServiceImpl
import com.example.mvvm.data.model.User
import com.example.mvvm.ui.base.ViewModelFactory
import com.example.mvvm.ui.main.adapter.MainAdapter
import com.example.mvvm.ui.main.viewmodel.MainViewModel
import com.example.mvvm.utils.Status

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        setupViewModel()
        setupObserver()
    }
    private fun setupUI(){
        val rv = findViewById<RecyclerView>(R.id.recyclerView)

        rv.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf())
        rv.addItemDecoration(
            DividerItemDecoration(
                rv.context,
                (rv.layoutManager as LinearLayoutManager).orientation
            )
        )
        rv.adapter = adapter
    }

    private fun setupObserver(){
        val pb = findViewById<ProgressBar>(R.id.progressBar)
        val rv = findViewById<RecyclerView>(R.id.recyclerView)
        mainViewModel.getUsers().observe(this, Observer {
            when (it.status){
                Status.SUCCESS -> {
                    pb.visibility = View.GONE
                    it.data?.let{users -> renderList(users)}
                    rv.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    pb.visibility = View.VISIBLE
                    rv.visibility = View.GONE
                }
                Status.ERROR -> {
                    pb.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(users: List<User>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }
    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(ApiServiceImpl()))
        ).get(MainViewModel::class.java)
    }
}