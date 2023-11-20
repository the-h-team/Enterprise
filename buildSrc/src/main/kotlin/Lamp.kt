import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.maven

/**
 * Manages Lamp dependencies.
 *
 * @author ms5984
 * @since 2.0.0
 */
@Suppress("MemberVisibilityCanBePrivate")
object Lamp {
    // the group of Lamp dependencies
    private const val GROUP = "com.github.Revxrsal.Lamp"
    // the repository for Lamp dependencies
    private val REPO: RepositoryHandler.() -> Unit = {
        maven("https://jitpack.io") {
            name = "jitpack-lamp"
            mavenContent {
                // Allow Lamp
                includeGroup(GROUP)
            }
        }
    }

    /**
     * The version of Lamp to use.
     */
    const val VERSION = "3.1.7"

    /**
     * The Lamp `common` dependency.
     */
    val common = Dependency(GROUP, "common", VERSION, repository = REPO)

    /**
     * The Lamp `bukkit` dependency.
     */
    val bukkit = Dependency(GROUP, "bukkit", VERSION, repository = REPO)
}