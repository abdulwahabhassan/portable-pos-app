plugins {
    id("com.bankly.convention.feature")
}

android {
    namespace = "com.bankly.feature.notification"
}
dependencies {
    api(libs.firebase.cloud.messaging)
}

