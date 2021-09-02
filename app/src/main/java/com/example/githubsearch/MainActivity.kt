package com.example.githubsearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doOnTextChanged
import com.example.githubsearch.databinding.ActivityMainBinding
import com.example.githubsearch.utils.closeKeyboard
import com.example.githubsearch.utils.getViewModel
import com.example.githubsearch.utils.observe

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by lazy { getViewModel<MainViewModel>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        viewModel.observeData()
    }

    private fun initView() {
        with(binding) {
            editor.setOnEditorActionListener { textView, actionId, keyEvent ->
                when (actionId) {
                    EditorInfo.IME_ACTION_SEARCH -> {
                        closeKeyboard(this@MainActivity, binding.root)
                        viewModel.searchUsers()
                    }
                }
                return@setOnEditorActionListener false
            }
            editor.doOnTextChanged { text, start, before, count ->
                text?.also { viewModel.searchKey = it.toString() }
            }
            recyclerView.adapter = MainAdapter(viewModel.needToLoadNextEvent)
        }
    }

    private fun MainViewModel.observeData() {
        observe(useListLiveData) {
            it ?: return@observe
            (binding.recyclerView.adapter as MainAdapter?)?.setData(it)
        }
        observe(needToLoadNextEvent) {
            viewModel.loadNextPage()
        }
        observe(noInternetEvent) {
            openErrorDialog("Internet connect fail!!")
        }
        observe(errorMsgEvent) {
            it ?: return@observe
            openErrorDialog(it)
        }
        observe(isLoading) {
            binding.progressBar.visibility = if (it==true) View.VISIBLE else View.GONE
        }
    }

    private fun openErrorDialog(description: String) {
        supportFragmentManager.beginTransaction().add(
            ErrorDialog(description) {
                viewModel.searchUsers()
            }, null
        ).commitAllowingStateLoss()
    }
}