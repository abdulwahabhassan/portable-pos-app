plugins {
    id("com.bankly.convention.feature")
}

android {
    namespace = "com.bankly.feature.checkcardbalance"
}

dependencies {
    implementation(files("../../app/libs/KozonPaymentLibraryModule-release.aar"))
}