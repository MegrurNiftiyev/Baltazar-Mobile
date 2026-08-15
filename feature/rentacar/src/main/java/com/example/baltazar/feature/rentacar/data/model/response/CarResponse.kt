package com.example.baltazar.feature.rentacar.data.model.response

import com.example.baltazar.core.data.model.response.ApiResponse
import com.example.baltazar.core.data.model.response.PaginatedResponse
import com.example.baltazar.feature.rentacar.data.model.dto.CarDetailDto
import com.example.baltazar.feature.rentacar.data.model.dto.CarDto

typealias CarResponse = PaginatedResponse<CarDto>
typealias CarDetailResponse = ApiResponse<CarDetailDto>
