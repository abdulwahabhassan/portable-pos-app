import com.android.build.gradle.LibraryExtension
import com.bankly.buildlogic.configureKotlinAndroid
import com.bankly.buildlogic.configureToolChain
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.kotlin

class BanklyCoreConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.kotlin.kapt")
                apply("dagger.hilt.android.plugin")
                apply("kotlinx-serialization")
            }
            configureToolChain()
            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
            }
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                add("implementation", libs.findLibrary("kotlinx.coroutines.android").get())
                add("implementation", libs.findLibrary("kotlinx.datetime").get())
                add("implementation", libs.findLibrary("kotlinx.serialization.json").get())
                add("implementation", libs.findLibrary("hilt.android").get())
                add("kapt", libs.findLibrary("hilt.compiler").get())
                add("kaptAndroidTest", libs.findLibrary("hilt.compiler").get())
                add("testImplementation", kotlin("test"))
                add("androidTestImplementation", kotlin("test"))
            }
        }
    }
}