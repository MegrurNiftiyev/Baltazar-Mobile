package com.example.baltazar.feature.company.core.exception

import com.example.baltazar.core.core.exceptions.BaseApiException

sealed class CompanyException(httpCode: Int, message: String) : BaseApiException(httpCode, message) {
    object CompanyNotFound : CompanyException(404, "Şirkət tapılmadı")
    object FetchFailed : CompanyException(500, "Şirkət məlumatları yüklənərkən xəta baş verdi")

    companion object {
        val allErrors = listOf(
            CompanyNotFound,
            FetchFailed
        )
    }
}
