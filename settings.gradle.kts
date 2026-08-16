pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "Baltazar"
include(":app")
include(":feature:travel")
include(":feature:auth")
include(":feature:food")
include(":feature:profile")
include(":feature:hotel")
include(":feature:rentacar")
include(":feature:explore")
include(":feature:order")
include(":core")
