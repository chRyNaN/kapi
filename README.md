![kapi graphic](assets/kapi.png)

# kapi

Kotlin multi-platform API utilities. Write APIs in Kotlin; generate Ktor bindings and Open API Specs.
<br/>
<img alt="GitHub tag (latest by date)" src="https://img.shields.io/github/v/tag/chRyNaN/kapi">
![Build](https://github.com/chRyNaN/kapi/actions/workflows/build.yml/badge.svg)

```kotlin
@Api
interface IdentityApi {

    @GET("/user/{id}")
    suspend fun getUser(@Path id: String): User
}
```

## Status

This project is in an early stage and under active development. Breaking changes may occur between released versions.

## Getting Started

TBD

## Defining an API

## Documentation

More detailed documentation is available in the [docs](https://github.com/chRyNaN/kapi/blob/main/docs) folder. The
entry point to the documentation can be found [here](https://github.com/chRyNaN/kapi/blob/main/docs/index.md).

## Security

For security vulnerabilities, concerns, or issues, please responsibly disclose the information either by opening a
public GitHub Issue or reaching out to the project owner.

## Inspiration

This project was inspired by the works of the following open source projects and libraries:

* [retrofit](https://github.com/square/retrofit)
* [ktor](https://github.com/ktorio/ktor)
* [Ktorfit](https://github.com/Foso/Ktorfit)
* [ktor-retrofit](https://github.com/bnorm/ktor-retrofit)
* [OpenAPI](https://www.openapis.org/)
* [JAX-RS](https://en.wikipedia.org/wiki/Jakarta_RESTful_Web_Services)

## Sponsorship ❤️

Support this project by [becoming a sponsor](https://www.buymeacoffee.com/chrynan) of my work!

## License

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
