package github.cybellereaper.rpgworks.clicker;
import java.util.HashMap;
import java.util.Map;

public final class SpellRegistry {
    private final Map<SpellCombo, Spell> spells = new HashMap<>();

    public void register(Spell spell) {
        spells.put(spell.combo(), spell);
    }

    public Spell get(SpellCombo combo) {
        return spells.get(combo);
    }
}