plugins {
    id("com.bankly.convention.core")
}

android {
    namespace = "com.bankly.core.domain"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:common"))
}
