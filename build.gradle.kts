//plugins {
//    id("info.solidsoft.pitest")
//}

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven(uri("https://plugins.gradle.org/m2/")) // For kotlinter-gradle
    }

//    configurations.maybeCreate("pitest")

    dependencies {
        // keeping this here to allow AS to automatically update
        classpath("com.android.tools.build:gradle:8.0.1")

        with(Deps.Gradle) {
            classpath(kotlin)
            classpath(kotlinSerialization)
            classpath(shadow)
            classpath(kotlinter)
            classpath(safeArgs)
            classpath("info.solidsoft.gradle.pitest:gradle-pitest-plugin:1.9.0")
        }
    }

}

allprojects {
    apply(plugin = "org.jmailen.kotlinter")

    repositories {
        google()
        mavenCentral()
        maven(url = "https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-js-wrappers")
        maven(url = "https://jitpack.io")
        maven(url = "https://dl.bintray.com/korlibs/korlibs")
    }
//

}
