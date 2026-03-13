package github.cybellereaper.rpgworks.clicker.combos;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import github.cybellereaper.rpgworks.clicker.ClickInput;
import github.cybellereaper.rpgworks.clicker.spells.SpellCombo;

public final class ComboManager {
    private final Map<UUID, ComboState> states = new HashMap<>();
    private final long comboTimeoutMillis;

    public ComboManager(long comboTimeoutMillis) {
        this.comboTimeoutMillis = comboTimeoutMillis;
    }

    public ComboState state(UUID uuid) {
        return states.computeIfAbsent(uuid, ignored -> new ComboState());
    }

    public SpellCombo registerInput(UUID uuid, ClickInput input) {
        ComboState playerComboState = state(uuid);
        playerComboState.push(input, System.currentTimeMillis(), comboTimeoutMillis);

        if (!playerComboState.isComplete()) {
            return null;
        }

        SpellCombo detectedCombo = SpellCombo.from(playerComboState.inputs());
        playerComboState.clear();
        return detectedCombo;
    }

    public void clear(UUID uuid) {
        ComboState comboState = states.get(uuid);
        if (comboState == null) {
            return;
        }

        comboState.clear();
    }

    public void remove(UUID uuid) {
        states.remove(uuid);
    }
}