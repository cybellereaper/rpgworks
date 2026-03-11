package github.cybellereaper.rpgworks.classes;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class ClassRegistry {

    private final Map<String, RpgClass> classesById = new HashMap<>();

    public void register(RpgClass rpgClass) {
        classesById.put(rpgClass.id().toLowerCase(), rpgClass);
    }

    public Optional<RpgClass> get(String id) {
        if (id == null) return Optional.empty();
        return Optional.ofNullable(classesById.get(id.toLowerCase()));
    }
}