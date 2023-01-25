package com.github.sanctum.economy.construct.system;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Internal
class ContextImpl implements Context {
    final @Type @NotNull String type;
    final @Value @NotNull String value;

    ContextImpl(@Type @NotNull String type, @Value @NotNull String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public final @Type @NotNull String getType() {
        return type;
    }

    @Override
    public final @Value @NotNull String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContextImpl)) return false;

        ContextImpl that = (ContextImpl) o;

        return type.equals(that.type) &&
                value.equals(that.value);
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }
}
