package github.cybellereaper.rpgworks.classes;

import github.cybellereaper.rpgworks.clicker.DashSpell;
import github.cybellereaper.rpgworks.clicker.spells.SpellCombo;

public final class Mage implements RpgClass {

    private final ClassSpellBook spellBook = new ClassSpellBook()
            .bind(SpellCombo.RLR, new DashSpell());
            // .bind(SpellCombo.LLR, new HealSpell())
            // .bind(SpellCombo.LRL, new MeteorSpell())
            // .bind(SpellCombo.RLL, new IceSnakeSpell());

    @Override
    public String id() {
        return "mage";
    }

    @Override
    public String displayName() {
        return "Mage";
    }

    @Override
    public ClassSpellBook spellBook() {
        return spellBook;
    }
}
