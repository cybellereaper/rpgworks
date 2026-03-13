package github.cybellereaper.rpgworks.clicker.combos;

import github.cybellereaper.rpgworks.clicker.ClickInput;
import github.cybellereaper.rpgworks.clicker.spells.SpellCombo;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ComboManagerTest {

    @Test
    void emitsComboWhenThreeInputsAreCollected() {
        MutableClock clock = new MutableClock();
        ComboManager manager = new ComboManager(800L, clock);
        UUID playerId = UUID.randomUUID();

        assertNull(manager.registerInput(playerId, ClickInput.R));
        assertNull(manager.registerInput(playerId, ClickInput.L));
        assertEquals(SpellCombo.RLR, manager.registerInput(playerId, ClickInput.R));
    }

    @Test
    void resetsInputBufferAfterTimeout() {
        MutableClock clock = new MutableClock();
        ComboManager manager = new ComboManager(800L, clock);
        UUID playerId = UUID.randomUUID();

        assertNull(manager.registerInput(playerId, ClickInput.L));
        clock.advanceMillis(900L);
        assertNull(manager.registerInput(playerId, ClickInput.L));
        assertNull(manager.registerInput(playerId, ClickInput.L));
        assertEquals(SpellCombo.LLR, manager.registerInput(playerId, ClickInput.R));
    }

    private static final class MutableClock extends Clock {
        private Instant instant = Instant.EPOCH;

        @Override
        public ZoneId getZone() {
            return ZoneId.of("UTC");
        }

        @Override
        public Clock withZone(ZoneId zone) {
            return this;
        }

        @Override
        public Instant instant() {
            return instant;
        }

        private void advanceMillis(long millis) {
            instant = instant.plusMillis(millis);
        }
    }
}
