/**
 * Needed to use plugins in build.gradle files
 */
pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
    }
}

/**
 * Plugins and toolChainManagement added to enforce toolchain version
 */
plugins {
    id("org.gradle.toolchains.foojay-resolver") version "0.8.0"
}
@Suppress("UnstableApiUsage")
toolchainManagement {
    jvm {
        javaRepositories {
            repository("foojay") {
                resolverClass.set(org.gradle.toolchains.foojay.FoojayToolchainResolver::class.java)
            }
        }
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositories{
        mavenCentral()
    }
}


rootProject.name = "jdbchttpsql"
include(":${rootProject}")