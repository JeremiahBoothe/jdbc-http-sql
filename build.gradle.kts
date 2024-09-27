
plugins {
    kotlin("jvm") version "1.9.10"
    kotlin("plugin.serialization") version "1.9.10"
    kotlin("plugin.lombok") version "1.9.10"
    id("io.freefair.lombok") version "8.1.0"
    id("org.owasp.dependencycheck") version "10.0.3"
    //id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0"
    java
    id("jvm-test-suite")
    application
}

group = "org.jeremiahboothe"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
}

dependencies {
    // Tests

    //testImplementation("io.kotest:kotest-runner-junit5:5.7.2")
    implementation("org.owasp:dependency-check-gradle:10.0.3")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation(kotlin("test"))

    implementation("com.h2database:h2") {
        version {
            strictly("2.2.224")
        }
    }

    modules {
        module("commons-collections:commons-collections"){
            replacedBy("org.apache.commons:commons-collections4")
        }
    }

    implementation("org.apache.commons:commons-collections4") {
        version{
            strictly("4.4")
        }
    }


    implementation("org.slf4j:slf4j-simple:2.0.9")
    testImplementation("org.slf4j:slf4j-api:2.0.9")

    implementation("org.bouncycastle:bcprov-jdk18on:1.76")

    implementation("org.ktorm:ktorm-core:3.5.0")

    implementation("io.ktor:ktor-client-okhttp-jvm:2.3.5")
    implementation("io.ktor:ktor-client-cio-jvm:2.3.5")
    implementation("io.ktor:ktor-client-auth-jvm:2.3.5")
    implementation("io.ktor:ktor-server-auth-jvm:2.3.5")
    implementation("io.ktor:ktor-client-json-jvm:2.3.5")
    implementation("io.ktor:ktor-client-serialization-jvm:2.3.5")
    implementation("io.ktor:ktor-client-logging-jvm:2.3.5")

    implementation("com.google.code.gson:gson:2.10.1")

    implementation("org.mongodb:bson-kotlinx:4.11.0")
    implementation("org.mongodb:mongodb-driver-kotlin-coroutine:4.11.0")
    implementation("org.mongodb:mongodb-driver-core:4.11.0")
    implementation("org.mongodb:mongodb-driver-kotlin-sync:4.11.0")
    implementation("org.mongodb:bson-kotlin:4.11.0")

    implementation("com.mysql:mysql-connector-j:8.2.0")
    implementation("app.pieces.pieces-os-client:pieces-os-client:1.0.0")

}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("MainKt")
}