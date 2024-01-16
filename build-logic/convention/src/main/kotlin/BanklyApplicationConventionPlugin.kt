import com.android.build.api.dsl.ApplicationExtension
import com.bankly.buildlogic.configureDesign
import com.bankly.buildlogic.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

class BanklyApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("com.android.application")
                apply("com.google.gms.google-services")
                apply("com.google.firebase.crashlytics")
            }
            extensions.configure<ApplicationExtension> {
                defaultConfig.targetSdk = 33
                configureKotlinAndroid(this)
                configureDesign(this)
            }
            dependencies {
                add("implementation", project(":core:common"))
                add("implementation", project(":feature:authentication"))
                add("implementation", project(":feature:dashboard"))
                add("implementation", project(":feature:paywithcard"))
                add("implementation", project(":feature:cardtransfer"))
                add("implementation", project(":feature:sendmoney"))
                add("implementation", project(":feature:paywithtransfer"))
                add("implementation", project(":feature:paybills"))
                add("implementation", project(":feature:transactiondetails"))
                add("implementation", project(":feature:eod"))
                add("implementation", project(":feature:contactus"))
                add("implementation", project(":feature:logcomplaint"))
                add("implementation", project(":feature:networkchecker"))
                add("implementation", project(":feature:settings"))
                add("implementation", project(":feature:checkcardbalance"))
                add("implementation", project(":feature:faq"))
                add("implementation", project(":feature:notification"))

            }
        }
    }
}
