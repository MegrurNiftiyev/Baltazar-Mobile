package com.example.baltazar.feature.hotel.data.model.response

import com.example.baltazar.core.data.model.response.ApiResponse
import com.example.baltazar.core.data.model.response.PaginatedResponse
import com.example.baltazar.feature.hotel.data.model.dto.HotelDetailDto
import com.example.baltazar.feature.hotel.data.model.dto.HotelDto
import com.example.baltazar.feature.hotel.data.model.dto.HotelRoomDto

typealias HotelResponse = PaginatedResponse<HotelDto>
typealias HotelDetailResponse = ApiResponse<HotelDetailDto>
typealias HotelRoomsResponse = PaginatedResponse<HotelRoomDto>
