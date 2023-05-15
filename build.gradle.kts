//plugins {
//    id("info.solidsoft.pitest") apply false
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
//            classpath("info.solidsoft.gradle.pitest:gradle-pitest-plugin:1.9.0")
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
//    plugins.withId("info.solidsoft.pitest") {
//        configure<info.solidsoft.gradle.pitest.PitestPluginExtension> {
//            jvmArgs.set(listOf("-Xmx512m"))
//            testPlugin.set("junit5")
//            avoidCallsTo.set(setOf("kotlin.jvm.internal"))
//            mutators.set(setOf("NEW_DEFAULTS"))
////            targetClasses.set(setOf("strikt.*"))  //by default "${project.group}.*"
////            targetTests.set(setOf("strikt.**.*"))
////            pitestVersion.set("1.5.0")
////            threads.set(System.getenv("PITEST_THREADS")?.toInt()
////                ?: Runtime.getRuntime().availableProcessors())
//            outputFormats.set(setOf("XML", "HTML"))
//        }
//    }


}
