import com.android.build.gradle.LibraryExtension
import com.bankly.buildlogic.configureDesign
import com.bankly.buildlogic.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

class BanklyFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("com.android.library")
            }
            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                configureDesign(this)
            }
            dependencies {
                add("implementation", project(":core:common"))
                add("api", files("../../app/libs/KozonPaymentLibraryModule-release.aar"))
            }
        }
    }
}
