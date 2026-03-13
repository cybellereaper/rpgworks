package github.cybellereaper.rpgworks.gear;

import org.bukkit.Material;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public final class DefaultGearTemplateRegistry implements GearTemplateRegistry {

    private final Map<String, GearTemplate> templates;

    public DefaultGearTemplateRegistry() {
        this.templates = Map.of(
                "iron_sword", new GearTemplate(
                        "iron_sword",
                        Material.IRON_SWORD,
                        new GearDefinition("Iron Sword", GearSlot.WEAPON, Map.of(
                                MinecraftAttribute.ATTACK_DAMAGE, 6.0,
                                MinecraftAttribute.ATTACK_SPEED, 1.6
                        ))
                ),
                "steel_chestplate", new GearTemplate(
                        "steel_chestplate",
                        Material.IRON_CHESTPLATE,
                        new GearDefinition("Steel Chestplate", GearSlot.CHESTPLATE, Map.of(
                                MinecraftAttribute.ARMOR, 8.0,
                                MinecraftAttribute.ARMOR_TOUGHNESS, 2.0
                        ))
                ),
                "swift_boots", new GearTemplate(
                        "swift_boots",
                        Material.IRON_BOOTS,
                        new GearDefinition("Swift Boots", GearSlot.BOOTS, Map.of(
                                MinecraftAttribute.ARMOR, 2.0,
                                MinecraftAttribute.MOVEMENT_SPEED, 0.03,
                                MinecraftAttribute.SAFE_FALL_DISTANCE, 1.0
                        ))
                ),
                "guardian_helm", new GearTemplate(
                        "guardian_helm",
                        Material.IRON_HELMET,
                        new GearDefinition("Guardian Helm", GearSlot.HELMET, Map.of(
                                MinecraftAttribute.ARMOR, 3.0,
                                MinecraftAttribute.MAX_HEALTH, 4.0,
                                MinecraftAttribute.OXYGEN_BONUS, 1.0
                        ))
                )
        );
    }

    @Override
    public Optional<GearTemplate> findById(String id) {
        if (id == null || id.isBlank()) {
            return Optional.empty();
        }
        return Optional.ofNullable(templates.get(id.toLowerCase(Locale.ROOT)));
    }

    @Override
    public Collection<GearTemplate> all() {
        return templates.values();
    }
}
