package github.cybellereaper.rpgworks.gear;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class GearGrantService {

    private final GearFactory gearFactory;
    private final GearTemplateRegistry templateRegistry;
    private final GearItemStackFactory itemStackFactory;

    public GearGrantService(GearFactory gearFactory, GearTemplateRegistry templateRegistry, GearItemStackFactory itemStackFactory) {
        this.gearFactory = gearFactory;
        this.templateRegistry = templateRegistry;
        this.itemStackFactory = itemStackFactory;
    }

    public ItemStack createItem(String templateId, ItemRarity rarity, int level) {
        GearTemplate template = templateRegistry.findById(templateId)
                .orElseThrow(() -> new IllegalArgumentException("Unknown template: " + templateId));

        GearItem generated = gearFactory.create(template.definition(), rarity, level);
        return itemStackFactory.createStack(template, generated);
    }

    public ItemStack grantItem(Player player, String templateId, ItemRarity rarity, int level) {
        ItemStack created = createItem(templateId, rarity, level);
        player.getInventory().addItem(created);
        return created;
    }
}
