import com.chrynan.kapi.buildSrc.LibraryConstants
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.dokka")
    id("maven-publish")
    kotlin("plugin.serialization")
}

group = LibraryConstants.group
version = LibraryConstants.versionName

kotlin {
    jvm()

    sourceSets {
        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
        val commonMain by getting {
            dependencies {
                api(project(":kapi-core"))

                implementation(Ktor.server.core)
                implementation(Ktor.client.contentNegotiation)
                implementation("io.ktor:ktor-serialization-kotlinx-json:_")
            }
        }
    }
}

tasks.withType<Jar> { duplicatesStrategy = DuplicatesStrategy.INHERIT }

afterEvaluate {
    publishing {
        repositories {
            maven {
                url = uri("https://repo.repsy.io/mvn/chrynan/public")

                credentials {
                    username = (project.findProperty("repsyUsername") ?: System.getenv("repsyUsername")) as? String
                    password = (project.findProperty("repsyToken") ?: System.getenv("repsyToken")) as? String
                }
            }
        }
    }
}
