plugins {
    id("com.bankly.convention.feature")
}

android {
    namespace = "com.bankly.feature.cardtransfer"
}

dependencies {
    implementation(files("../../app/libs/KozonPaymentLibraryModule-release.aar"))
}