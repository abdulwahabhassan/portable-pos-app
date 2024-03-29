import com.bankly.buildlogic.BuildType
import java.io.FileInputStream
import java.util.Properties

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
                "proguard-rules.pro",
            )
            signingConfig = signingConfigs.getByName("app-signing-config")
        }
    }
}

kapt {
    correctErrorTypes = true
}

