package com.cube.remotechains.ui.main.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.cube.remotechains.data.db.RemoteJobDatabase
import com.cube.remotechains.data.repository.RemoteJobRepository
import com.cube.remotechains.databinding.ActivityMainBinding
import com.cube.remotechains.ui.main.viewmodel.RemoteJobViewModel
import com.cube.remotechains.ui.main.viewmodel.RemoteJobViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: RemoteJobViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""
        setUpViewModel()
    }

    private fun setUpViewModel() {
        val remoteRepository = RemoteJobRepository(RemoteJobDatabase(this))
        val viewModelProviderFactory = RemoteJobViewModelFactory(
            application, remoteRepository
        )
        viewModel = ViewModelProvider(
            this, viewModelProviderFactory
        ).get(RemoteJobViewModel::class.java)
    }
}