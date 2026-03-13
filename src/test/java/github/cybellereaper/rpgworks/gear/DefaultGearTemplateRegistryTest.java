package github.cybellereaper.rpgworks.gear;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultGearTemplateRegistryTest {

    @Test
    void findByIdIsCaseInsensitive() {
        DefaultGearTemplateRegistry registry = new DefaultGearTemplateRegistry();

        assertTrue(registry.findById("IRON_SWORD").isPresent());
        assertTrue(registry.findById("iron_sword").isPresent());
    }

    @Test
    void findByIdReturnsEmptyForUnknownId() {
        DefaultGearTemplateRegistry registry = new DefaultGearTemplateRegistry();

        assertTrue(registry.findById("unknown_template").isEmpty());
    }
}
