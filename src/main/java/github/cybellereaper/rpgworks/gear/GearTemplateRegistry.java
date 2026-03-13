package github.cybellereaper.rpgworks.gear;

import java.util.Collection;
import java.util.Optional;

public interface GearTemplateRegistry {
    Optional<GearTemplate> findById(String id);

    Collection<GearTemplate> all();
}
