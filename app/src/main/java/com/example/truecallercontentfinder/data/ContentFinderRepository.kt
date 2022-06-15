package com.example.truecallercontentfinder.data

import com.example.truecallercontentfinder.core.ApiService
import com.example.truecallercontentfinder.core.util.Result
import com.example.truecallercontentfinder.domain.models.Content
import com.example.truecallercontentfinder.domain.models.ContentImpl
import java.util.regex.Pattern
import javax.inject.Inject


class ContentFinderRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun fetchContent(): Result<Content> {
        val response = apiService.fetchContent()
        return if (response.isSuccessful) {
            try {
                val result = response.body().orEmpty()

                val list = result.toList()
                val map: MutableMap<String, Int> = mutableMapOf()
                result.trim().split(Pattern.compile("\\s+")).forEach {
                    val str = it.lowercase().trim()
                    map[str] = (map[str] ?: 0).plus(1)
                }

                Result.Success(
                    ContentImpl(
                        tenthCharContent = list.getOrNull(9).toString(),
                        everyTenthCharContent = list.slice(9..list.lastIndex step 10).joinToString(""),
                        wordCountContent = map.toString()

                    )
                )

            } catch (e: Exception) {
                Result.Error((e.localizedMessage.orEmpty()))
            }
        } else {
            Result.Error((response.errorBody().toString()))
        }
    }
}