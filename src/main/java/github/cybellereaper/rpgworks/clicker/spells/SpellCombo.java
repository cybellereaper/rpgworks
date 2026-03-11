package github.cybellereaper.rpgworks.clicker.spells;

import java.util.Arrays;

import github.cybellereaper.rpgworks.clicker.ClickInput;

public enum SpellCombo {
    LLR(ClickInput.L, ClickInput.L, ClickInput.R),
    LRL(ClickInput.L, ClickInput.R, ClickInput.L),
    LRR(ClickInput.L, ClickInput.R, ClickInput.R),
    RLL(ClickInput.R, ClickInput.L, ClickInput.L),
    RLR(ClickInput.R, ClickInput.L, ClickInput.R),
    RRL(ClickInput.R, ClickInput.R, ClickInput.L),
    RRR(ClickInput.R, ClickInput.R, ClickInput.R),
    LLL(ClickInput.L, ClickInput.L, ClickInput.L);

    private final ClickInput[] inputs;

    SpellCombo(ClickInput... inputs) {
        this.inputs = inputs;
    }

    public ClickInput[] inputs() {
        return inputs;
    }

    public static SpellCombo from(java.util.List<ClickInput> buffer) {
        if (buffer.size() != 3) return null;

        for (SpellCombo combo : values()) {
            if (Arrays.equals(combo.inputs, buffer.toArray(new ClickInput[0]))) {
                return combo;
            }
        }
        return null;
    }
}
