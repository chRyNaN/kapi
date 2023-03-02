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
    implementation(project(":kapi-server-core"))
    implementation(project(":kapi-server-processor-core"))
    implementation(project(":kapi-server-processor-json"))
    implementation(project(":kapi-server-processor-ktor"))
    implementation(project(":kapi-server-processor-openapi"))

    implementation(Ktor.server.core)

    implementation(KotlinX.serialization.json)
    implementation(KotlinX.coroutines.core)

    implementation("com.google.devtools.ksp:symbol-processing-api:_")
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
