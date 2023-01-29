package com.chrynan.kapi.buildSrc

object LibraryConstants {

    const val group = "com.chrynan.kapi"
    const val owner = "chrynan"
    const val repoName = "kapi"
    const val versionName = "0.1.0"
    const val versionCode = 1
    const val versionDescription = "Release $versionName ($versionCode)"
    const val license = "Apache-2.0"
    const val vcsUrl = "https://github.com/chRyNaN/kapi.git"

    object Android {

        const val compileSdkVersion = 33
        const val buildToolsVersion = "30.0.3"
        const val minSdkVersion = 23
        const val targetSdkVersion = 33
    }
}
