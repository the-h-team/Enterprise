package com.github.sanctum.economy.construct.assets;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Internal
class AssetImpl implements Asset {
    final @Group String group;
    final @Identifier String identifier;

    AssetImpl(@Group String group, @Identifier String identifier) {
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
}
