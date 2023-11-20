plugins {
    id("enterprise.java-conventions")
    id("enterprise.shadow-conventions")
    id("enterprise.platform-conventions")
    id("enterprise.publishing-conventions")
}

val platform by extra(Bukkit)

dependencies {
    api(project(":enterprise-api"))
}