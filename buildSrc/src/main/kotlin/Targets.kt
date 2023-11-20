import org.gradle.kotlin.dsl.maven

/**
 * Represents a target platform of this project.
 *
 * @property name The name of the target platform (used for programmatic access)
 * @property dependency The dependency for the target platform
 * @author ms5984
 * @since 2.0.0
 */
open class Target(val name: String, val dependency: Dependency)

/**
 * The Bukkit platform target.
 *
 * @author ms5984
 * @since 2.0.0
 */
object Bukkit : Target(
    "Bukkit",
    Dependency("org.spigotmc", "spigot-api", "1.20.2-R0.1-SNAPSHOT", repository = {
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") {
            name = "spigotmc"
        }
    })
)