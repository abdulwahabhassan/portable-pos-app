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
dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:common"))
    implementation(project(":core:network"))
}
