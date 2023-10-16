import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
    kotlin("plugin.serialization") version "1.7.21"

    application
}

group = "org.jeremiahboothe"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.ktorm:ktorm-core:3.5.0")
    implementation("io.ktor:ktor-client-okhttp:2.1.3")
    implementation("io.ktor:ktor-client-cio:2.1.3")
    implementation("io.ktor:ktor-client-auth:2.1.3")
    implementation("io.ktor:ktor-auth:1.6.8")
    implementation("io.ktor:ktor-client-json:2.1.3")
    implementation("io.ktor:ktor-client-serialization-jvm:2.1.3")
    implementation("io.ktor:ktor-client-logging-jvm:2.1.3")
    implementation("com.google.code.gson:gson:2.10")
    implementation("mysql:mysql-connector-java:8.0.31")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")

}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}