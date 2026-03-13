package github.cybellereaper.rpgworks.gear;

import java.util.Map;

public record GearDefinition(
        String itemTypeName,
        GearSlot slot,
        Map<MinecraftAttribute, Double> baseAttributes
) {
    public GearDefinition {
        if (itemTypeName == null || itemTypeName.isBlank()) {
            throw new IllegalArgumentException("Item type name cannot be blank");
        }
        if (slot == null) {
            throw new IllegalArgumentException("Slot cannot be null");
        }
        if (baseAttributes == null || baseAttributes.isEmpty()) {
            throw new IllegalArgumentException("Base attributes cannot be empty");
        }
        baseAttributes = Map.copyOf(baseAttributes);
    }
}
