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
        maven {
            url = uri("https://github.com/psiegman/mvn-repo/raw/master/releases")
        }
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
include(":core:network")
include(":feature:booklist")
include(":feature:booklist:api")
include(":feature:booklist:impl")
include(":feature:reader")
include(":feature:reader:api")
include(":feature:reader:impl")
include(":feature:profile")
include(":feature:profile:api")
include(":feature:profile:impl")
include(":core:database")
