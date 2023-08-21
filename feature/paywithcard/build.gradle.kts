plugins {
    id("com.bankly.convention.feature")
}

android {
    namespace = "com.bankly.feature.paywithcard"
}


dependencies {
    implementation(files("../../app/libs/KozonPaymentLibraryModule-release.aar"))
}
