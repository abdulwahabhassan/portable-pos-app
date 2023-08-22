plugins {
    id("com.bankly.convention.feature")
}

android {
    namespace = "com.bankly.feature.paywithcard"
}


dependencies {
    api(files("../../app/libs/KozonPaymentLibraryModule-release.aar"))
}
