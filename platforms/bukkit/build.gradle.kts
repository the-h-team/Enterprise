plugins {
    id("enterprise.java-conventions")
    id("enterprise.shadow-conventions")
}

repositories {
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") {
        name = "spigotmc"
    }
}

val spigotApiVersion = "1.20.1-R0.1-SNAPSHOT"

dependencies {
    api(project(":enterprise-api"))
    api("org.spigotmc:spigot-api:$spigotApiVersion")
}

description = "Spigot platform implementation for Enterprise"