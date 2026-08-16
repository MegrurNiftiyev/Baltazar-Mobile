package com.example.baltazar.feature.travel.ui.screens.tour_roadmap

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baltazar.feature.travel.domain.model.TourRoadmapPoint
import com.example.baltazar.feature.travel.domain.repository.ITravelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TourRoadmapViewModel @Inject constructor(
    private val travelRepository: ITravelRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val tourId: String = savedStateHandle["tourId"] ?: savedStateHandle["id"] ?: ""
    private val _state = MutableStateFlow(TourRoadmapState())
    val state: StateFlow<TourRoadmapState> = _state.asStateFlow()

    init {
        loadTourRoadmap()
    }

    fun onMessageShown() = _state.update { it.copy(userMessage = null) }

    fun selectPoint(point: TourRoadmapPoint) = _state.update { it.copy(selectedPoint = point) }

    fun loadTourRoadmap() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true, error = null) }
            travelRepository.getTourDetail(tourId)
                .onSuccess { detail ->
                    _state.update {
                        it.copy(
                            tour = detail,
                            roadmap = detail.roadmap,
                            selectedPoint = detail.roadmap.firstOrNull(),
                            isLoading = false
                        )
                    }
                }
                .onFailure { error ->
                    _state.update { it.copy(isLoading = false, error = error.message) }
                }
        }
    }
}
