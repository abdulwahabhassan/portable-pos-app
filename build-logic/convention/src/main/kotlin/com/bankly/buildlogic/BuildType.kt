package com.bankly.buildlogic

/**
 * This is to provide configurations type safety.
 */
enum class BuildType(val applicationIdSuffix: String? = null) {
    DEBUG(".debug"),
    RELEASE
}
