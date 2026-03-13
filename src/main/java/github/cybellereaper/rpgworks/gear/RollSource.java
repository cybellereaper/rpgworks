package github.cybellereaper.rpgworks.gear;

public interface RollSource {
    int nextInt(int boundExclusive);

    double nextDouble(double minimumInclusive, double maximumExclusive);
}
