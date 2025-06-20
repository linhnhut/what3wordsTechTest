rootProject.name = "MovieApp"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":composeApp")

// Core modules
include(":core:common")
include(":core:data")
include(":core:database")
include(":core:design-system")
include(":core:domain")
include(":core:model")
include(":core:network")
include(":core:ui")
include(":core:utils")

// Feature modules
include(":feature:movies")
include(":feature:moviedetail")
