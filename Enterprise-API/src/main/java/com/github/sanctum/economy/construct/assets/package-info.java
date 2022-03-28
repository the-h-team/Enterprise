/**
 * <h1>Assets</h1>
 * Assets in Enterprise represent a unique, holdable/tradable good.
 * <p>
 * Assets are identified by a <code>group</code>--a brief description of the
 * asset's type; and an <code>identifier</code>--a group-unique name for the
 * asset. These combine to form a fully-qualified name string that is used to
 * uniquely identify the asset system-wide.
 * <p>
 * Let's consider a library-native example of what an asset might look like:
 * <ul>
 *     <li>A group: <code>item</code></li>
 *     <li>An identifier: <code>book</code></li>
 *     <li>
 *         The fully-qualified name (<code>group:identifier</code>) for the
 *         asset: <code>item:book</code>
 *     </li>
 * </ul>
 * This is how the Enterprise Spigot module represents the
 * <a href="https://minecraft.fandom.com/wiki/Book">Book</a> Minecraft item.
 *
 * @since 2.0.0
 */
package com.github.sanctum.economy.construct.assets;