plugins {
    id("com.bankly.convention.core")
}

android {
    namespace = "com.bankly.core.domain"
}

dependencies {
    api(project(":core:model"))
}
