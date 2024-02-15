pluginManagement {
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
        jcenter() //
        maven(url = "https://jitpack.io")// Warning: this repository is going to shut down soon
    }
}

rootProject.name = "My News Application"
include(":app")
