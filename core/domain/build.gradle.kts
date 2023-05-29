plugins {
    id("com.bankly.android.library")
    id("com.bankly.android.hilt")
    kotlin("kapt")
}

android {
    namespace = "com.bankly.core.domain"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:common"))
    implementation(libs.hilt.android)
    implementation(libs.kotlinx.coroutines.android)
    kapt(libs.hilt.compiler)
}
