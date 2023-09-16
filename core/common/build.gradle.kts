plugins {
    id("com.bankly.convention.core")
    id("com.bankly.convention.compose")
}

android {
    namespace = "com.bankly.core.common"
}

dependencies {
    implementation(libs.androidx.lifecycle.viewModel.ktx)
    implementation(project(":core:designsystem"))
    implementation(project(":core:model"))
    implementation(project(":core:domain"))
    implementation(project(":core:data"))
}
