import com.bankly.buildlogic.configureDesign

plugins {
    id("com.bankly.convention.core")
}

android {
    namespace = "com.bankly.core.common"
    configureDesign(this)
}

dependencies {
    api(project(":core:designsystem"))
    api(project(":core:data"))
}
