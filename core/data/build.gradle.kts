plugins {
    id("com.bankly.convention.core")
}

android {
    namespace = "com.bankly.core.data"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:network"))
    implementation(project(":core:domain"))
    implementation(libs.retrofit.core)
    implementation(libs.androidx.dataStore.core)
    implementation(libs.moshi.kotlin)
    implementation(libs.converter.moshi)
    implementation(libs.gson)
}
