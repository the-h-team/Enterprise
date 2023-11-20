plugins {
    id("enterprise.java-conventions")
    id("enterprise.shadow-conventions")
    id("enterprise.publishing-conventions")
}

repositories {
    // pull in bukkit's repository
    Bukkit.dependency.repository!!()
    maven("https://jitpack.io") {
        name = "jitpack-vault"
        mavenContent {
            includeModule("com.github.MilkBowl", "VaultAPI")
        }
    }
}

dependencies {
    // pull in bukkit platform
    implementation(project(":enterprise-bukkit"))
    // compile against VaultAPI
    compileOnly("com.github.MilkBowl:VaultAPI:1.7.1") {
        exclude(group = "org.bukkit", module = "bukkit")
        exclude(group = "junit", module = "junit")
    }
}

description = "The Bukkit plugin implementation of Enterprise"

tasks.withType<ProcessResources> {
    filesMatching(listOf("plugin.yml")) {
        expand(project.properties)
    }
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveFileName.set("${project.name}-${project.version}.jar")
    archiveClassifier.set("plugin")
    dependencies {
        include(project(":enterprise-bukkit"))
    }
}