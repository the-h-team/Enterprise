/**
 * <h1>System:</h1>
 * Participant capabilities and system state support.
 * <p>
 * This package contains interfaces for all participant capabilities as well
 * as supporting classes able to control and convey system state.
 * <p>
 * <h2>Capabilities:</h2>
 * <ul>
 *     <li>{@link com.github.sanctum.economy.construct.system.Account}
 *         - Manage account state
 *     <li>{@link com.github.sanctum.economy.construct.system.AccountView}
 *         - Perform account capabilities per-participant
 *     <li>{@link com.github.sanctum.economy.construct.system.Balance}
 *         - A combination of other capabilities
 *     <li>{@link com.github.sanctum.economy.construct.system.Custodian}
 *         - Maintains accounts for others
 *     <li>{@link com.github.sanctum.economy.construct.system.Contextual}
 *         - Able to provide the participant's context(s)
 *     <li>{@link com.github.sanctum.economy.construct.system.Queryable}
 *         - Able to hold specific asset amounts
 *     <li>{@link com.github.sanctum.economy.construct.system.Receiver}
 *         - Able to accept asset amounts
 *     <li>{@link com.github.sanctum.economy.construct.system.Resolvable}
 *         - Able to resolve the participant's entity representation
 *     <li>{@link com.github.sanctum.economy.construct.system.Settable}
 *         - Able to accept direct changes to asset amounts
 *     <li>{@link com.github.sanctum.economy.construct.system.Source}
 *         - Able to provide asset amounts
 *     <li>{@link com.github.sanctum.economy.construct.system.Total}
 *         - Able to provide asset totals
 *     <li>{@link com.github.sanctum.economy.construct.system.Wallet}
 *         - Manage wallet state
 * </ul>
 *
 * <h2>State:</h2>
 * {@link com.github.sanctum.economy.construct.system.AbstractSystemException},
 * {@link com.github.sanctum.economy.construct.system.AmountException} and
 * {@link com.github.sanctum.economy.construct.system.ParticipantException} as well
 * their tightly-coupled subclasses are used to provide rich responses where
 * one return type is not truly sufficient.
 * <p>
 * <h3>Amount-based:</h3>
 * <ul>
 *     <li>{@link com.github.sanctum.economy.construct.system.Receiver.AcceptError}
 *     <li>{@link com.github.sanctum.economy.construct.system.Settable.SetError}
 *     <li>{@link com.github.sanctum.economy.construct.system.Source.SupplyError}
 * </ul>
 * <h3>Entity-based:</h3>
 * <ul>
 *     <li>{@link com.github.sanctum.economy.construct.system.Account.AccessDenied}
 *     <li>{@link com.github.sanctum.economy.construct.system.Account.DuplicateParticipant}
 *     <li>{@link com.github.sanctum.economy.construct.system.Account.LastOwner}
 *     <li>{@link com.github.sanctum.economy.construct.system.Account.NotAnAccountParticipant}
 * </ul>
 *
 * @since 2.0.0
 */
package com.github.sanctum.economy.construct.system;