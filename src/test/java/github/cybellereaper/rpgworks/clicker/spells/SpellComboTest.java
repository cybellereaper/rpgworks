package github.cybellereaper.rpgworks.clicker.spells;

import github.cybellereaper.rpgworks.clicker.ClickInput;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SpellComboTest {

    @Test
    void resolvesKnownCombo() {
        SpellCombo combo = SpellCombo.from(List.of(ClickInput.R, ClickInput.L, ClickInput.R));

        assertEquals(SpellCombo.RLR, combo);
    }

    @Test
    void returnsNullForUnknownOrInvalidLength() {
        assertNull(SpellCombo.from(List.of(ClickInput.L, ClickInput.L)));
        assertNull(SpellCombo.from(List.of(ClickInput.L, ClickInput.L, ClickInput.L, ClickInput.R)));
    }
}
