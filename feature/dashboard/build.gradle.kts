plugins {
    id("com.bankly.android.feature")
    id("com.bankly.android.library.compose")
}

android {
    namespace = "com.bankly.feature.dashboard"
    buildFeatures {
        buildConfig = true
    }
}
dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:model"))
    implementation(project(":core:common"))
}
