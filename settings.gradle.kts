rootProject.name = "RunicRealms"

pluginManagement {
    plugins {
        kotlin("jvm") version "1.7.21"
        kotlin("kapt") version "1.7.21"
        id("com.github.johnrengelman.shadow") version "7.1.2"
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("commonLibs") {
            library("paper", "io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT")
        }
    }
}