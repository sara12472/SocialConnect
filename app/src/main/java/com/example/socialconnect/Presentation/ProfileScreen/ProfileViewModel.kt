package com.example.socialconnect.Presentation.ProfileScreen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialconnect.Domain.UseCases.FollowUserUseCase
import com.example.socialconnect.Domain.UseCases.GetUserPostsUseCase
import com.example.socialconnect.Domain.UseCases.GetUserUseCase
import com.example.socialconnect.Domain.UseCases.IsFollowingUseCase
import com.example.socialconnect.Domain.UseCases.UnfollowUserUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getUserPostsUseCase: GetUserPostsUseCase,
    private val followUserUseCase: FollowUserUseCase,
    private val unfollowUserUseCase: UnfollowUserUseCase,
    private val isFollowingUseCase: IsFollowingUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()


    fun onTabSelected(index: Int) {

        _state.value = _state.value.copy(
            selectedTab = index
        )
    }
    fun loadUser(profileUserId: String) {

        viewModelScope.launch {

            val currentUserId =
                FirebaseAuth.getInstance().currentUser?.uid

            val user = getUserUseCase(profileUserId)

            var following = false

            if (
                currentUserId != null &&
                currentUserId != profileUserId
            ) {
                following = isFollowingUseCase(
                    currentUserId,
                    profileUserId
                )
            }

            _state.value = _state.value.copy(
                userId = profileUserId,
                currentUserId = currentUserId ?: "",
                isOwnProfile = currentUserId == profileUserId,
                isFollowing = following,

                name = user.name,
                username = user.username,
                bio = user.bio,
                profileImage = user.profileImage
            )

            loadPosts(profileUserId)
        }
    }
    private fun loadPosts(uid: String) {
        viewModelScope.launch {

            getUserPostsUseCase(uid).collect { posts ->
                _state.value = _state.value.copy(posts = posts)
            }
        }
    }
    fun onFollowClick(targetUserId: String) {

        viewModelScope.launch {

            val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
                ?: return@launch

            val current = _state.value.isFollowing

            if (current) {
                unfollowUserUseCase(currentUserId, targetUserId)
            } else {
                followUserUseCase(currentUserId, targetUserId)
            }

            _state.value = _state.value.copy(
                isFollowing = !current
            )
        }
    }
}