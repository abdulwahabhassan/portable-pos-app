plugins {
    id("com.bankly.convention.feature")
}

android {
    namespace = "com.bankly.feature.checkcardbalance"
}

dependencies {
    api(files("../../app/libs/KozonPaymentLibraryModule-release.aar"))
}