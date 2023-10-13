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
package io.github.sanctum.economy.construct.entity;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * @since 2.0.0
 * @author ms5984
 * @param <T> the type of the identifying property
 */
@ApiStatus.Internal
class PlayerEntityImpl<T> extends EnterpriseEntityImpl implements PlayerEntity<T> {
    T identifyingProperty;

    PlayerEntityImpl(@Namespace String namespace, T identifyingProperty) {
        super(namespace, identifyingProperty.toString());
        this.identifyingProperty = identifyingProperty;
    }

    @Override
    public final @NotNull T getIdentifyingProperty() {
        return identifyingProperty;
    }

    @Override
    public @NotNull String getFriendlyName() {
        if (identifyingProperty instanceof String)
            return (String) identifyingProperty;
        return "Player:" + identifyingProperty;
    }
}
