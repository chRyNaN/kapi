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

                api(Ktor.server.core)
                api(Ktor.server.auth)
                api(Ktor.server.auth.jwt)
                implementation(Ktor.client.contentNegotiation)
                implementation("io.ktor:ktor-serialization-kotlinx-json:_")
                implementation("io.ktor:ktor-server-openapi:_")
                implementation("io.ktor:ktor-server-swagger:_")
                implementation("io.ktor:ktor-server-html-builder:_")
                implementation("io.ktor:ktor-server-content-negotiation:_")

                implementation("com.strumenta.antlr-kotlin:antlr-kotlin-runtime:b5135079b8")

                // Multiplatform Locale
                implementation("com.chrynan.locale:locale-core:_")

                // I/0 - OKIO
                implementation("com.squareup.okio:okio:_")

                // Big Numbers
                implementation("com.ionspin.kotlin:bignum:_")
                implementation("com.ionspin.kotlin:bignum-serialization-kotlinx:_")
            }

            kotlin.srcDir("build/generated-src/commonMain/kotlin")
        }
        val jvmMain by getting {
            dependencies {
                implementation("com.graphql-java:graphql-java:_")
            }
        }
    }
}

tasks.withType<Jar> { duplicatesStrategy = DuplicatesStrategy.INHERIT }

// in antlr-kotlin-plugin <0.0.5, the configuration was applied by the plugin.
// starting from verison 0.0.5, you have to apply it manually:
tasks.register<com.strumenta.antlrkotlin.gradleplugin.AntlrKotlinTask>("generateKotlinCommonGrammarSource") {
    // the classpath used to run antlr code generation
    antlrClasspath = configurations.detachedConfiguration(
        // antlr itself
        // antlr is transitive added by antlr-kotlin-target,
        // add another dependency if you want to choose another antlr4 version (not recommended)
        // project.dependencies.create("org.antlr:antlr4:$antlrVersion"),

        // antlr target, required to create kotlin code
        project.dependencies.create("com.strumenta.antlr-kotlin:antlr-kotlin-target:b5135079b8")
    )
    maxHeapSize = "64m"
    packageName = "com.chrynan.kapi.server.graphql.core.antlr"
    arguments = listOf("-no-visitor", "-no-listener")
    source = project.objects
        .sourceDirectorySet("antlr", "antlr")
        .srcDir("src/commonMain/antlr").apply {
            include("*.g4")
        }
    // outputDirectory is required, put it into the build directory
    // if you do not want to add the generated sources to version control
    outputDirectory = File("build/generated-src/commonMain/kotlin")
    // use this settings if you want to add the generated sources to version control
    // outputDirectory = File("src/commonAntlr/kotlin")
}

// run generate task before build
// not required if you add the generated sources to version control
// you can call the task manually in this case to update the generated sources
tasks.getByName("compileKotlinJvm").dependsOn("generateKotlinCommonGrammarSource")

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
