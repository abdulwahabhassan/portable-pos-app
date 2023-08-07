plugins {
    id("com.bankly.convention.core")
    id("com.bankly.convention.compose")
}

android {
    namespace = "com.bankly.core.designsystem"
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }
}
