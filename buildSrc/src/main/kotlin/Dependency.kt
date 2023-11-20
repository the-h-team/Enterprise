import org.gradle.api.artifacts.dsl.RepositoryHandler

/**
 * Stores static information about a Maven dependency and repository.
 *
 * @property groupId The groupId of the dependency
 * @property artifactId The artifactId of the dependency
 * @property version The version of the dependency
 * @property classifier The classifier of the dependency, if any
 * @property repository The function to create the repository for the dependency, if necessary
 * @author ms5984
 * @since 2.0.0
 */
data class Dependency @JvmOverloads constructor(
    val groupId: String,
    val artifactId: String,
    val version: String,
    val classifier: String? = null,
    val repository: (RepositoryHandler.() -> Unit)? = null
) {
    /**
     * The dependency notation.
     */
    val notation: String = "$groupId:$artifactId:$version${classifier?.let { ":$it" } ?: ""}"
}
