package github.cybellereaper.rpgworks.gear;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class MinecraftGearFactory implements GearFactory {

    private static final double LEVEL_SCALING_STEP = 0.045;
    private static final double QUALITY_MIN = 0.93;
    private static final double QUALITY_MAX = 1.08;

    private final RollSource rollSource;
    private final SlotAffixPool slotAffixPool;

    public MinecraftGearFactory() {
        this(new ThreadLocalRandomSource(), new DefaultSlotAffixPool());
    }

    public MinecraftGearFactory(RollSource rollSource, SlotAffixPool slotAffixPool) {
        this.rollSource = rollSource;
        this.slotAffixPool = slotAffixPool;
    }

    @Override
    public GearItem create(GearDefinition definition, ItemRarity rarity, int itemLevel) {
        validate(definition, rarity, itemLevel);

        double levelMultiplier = 1.0 + ((itemLevel - 1) * LEVEL_SCALING_STEP);
        double qualityMultiplier = rollSource.nextDouble(QUALITY_MIN, QUALITY_MAX);
        double totalMultiplier = rarity.statMultiplier() * levelMultiplier * qualityMultiplier;

        Map<MinecraftAttribute, AttributeBonus> totals = new LinkedHashMap<>();
        addBaseBonuses(definition, totalMultiplier, totals);
        addAffixBonuses(definition.slot(), rarity.affixCount(), totalMultiplier, totals);

        String displayName = "%s %s".formatted(rarity.displayName(), definition.itemTypeName());
        return new GearItem(
                UUID.randomUUID(),
                displayName,
                definition.itemTypeName(),
                definition.slot(),
                itemLevel,
                rarity,
                List.copyOf(totals.values())
        );
    }

    private static void validate(GearDefinition definition, ItemRarity rarity, int itemLevel) {
        if (definition == null) {
            throw new IllegalArgumentException("Gear definition is required");
        }
        if (rarity == null) {
            throw new IllegalArgumentException("Rarity is required");
        }
        if (itemLevel < 1) {
            throw new IllegalArgumentException("Item level must be greater than 0");
        }
    }

    private static void addBaseBonuses(
            GearDefinition definition,
            double totalMultiplier,
            Map<MinecraftAttribute, AttributeBonus> totals
    ) {
        for (Map.Entry<MinecraftAttribute, Double> entry : definition.baseAttributes().entrySet()) {
            double scaledAmount = entry.getValue() * totalMultiplier;
            mergeBonus(totals, new AttributeBonus(entry.getKey(), scaledAmount, AttributeOperation.ADD_NUMBER));
        }
    }

    private void addAffixBonuses(
            GearSlot slot,
            int affixCount,
            double totalMultiplier,
            Map<MinecraftAttribute, AttributeBonus> totals
    ) {
        List<AttributeBonus> pool = new ArrayList<>(slotAffixPool.affixesFor(slot));
        if (pool.isEmpty()) {
            return;
        }

        int selectedAffixes = Math.min(affixCount, pool.size());

        for (int index = 0; index < selectedAffixes; index++) {
            int pickedIndex = rollSource.nextInt(pool.size());
            AttributeBonus baseAffix = pool.remove(pickedIndex);
            double scaledAmount = baseAffix.amount() * totalMultiplier;
            mergeBonus(totals, new AttributeBonus(baseAffix.attribute(), scaledAmount, baseAffix.operation()));
        }
    }

    private static void mergeBonus(Map<MinecraftAttribute, AttributeBonus> totals, AttributeBonus bonus) {
        AttributeBonus existing = totals.get(bonus.attribute());
        if (existing == null) {
            totals.put(bonus.attribute(), bonus);
            return;
        }

        totals.put(
                bonus.attribute(),
                new AttributeBonus(
                        bonus.attribute(),
                        existing.amount() + bonus.amount(),
                        existing.operation()
                )
        );
    }
}
