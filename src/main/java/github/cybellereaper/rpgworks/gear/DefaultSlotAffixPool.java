package github.cybellereaper.rpgworks.gear;

import java.util.List;
import java.util.Map;

public final class DefaultSlotAffixPool implements SlotAffixPool {

    private static final Map<GearSlot, List<AttributeBonus>> SLOT_AFFIXES = Map.of(
            GearSlot.WEAPON, List.of(
                    new AttributeBonus(MinecraftAttribute.ATTACK_DAMAGE, 2.0, AttributeOperation.ADD_NUMBER),
                    new AttributeBonus(MinecraftAttribute.ATTACK_SPEED, 0.06, AttributeOperation.ADD_SCALAR),
                    new AttributeBonus(MinecraftAttribute.ATTACK_KNOCKBACK, 0.3, AttributeOperation.ADD_NUMBER)
            ),
            GearSlot.HELMET, List.of(
                    new AttributeBonus(MinecraftAttribute.MAX_HEALTH, 1.5, AttributeOperation.ADD_NUMBER),
                    new AttributeBonus(MinecraftAttribute.ARMOR, 0.8, AttributeOperation.ADD_NUMBER),
                    new AttributeBonus(MinecraftAttribute.OXYGEN_BONUS, 0.5, AttributeOperation.ADD_NUMBER)
            ),
            GearSlot.CHESTPLATE, List.of(
                    new AttributeBonus(MinecraftAttribute.ARMOR, 1.6, AttributeOperation.ADD_NUMBER),
                    new AttributeBonus(MinecraftAttribute.ARMOR_TOUGHNESS, 0.8, AttributeOperation.ADD_NUMBER),
                    new AttributeBonus(MinecraftAttribute.KNOCKBACK_RESISTANCE, 0.02, AttributeOperation.ADD_SCALAR)
            ),
            GearSlot.LEGGINGS, List.of(
                    new AttributeBonus(MinecraftAttribute.ARMOR, 1.2, AttributeOperation.ADD_NUMBER),
                    new AttributeBonus(MinecraftAttribute.MOVEMENT_SPEED, 0.015, AttributeOperation.ADD_SCALAR),
                    new AttributeBonus(MinecraftAttribute.STEP_HEIGHT, 0.10, AttributeOperation.ADD_NUMBER)
            ),
            GearSlot.BOOTS, List.of(
                    new AttributeBonus(MinecraftAttribute.ARMOR, 0.9, AttributeOperation.ADD_NUMBER),
                    new AttributeBonus(MinecraftAttribute.MOVEMENT_SPEED, 0.02, AttributeOperation.ADD_SCALAR),
                    new AttributeBonus(MinecraftAttribute.SAFE_FALL_DISTANCE, 1.2, AttributeOperation.ADD_NUMBER)
            ),
            GearSlot.OFF_HAND, List.of(
                    new AttributeBonus(MinecraftAttribute.BLOCK_INTERACTION_RANGE, 0.35, AttributeOperation.ADD_NUMBER),
                    new AttributeBonus(MinecraftAttribute.ARMOR, 0.8, AttributeOperation.ADD_NUMBER),
                    new AttributeBonus(MinecraftAttribute.KNOCKBACK_RESISTANCE, 0.02, AttributeOperation.ADD_SCALAR)
            ),
            GearSlot.RING, List.of(
                    new AttributeBonus(MinecraftAttribute.LUCK, 0.8, AttributeOperation.ADD_NUMBER),
                    new AttributeBonus(MinecraftAttribute.MOVEMENT_SPEED, 0.012, AttributeOperation.ADD_SCALAR),
                    new AttributeBonus(MinecraftAttribute.MAX_HEALTH, 1.2, AttributeOperation.ADD_NUMBER)
            ),
            GearSlot.AMULET, List.of(
                    new AttributeBonus(MinecraftAttribute.MAX_HEALTH, 2.0, AttributeOperation.ADD_NUMBER),
                    new AttributeBonus(MinecraftAttribute.LUCK, 0.5, AttributeOperation.ADD_NUMBER),
                    new AttributeBonus(MinecraftAttribute.ATTACK_DAMAGE, 0.8, AttributeOperation.ADD_NUMBER)
            )
    );

    @Override
    public List<AttributeBonus> affixesFor(GearSlot slot) {
        return SLOT_AFFIXES.getOrDefault(slot, List.of());
    }
}
