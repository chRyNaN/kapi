import com.chrynan.kapi.buildSrc.LibraryConstants

plugins {
    kotlin("jvm")
    kotlin("kapt")
    id("java-gradle-plugin")
    id("com.gradle.plugin-publish") version "1.1.0"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin-api:1.8.10")
}

gradlePlugin {
    //website.set(LibraryConstants.vcsUrl)
    //vcsUrl.set(LibraryConstants.vcsUrl)

    plugins {
        create("KapiServerPlugin") {
            id = "com.chrynan.kapi.server"
            implementationClass = "com.chrynan.kapi.server.gradle.KapiServerPlugin"
            displayName = "kapi Gradle Plugin"
            description = "Gradle Plugin for Kotlin API development."
            //tags.set(listOf("kapi", "ktor", "openapi", "http", "rest"))
        }
    }
}

tasks.register("sourcesJar", Jar::class) {
    group = "build"
    description = "Assembles Kotlin sources"

    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
    dependsOn(tasks.classes)
}
