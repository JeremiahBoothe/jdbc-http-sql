plugins {
    kotlin("jvm") version "1.9.10"
    kotlin("plugin.serialization") version "1.9.10"
    //kotlin("plugin.lombok") version "1.9.10"
    //id("io.freefair.lombok") version "8.1.0"
    java
    `jvm-test-suite`
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
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation(kotlin("test"))


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