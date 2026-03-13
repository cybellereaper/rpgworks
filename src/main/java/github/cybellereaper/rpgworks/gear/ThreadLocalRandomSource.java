package github.cybellereaper.rpgworks.gear;

import java.util.concurrent.ThreadLocalRandom;

public final class ThreadLocalRandomSource implements RollSource {
    @Override
    public int nextInt(int boundExclusive) {
        return ThreadLocalRandom.current().nextInt(boundExclusive);
    }

    @Override
    public double nextDouble(double minimumInclusive, double maximumExclusive) {
        return ThreadLocalRandom.current().nextDouble(minimumInclusive, maximumExclusive);
    }
}
