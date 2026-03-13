package github.cybellereaper.rpgworks.gear;

public record AttributeBonus(
        MinecraftAttribute attribute,
        double amount,
        AttributeOperation operation
) {
    public AttributeBonus {
        if (amount < 0) {
            throw new IllegalArgumentException("Attribute bonus amount cannot be negative");
        }
    }
}
