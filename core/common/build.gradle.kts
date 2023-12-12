import com.bankly.buildlogic.configureDesign

plugins {
    id("com.bankly.convention.core")
}

android {
    namespace = "com.bankly.core.common"
    configureDesign(this)
}

dependencies {
    implementation(libs.androidx.lifecycle.viewModel.ktx)
    api(project(":core:designsystem"))
    api(project(":core:data"))
}
