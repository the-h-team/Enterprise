plugins {
    id("enterprise.java-conventions")
    id("enterprise.shadow-conventions")
    id("enterprise.publishing-conventions")
}

repositories {
    // pull in bukkit's repository
    Bukkit.dependency.repository!!()
    maven("https://jitpack.io") {
        name = "jitpack-deps"
        mavenContent {
            // Allow vault
            includeModule("com.github.MilkBowl", "VaultAPI")
            // Allow Lamp
            includeGroup("com.github.Revxrsal.Lamp")
        }
    }
}

dependencies {
    // pull in bukkit platform
    implementation(project(":enterprise-bukkit"))
    // pull in lamp
    implementation(Lamp.common.notation)
    implementation(Lamp.bukkit.notation)
    // compile against VaultAPI
    compileOnly("com.github.MilkBowl:VaultAPI:1.7.1") {
        exclude(group = "org.bukkit", module = "bukkit")
        exclude(group = "junit", module = "junit")
    }
}

description = "The ${Bukkit.name} plugin implementation of Enterprise"

tasks.withType<ProcessResources> {
    // wire properties referenced as task inputs (to detect changes + re-run task)
    inputs.property("version", project.version)
    inputs.property("rootProject.description", project.rootProject.description)
    inputs.property("url", project.properties["url"])
    filesMatching(listOf("plugin.yml")) {
        expand(project.properties)
    }
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveFileName.set("${project.name}-${project.version}.jar")
    archiveClassifier.set("plugin")
    dependencies {
        include(project(":enterprise-bukkit"))
        // shade lamp
        include(Lamp.common.notation)
        include(Lamp.bukkit.notation)
    }
}