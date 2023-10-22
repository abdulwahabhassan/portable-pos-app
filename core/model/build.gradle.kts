plugins {
    id("kotlin")
    id("kotlinx-serialization")
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)
}
