pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    includeBuild("build-logic")
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "OneStep"
include(":app")
include(":core:model")
include(":core:data")
include(":core:database")
include(":core:ui")
include(":core:datastore")
include(":core:domain")
include(":core:designsystem")


include(":feature:home")
include(":feature:exercise")
include(":feature:exercisedetail")
include(":feature:routine")
include(":feature:history")
