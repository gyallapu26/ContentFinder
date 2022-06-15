package com.example.truecallercontentfinder.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.truecallercontentfinder.TestCoroutineRule
import com.example.truecallercontentfinder.core.util.Result
import com.example.truecallercontentfinder.domain.GetContentUseCase
import com.example.truecallercontentfinder.domain.models.ContentImpl
import com.example.truecallercontentfinder.ui.state.ContentUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class ContentFinderViewModelTest {

    private lateinit var viewModel: ContentFinderViewModel
    private val mockGetContentUseCase: GetContentUseCase =
        Mockito.mock(GetContentUseCase::class.java)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = TestCoroutineRule()


    @BeforeEach
    fun setUp() {
        viewModel =
            ContentFinderViewModel(mockGetContentUseCase, mainCoroutineRule.testCoroutineDispatcher)
    }

    @Test
    fun `getContent should return error result`() = mainCoroutineRule.runBlockingTest {

        val errorMessage = "Something went wrong!"
        Mockito.`when`(mockGetContentUseCase.invoke()).thenReturn(Result.Error(errorMessage))
        val result = mutableListOf<ContentUiState>()
        val job = launch {
            viewModel.uiState.toList(result)
        }
        viewModel.fetchContent()
        println(result)
        val currentUiState = result.last()
        assert(currentUiState.isLoading == false)
        assert(currentUiState.errorMessage == errorMessage)
        assert(currentUiState.content == null)
        job.cancel()
    }

    @Test
    fun `getContent should return content result`() = mainCoroutineRule.runBlockingTest {

        val fakeTenthChar = " "
        val fakeEveryTenthChar = "jkasdj9>3"
        val fakeWordCount = "{<Doctype ->1}"
        val fakeContent = ContentImpl(
            tenthCharContent = fakeTenthChar,
            everyTenthCharContent = fakeEveryTenthChar,
            wordCountContent = fakeWordCount
        )
        Mockito.`when`(mockGetContentUseCase.invoke()).thenReturn(Result.Success(fakeContent))
        val result = mutableListOf<ContentUiState>()
        val job = launch {
            viewModel.uiState.toList(result)
        }
        viewModel.fetchContent()
        val currentUiState = result.last()
        assert(currentUiState.isLoading == false)
        assert(currentUiState.errorMessage == null)
        assert(currentUiState.content?.tenthChar.equals(fakeTenthChar))
        assert(currentUiState.content?.everyTenthChar.equals(fakeEveryTenthChar))
        assert(currentUiState.content?.wordCount.equals(fakeWordCount))
        job.cancel()
    }


}