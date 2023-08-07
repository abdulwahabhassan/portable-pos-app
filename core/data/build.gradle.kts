plugins {
    id("com.bankly.convention.core")
    id("kotlinx-serialization")
}

android {
    namespace = "com.bankly.core.data"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":core:network"))
    implementation(project(":core:domain"))
    implementation(libs.retrofit.core)
    implementation(libs.androidx.dataStore.core)
    implementation(libs.kotlinx.serialization.json)
}
