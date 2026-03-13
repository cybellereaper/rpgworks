package github.cybellereaper.rpgworks.gear;

public interface GearFactory {
    GearItem create(GearDefinition definition, ItemRarity rarity, int itemLevel);
}
