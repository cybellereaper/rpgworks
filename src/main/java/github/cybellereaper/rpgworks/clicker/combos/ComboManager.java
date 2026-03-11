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
        ComboState state = state(uuid);
        state.push(input, System.currentTimeMillis(), comboTimeoutMillis);

        if (!state.isComplete()) {
            return null;
        }

        SpellCombo combo = SpellCombo.from(state.buffer());
        state.clear();
        return combo;
    }

    public void clear(UUID uuid) {
        ComboState state = states.get(uuid);
        if (state != null) {
            state.clear();
        }
    }

    public void remove(UUID uuid) {
        states.remove(uuid);
    }
}