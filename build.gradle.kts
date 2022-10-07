plugins {
    id("java")
    id("idea")
    id("application")
    id("org.jetbrains.kotlin.jvm") version "1.6.20"
    id("org.openjfx.javafxplugin") version "0.0.13"
    id("org.beryx.jlink") version "2.25.0"
    id("org.jetbrains.dokka") version "1.6.20"
}

val applicationName = "FxTemplate"
group = "dev.egonr"
version = "1.0.0"

val moduleArgs = arrayOf<String>(
    "--add-reads", "flogger=flogger.system.backend",
    "--add-reads", "flogger.system.backend=flogger",
    "--add-reads", "flogger.system.backend=java.logging",
    "--add-reads", "flogger=java.logging",
    "--add-exports", "flogger/com.google.common.flogger.backend=ALL-UNNAMED",
    "--add-exports", "flogger.system.backend/com.google.common.flogger.backend.system=ALL-UNNAMED",
)
val appJvmArgs = listOf( // used in run task, jlink and jpackage
    "-XX:+UseZGC", // Requires Windows 10 version > 1803
    "-Xmx1G",
    *moduleArgs,
)

repositories {
    mavenCentral()
}

dependencies {
    // kotlin
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    // ui
    implementation("de.jensd:fontawesomefx-commons:9.1.2")
    implementation("de.jensd:fontawesomefx-controls:9.1.2")
    implementation("de.jensd:fontawesomefx-fontawesome:4.7.0-9.1.2")
    implementation("de.jensd:fontawesomefx-materialicons:2.2.0-9.1.2")

    // logging
    implementation("com.google.flogger:flogger:0.7.4")
    implementation("com.google.flogger:flogger-system-backend:0.7.4")

    // testing
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")

    testImplementation("org.testfx:testfx-core:4.0.16-alpha")
    testImplementation("org.testfx:testfx-junit5:4.0.16-alpha")

    testImplementation("org.assertj:assertj-core:3.23.1")
}

java {
    setSourceCompatibility("17")
    setTargetCompatibility("17")
}

javafx {
    version = "20-ea+3"
    modules = listOf("javafx.controls", "javafx.fxml")
}

application {
    mainModule.set("dev.egonr.jfxtemplate")
    mainClass.set("dev.egonr.jfxtemplate.main.Main")
    val run by tasks.getting(JavaExec::class) {
        jvmArgs = appJvmArgs
    }
}

tasks {
    test {
        useJUnitPlatform()
    }

    withType(JavaCompile::class) {
        options.encoding = "UTF-8"
    }

    compileKotlin {
        kotlinOptions {
            jvmTarget = "17"
        }
    }

    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "17"
        }
    }

    jlink {
        imageZip.set(project.file("$buildDir/distributions/app-${javafx.platform.classifier}.zip"))
        options.set(listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages"))
        launcher {
            name = applicationName
            jvmArgs = appJvmArgs
        }
    }

    jlinkZip {
        group = "distribution"
    }

    test {
        useJUnitPlatform()
        val dir = file("build/run-test/")
        dir.mkdirs()
        workingDir = dir
    }

    named<JavaExec>("run") {
        val dir = file("build/run/")
        dir.mkdirs()
        workingDir = dir
    }
}