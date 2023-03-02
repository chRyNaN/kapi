import com.chrynan.kapi.buildSrc.LibraryConstants
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka")
    id("maven-publish")
    kotlin("plugin.serialization")
}

group = LibraryConstants.group
version = LibraryConstants.versionName

dependencies {
    api(project(":kapi-core"))
    api(project(":kapi-server-core"))

    api(KotlinX.serialization.json)
    api(KotlinX.coroutines.core)
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
