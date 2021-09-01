package com.example.githubsearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.githubsearch.databinding.ActivityMainBinding
import com.example.githubsearch.utils.getViewModel
import com.example.githubsearch.utils.observe

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by lazy { getViewModel<MainViewModel>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.observeData()
        viewModel.searchUsers("david")
    }

    private fun MainViewModel.observeData() {
        observe(useListLiveData) {
            // TODO
            binding.test.text = "${it?.get(0)?.name}"
        }
    }
}