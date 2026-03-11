package github.cybellereaper.rpgworks.clicker;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class SpellCaster {
    private final ManaService manaService;
    private final Map<UUID, Map<String, Long>> cooldowns = new HashMap<>();

    public SpellCaster(ManaService manaService) {
        this.manaService = manaService;
    }

    public boolean canUseSpellSystem(Player player) {
        return true; // replace with held weapon/class checks
    }

    public void cast(Player player, Spell spell) {
        if (!manaService.hasMana(player, spell.manaCost())) {
            fail(player, "Not enough mana.");
            return;
        }

        if (isOnCooldown(player, spell)) {
            fail(player, "Spell is on cooldown.");
            return;
        }

        manaService.takeMana(player, spell.manaCost());

        boolean success = spell.cast(player);
        if (!success) {
            return;
        }

        putOnCooldown(player, spell);
    }

    public void fail(Player player, String message) {
        player.sendActionBar(net.kyori.adventure.text.Component.text(message));
    }

    public void showPartialCombo(Player player, java.util.List<ClickInput> inputs) {
        String text = inputs.stream()
                .map(Enum::name)
                .reduce("", (a, b) -> a + b);
        player.sendActionBar(net.kyori.adventure.text.Component.text(text));
    }

    private boolean isOnCooldown(Player player, Spell spell) {
        long now = System.currentTimeMillis();
        long until = cooldowns
                .getOrDefault(player.getUniqueId(), Map.of())
                .getOrDefault(spell.id(), 0L);
        return now < until;
    }

    private void putOnCooldown(Player player, Spell spell) {
        cooldowns
                .computeIfAbsent(player.getUniqueId(), ignored -> new HashMap<>())
                .put(spell.id(), System.currentTimeMillis() + spell.cooldownMillis());
    }
}
