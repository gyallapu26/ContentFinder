package com.example.truecallercontentfinder.domain.models


/**
 * It's Domain layer, it respects Domain driven design principle
 *
 * @see tenthCharContent
 * Tenth char of the content
 *
 * @see everyTenthCharContent
 * Every tenth char from the content
 *
 * @see wordCountContent
 * Word count from the content
 *
 */


interface Content {

    val tenthCharContent: String
    val everyTenthCharContent: String
    val wordCountContent: String

}