import com.android.build.gradle.LibraryExtension
import com.bankly.buildlogic.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class BanklyCoreConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply{
                apply("com.android.library")
            }
            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
            }
        }
    }
}
