plugins {
    kotlin("jvm")
    id("com.google.devtools.ksp") version "1.8.10-1.0.9"
    kotlin("plugin.serialization")
    id("application")
}

dependencies {
    implementation(project(":kapi-server-core"))
    implementation(project(":kapi-client-core"))
    implementation(project(":kapi-openapi"))

    implementation(Ktor.client.okHttp)
    implementation(Ktor.server.auth)
    implementation(Ktor.server.netty)

    implementation(KotlinX.datetime)
    implementation(KotlinX.serialization.json)
    implementation(KotlinX.coroutines.core)

    "ksp"(project(":kapi-server-ksp"))
}

kotlin {
    sourceSets.main {
        kotlin.srcDir("build/generated/ksp/main/kotlin")
        resources.srcDir("build/generated/ksp/main/resources/")
    }
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
