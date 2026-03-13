package github.cybellereaper.rpgworks.clicker.combos;

import github.cybellereaper.rpgworks.clicker.ClickInput;
import github.cybellereaper.rpgworks.clicker.spells.SpellCombo;

import java.time.Clock;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public final class ComboManager {
    private final Map<UUID, ComboState> states = new HashMap<>();
    private final long comboTimeoutMillis;
    private final Clock clock;

    public ComboManager(long comboTimeoutMillis) {
        this(comboTimeoutMillis, Clock.systemUTC());
    }

    ComboManager(long comboTimeoutMillis, Clock clock) {
        if (comboTimeoutMillis <= 0L) {
            throw new IllegalArgumentException("comboTimeoutMillis must be > 0");
        }

        this.comboTimeoutMillis = comboTimeoutMillis;
        this.clock = Objects.requireNonNull(clock, "clock");
    }

    public ComboState state(UUID uuid) {
        return states.computeIfAbsent(uuid, ignored -> new ComboState());
    }

    public SpellCombo registerInput(UUID uuid, ClickInput input) {
        ComboState state = state(uuid);
        state.push(input, clock.millis(), comboTimeoutMillis);

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
