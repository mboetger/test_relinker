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
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        val flutterStorageUrl = System.getenv("FLUTTER_STORAGE_BASE_URL") ?: "https://storage.googleapis.com"
        val libPath = "/Users/boetger/src/test_relinker/aar_module/build/host/outputs/repo"
        maven(libPath)
        maven(url = "$flutterStorageUrl/download.flutter.io")
    }
}

rootProject.name = "test_relinker"
include(":app")
