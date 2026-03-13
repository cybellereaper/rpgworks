package github.cybellereaper.rpgworks.gear;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MinecraftGearFactoryTest {

    @Test
    void createScalesBaseAttributesByLevelRarityAndQuality() {
        GearDefinition ironSword = new GearDefinition(
                "Sword",
                GearSlot.WEAPON,
                Map.of(MinecraftAttribute.ATTACK_DAMAGE, 5.0)
        );
        MinecraftGearFactory factory = new MinecraftGearFactory(
                new FixedRollSource(0, 1.0),
                new DefaultSlotAffixPool()
        );

        GearItem item = factory.create(ironSword, ItemRarity.COMMON, 1);

        assertEquals("Common Sword", item.displayName());
        assertEquals(5.0, findAmount(item, MinecraftAttribute.ATTACK_DAMAGE), 0.0001);
    }

    @Test
    void createMergesAffixAndBaseAttributeWhenTheyShareSameAttribute() {
        GearDefinition steelSword = new GearDefinition(
                "Sword",
                GearSlot.WEAPON,
                Map.of(MinecraftAttribute.ATTACK_DAMAGE, 4.0)
        );

        SlotAffixPool onlyDamageAffix = slot -> List.of(
                new AttributeBonus(MinecraftAttribute.ATTACK_DAMAGE, 2.5, AttributeOperation.ADD_NUMBER)
        );

        MinecraftGearFactory factory = new MinecraftGearFactory(
                new FixedRollSource(0, 1.0),
                onlyDamageAffix
        );

        GearItem item = factory.create(steelSword, ItemRarity.UNCOMMON, 1);

        assertEquals(7.02, findAmount(item, MinecraftAttribute.ATTACK_DAMAGE), 0.0001);
    }

    @Test
    void createRejectsInvalidItemLevel() {
        GearDefinition ring = new GearDefinition(
                "Ring",
                GearSlot.RING,
                Map.of(MinecraftAttribute.LUCK, 1.0)
        );
        MinecraftGearFactory factory = new MinecraftGearFactory();

        IllegalArgumentException error = assertThrows(
                IllegalArgumentException.class,
                () -> factory.create(ring, ItemRarity.RARE, 0)
        );

        assertTrue(error.getMessage().contains("Item level"));
    }

    private static double findAmount(GearItem item, MinecraftAttribute attribute) {
        return item.bonuses().stream()
                .filter(bonus -> bonus.attribute() == attribute)
                .findFirst()
                .orElseThrow()
                .amount();
    }

    private record FixedRollSource(int fixedInt, double fixedDouble) implements RollSource {
        @Override
        public int nextInt(int boundExclusive) {
            if (boundExclusive <= 0) {
                return 0;
            }
            return Math.min(fixedInt, boundExclusive - 1);
        }

        @Override
        public double nextDouble(double minimumInclusive, double maximumExclusive) {
            return fixedDouble;
        }
    }
}
