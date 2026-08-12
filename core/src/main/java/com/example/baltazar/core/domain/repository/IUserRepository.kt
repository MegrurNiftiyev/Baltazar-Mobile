package com.example.baltazar.core.domain.repository

import com.example.baltazar.core.domain.model.DriverLicenseInfo
import com.example.baltazar.core.domain.model.PassportInfo
import com.example.baltazar.core.domain.model.PersonalInfo
import com.example.baltazar.core.domain.model.ProfileImage
import com.example.baltazar.core.domain.model.User
import com.example.baltazar.core.domain.model.UserInfo

interface IUserRepository {
    suspend fun getUser(): Result<User>
    suspend fun updateUser(userInfo: UserInfo): Result<User>
    suspend fun updateProfilePhoto(profileImage: ProfileImage): Result<User>
    suspend fun updatePersonalInfo(personalInfo: PersonalInfo): Result<User>
    suspend fun updatePassportInfo(passportInfo: PassportInfo): Result<User>
    suspend fun updateDriverLicense(driverLicenseInfo: DriverLicenseInfo): Result<User>
}
