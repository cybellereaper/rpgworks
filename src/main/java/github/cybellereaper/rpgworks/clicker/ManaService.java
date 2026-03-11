package github.cybellereaper.rpgworks.clicker;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class ManaService {
    private final Map<UUID, Integer> mana = new HashMap<>();

    public boolean hasMana(Player player, int amount) {
        return mana.getOrDefault(player.getUniqueId(), 100) >= amount;
    }

    public void takeMana(Player player, int amount) {
        mana.put(player.getUniqueId(), Math.max(0, mana.getOrDefault(player.getUniqueId(), 100) - amount));
    }

    public int getMana(Player player) {
        return mana.getOrDefault(player.getUniqueId(), 100);
    }

    public void setMana(Player player, int value) {
        mana.put(player.getUniqueId(), Math.max(0, value));
    }
}