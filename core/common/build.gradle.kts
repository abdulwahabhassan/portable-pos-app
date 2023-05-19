plugins {
    id("com.bankly.android.library")
    id("com.bankly.android.hilt")
}

android {
    namespace = "com.bankly.core.common"
}

dependencies {
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.kotlinx.coroutines.android)
}