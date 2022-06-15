package com.example.truecallercontentfinder.domain

import com.example.truecallercontentfinder.data.ContentFinderRepository
import javax.inject.Inject

/**
 * UseCase encapsulate complex business logic and can be reused by mutiple viewmodel
 * Although @see GetContentUseCase might not add much benefit in simple case but
 * when app grows complex it's useful!
 * */

class GetContentUseCase @Inject constructor (private val contentFinderRepository: ContentFinderRepository) {

    suspend operator fun invoke() = contentFinderRepository.fetchContent()
}