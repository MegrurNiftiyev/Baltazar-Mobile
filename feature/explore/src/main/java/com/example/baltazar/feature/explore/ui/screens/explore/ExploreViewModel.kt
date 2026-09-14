package com.example.baltazar.feature.explore.ui.screens.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baltazar.core.R
import com.example.baltazar.core.core.enums.ServiceType
import com.example.baltazar.core.core.enums.UserRole
import com.example.baltazar.core.core.managers.SessionManager
import com.example.baltazar.core.core.utils.SnackbarMessage
import com.example.baltazar.core.core.utils.SnackbarType
import com.example.baltazar.core.core.utils.UiText
import com.example.baltazar.core.domain.repository.IWishlistRepository
import com.example.baltazar.feature.explore.domain.repository.IExploreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.baltazar.core.core.managers.AuthGateManager
import com.example.baltazar.core.core.managers.SessionManager

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val exploreRepository: IExploreRepository,
    private val wishlistRepository: IWishlistRepository,
    val authGateManager: AuthGateManager,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _state = MutableStateFlow(ExploreState(isBannersLoading = true, isSectionsLoading = true))
    val state: StateFlow<ExploreState> = _state.asStateFlow()

    init {
        observeSessionUser()
        loadExploreData()
    }

    private fun observeSessionUser() {
        viewModelScope.launch {
            var previousUserId: String? = null
            sessionManager.user.collect { sessionUser ->
                val userChanged = previousUserId != null && previousUserId != sessionUser.id
                previousUserId = sessionUser.id
                _state.update { it.copy(user = sessionUser) }
                if (userChanged) {
                    loadExploreData()
                }
            }
        }
        viewModelScope.launch {
            sessionManager.isLoadingUser.collect { isLoading ->
                _state.update { it.copy(isUserLoading = isLoading) }
            }
        }
    }

    fun loadExploreData() {
        loadBanners()
        loadSections()
    }

    private fun loadBanners() {
        viewModelScope.launch(IO) {
            _state.update { it.copy(isBannersLoading = true, isBannersError = false) }
            val bannersResult = exploreRepository.getBanners()
            bannersResult.fold(
                onSuccess = { bannerList ->
                    val sortedBanners = bannerList.sortedBy { it.order }
                    _state.update {
                        it.copy(
                            isBannersLoading = false,
                            isBannersError = false,
                            banners = sortedBanners
                        )
                    }
                },
                onFailure = { error ->
                    _state.update {
                        it.copy(
                            isBannersLoading = false,
                            isBannersError = true,
                            userMessage = error.message?.let { msg ->
                                SnackbarMessage(UiText.DynamicString(msg), SnackbarType.ERROR)
                            }
                        )
                    }
                }
            )
        }
    }

    private fun loadSections() {
        viewModelScope.launch(IO) {
            _state.update { it.copy(isSectionsLoading = true, isSectionsError = false) }
            val sectionsResult = exploreRepository.getExploreSections()
            sectionsResult.fold(
                onSuccess = { sectionList ->
                    val sortedSections = sectionList.sortedBy { it.order }
                    _state.update {
                        it.copy(
                            isSectionsLoading = false,
                            isSectionsError = false,
                            sections = sortedSections
                        )
                    }
                },
                onFailure = { error ->
                    _state.update {
                        it.copy(
                            isSectionsLoading = false,
                            isSectionsError = true,
                            userMessage = error.message?.let { msg ->
                                SnackbarMessage(UiText.DynamicString(msg), SnackbarType.ERROR)
                            }
                        )
                    }
                }
            )
        }
    }

    fun isGuest(): Boolean = authGateManager.isGuest()

    fun toggleFavorite(itemId: String, serviceType: ServiceType, isFav: Boolean) {
        if (sessionManager.user.value.isGuest) {
            return
        }

        _state.update { currentState ->
            val updatedSections = currentState.sections.map { sec ->
                val updatedItems = sec.items.map { item ->
                    if (item.id == itemId) item.copy(isLiked = isFav) else item
                }
                sec.copy(items = updatedItems)
            }
            currentState.copy(sections = updatedSections)
        }
        viewModelScope.launch(IO) {
            withContext(NonCancellable) {
                if (isFav) {
                    wishlistRepository.addToWishlist(serviceId = itemId, serviceType = serviceType)
                } else {
                    wishlistRepository.removeFromWishlist(id = itemId)
                }
            }
        }
    }

    fun onMessageShown() {
        _state.update { it.copy(userMessage = null) }
    }
}
