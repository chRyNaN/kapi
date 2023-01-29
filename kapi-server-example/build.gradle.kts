plugins {
    kotlin("jvm")
    id("com.google.devtools.ksp") version "1.8.0-1.0.8"
}

dependencies {

    implementation(project(":kapi-server-core"))
    "ksp"(project(":kapi-server-ksp"))
}
