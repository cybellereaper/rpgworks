package github.cybellereaper.rpgworks.clicker.spells;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private static final int COMBO_LENGTH = 3;
    private static final Map<List<ClickInput>, SpellCombo> COMBO_BY_INPUTS = Stream.of(values())
            .collect(Collectors.toUnmodifiableMap(
                    combo -> List.of(combo.inputs),
                    combo -> combo
            ));

    private final ClickInput[] inputs;

    SpellCombo(ClickInput... inputs) {
        this.inputs = inputs;
    }

    public ClickInput[] inputs() {
        return inputs.clone();
    }

    public static SpellCombo from(List<ClickInput> inputBuffer) {
        if (inputBuffer == null || inputBuffer.size() != COMBO_LENGTH) {
            return null;
        }

        return COMBO_BY_INPUTS.get(List.copyOf(inputBuffer));
    }
}
