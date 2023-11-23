/**
 * <h1>System:</h1>
 * Participant capabilities and system state support.
 * <p>
 * This package contains interfaces for all participant capabilities as well
 * as supporting classes able to control and convey system state.
 * <p>
 * <h2>Capabilities:</h2>
 * <ul>
 *     <li>{@link io.github.sanctum.economy.construct.system.accounts.Account}
 *         - Manage account state
 *     <li>{@link io.github.sanctum.economy.construct.system.accounts.AccountView}
 *         - Perform account capabilities per-participant
 *     <li>{@link io.github.sanctum.economy.construct.system.Balance}
 *         - A combination of other capabilities
 *     <li>{@link io.github.sanctum.economy.construct.system.accounts.Custodian}
 *         - Maintains accounts for others
 *     <li>{@link io.github.sanctum.economy.construct.system.Contextual}
 *         - Able to provide the participant's context(s)
 *     <li>{@link io.github.sanctum.economy.construct.system.behaviors.Queryable}
 *         - Able to hold specific asset amounts
 *     <li>{@link io.github.sanctum.economy.construct.system.behaviors.Receiver}
 *         - Able to accept asset amounts
 *     <li>{@link io.github.sanctum.economy.construct.system.Resolvable}
 *         - Able to resolve the participant's entity representation
 *     <li>{@link io.github.sanctum.economy.construct.system.behaviors.Settable}
 *         - Able to accept direct changes to asset amounts
 *     <li>{@link io.github.sanctum.economy.construct.system.behaviors.Source}
 *         - Able to provide asset amounts
 *     <li>{@link io.github.sanctum.economy.construct.system.behaviors.Total}
 *         - Able to provide asset totals
 *     <li>{@link io.github.sanctum.economy.construct.system.Wallet}
 *         - Manage wallet state
 * </ul>
 *
 * <h2>State:</h2>
 * {@link io.github.sanctum.economy.construct.system.exceptions.AbstractSystemException},
 * {@link io.github.sanctum.economy.construct.system.exceptions.AmountException} and
 * {@link io.github.sanctum.economy.construct.system.exceptions.ParticipantException} as well
 * their tightly-coupled subclasses are used to provide rich responses where
 * one return type is not truly sufficient.
 * <p>
 * <h3>Amount-based:</h3>
 * <ul>
 *     <li>{@link io.github.sanctum.economy.construct.system.behaviors.Receiver.AcceptError}
 *     <li>{@link io.github.sanctum.economy.construct.system.behaviors.Settable.SetError}
 *     <li>{@link io.github.sanctum.economy.construct.system.behaviors.Source.SupplyError}
 * </ul>
 * <h3>Entity-based:</h3>
 * <ul>
 *     <li>{@link io.github.sanctum.economy.construct.system.accounts.exceptions.AccessDenied}
 *     <li>{@link io.github.sanctum.economy.construct.system.accounts.exceptions.DuplicateParticipant}
 *     <li>{@link io.github.sanctum.economy.construct.system.accounts.exceptions.LastOwner}
 *     <li>{@link io.github.sanctum.economy.construct.system.accounts.exceptions.NotAnAccountParticipant}
 * </ul>
 *
 * @since 2.0.0
 */
package io.github.sanctum.economy.construct.system;