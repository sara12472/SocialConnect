package com.example.socialconnect.Domain.UseCases.UserUsecase

import com.example.socialconnect.Domain.Repository.PostRepository
import javax.inject.Inject

class GetFeedPostsUseCase @Inject constructor(
    private val repository: PostRepository
) {
    operator fun invoke(
        followingIds: List<String>
    ) = repository.getFeedPosts(followingIds)
}