package com.example.truecallercontentfinder.ui.state

/**/

data class ContentUiState(
    val isLoading: Boolean? = null,
    val errorMessage: String? = null,
    val content: TrueCallerContent? = null
)

data class TrueCallerContent(
    val tenthChar: String? = null,
    val everyTenthChar: String? = null,
    val wordCount: String? = null,
)

