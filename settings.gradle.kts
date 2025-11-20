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
    }
}

rootProject.name = "BookApp"
include(":app")
include(":feature")
include(":feature:auth")
include(":feature:auth:impl")
include(":feature:auth:api")
include(":core")
include(":core:designsystem")
include(":core:util")
include(":core:ui")
include(":feature:bookupload")
include(":feature:bookupload:api")
include(":feature:bookupload:impl")
