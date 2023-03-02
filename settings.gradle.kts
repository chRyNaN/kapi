pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven { url = uri("https://repo.repsy.io/mvn/chrynan/public") }
    }
}

plugins {
    // See https://jmfayard.github.io/refreshVersions
    id("de.fayard.refreshVersions") version "0.51.0"

    // See build.gradle.kts file in root project folder for the rest of the plugins applied.
}

rootProject.name = "kapi"

include(":kapi-core")
include(":kapi-client-core")
include(":kapi-server-core")
include(":kapi-server-ksp")
include(":kapi-server-processor-core")
include(":kapi-server-processor-json")
include(":kapi-server-processor-ktor")
include(":kapi-server-processor-openapi")
include(":kapi-server-example")
