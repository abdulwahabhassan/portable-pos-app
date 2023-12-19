plugins {
    id("com.bankly.convention.core")
}

android {
    namespace = "com.bankly.core.data"
}

dependencies {
    api(project(":core:network"))
    api(project(":core:domain"))
    implementation(libs.retrofit.core)
    implementation(libs.androidx.dataStore.core)
    implementation(libs.moshi.kotlin)
    implementation(libs.converter.moshi)
    implementation(libs.gson)
    implementation(libs.play.services.basement)
}
