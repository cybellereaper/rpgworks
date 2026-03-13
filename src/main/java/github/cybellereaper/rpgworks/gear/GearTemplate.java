package github.cybellereaper.rpgworks.gear;

import org.bukkit.Material;

public record GearTemplate(
        String id,
        Material material,
        GearDefinition definition
) {
    public GearTemplate {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Template id cannot be blank");
        }
        if (material == null || definition == null) {
            throw new IllegalArgumentException("Material and definition are required");
        }
    }
}
