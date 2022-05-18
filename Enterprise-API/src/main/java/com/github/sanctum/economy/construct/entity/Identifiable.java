/*
 *   Copyright 2022 Sanctum <https://github.com/the-h-team>
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.github.sanctum.economy.construct.entity;

import com.github.sanctum.economy.construct.system.Resolvable;
import com.github.sanctum.economy.impl.WalletService;
import org.jetbrains.annotations.NotNull;

/**
 * Marks a class as convertible to an {@link EnterpriseEntity}.
 * <p>
 * This interface is provided to assist API developers--it facilitates the
 * decoupling of public identity API from implementation. It is even used
 * internally (see {@link EnterpriseEntity.PlayerEntity} and
 * {@link WalletService}) to encourage APIs exposing simple, platform-agnostic
 * definitions of identity classes (such as players) and allow shared,
 * cross-platform identity-related business logic.
 * <p>
 * This interface somewhat resembles {@link Resolvable} but differs in that its
 * contract mandates the {@link #asEntity()} return the reference it is called
 * upon.
 *
 * @since 2.0.0
 * @author ms5984
 */
public interface Identifiable {
    /**
     * Access this object as its EnterpriseEntity form.
     *
     * @return this object as its EnterpriseEntity form
     * @throws IllegalStateException if the object cannot be converted
     * @implSpec This method must return the same object.
     * @implNote The default implementation performs a cast.
     */
    default @NotNull EnterpriseEntity asEntity() throws IllegalStateException {
        try {
            return (EnterpriseEntity) this;
        } catch (ClassCastException e) {
            throw new IllegalStateException("This object is not an EnterpriseEntity", e);
        }
    }
}
