import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

gradlePlugin {
    plugins {
        register("banklyApplicationConvention") {
            id = "com.bankly.convention.application"
            implementationClass = "BanklyApplicationConventionPlugin"
        }
        register("banklyCoreConvention") {
            id = "com.bankly.convention.core"
            implementationClass = "BanklyCoreConventionPlugin"
        }
        register("banklyFeatureConvention") {
            id = "com.bankly.convention.feature"
            implementationClass = "BanklyFeatureConventionPlugin"
        }
        register("banklyComposeConvention") {
            id = "com.bankly.convention.compose"
            implementationClass = "BanklyComposeConventionPlugin"
        }
    }
}