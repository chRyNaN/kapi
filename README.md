![kapi graphic](assets/kapi.png)

# kapi

Kotlin multi-platform API utilities. Write APIs in Kotlin; generate Ktor bindings and Open API Specs.
<br/>
<br/>
<img alt="GitHub tag (latest by date)" src="https://img.shields.io/github/v/tag/chRyNaN/kapi">
![Build](https://github.com/chRyNaN/kapi/actions/workflows/build.yml/badge.svg)

```kotlin
@Api
interface IdentityApi {

    @GET("/user/{id}")
    suspend fun getUser(@Path id: String): User
}

fun Application.module() {
    routing {
        // Function is auto-generated. Provide an `IdentityApi` instance.
        registerIdentityApi(api = IdentityApiImpl())
    }
}
```

## Status ⚠️

This project is in an early stage and under active development. Breaking changes may occur between released versions.

## Getting Started 🏁

First, set up the [KSP](https://kotlinlang.org/docs/ksp-quickstart.html) plugin. The following can be added to the
module's `build.gradle.kts` file to add the KSP plugin:

```kotlin
plugins {
    id("com.google.devtools.ksp") version "1.8.10-1.0.9"
}
```

Then, add the repositories for this project:

```kotlin
repositories {
    maven { url = uri("https://repo.repsy.io/mvn/chrynan/public") }
}
```

Finally, add the dependencies of this project:

```kotlin
dependencies {
    // The annotation processor - see ksp documentation for multiplatform builds
    ksp("com.chrynan.kapi:kapi-server-ksp:$kapiVersion")

    // The runtime library
    implementation("com.chrynan.kapi:kapi-server-core:$kapiVersion")
}
```

**Note:** It may be required
to [add the generated sources](https://kotlinlang.org/docs/ksp-quickstart.html#make-ide-aware-of-generated-code) of the
KSP processor for the IDE to recognize the generated code and resources. The following is an example of adding the
generated sources for a Kotlin JVM project:

```kotlin
kotlin {
    sourceSets.main {
        kotlin.srcDir("build/generated/ksp/main/kotlin")
        resources.srcDir("build/generated/ksp/main/resources/")
    }
}
```

## Defining an API 🧑‍💻

An API component can be a Kotlin interface, class, or object. Just annotate the API component with the `@Api` annotation
for the processor to register the component. The `@Api` annotation contains properties that can be provided that are
useful to the processor when it generates the Ktor bindings and Open API Spec files.

```kotlin
@Api(
    name = "IdentityApi",
    basePath = "/identity",
    info = Info(
        title = "Identity Service API",
        summary = "Provides API functions for accessing and updating user identities."
    )
)
interface IdentityApiComponent {}
```

## Documentation 📄

More detailed documentation is available in the [docs](https://github.com/chRyNaN/kapi/blob/main/docs) folder. The
entry point to the documentation can be found [here](https://github.com/chRyNaN/kapi/blob/main/docs/index.md).

## Security 🛡️

For security vulnerabilities, concerns, or issues, please responsibly disclose the information either by opening a
public GitHub Issue or reaching out to the project owner.

## Inspiration 💡

This project was inspired by the works of the following open source projects and libraries:

* [retrofit](https://github.com/square/retrofit)
* [ktor](https://github.com/ktorio/ktor)
* [Ktorfit](https://github.com/Foso/Ktorfit)
* [ktor-retrofit](https://github.com/bnorm/ktor-retrofit)
* [OpenAPI](https://www.openapis.org/)
* [JAX-RS](https://en.wikipedia.org/wiki/Jakarta_RESTful_Web_Services)

## Sponsorship ❤️

Support this project by [becoming a sponsor](https://www.buymeacoffee.com/chrynan) of my work!

## License ⚖️

```
Copyright 2023 chRyNaN

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
