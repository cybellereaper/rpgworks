package github.cybellereaper.rpgworks.gear;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public final class GearItemStackFactory {

    private static final DecimalFormat STAT_FORMAT = new DecimalFormat("0.###");

    private final BukkitAttributeMapper attributeMapper;

    public GearItemStackFactory() {
        this(new BukkitAttributeMapper());
    }

    public GearItemStackFactory(BukkitAttributeMapper attributeMapper) {
        this.attributeMapper = attributeMapper;
    }

    public ItemStack createStack(GearTemplate template, GearItem gearItem) {
        Material material = template.material();
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.displayName(Component.text(gearItem.displayName(), rarityColor(gearItem.rarity())));
        itemMeta.lore(buildLore(gearItem));

        for (AttributeBonus bonus : gearItem.bonuses()) {
            addModifier(itemMeta, bonus);
        }

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private void addModifier(ItemMeta itemMeta, AttributeBonus bonus) {
        Attribute attribute = attributeMapper.map(bonus.attribute()).orElse(null);
        if (attribute == null) {
            return;
        }

        AttributeModifier.Operation operation = toOperation(bonus.operation());
        AttributeModifier modifier = new AttributeModifier(
                NamespacedKey.minecraft("rpgworks_" + bonus.attribute().name().toLowerCase()),
                bonus.amount(),
                operation,
                EquipmentSlotGroup.ANY
        );
        itemMeta.addAttributeModifier(attribute, modifier);
    }

    private static AttributeModifier.Operation toOperation(AttributeOperation operation) {
        return switch (operation) {
            case ADD_NUMBER -> AttributeModifier.Operation.ADD_NUMBER;
            case ADD_SCALAR -> AttributeModifier.Operation.ADD_SCALAR;
        };
    }

    private static List<Component> buildLore(GearItem item) {
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Item Level: " + item.itemLevel(), NamedTextColor.GRAY));
        lore.add(Component.text("Rarity: " + item.rarity().displayName(), NamedTextColor.DARK_GRAY));

        for (AttributeBonus bonus : item.bonuses()) {
            lore.add(Component.text(
                    "+" + STAT_FORMAT.format(bonus.amount()) + " " + bonus.attribute().key(),
                    NamedTextColor.BLUE
            ));
        }

        return lore;
    }

    private static NamedTextColor rarityColor(ItemRarity rarity) {
        return switch (rarity) {
            case COMMON -> NamedTextColor.WHITE;
            case UNCOMMON -> NamedTextColor.GREEN;
            case RARE -> NamedTextColor.AQUA;
            case EPIC -> NamedTextColor.LIGHT_PURPLE;
            case LEGENDARY -> NamedTextColor.GOLD;
        };
    }
}
