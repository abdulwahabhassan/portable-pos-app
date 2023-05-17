plugins {
    id("com.bankly.android.feature")
    id("com.bankly.android.library.compose")
}

android {
    namespace = "com.bankly.feature.authentication"
    buildFeatures {
        buildConfig = true
    }
}
