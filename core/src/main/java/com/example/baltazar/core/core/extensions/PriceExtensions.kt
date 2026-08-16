package com.example.baltazar.core.core.extensions

fun formatPrice(price: Double, currency: String = "AZN", priceSuffix: String = ""): String {
    val basePrice = if (price % 1.0 == 0.0) price.toInt().toString() else price.toString()
    val cleanCurrency = currency.trim()
    val cleanSuffix = priceSuffix.trim()

    return when {
        cleanSuffix.isBlank() -> {
            if (cleanCurrency.isBlank()) basePrice else "$basePrice $cleanCurrency"
        }
        cleanCurrency.isBlank() -> {
            "$basePrice $cleanSuffix"
        }
        cleanSuffix.contains(cleanCurrency, ignoreCase = true) -> {
            "$basePrice $cleanSuffix"
        }
        cleanSuffix.startsWith("/") -> {
            "$basePrice $cleanCurrency $cleanSuffix"
        }
        else -> {
            "$basePrice $cleanCurrency / $cleanSuffix"
        }
    }
}
