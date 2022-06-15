package com.example.truecallercontentfinder.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truecallercontentfinder.core.util.Result
import com.example.truecallercontentfinder.domain.GetContentUseCase
import com.example.truecallercontentfinder.ui.state.ContentUiState
import com.example.truecallercontentfinder.ui.state.TrueCallerContent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContentFinderViewModel @Inject constructor(
    private val getContentUseCase: GetContentUseCase,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState: MutableStateFlow<ContentUiState> = MutableStateFlow(ContentUiState())
    val uiState = _uiState.asStateFlow()

    fun fetchContent() {
        _uiState.update { currentUiState ->
            currentUiState.copy(
                isLoading = true
            )
        }
        viewModelScope.launch(
            ioDispatcher +
                    CoroutineExceptionHandler { _, throwable ->
                        handleException(throwable)
                    }) {
            val result = getContentUseCase()
            _uiState.update { currentUiState ->
                when (result) {
                    is Result.Success -> {
                        val content = result.data
                        currentUiState.copy(
                            isLoading = false,
                            content = TrueCallerContent(
                                tenthChar = content.tenthCharContent,
                                everyTenthChar = content.everyTenthCharContent,
                                wordCount = content.wordCountContent
                            )
                        )
                    }
                    is Result.Error -> {
                        currentUiState.copy(
                            isLoading = false,
                            errorMessage = result.errorMessage
                        )
                    }
                }
            }
        }
    }

    private fun handleException(throwable: Throwable) {
        _uiState.update {
            it.copy(
                isLoading = false,
                errorMessage = throwable.localizedMessage
            )
        }
    }

}