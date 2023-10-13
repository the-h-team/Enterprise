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

@ApiStatus.Internal
class AmountImpl implements Amount {
    final Asset asset;

    AmountImpl(@NotNull Asset asset) {
        this.asset = asset;
    }

    @Override
    public @NotNull Asset getAsset() {
        return asset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AmountImpl)) return false;
        AmountImpl that = (AmountImpl) o;
        return asset.equals(that.asset);
    }

    @Override
    public int hashCode() {
        return asset.hashCode();
    }

    @Override
    public String toString() {
        return "AmountImpl{" +
                "asset=" + asset +
                '}';
    }
}
