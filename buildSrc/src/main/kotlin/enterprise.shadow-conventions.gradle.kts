plugins {
    id("com.github.johnrengelman.shadow")
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    dependencies {
        include(project(":enterprise-api"))
    }
}