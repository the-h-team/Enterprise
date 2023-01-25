package com.github.sanctum.economy.construct.assets;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

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
}
