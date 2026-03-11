package github.cybellereaper.rpgworks.clicker;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.entity.Player;

public final class ComboListener implements Listener {

    private final ComboManager comboManager;
    private final SpellRegistry spellRegistry;
    private final SpellCaster spellCaster;

    public ComboListener(ComboManager comboManager, SpellRegistry spellRegistry, SpellCaster spellCaster) {
        this.comboManager = comboManager;
        this.spellRegistry = spellRegistry;
        this.spellCaster = spellCaster;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Action action = event.getAction();
        ClickInput input = map(action);

        if (input == null) {
            return;
        }

        Player player = event.getPlayer();

        // Optional: require specific weapon/item held
        if (!spellCaster.canUseSpellSystem(player)) {
            return;
        }

        // Optional: cancel normal interaction while combo casting
        event.setCancelled(true);

        SpellCombo combo = comboManager.registerInput(player.getUniqueId(), input);
        if (combo == null) {
            spellCaster.showPartialCombo(player, comboManager.state(player.getUniqueId()).buffer());
            return;
        }

        Spell spell = spellRegistry.get(combo);
        if (spell == null) {
            spellCaster.fail(player, "Unknown combo.");
            return;
        }

        spellCaster.cast(player, spell);
    }

    private ClickInput map(Action action) {
        return switch (action) {
            case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> ClickInput.L;
            case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> ClickInput.R;
            default -> null;
        };
    }
}
