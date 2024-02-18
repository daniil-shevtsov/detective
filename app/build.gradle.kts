import info.solidsoft.gradle.pitest.PitestPlugin

//import info.solidsoft.gradle.pitest.PitestPluginExtension

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("org.jetbrains.kotlin.kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("info.solidsoft.pitest")
}
plugins.apply(PitestPlugin::class.java)

android {
    compileSdk = Versions.androidCompileSdk

    defaultConfig {
        applicationId = "com.daniil.shevtsov.detective"
        minSdk = Versions.androidMinSdk
        targetSdk = Versions.androidTargetSdk

        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
        }
    }

    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs = listOf(
            "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-Xopt-in=kotlinx.coroutines.time",
            "-Xopt-in=kotlin.Experimental",
            "-Xopt-in=kotlin.time.ExperimentalTime",
            "-Xopt-in=androidx.compose.foundation.ExperimentalFoundationApi",
        )
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
        viewBinding = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeCompiler
    }
    namespace = "com.daniil.shevtsov.detective"

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    afterEvaluate {
        plugins.withId("info.solidsoft.pitest") {
            configure<info.solidsoft.gradle.pitest.PitestPluginExtension> {
//            jvmArgs.set(listOf("-Xmx512m"))
                testPlugin.set("junit5")
//            avoidCallsTo.set(setOf("kotlin.jvm.internal"))
//            mutators.set(setOf("NEW_DEFAULTS"))
////            targetClasses.set(setOf("strikt.*"))  //by default "${project.group}.*"
////            targetTests.set(setOf("strikt.**.*"))
////            pitestVersion.set("1.5.0")
////            threads.set(System.getenv("PITEST_THREADS")?.toInt()
////                ?: Runtime.getRuntime().availableProcessors())
//            outputFormats.set(setOf("XML", "HTML"))
            }
        }
    }

}

dependencies {
    with(Deps.Android) {
        implementation(material)
    }

    with(Deps.Accompanist) {
        implementation(insets)
        implementation(pager)
    }

    with(Deps.Dagger) {
        implementation(dagger)
        kapt(daggerCompiler)
    }

    with(Deps.Logging) {
        implementation(timber)
    }

    with(Deps.NavigationComponent) {
        implementation(uiKtx)
        implementation(fragmentKtx)
    }

    with(Deps.Compose) {
        implementation(uiGraphics)
        implementation(uiTooling)
        implementation(foundationLayout)
        implementation(materialExtended)
        implementation(material)
    }

    with(Deps.Koin) {
        implementation(core)
        implementation(android)
        implementation(compose)
    }

    with(Deps.ViewBinding) {
        implementation(delegate)
    }

    with(Deps.UnitTest) {
        testImplementation(assertk)
        testImplementation(coroutinesTest)
        testImplementation(jupiter)
        testImplementation(mockk)
        testImplementation(mockkAgent)
        testImplementation(turbine)
//        testImplementation(pitest)
    }
}




//plugins.withId("info.solidsoft.pitest") {
//    configure<info.solidsoft.gradle.pitest.PitestPluginExtension> {
//        junit5PluginVersion.set("0.12")
//////                verbose.set(true)
//////        jvmArgs.set(listOf("-Xmx512m")) // necessary on CI
//////        avoidCallsTo.set(setOf("kotlin.jvm.internal", "kotlin.Result"))
//////        excludedTestClasses.set(setOf("failgood.MultiThreadingPerformanceTest*"))
//////        targetClasses.set(setOf("failgood.*")) // by default "${project.group}.*"
//////        targetTests.set(setOf("failgood.*Test", "failgood.**.*Test"))
//////        pitestVersion.set(failgood.versions.pitestVersion)
//////        threads.set(
//////            System.getenv("PITEST_THREADS")?.toInt() ?: Runtime.getRuntime().availableProcessors()
//////        )
//////        outputFormats.set(setOf("XML", "HTML"))
//    }
//}
