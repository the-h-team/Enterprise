package com.github.sanctum.economy.construct.system;

/**
 * A point that implements the functionality of {@link Queryable},
 * {@link Receiver}, {@link Settable}, {@link Source} and {@link Total}
 * to some degree. It is not necessary to support all assets.
 *
 * @since 2.0.0
 */
public interface Balance extends Queryable, Receiver, Settable, Source, Total {
}
