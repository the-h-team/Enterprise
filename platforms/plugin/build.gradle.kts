plugins {
    id("enterprise.java-conventions")
}

// TODO shadow configuration

repositories {
    maven("https://jitpack.io") {
        name = "jitpack-vault"
        mavenContent {
            includeModule("com.github.MilkBowl", "VaultAPI")
        }
    }
}

dependencies {
    implementation(project(":enterprise-bukkit"))
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