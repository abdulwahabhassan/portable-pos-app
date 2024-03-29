pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "BanklyKozenPos"

/**
 * App module
 */
include(":app")

/**
 * Core modules
 */
include(":core:common")
include(":core:data")
include(":core:designsystem")
include(":core:domain")
include(":core:model")
include(":core:network")
include(":core:database")

/**
 * Feature modules
 */
include(":feature:authentication")
include(":feature:dashboard")
include(":feature:paywithcard")
include(":feature:cardtransfer")
include(":feature:sendmoney")
include(":feature:paywithtransfer")
include(":feature:paybills")
include(":feature:transactiondetails")
include(":feature:eod")
include(":feature:contactus")
include(":feature:logcomplaint")
include(":feature:networkchecker")
include(":feature:settings")
include(":feature:checkcardbalance")
include(":feature:faq")
include(":feature:notification")
