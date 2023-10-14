import java.util.Base64

plugins {
    id("enterprise.java-conventions")
    `maven-publish`
}

java {
    withSourcesJar()
    withJavadocJar()
}

// un-wire sourcesJar and javadocJar from normal assemble
tasks.named("assemble") {
    setDependsOn(dependsOn.filterNot {
        (it as? Named)?.name in listOf("sourcesJar", "javadocJar")
    })
}

tasks.withType<AbstractPublishToMaven> {
    dependsOn.add(tasks.named("sourcesJar"))
    dependsOn.add(tasks.named("javadocJar"))
}

afterEvaluate {
    publishing {
        publications.create<MavenPublication>("maven") {
            pom {
                description.set(
                    project.description.takeIf { it != rootProject.description } ?:
                    throw IllegalStateException("Set the project description in ${project.projectDir.name}/build.gradle.kts before activating publishing.")
                )
                url.set(project.properties["url"] as String)
                inceptionYear.set(project.properties["inceptionYear"] as String)
                licenses {
                    license {
                        name.set("Apache License 2.0")
                        url.set("https://opensource.org/licenses/Apache-2.0")
                        distribution.set("repo")
                    }
                }
                organization {
                    name.set("Sanctum")
                    url.set("https://github.com/the-h-team")
                }
                developers {
                    developer {
                        id.set("ms5984")
                        name.set("Matt")
                        url.set("https://github.com/ms5984")
                    }
                    developer {
                        id.set("Hempfest")
                        name.set("Austin")
                        url.set("https://github.com/Hempfest")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/the-h-team/Enterprise.git")
                    developerConnection.set("scm:git:ssh://github.com/the-h-team/Enterprise.git")
                    url.set("https://github.com/the-h-team/Enterprise/tree/master")
                }
            }
            from(components["java"])
            if (hasProperty("signingKeyPassphrase")) {
                apply(plugin = "signing")
                configure<SigningExtension> {
                    useInMemoryPgpKeys(
                        "base64SigningKey".let(::findProperty).let {
                            Base64.getDecoder().decode(it as String).toString(Charsets.UTF_8)
                        },
                        findProperty("signingKeyPassphrase") as String
                    )
                    sign(publishing.publications[name])
                }
            }
        }
    }
}