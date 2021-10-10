package com.wwt.nimbleviewing.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.wwt.nimbleviewing.data.api.ApiHelper
import com.wwt.nimbleviewing.data.api.RetrofitBuilder
import com.wwt.nimbleviewing.data.model.Photos
import com.wwt.nimbleviewing.ui.adapter.AlbumListAdapter
import com.wwt.nimbleviewing.databinding.ActivityScrollingBinding
import com.wwt.nimbleviewing.ui.base.ViewModelFactory
import com.wwt.nimbleviewing.utils.Status
import com.wwt.nimbleviewing.viewmodel.ApiViewModel
import kotlinx.android.synthetic.main.activity_scrolling.*
import kotlinx.android.synthetic.main.content_scrolling.*

class ScrollingActivity : AppCompatActivity() {
    private lateinit var listAdapter: AlbumListAdapter
    private lateinit var viewModel: ApiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(ActivityScrollingBinding.inflate(LayoutInflater.from(this), null, false)) {
            setContentView(root)
            setSupportActionBar(toolbar)
            setUpViewModel()
            setupObservers()
            setupUI()

            toolbarLayout.title = this@ScrollingActivity.title

            fab.setOnClickListener {
                Snackbar.make(
                    album_list,
                    "Display album titles and images from ${"BuildConfig.BASE_URL"}",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setupUI() {
        album_list.layoutManager = LinearLayoutManager(this)
        listAdapter = AlbumListAdapter(arrayListOf())
        album_list.adapter = listAdapter
        album_list.addItemDecoration(
            DividerItemDecoration(
                album_list.context,
                (album_list.layoutManager as LinearLayoutManager).orientation
            )
        )
        album_list.adapter = listAdapter
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(ApiViewModel::class.java)
    }

    private fun setupObservers() {
        viewModel.getData().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        album_list.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        resource.data?.let { users -> retrieveList(users) }
                    }
                    Status.ERROR -> {
                        album_list.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        album_list.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun retrieveList(data: List<Photos>) {
        listAdapter.apply {
            addData(data)
            notifyDataSetChanged()
        }
    }
}