package com.example.truecallercontentfinder.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.truecallercontentfinder.R
import com.example.truecallercontentfinder.core.util.gone
import com.example.truecallercontentfinder.core.util.visible
import com.example.truecallercontentfinder.databinding.ActivityMainBinding
import com.example.truecallercontentfinder.helper.isNetworkAvailable
import com.example.truecallercontentfinder.ui.state.TrueCallerContent
import com.example.truecallercontentfinder.ui.viewmodel.ContentFinderViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ContentFinderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ContentFinderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[ContentFinderViewModel::class.java]
        setContentView(binding.root)
        initClickListener()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { uiState ->
                    if (uiState.isLoading == true) {
                        binding.fetchContentButton.gone()
                        binding.progressBar.visible()
                        binding.errorMsgText.gone()
                        return@collectLatest
                    }
                    uiState.errorMessage?.let {
                        binding.fetchContentButton.visible()
                        binding.progressBar.gone()
                        binding.errorMsgText.visible()
                        return@collectLatest
                    }
                    uiState.content?.let {
                        binding.progressBar.gone()
                        binding.errorMsgText.gone()
                        binding.fetchContentButton.gone()
                        showContent(it)
                    }

                }
            }
        }
    }

    private fun initClickListener() {
        binding.fetchContentButton.setOnClickListener {
            checkConnectivityAndFetchData()
        }
    }

    private fun checkConnectivityAndFetchData() {
        if (this.isNetworkAvailable()) {
            viewModel.fetchContent()
        } else {
            binding.errorMsgText.visible()
            binding.errorMsgText.text = getString(R.string.no_internet_message)
        }
    }

    private fun showContent(content: TrueCallerContent) {
        with(binding) {
            tenthCharTv.text = getString(R.string.first_char, content.tenthChar.orEmpty())
            everyTenthCharTv.text = getString(R.string.every_tenth_char, content.everyTenthChar.orEmpty())
            countForEachWordTv.text = getString(R.string.word_count, content.wordCount.orEmpty())
        }
    }

}