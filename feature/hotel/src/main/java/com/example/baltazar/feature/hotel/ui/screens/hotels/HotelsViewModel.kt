package com.example.baltazar.feature.hotel.ui.screens.hotels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baltazar.core.domain.repository.ISettingsRepository
import com.example.baltazar.feature.hotel.domain.repository.IHotelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HotelsViewModel @Inject constructor(
    private val hotelRepository: IHotelRepository,
    private val settingsRepository: ISettingsRepository
) : ViewModel() {
    private val _state = MutableStateFlow(HotelsState())
    val state: StateFlow<HotelsState> = _state.asStateFlow()

    init {
        observeSettings()
        loadInitialData()
    }

    private fun observeSettings() {
        viewModelScope.launch {
            settingsRepository.cardViewMode.collect { mode ->
                _state.update { it.copy(cardViewMode = mode) }
            }
        }
    }

    fun loadInitialData() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true, error = null) }
            val result = hotelRepository.getHotels(limit = 20, cursor = null)
            result.onSuccess { paginatedList ->
                val hasMore = paginatedList.pagination.hasMore && paginatedList.items.isNotEmpty() && paginatedList.pagination.nextCursor != null
                _state.update {
                    it.copy(
                        isLoading = false,
                        items = paginatedList.items,
                        nextCursor = if (hasMore) paginatedList.pagination.nextCursor else null,
                        hasMore = hasMore
                    )
                }
            }.onFailure { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = error.message ?: "Failed to load hotels"
                    )
                }
            }
        }
    }

    fun loadNextPage() {
        val currentState = _state.value
        if (currentState.isPaginationLoading || currentState.isLoading || !currentState.hasMore || currentState.nextCursor == null) {
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isPaginationLoading = true) }
            val result = hotelRepository.getHotels(limit = 20, cursor = currentState.nextCursor)
            result.onSuccess { paginatedList ->
                val newItems = paginatedList.items
                val existingIds = _state.value.items.map { it.id }.toSet()
                val uniqueNewItems = newItems.filterNot { it.id in existingIds }

                val isDuplicateCursor = paginatedList.pagination.nextCursor == currentState.nextCursor
                val shouldStop = newItems.isEmpty() || uniqueNewItems.isEmpty() || isDuplicateCursor || !paginatedList.pagination.hasMore

                _state.update {
                    it.copy(
                        isPaginationLoading = false,
                        items = it.items + uniqueNewItems,
                        nextCursor = if (shouldStop) null else paginatedList.pagination.nextCursor,
                        hasMore = !shouldStop && paginatedList.pagination.hasMore
                    )
                }
            }.onFailure {
                _state.update { it.copy(isPaginationLoading = false) }
            }
        }
    }
}
