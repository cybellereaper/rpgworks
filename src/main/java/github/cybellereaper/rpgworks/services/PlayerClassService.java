package github.cybellereaper.rpgworks.services;

import org.bukkit.entity.Player;

import github.cybellereaper.rpgworks.classes.ClassRegistry;
import github.cybellereaper.rpgworks.classes.RpgClass;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public final class PlayerClassService {

    private final Map<UUID, String> selectedClasses = new HashMap<>();
    private final ClassRegistry classRegistry;

    public PlayerClassService(ClassRegistry classRegistry) {
        this.classRegistry = classRegistry;
    }

    public void setPlayerClass(Player player, String classId) {
        selectedClasses.put(player.getUniqueId(), classId.toLowerCase());
    }

    public Optional<RpgClass> getPlayerClass(Player player) {
        String id = selectedClasses.get(player.getUniqueId());
        return classRegistry.get(id);
    }

    public boolean hasClass(Player player) {
        return selectedClasses.containsKey(player.getUniqueId());
    }
}