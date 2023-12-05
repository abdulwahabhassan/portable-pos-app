import com.bankly.buildlogic.configureDesign

plugins {
    id("com.bankly.convention.core")

}

android {
    namespace = "com.bankly.core.designsystem"
    configureDesign(this)
}
