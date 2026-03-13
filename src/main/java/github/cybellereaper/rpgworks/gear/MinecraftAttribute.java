package github.cybellereaper.rpgworks.gear;

public enum MinecraftAttribute {
    ATTACK_DAMAGE("minecraft:attack_damage"),
    ATTACK_SPEED("minecraft:attack_speed"),
    ATTACK_KNOCKBACK("minecraft:attack_knockback"),
    ARMOR("minecraft:armor"),
    ARMOR_TOUGHNESS("minecraft:armor_toughness"),
    KNOCKBACK_RESISTANCE("minecraft:knockback_resistance"),
    MAX_HEALTH("minecraft:max_health"),
    MOVEMENT_SPEED("minecraft:movement_speed"),
    SAFE_FALL_DISTANCE("minecraft:safe_fall_distance"),
    STEP_HEIGHT("minecraft:step_height"),
    BLOCK_INTERACTION_RANGE("minecraft:block_interaction_range"),
    LUCK("minecraft:luck"),
    OXYGEN_BONUS("minecraft:oxygen_bonus");

    private final String key;

    MinecraftAttribute(String key) {
        this.key = key;
    }

    public String key() {
        return key;
    }
}
