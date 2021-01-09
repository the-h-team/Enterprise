package com.youtube.hempfest.economy.construct.entity.types;

import com.sun.istack.internal.NotNull;
import com.youtube.hempfest.economy.construct.entity.EconomyAbstractEntity;
import org.bukkit.entity.Player;

/**
 * Represent players by name only. Good for quick transactions but not suitable
 * for offline transactions. Contains convenience method to grab live Player object.
 */
public final class TemporaryPlayerEntity extends EconomyAbstractEntity {
    private final Player player;

    public TemporaryPlayerEntity(@NotNull Player player) {
        super(player);
        this.player = player;
    }

    /**
     * Represent this player by name
     * <p>Since player names persist for only a current session, this method
     * is best used in circumstances where transactions happen in view of the
     * player (GUIs, transactions between online players)</p>
     * @return String following "p_name=%playerName%" format
     */
    @Override
    public String id() {
        return "p_name=".concat(offlinePlayer.getName());
    }

    /**
     * Get the active Player contained in this object
     * @return {@link Player} the online player
     */
    @Override
    public Player getPlayer() {
        return player;
    }
}
