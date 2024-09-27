/**
 * repositories opens access to declare repositories
 * https://docs.gradle.org/current/userguide/declaring_repositories.html#sec:declaring_public_repository
 */
repositories {
    google()
    mavenCentral() //must have to retrieve dependencies from mavenCentral
    /**
     * testing directory usually found at c:/users/{username}/.m2/repository on windows
     * https://docs.gradle.org/current/userguide/declaring_repositories.html#sec:case-for-maven-local
     */
    mavenLocal()}


plugins {
    /**
     *  Dokka plugin used to generate javadoc from kotlin files, can generate in javadoc, html, github markdown and jekyll markdown.
     */
    id("org.jetbrains.dokka") version "1.9.20"

    /**
     * https://docs.gradle.org/current/userguide/java_library_plugin.html#header
     * opens access to java-library functions with api exposure(java is similar but doesn't open API access)
     */
    id("java-library") //
    /**
     * https://docs.gradle.org/current/userguide/publishing_maven.html#header
     * opens access to the maven publishing functions
     */
    id("maven-publish")
    /**
     * https://docs.gradle.org/current/userguide/signing_plugin.html#header
     * opens access to signing functions
     */
    id("signing")

    kotlin("jvm") version "1.9.20"
    kotlin("plugin.serialization") version "1.9.20"
    id("jvm-test-suite")
    id("org.owasp.dependencycheck") version "10.0.3"
    id("org.openapi.generator") version "7.0.1"
    //kapt("androidx.room")
    //kotlin("plugin.lombok") version "1.9.20"
    //id("io.freefair.lombok") version "8.10"
    application
}

openApiGenerate {
    inputSpec.set("src/main/kotlin/jdbchttpsql/openapi/sleradio.yml")
    generatorName.set("kotlin")
    library.set("jvm-retrofit2")
}


val localRepo = repositories.mavenLocal().url.path
group = "org.jeremiahboothe"
version = "1.0-SNAPSHOT"

/**
 * creates javadocJar
 */
tasks.register<Jar>("dokkaJavadocJar").configure {
    dependsOn(tasks.dokkaJavadoc)
    from(tasks.dokkaJavadoc.flatMap { it.outputDirectory })
    archiveClassifier.set("javadoc")
}
tasks.register<Jar>("dokkaJekyllJar").configure {
    dependsOn(tasks.dokkaJekyll)
    from(tasks.dokkaJekyll.flatMap { it.outputDirectory })
    archiveClassifier.set("dokkajekyll")
}


/**
 * creates publishable kotlinSourcesJar, needed for automaticmanifest control
 */
tasks.register<Jar>("kotlinSources").configure {
    dependsOn(tasks.kotlinSourcesJar)
    from(fileTree("src"))
    archiveClassifier.set("sources")
}

kotlin {
    jvmToolchain(17)
    sourceSets.all {
        languageSettings {
            languageVersion = "1.9"
        }
    }
}

// not necessary for purely kotlin builds
java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
    withJavadocJar()
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

application {
    mainClass.set("MainKt")
}
/**
 * adds automatic generation of manifest entries from files
 */
tasks.withType<Jar>().configureEach {
    manifest.attributes["Main-Class"] = "$group"
    manifest.attributes["Class-Path"] = configurations
        .runtimeClasspath
        .get()
        .joinToString(separator = " ") { file -> "libs/${file.name}" }
    manifest.attributes["API"] = fileTree("src")
        .joinToString(separator = " ") { file -> "${file.name}" }
}

dependencies {
    val ktorVersion = "2.3.12"

    dokkaPlugin("org.jetbrains.dokka:android-documentation-plugin:1.9.20")

    implementation("org.ktorm:ktorm-core") {
        version {
            strictly("4.1.1")
        }
    }

    implementation("org.slf4j:slf4j-simple:2.0.9")
    testImplementation("org.slf4j:slf4j-api:2.0.9")

    implementation("org.bouncycastle:bcprov-jdk18on:1.78.1")

    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-auth:$ktorVersion")
    implementation("io.ktor:ktor-server-auth:$ktorVersion")
    implementation("io.ktor:ktor-client-json:$ktorVersion")
    implementation("io.ktor:ktor-serialization-gson:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
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

    /**
     * This commented out implementation is to import the library after creation
     */
    //implementation("app.jdbc.jdbc-http-database:jdbc-http-database:$version")
    implementation("org.owasp:dependency-check-gradle:10.0.3")

    // Tests
    testImplementation("io.kotest:kotest-runner-junit5:5.8.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation(kotlin("test"))
}

publishing {
    // adds attributes to manifest in generated jar file.  The entries are just for demonstration.
    tasks.jar {
        manifest {
            attributes(
                "Implementation-Title" to "Gradle",
                "Implementation-Version" to archiveVersion,
                "Import-Package" to "com.squareup.okhttp3:okhttp:4.2.2",
                "Require-Capability" to "com.squareup.okhttp;\"version=[4.2.2)\"",
                "Export-Package" to "com.squareup.okhttp;\"version=[4.2.2)\""
            )
        }
    }

    publications {
        create<MavenPublication>("myLibrary") {
            from(components["kotlin"])

            /**
             * builds the sources and javadoc jar files automatically when executing publishing tasks
             */
            defaultArtifacts {
                artifacts {
                    artifact(archives(tasks["kotlinSources"])) {
                        classifier = "sources"
                    }
                    /**
                     * reconfigured to generate kotlin javadoc files properly
                     */
                    artifact(archives(tasks["dokkaJavadocJar"])) {
                        classifier = "javadoc"
                    }
                    artifact(archives(tasks["dokkaJekyllJar"])) {
                        classifier = "dokkajekyll"
                    }

                }
            }

            /**
             * sets the properties of the generated pom file, to include all the sonatype requirements
             */
            pom {
                name.set("Indie Radio Repository Layer Library")
                description.set("Repository layer for Indie Radio using JDBC")
                url.set("https://jeremiahboothe.github.io")
                artifactId = "jdbc-http-database"
                groupId = "$group"
                version = version

                /**
                 * licenses
                 */

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://www.opensource.org/licenses/mit-license.php")
                    }
                }

                /**
                 * developers
                 */
                developers {
                    developer {
                        organization.set("ArmstrongIndustries")
                        organizationUrl.set("https://jeremiahboothe.github.io")
                        name.set("Indie Radio JDBC Repo")
                        email.set("jeremiahboothe@outlook.com")
                    }
                }
                /**
                 * source control management node properties
                 */
                scm {
                    connection.set("scm:git:git://https://github.com/JeremiahBoothe/jdbc-http-sql.git")
                    developerConnection.set("scm:git:ssh://https://github.com/JeremiahBoothe/jdbc-http-sql.git")
                    url.set("https://github.com/JeremiahBoothe/jdbc-http-sql/tree/main")
                }
            }
            /**
             * edits the pom after initial creation, adds the packaging node with the value of jar
             */
            pom.withXml {
                asNode().appendNode("packaging", "jar")
            }
        }
    }
    /**
     *
     * sets up direct to maven publishing with signing
     */
    repositories {
        maven {
            name = "OSSRH"
            setUrl("https://oss.sonatype.org/service/local/staging/deploy/maven2")
            credentials {
                username = System.getenv("OSSRH_USER") ?: return@credentials
                password = System.getenv("OSSRH_PASSWORD") ?: return@credentials
            }
        }
    }
    /**
     * allows local publication with signing, can be authenticated/tested with kleopatra/gpg to ensure signing is occuring before
     * final publication
     */
    repositories {
        mavenLocal {
            url = uri(layout.buildDirectory.dir("$localRepo"))
        }
    }
    /**
     * generate public/private keys and distribute the public key to pgp.mit.edu this step is required so sonatype
     * can verify the checksums when publishing, without this step the publication will fail
     * https://blog.sonatype.com/2010/01/how-to-generate-pgp-signatures-with-maven/
     */
    signing {
        useGpgCmd() // runs local gpg installation(I used gpg4win/kleopatra to generate keys)
        sign(publishing.publications["myLibrary"]) // the publication being signed
    }
}

