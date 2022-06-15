package com.example.truecallercontentfinder.domain.models


class ContentImpl(
    override val tenthCharContent: String,
    override val everyTenthCharContent: String,
    override val wordCountContent: String
) : Content {
}