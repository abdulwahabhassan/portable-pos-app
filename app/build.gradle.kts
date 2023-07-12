import com.bankly.buildlogic.BuildType
import java.util.Properties
import java.io.FileInputStream
import java.util.Locale
import java.time.LocalDateTime as localDateTime
import java.time.format.DateTimeFormatter as dateTimeFormatter
import java.util.Date as date
import java.util.Locale as locale
import java.io.ByteArrayOutputStream as byteArrayOutputStream

plugins {
    id("com.bankly.android.application")
    id("com.bankly.android.application.compose")
    id("com.bankly.android.hilt")
    id("org.jetbrains.kotlin.android")
}

val keystorePropertiesFile: File? = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
keystoreProperties.load(keystorePropertiesFile?.let { FileInputStream(it) })

android {
    namespace = "com.bankly.banklykozenpos"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.bankly.banklykozenpos"
        versionCode = 1
        versionName = "0.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("config") {
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
            signingConfig = signingConfigs.getByName("config")
        }
//        applicationVariants.all{ variant ->
//            variant.outputs.forEach{ output ->
//                //project.ext { appName = "Bankly-Kozen" }
////                .format("dd-MMM-yy")
//                val formattedDate = date().toString()
//                var newName = output.outputFile.name
//                newName = newName.replace("app-", "Bankly-Kozen-" + "${variant.versionName}-${getBranchName()}-")
//                newName = newName.replace("-debug", "-debug-$formattedDate")
//                newName = newName.replace("-release", "-release-$formattedDate")
//                (output as com.android.build.gradle.internal.api.BaseVariantOutputImpl).outputFileName  = newName
//            }
//            false
//        }

    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

//    buildFeatures {
//        compose = true
//    }
//    composeOptions {
//        kotlinCompilerExtensionVersion = "1.4.7"
//    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

kapt {
    correctErrorTypes = true
}

//fun getBranchName(): String {
//    return try {
//        println("Task Getting Branch Name..")
//        val stdout = byteArrayOutputStream()
//        exec {
//            commandLine("git", "rev-parse", "--abbrev-ref", "HEAD")
//            standardOutput = stdout
//        }
//        println("Git Current Branch = $stdout")
//        stdout.toString().replace("/", "-").replace("\n", "").lowercase(Locale.getDefault())
//    }
//    catch (e: Exception) {
//        println("Exception = " + e.message)
//        ""
//    }
//}

dependencies {
    implementation(project(":core:designsystem"))
    implementation(project(":core:data"))
    implementation(project(":core:common"))
    implementation(project(":core:domain"))
    implementation(project(":core:model"))

    implementation(project(":feature:authentication"))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.kotlinx.datetime)
}
