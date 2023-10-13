rootProject.name = "enterprise-parent"

include(":enterprise-api")
project(":enterprise-api").projectDir = file("api")

sequenceOf("bukkit", "plugin").forEach {
    include(":enterprise-$it")
    project(":enterprise-$it").projectDir = file("platforms/$it")
}