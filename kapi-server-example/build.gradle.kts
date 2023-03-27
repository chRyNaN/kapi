plugins {
    kotlin("jvm")
    id("com.google.devtools.ksp") version "1.8.0-1.0.8"
}

dependencies {
    implementation(project(":kapi-server-core"))
    implementation(project(":kapi-client-core"))
    implementation(project(":kapi-openapi"))

    implementation(Ktor.client.okHttp)
    implementation(Ktor.server.auth)

    "ksp"(project(":kapi-server-ksp"))
}

kotlin {
    sourceSets.main {
        kotlin.srcDir("build/generated/ksp/main/kotlin")
        resources.srcDir("build/generated/ksp/main/resources/")
    }
}
