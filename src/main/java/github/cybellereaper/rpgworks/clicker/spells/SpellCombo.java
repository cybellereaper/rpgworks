package github.cybellereaper.rpgworks.clicker.spells;

import github.cybellereaper.rpgworks.clicker.ClickInput;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum SpellCombo {
    LLR(ClickInput.L, ClickInput.L, ClickInput.R),
    LRL(ClickInput.L, ClickInput.R, ClickInput.L),
    LRR(ClickInput.L, ClickInput.R, ClickInput.R),
    RLL(ClickInput.R, ClickInput.L, ClickInput.L),
    RLR(ClickInput.R, ClickInput.L, ClickInput.R),
    RRL(ClickInput.R, ClickInput.R, ClickInput.L),
    RRR(ClickInput.R, ClickInput.R, ClickInput.R),
    LLL(ClickInput.L, ClickInput.L, ClickInput.L);

    public static final int COMBO_LENGTH = 3;

    private static final Map<List<ClickInput>, SpellCombo> LOOKUP = Arrays.stream(values())
            .collect(Collectors.toUnmodifiableMap(SpellCombo::inputs, combo -> combo));

    private final List<ClickInput> inputs;

    SpellCombo(ClickInput... inputs) {
        this.inputs = List.copyOf(Arrays.asList(inputs));
    }

    public List<ClickInput> inputs() {
        return inputs;
    }

    public static SpellCombo from(List<ClickInput> buffer) {
        if (buffer == null || buffer.size() != COMBO_LENGTH) {
            return null;
        }

        return LOOKUP.get(List.copyOf(buffer));
    }
}
