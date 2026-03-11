package github.cybellereaper.rpgworks.clicker;

import java.util.ArrayList;
import java.util.List;

public final class ComboState {
    private final List<ClickInput> buffer = new ArrayList<>(3);
    private long lastInputAt;

    public void push(ClickInput input, long now, long timeoutMillis) {
        if (now - lastInputAt > timeoutMillis) {
            buffer.clear();
        }
        buffer.add(input);
        lastInputAt = now;
    }

    public List<ClickInput> buffer() {
        return buffer;
    }

    public boolean isComplete() {
        return buffer.size() >= 3;
    }

    public void clear() {
        buffer.clear();
    }

    public long lastInputAt() {
        return lastInputAt;
    }
}
