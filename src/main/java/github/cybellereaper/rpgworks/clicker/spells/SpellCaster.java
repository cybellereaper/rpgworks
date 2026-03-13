package github.cybellereaper.rpgworks.clicker.spells;

import github.cybellereaper.rpgworks.clicker.ClickInput;
import github.cybellereaper.rpgworks.services.ManaService;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.time.Clock;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public final class SpellCaster {
    private final ManaService manaService;
    private final Map<UUID, Map<String, Long>> cooldowns = new HashMap<>();
    private final Clock clock;

    public enum CastResult {
        CAST,
        INSUFFICIENT_MANA,
        ON_COOLDOWN,
        FAILED
    }

    public SpellCaster(ManaService manaService) {
        this(manaService, Clock.systemUTC());
    }

    SpellCaster(ManaService manaService, Clock clock) {
        this.manaService = Objects.requireNonNull(manaService, "manaService");
        this.clock = Objects.requireNonNull(clock, "clock");
    }

    public boolean canUseSpellSystem(Player player) {
        return player.isOnline();
    }

    public CastResult cast(Player player, Spell spell) {
        if (!manaService.hasMana(player, spell.manaCost())) {
            fail(player, "Not enough mana.");
            return CastResult.INSUFFICIENT_MANA;
        }

        if (isOnCooldown(player, spell)) {
            fail(player, "Spell is on cooldown.");
            return CastResult.ON_COOLDOWN;
        }

        manaService.takeMana(player, spell.manaCost());
        if (!spell.cast(player)) {
            return CastResult.FAILED;
        }

        putOnCooldown(player, spell);
        return CastResult.CAST;
    }

    public void fail(Player player, String message) {
        player.sendActionBar(Component.text(message));
    }

    public void showPartialCombo(Player player, List<ClickInput> inputs) {
        String comboPreview = inputs.stream()
                .map(Enum::name)
                .collect(Collectors.joining());
        player.sendActionBar(Component.text(comboPreview));
    }

    private boolean isOnCooldown(Player player, Spell spell) {
        long now = clock.millis();
        long until = cooldowns
                .getOrDefault(player.getUniqueId(), Map.of())
                .getOrDefault(spell.id(), 0L);
        return now < until;
    }

    private void putOnCooldown(Player player, Spell spell) {
        cooldowns
                .computeIfAbsent(player.getUniqueId(), ignored -> new HashMap<>())
                .put(spell.id(), clock.millis() + spell.cooldownMillis());
    }
}
