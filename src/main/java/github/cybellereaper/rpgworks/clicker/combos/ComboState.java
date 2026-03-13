package github.cybellereaper.rpgworks.clicker.combos;

import github.cybellereaper.rpgworks.clicker.ClickInput;
import github.cybellereaper.rpgworks.clicker.spells.SpellCombo;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public final class ComboState {
    private final Deque<ClickInput> buffer = new ArrayDeque<>(SpellCombo.COMBO_LENGTH);
    private long lastInputAt;

    public void push(ClickInput input, long now, long timeoutMillis) {
        if (now - lastInputAt > timeoutMillis) {
            clear();
        }

        buffer.addLast(input);
        while (buffer.size() > SpellCombo.COMBO_LENGTH) {
            buffer.removeFirst();
        }
        lastInputAt = now;
    }

    public List<ClickInput> buffer() {
        return List.copyOf(buffer);
    }

    public boolean isComplete() {
        return buffer.size() == SpellCombo.COMBO_LENGTH;
    }

    public void clear() {
        buffer.clear();
        lastInputAt = 0L;
    }

    public long lastInputAt() {
        return lastInputAt;
    }
}
