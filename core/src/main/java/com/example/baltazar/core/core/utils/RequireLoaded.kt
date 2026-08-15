package com.example.baltazar.core.core.utils

fun requireLoadedFields(componentName: String, vararg fields: Pair<String, Any?>) {
    val nullFields = fields.filter { it.second == null }.map { it.first }
    if (nullFields.isNotEmpty()) {
        error("$componentName: required field(s) [${nullFields.joinToString(", ")}] must not be null when isLoading = false")
    }
}
