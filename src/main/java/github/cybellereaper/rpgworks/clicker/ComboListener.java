package github.cybellereaper.rpgworks.clicker;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import github.cybellereaper.rpgworks.classes.RpgClass;
import github.cybellereaper.rpgworks.services.PlayerClassService;

import org.bukkit.entity.Player;

public final class ComboListener implements Listener {

    private final ComboManager comboManager;
    private final PlayerClassService playerClassService;
    private final SpellCaster spellCaster;

    public ComboListener(
            ComboManager comboManager,
            PlayerClassService playerClassService,
            SpellCaster spellCaster
    ) {
        this.comboManager = comboManager;
        this.playerClassService = playerClassService;
        this.spellCaster = spellCaster;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        ClickInput input = map(event.getAction());
        if (input == null) {
            return;
        }

        Player player = event.getPlayer();

        if (!spellCaster.canUseSpellSystem(player)) {
            return;
        }

        event.setCancelled(true);

        SpellCombo combo = comboManager.registerInput(player.getUniqueId(), input);
        if (combo == null) {
            spellCaster.showPartialCombo(player, comboManager.state(player.getUniqueId()).buffer());
            return;
        }

        RpgClass rpgClass = playerClassService.getPlayerClass(player).orElse(null);
        if (rpgClass == null) {
            spellCaster.fail(player, "No class selected.");
            return;
        }

        Spell spell = rpgClass.spellBook().get(combo);
        if (spell == null) {
            spellCaster.fail(player, "No spell bound to " + combo.name() + " for " + rpgClass.displayName() + ".");
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
