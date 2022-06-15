package com.example.truecallercontentfinder.ui.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.truecallercontentfinder.TestCoroutineRule
import com.example.truecallercontentfinder.core.ApiService
import com.example.truecallercontentfinder.core.util.Result
import com.example.truecallercontentfinder.data.ContentFinderRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import retrofit2.Response

@ExperimentalCoroutinesApi
class ContentFinderRepositoryTest {

    private lateinit var repository: ContentFinderRepository
    private val fakeApiService: ApiService = Mockito.mock(ApiService::class.java)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = TestCoroutineRule()

    @BeforeEach
    fun setUp() {
        repository =
            ContentFinderRepository(apiService = fakeApiService)
    }

    @Test
    fun `getContent should return string result`() = mainCoroutineRule.runBlockingTest {

        val fakeContent = "<!DOCTYPE html>\n" +
                "<!--[if IE 7]>\n" +
                "<html class=\"ie ie7\" lang=\"en-US\">\n" +
                "<![endif]-->\n" +
                "<!--[if IE 8]>\n" +
                "<html class=\"ie ie8\" lang=\"en-US\">\n" +
                "<![endif]-->\n" +
                "<!--[if !(IE 7) & !(IE 8)]><!-->\n" +
                "<html lang=\"en-US\">\n" +
                "<!--<![endif]-->\n" +
                "<head>"
        Mockito.`when`(fakeApiService.fetchContent()).thenReturn(Response.success(fakeContent))
        val job = launch {
            val result = repository.fetchContent()
            assert(result is Result.Success)
            val resultContent = (result) as Result.Success
            assert(resultContent.data.tenthCharContent == " ")
            assert(result.data.everyTenthCharContent == " ->a7ne<   =<-  <l-<-")
        }
        job.cancel()
    }


    @Test
    fun `getContent should return error result`() = mainCoroutineRule.runBlockingTest {

        Mockito.`when`(fakeApiService.fetchContent()).thenReturn(
            Response.error(
                503,
                "".toResponseBody()
            )
        )
        val job = launch {
            val result = repository.fetchContent()
            assert(result is Result.Error)
        }
        job.cancel()
    }


}