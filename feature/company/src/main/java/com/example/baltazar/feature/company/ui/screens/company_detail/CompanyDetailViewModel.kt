package com.example.baltazar.feature.company.ui.screens.company_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baltazar.core.domain.repository.IWishlistRepository
import com.example.baltazar.feature.company.domain.repository.ICompanyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyDetailViewModel @Inject constructor(
    private val companyRepository: ICompanyRepository,
    private val wishlistRepository: IWishlistRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val companyId: String = savedStateHandle["id"] ?: savedStateHandle["companyId"] ?: ""
    private val _state = MutableStateFlow(CompanyDetailState())
    val state: StateFlow<CompanyDetailState> = _state.asStateFlow()

    init {
        loadCompanyDetails()
        loadRelatedItems()
    }

    fun onMessageShown() = _state.update { it.copy(userMessage = null) }

    fun loadCompanyDetails() {
        if (companyId.isBlank()) {
            _state.update { it.copy(error = "Məlumat tapılmadı") }
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true, error = null) }
            companyRepository.getCompanyDetails(companyId)
                .onSuccess { detail ->
                    _state.update { it.copy(company = detail, isLoading = false) }
                }
                .onFailure { error ->
                    _state.update { it.copy(isLoading = false, error = error.message ?: "Xəta baş verdi") }
                }
        }
    }

    private fun loadRelatedItems() {
        if (companyId.isBlank()) return

        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isRelatedItemsLoading = true) }
            companyRepository.getRelatedItems(companyId)
                .onSuccess { items ->
                    _state.update { it.copy(relatedItems = items, isRelatedItemsLoading = false) }
                }
                .onFailure {
                    _state.update { it.copy(isRelatedItemsLoading = false) }
                }
        }
    }

    fun toggleFavorite(isFav: Boolean) {
        _state.update { it.copy(isFavorite = isFav) }
        viewModelScope.launch(Dispatchers.IO) {
            if (isFav) {
                wishlistRepository.addToWishlist(serviceId = companyId, serviceType = com.example.baltazar.core.core.enums.ServiceType.FOOD)
            } else {
                wishlistRepository.removeFromWishlist(id = companyId)
            }
        }
    }
}
