package github.cybellereaper.rpgworks.clicker.combos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import github.cybellereaper.rpgworks.clicker.ClickInput;

class ComboStateTest {

    @Test
    void pushShouldResetBufferWhenTimeoutExpires() {
        ComboState state = new ComboState();

        state.push(ClickInput.L, 1000, 500);
        state.push(ClickInput.R, 1300, 500);
        state.push(ClickInput.L, 2001, 500);

        assertEquals(List.of(ClickInput.L), state.inputs());
        assertEquals(2001, state.lastInputAt());
    }

    @Test
    void clearShouldResetBufferAndLastInputTimestamp() {
        ComboState state = new ComboState();

        state.push(ClickInput.L, 500, 500);
        state.clear();

        assertTrue(state.inputs().isEmpty());
        assertEquals(0, state.lastInputAt());
    }

    @Test
    void inputsShouldReturnImmutableCopy() {
        ComboState state = new ComboState();
        state.push(ClickInput.R, 100, 500);

        List<ClickInput> snapshot = state.inputs();

        assertThrows(UnsupportedOperationException.class, () -> snapshot.add(ClickInput.L));
        assertEquals(List.of(ClickInput.R), state.inputs());
        assertFalse(state.isComplete());
    }

    @Test
    void isCompleteShouldBeTrueOnlyWhenThreeOrMoreInputsArePresent() {
        ComboState state = new ComboState();

        state.push(ClickInput.L, 0, 500);
        state.push(ClickInput.R, 100, 500);
        assertFalse(state.isComplete());

        state.push(ClickInput.L, 200, 500);
        assertTrue(state.isComplete());
    }
}
