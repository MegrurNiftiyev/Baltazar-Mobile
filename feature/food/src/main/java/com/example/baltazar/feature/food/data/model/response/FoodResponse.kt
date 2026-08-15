package com.example.baltazar.feature.food.data.model.response

import com.example.baltazar.core.data.model.response.ApiResponse
import com.example.baltazar.core.data.model.response.PaginatedResponse
import com.example.baltazar.feature.food.data.model.dto.FoodDetailDto
import com.example.baltazar.feature.food.data.model.dto.FoodItemDto

typealias FoodResponse = PaginatedResponse<FoodItemDto>
typealias FoodDetailResponse = ApiResponse<FoodDetailDto>
