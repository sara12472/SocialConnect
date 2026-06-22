package com.example.socialconnect.Presentation.ProfileScreen


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialconnect.Domain.UseCases.FollowUseCase.FollowUserUseCase
import com.example.socialconnect.Domain.UseCases.UserUsecase.GetCurrentUserIdUseCase
import com.example.socialconnect.Domain.UseCases.FollowUseCase.GetFollowersCountUseCase
import com.example.socialconnect.Domain.UseCases.FollowUseCase.GetFollowingCountUseCase
import com.example.socialconnect.Domain.UseCases.PostUseCase.GetUserPostsUseCase
import com.example.socialconnect.Domain.UseCases.UserUsecase.GetUserUseCase
import com.example.socialconnect.Domain.UseCases.FollowUseCase.IsFollowingUseCase
import com.example.socialconnect.Domain.UseCases.FollowUseCase.UnfollowUserUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
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
    private val isFollowingUseCase: IsFollowingUseCase,
    private val getFollowersCountUseCase: GetFollowersCountUseCase,
    private  val getFollowingCountUseCase: GetFollowingCountUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase
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
            Log.d("ProfileViewModel", "loadUser called with: $profileUserId")

            val currentUserId = getCurrentUserIdUseCase()
            Log.d("ProfileViewModel", "currentUserId from useCase: $currentUserId")
            val user = getUserUseCase(profileUserId)
            Log.d("ProfileViewModel", "Fetched user: ${user.username}, profileImage: ${user.profileImage}")

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
            loadFollowCounts(profileUserId)
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

            val currentUserId = getCurrentUserIdUseCase() ?: return@launch
            val current = _state.value.isFollowing

            if (current) {
                unfollowUserUseCase(currentUserId, targetUserId)
            } else {
                followUserUseCase(currentUserId, targetUserId)
            }

            loadFollowCounts(targetUserId)
            _state.value = _state.value.copy(isFollowing = !current)
        }
    }
    private fun loadFollowCounts(userId: String) {

        viewModelScope.launch {

            getFollowersCountUseCase(userId).collect { count ->

                _state.value = _state.value.copy(
                    followersCount = count
                )
            }
        }

        viewModelScope.launch {

            getFollowingCountUseCase(userId).collect { count ->

                _state.value = _state.value.copy(
                    followingCount = count
                )
            }
        }
    }
}