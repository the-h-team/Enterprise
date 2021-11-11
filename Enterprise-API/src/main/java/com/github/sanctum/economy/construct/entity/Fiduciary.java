package com.github.sanctum.economy.construct.entity;

import org.jetbrains.annotations.NotNull;

/**
 * A special type of entity that is responsible for the safekeeping
 * and/or management of others' assets.
 * <p>
 * This is akin to banks, credit unions and brokerage firms.
 *
 * @since 2.0.0
 * @author ms5984
 */
public class Fiduciary extends EnterpriseEntity {
    /**
     * Create a fiduciary from a namespace and identifier.
     *
     * @param namespace the namespace for the entity
     * @param identity the namespace-unique identifier for the entity
     * @throws IllegalArgumentException if <code>namespace</code> does not
     * follow the pattern of {@link EnterpriseEntity#VALID_NAMESPACE} and/or
     * <code>identity</code> does not follow the pattern of
     * {@link EnterpriseEntity#VALID_IDENTIFIER}
     */
    public Fiduciary(@NotNull String namespace, @NotNull String identity) throws IllegalArgumentException {
        super(validateNamespace(namespace), validateIdentity(identity));
    }
}
