package com.example.baltazar.feature.company.ui.screens.company_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baltazar.core.core.enums.ServiceType
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
class CompanyListViewModel @Inject constructor(
    private val companyRepository: ICompanyRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val serviceTypeString: String? = savedStateHandle["serviceType"]
    private val initialServiceType: ServiceType? = try {
        serviceTypeString?.takeIf { it.isNotBlank() }?.let { ServiceType.valueOf(it.uppercase()) }
    } catch (_: Exception) {
        null
    }

    private val _state = MutableStateFlow(CompanyListState(serviceType = initialServiceType))
    val state: StateFlow<CompanyListState> = _state.asStateFlow()

    init {
        loadCompanies()
    }

    fun loadCompanies() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true, error = null) }
            val currentType = _state.value.serviceType
            companyRepository.getCompanies(serviceType = currentType)
                .onSuccess { paginatedList ->
                    _state.update {
                        it.copy(
                            companies = paginatedList.items,
                            nextCursor = paginatedList.pagination.nextCursor,
                            hasMore = paginatedList.pagination.hasMore,
                            isLoading = false
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(isLoading = false, error = error.message ?: "Xəta baş verdi")
                    }
                }
        }
    }

    fun refresh() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isRefreshing = true, error = null) }
            val currentType = _state.value.serviceType
            companyRepository.getCompanies(serviceType = currentType)
                .onSuccess { paginatedList ->
                    _state.update {
                        it.copy(
                            companies = paginatedList.items,
                            nextCursor = paginatedList.pagination.nextCursor,
                            hasMore = paginatedList.pagination.hasMore,
                            isRefreshing = false
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(isRefreshing = false, error = error.message ?: "Xəta baş verdi")
                    }
                }
        }
    }

    fun loadMore() {
        val currentState = _state.value
        if (currentState.isLoadingMore || !currentState.hasMore || currentState.nextCursor == null) return

        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoadingMore = true) }
            companyRepository.getCompanies(
                serviceType = currentState.serviceType,
                cursor = currentState.nextCursor
            ).onSuccess { paginatedList ->
                _state.update {
                    it.copy(
                        companies = it.companies + paginatedList.items,
                        nextCursor = paginatedList.pagination.nextCursor,
                        hasMore = paginatedList.pagination.hasMore,
                        isLoadingMore = false
                    )
                }
            }.onFailure {
                _state.update { it.copy(isLoadingMore = false) }
            }
        }
    }
}
