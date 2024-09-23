
plugins {
    kotlin("jvm") version "1.9.20"
    kotlin("plugin.serialization") version "1.9.20"
    kotlin("plugin.lombok") version "1.9.20"
    id("io.freefair.lombok") version "8.10"
    id("org.owasp.dependencycheck") version "10.0.3"
    id("org.openapi.generator") version "7.0.1"
    //kapt("androidx.room")

    //id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0"
    java
    id("jvm-test-suite")
    application
}

openApiGenerate {
    inputSpec.set("src/main/kotlin/jdbchttpsql/openapi/sleradio.yml")
    generatorName.set("kotlin")
    library.set("jvm-retrofit2")
}

group = "org.jeremiahboothe"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
}

dependencies {
    // Tests

    testImplementation("io.kotest:kotest-runner-junit5:5.8.1")
    implementation("org.owasp:dependency-check-gradle:10.0.3")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
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

    val ktorVersion = "2.3.12"

    implementation("org.slf4j:slf4j-simple:2.0.9")
    testImplementation("org.slf4j:slf4j-api:2.0.9")

    implementation("org.bouncycastle:bcprov-jdk18on:1.78.1")

    implementation("org.ktorm:ktorm-core:4.1.1")
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("io.ktor:ktor-client-core:$ktorVersion") // Ensure you're using the latest version
    implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-auth:$ktorVersion")
    implementation("io.ktor:ktor-server-auth:$ktorVersion")
    implementation("io.ktor:ktor-client-json:$ktorVersion")
    implementation("io.ktor:ktor-serialization-gson:$ktorVersion") // Gson for se
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion") // Kotlinx JSON
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-client-serialization:$ktorVersion")
    implementation("io.ktor:ktor-client-logging-jvm:$ktorVersion")


    implementation("com.google.code.gson:gson:2.11.0")

    implementation("org.mongodb:bson-kotlinx:5.1.4")
    implementation("org.mongodb:mongodb-driver-kotlin-coroutine:5.1.4")
    implementation("org.mongodb:mongodb-driver-core:5.1.4")
    implementation("org.mongodb:mongodb-driver-kotlin-sync:5.1.4")
    implementation("org.mongodb:bson-kotlin:5.1.4")

    implementation("com.mysql:mysql-connector-j:9.0.0")
    //implementation("app.pieces.pieces-os-client:pieces-os-client:1.2.2")
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