package com.bankly.buildlogic

import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.groupedCompose(libs: VersionCatalog) {
    val bom = libs.findLibrary("androidx-compose-bom").get()
    add("implementation", platform(bom))
    add("androidTestImplementation", platform(bom))
    add("implementation", libs.findLibrary("androidx.activity.compose").get())
    add("api", libs.findLibrary("androidx.compose.foundation").get())
    add("api", libs.findLibrary("androidx.compose.foundation.layout").get())
    add("api", libs.findLibrary("androidx.compose.material.iconsExtended").get())
    add("api", libs.findLibrary("androidx.compose.material3").get())
    add("api", libs.findLibrary("androidx.compose.runtime").get())
    add("api", libs.findLibrary("androidx.compose.ui.tooling.preview").get())
    add("api", libs.findLibrary("androidx.compose.ui.util").get())
    add("debugImplementation", libs.findLibrary("androidx.compose.ui.tooling").get())
    add("implementation", libs.findLibrary("coil.kt.compose").get())
    add("implementation", libs.findLibrary("androidx.navigation.compose").get())
    add("implementation", libs.findLibrary("androidx.hilt.navigation.compose").get())
    add("implementation", libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
    add("implementation", libs.findLibrary("androidx.lifecycle.viewModelCompose").get())
    add("implementation", libs.findLibrary("coil.kt.compose").get())
    add("implementation", libs.findLibrary("coil.kt.gif").get())
}
