package github.cybellereaper.rpgworks.clicker.combos;

import java.util.ArrayList;
import java.util.List;

import github.cybellereaper.rpgworks.clicker.ClickInput;

public final class ComboState {
    private static final int REQUIRED_COMBO_LENGTH = 3;

    private final List<ClickInput> buffer = new ArrayList<>(3);
    private long lastInputAt;

    public void push(ClickInput input, long now, long timeoutMillis) {
        if (hasExpired(now, timeoutMillis)) {
            buffer.clear();
        }

        buffer.add(input);
        lastInputAt = now;
    }

    public List<ClickInput> inputs() {
        return List.copyOf(buffer);
    }

    public boolean isComplete() {
        return buffer.size() >= REQUIRED_COMBO_LENGTH;
    }

    public void clear() {
        buffer.clear();
        lastInputAt = 0;
    }

    public long lastInputAt() {
        return lastInputAt;
    }

    private boolean hasExpired(long now, long timeoutMillis) {
        return now - lastInputAt > timeoutMillis;
    }
}
