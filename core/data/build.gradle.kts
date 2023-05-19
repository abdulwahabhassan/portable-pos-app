plugins {
    id("com.bankly.android.library")
    id("com.bankly.android.hilt")
    id("kotlinx-serialization")
}

android {
    namespace = "com.bankly.core.data"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":core:network"))
    implementation(libs.retrofit.core)
    implementation(libs.kotlinx.serialization.json)
}
