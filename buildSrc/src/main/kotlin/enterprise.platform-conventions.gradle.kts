plugins {
    id("enterprise.java-conventions")
}

val resolvedExtra by lazy { extra["platform"] as Target }

afterEvaluate {
    resolvedExtra.dependency.repository?.let {
        repositories {
            it()
        }
    }
    dependencies {
        api(resolvedExtra.dependency.notation)
    }
    description = "${resolvedExtra.name} platform implementation for Enterprise"
}