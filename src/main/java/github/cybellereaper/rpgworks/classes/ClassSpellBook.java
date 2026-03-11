package github.cybellereaper.rpgworks.classes;

import java.util.EnumMap;
import java.util.Map;

import github.cybellereaper.rpgworks.clicker.*;

public final class ClassSpellBook {

    private final Map<SpellCombo, Spell> spells = new EnumMap<>(SpellCombo.class);

    public ClassSpellBook bind(SpellCombo combo, Spell spell) {
        spells.put(combo, spell);
        return this;
    }

    public Spell get(SpellCombo combo) {
        return spells.get(combo);
    }

    public Map<SpellCombo, Spell> asMap() {
        return Map.copyOf(spells);
    }
}
