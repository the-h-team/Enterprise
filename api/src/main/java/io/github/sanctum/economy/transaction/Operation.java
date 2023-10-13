/*
 *   Copyright 2023 Sanctum <https://github.com/the-h-team>
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
package io.github.sanctum.economy.transaction;

import io.github.sanctum.economy.construct.system.Queryable;
import io.github.sanctum.economy.construct.system.Receiver;
import io.github.sanctum.economy.construct.system.Settable;
import io.github.sanctum.economy.construct.system.Source;
import io.github.sanctum.economy.construct.system.Total;

/**
 * Represents the action performed in a transaction.
 *
 * @since 2.0.0
 * @author ms5984
 */
public enum Operation {
    /**
     * A point is checked for an amount (true or false only).
     */
    QUERY(Queryable.class),
    /**
     * A point is given an amount.
     */
    GIVE(Receiver.class),
    /**
     * An amount is set for a point.
     */
    SET(Settable.class),
    /**
     * An amount is taken from a point.
     */
    TAKE(Source.class),
    /**
     * A point is asked for its total of an asset.
     */
    TOTAL(Total.class),
    ;

    /**
     * The system class that represents this operation.
     */
    public final Class<?> interfaceClass;

    Operation(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }
}
