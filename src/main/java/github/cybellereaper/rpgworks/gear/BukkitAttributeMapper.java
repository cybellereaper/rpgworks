package github.cybellereaper.rpgworks.gear;

import org.bukkit.attribute.Attribute;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

public final class BukkitAttributeMapper {

    private static final Map<MinecraftAttribute, Attribute> ATTRIBUTE_MAP = new EnumMap<>(MinecraftAttribute.class);

    static {
        ATTRIBUTE_MAP.put(MinecraftAttribute.ATTACK_DAMAGE, Attribute.ATTACK_DAMAGE);
        ATTRIBUTE_MAP.put(MinecraftAttribute.ATTACK_SPEED, Attribute.ATTACK_SPEED);
        ATTRIBUTE_MAP.put(MinecraftAttribute.ATTACK_KNOCKBACK, Attribute.ATTACK_KNOCKBACK);
        ATTRIBUTE_MAP.put(MinecraftAttribute.ARMOR, Attribute.ARMOR);
        ATTRIBUTE_MAP.put(MinecraftAttribute.ARMOR_TOUGHNESS, Attribute.ARMOR_TOUGHNESS);
        ATTRIBUTE_MAP.put(MinecraftAttribute.KNOCKBACK_RESISTANCE, Attribute.KNOCKBACK_RESISTANCE);
        ATTRIBUTE_MAP.put(MinecraftAttribute.MAX_HEALTH, Attribute.MAX_HEALTH);
        ATTRIBUTE_MAP.put(MinecraftAttribute.MOVEMENT_SPEED, Attribute.MOVEMENT_SPEED);
        ATTRIBUTE_MAP.put(MinecraftAttribute.SAFE_FALL_DISTANCE, Attribute.SAFE_FALL_DISTANCE);
        ATTRIBUTE_MAP.put(MinecraftAttribute.STEP_HEIGHT, Attribute.STEP_HEIGHT);
        ATTRIBUTE_MAP.put(MinecraftAttribute.BLOCK_INTERACTION_RANGE, Attribute.BLOCK_INTERACTION_RANGE);
        ATTRIBUTE_MAP.put(MinecraftAttribute.LUCK, Attribute.LUCK);
        ATTRIBUTE_MAP.put(MinecraftAttribute.OXYGEN_BONUS, Attribute.OXYGEN_BONUS);
    }

    public Optional<Attribute> map(MinecraftAttribute attribute) {
        return Optional.ofNullable(ATTRIBUTE_MAP.get(attribute));
    }
}
