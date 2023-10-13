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
package com.github.sanctum.economy.construct.assets;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * @since 2.0.0
 * @author ms5984
 */
@ApiStatus.Internal
class AssetImpl implements Asset {
    final @Group String group;
    final @Identifier String identifier;

    AssetImpl(@Group @NotNull String group, @Identifier @NotNull String identifier) {
        this.group = group;
        this.identifier = identifier;
    }

    @Override
    public final @Group @NotNull String getGroup() {
        return group;
    }

    @Override
    public final @Identifier @NotNull String getIdentifier() {
        return identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AssetImpl)) return false;

        AssetImpl that = (AssetImpl) o;

        return group.equals(that.group) &&
                identifier.equals(that.identifier);
    }

    @Override
    public int hashCode() {
        int result = group.hashCode();
        result = 31 * result + identifier.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AssetImpl{" +
                "group='" + group + '\'' +
                ", identifier='" + identifier + '\'' +
                '}';
    }
}
