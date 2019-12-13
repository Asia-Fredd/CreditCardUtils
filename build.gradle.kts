@file:Suppress("LocalVariableName")

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
        maven { url = uri("https://dl.bintray.com/kotlin/kotlin-eap") }
    }
    dependencies {
        classpath(Configs.KotlinGradlePlugin)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

plugins {
    id(BuildScan.ID).version(BuildScan.version)
}
buildScan {
    termsOfServiceUrl = BuildScan.termsOfServiceUrl
    termsOfServiceAgree = BuildScan.termsOfServiceAgree
}

allprojects {
    repositories {
        google()
        jcenter()
        maven { url = uri("https://dl.bintray.com/kotlin/kotlin-eap") }
    }
}

val clean by tasks.registering(Delete::class) {
    rootProject.run {
        arrayOf(
            buildDir,
            file("buildSrc/build")
        )
    }.run(::delete)
}