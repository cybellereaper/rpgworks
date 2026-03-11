package github.cybellereaper.rpgworks.clicker.spells;

import org.bukkit.entity.Player;

public interface Spell {
    String id();
    SpellCombo combo();
    int manaCost();
    long cooldownMillis();
    boolean cast(Player player);
}