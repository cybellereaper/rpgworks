package github.cybellereaper.rpgworks.gear;

import java.util.List;
import java.util.UUID;

public record GearItem(
        UUID id,
        String displayName,
        String itemTypeName,
        GearSlot slot,
        int itemLevel,
        ItemRarity rarity,
        List<AttributeBonus> bonuses
) {
    public GearItem {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        if (displayName == null || displayName.isBlank()) {
            throw new IllegalArgumentException("Display name cannot be blank");
        }
        if (itemTypeName == null || itemTypeName.isBlank()) {
            throw new IllegalArgumentException("Item type name cannot be blank");
        }
        if (slot == null || rarity == null) {
            throw new IllegalArgumentException("Slot and rarity are required");
        }
        if (itemLevel < 1) {
            throw new IllegalArgumentException("Item level must be greater than 0");
        }
        bonuses = List.copyOf(bonuses);
    }
}
