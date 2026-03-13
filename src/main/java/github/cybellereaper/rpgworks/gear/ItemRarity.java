package github.cybellereaper.rpgworks.gear;

public enum ItemRarity {
    COMMON("Common", 1.00, 0),
    UNCOMMON("Uncommon", 1.08, 1),
    RARE("Rare", 1.20, 2),
    EPIC("Epic", 1.35, 3),
    LEGENDARY("Legendary", 1.55, 4);

    private final String displayName;
    private final double statMultiplier;
    private final int affixCount;

    ItemRarity(String displayName, double statMultiplier, int affixCount) {
        this.displayName = displayName;
        this.statMultiplier = statMultiplier;
        this.affixCount = affixCount;
    }

    public String displayName() {
        return displayName;
    }

    public double statMultiplier() {
        return statMultiplier;
    }

    public int affixCount() {
        return affixCount;
    }
}
