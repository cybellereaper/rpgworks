package github.cybellereaper.rpgworks.gear;

import java.util.List;

public interface SlotAffixPool {
    List<AttributeBonus> affixesFor(GearSlot slot);
}
