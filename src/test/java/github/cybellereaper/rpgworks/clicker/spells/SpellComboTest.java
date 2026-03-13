package github.cybellereaper.rpgworks.clicker.spells;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import github.cybellereaper.rpgworks.clicker.ClickInput;

class SpellComboTest {

    @Test
    void fromShouldReturnMatchingComboForValidInputSequence() {
        SpellCombo combo = SpellCombo.from(List.of(ClickInput.L, ClickInput.R, ClickInput.L));

        assertEquals(SpellCombo.LRL, combo);
    }

    @Test
    void fromShouldReturnNullWhenInputLengthIsNotExactlyThree() {
        assertNull(SpellCombo.from(List.of(ClickInput.L, ClickInput.R)));
        assertNull(SpellCombo.from(List.of(ClickInput.L, ClickInput.R, ClickInput.L, ClickInput.R)));
    }

    @Test
    void fromShouldReturnNullForNullInput() {
        assertNull(SpellCombo.from(null));
    }

    @Test
    void inputsShouldReturnDefensiveCopy() {
        SpellCombo combo = SpellCombo.LLR;
        ClickInput[] copy = combo.inputs();

        copy[0] = ClickInput.R;

        assertArrayEquals(new ClickInput[] { ClickInput.L, ClickInput.L, ClickInput.R }, combo.inputs());
    }
}
