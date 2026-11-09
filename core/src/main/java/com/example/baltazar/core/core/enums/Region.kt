package com.example.baltazar.core.core.enums

import androidx.annotation.StringRes
import com.example.baltazar.core.R

enum class Region(val code: String, @StringRes val labelRes: Int) {
    AZ("AZ", R.string.region_azerbaijan),
    TR("TR", R.string.region_turkey),
    US("US", R.string.region_usa),
    RU("RU", R.string.region_russia);

    companion object {
        fun fromCode(code: String?): Region {
            return entries.firstOrNull { it.code.equals(code, ignoreCase = true) } ?: AZ
        }
    }
}
