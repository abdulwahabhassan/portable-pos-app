import com.bankly.buildlogic.BuildType
import java.util.Properties
import java.io.FileInputStream

plugins {
    id("com.bankly.convention.application")
}

val keystorePropertiesFile: File? = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
keystoreProperties.load(keystorePropertiesFile?.let { FileInputStream(it) })

android {
    namespace = "com.bankly.banklykozenpos"
    defaultConfig {
        applicationId = "com.bankly.banklykozenpos"
        versionCode = 1
        versionName = "1.0.0"
    }
    signingConfigs {
        create("app-signing-config") {
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
        }
    }
    buildTypes {
        debug {
            applicationIdSuffix = BuildType.DEBUG.applicationIdSuffix
        }
        release {
            isMinifyEnabled = false
            isDebuggable = true
            applicationIdSuffix = BuildType.RELEASE.applicationIdSuffix
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("app-signing-config")
        }
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation (libs.google.android.material)

    implementation(project(":core:designsystem"))
    implementation(project(":core:data"))
    implementation(project(":core:common"))
    implementation(project(":core:domain"))
    implementation(project(":core:model"))

    implementation(project(":feature:authentication"))
    implementation(project(":feature:dashboard"))
    implementation(project(":feature:paywithcard"))
    implementation(project(":feature:cardtransfer"))
    implementation(project(":feature:sendmoney"))
}
