package github.cybellereaper.rpgworks.gear;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public final class GearCommand implements CommandExecutor, TabCompleter {

    private final GearGrantService gearGrantService;
    private final GearTemplateRegistry templateRegistry;

    public GearCommand(GearGrantService gearGrantService, GearTemplateRegistry templateRegistry) {
        this.gearGrantService = gearGrantService;
        this.templateRegistry = templateRegistry;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length != 4) {
            sender.sendMessage("Usage: /gear <player> <template> <rarity> <level>");
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            sender.sendMessage("Player not found: " + args[0]);
            return true;
        }

        ItemRarity rarity;
        try {
            rarity = ItemRarity.valueOf(args[2].toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ignored) {
            sender.sendMessage("Invalid rarity. Allowed: " + Arrays.toString(ItemRarity.values()));
            return true;
        }

        int level;
        try {
            level = Integer.parseInt(args[3]);
        } catch (NumberFormatException ignored) {
            sender.sendMessage("Level must be a number.");
            return true;
        }

        if (level < 1) {
            sender.sendMessage("Level must be greater than 0.");
            return true;
        }

        try {
            gearGrantService.grantItem(target, args[1], rarity, level);
            sender.sendMessage("Granted " + rarity.displayName() + " " + args[1] + " (lvl " + level + ") to " + target.getName());
        } catch (IllegalArgumentException error) {
            sender.sendMessage(error.getMessage());
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String alias,
            @NotNull String[] args
    ) {
        if (args.length == 1) {
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(name -> name.toLowerCase(Locale.ROOT).startsWith(args[0].toLowerCase(Locale.ROOT)))
                    .collect(Collectors.toList());
        }

        if (args.length == 2) {
            String templatePrefix = args[1].toLowerCase(Locale.ROOT);
            return templateRegistry.all().stream()
                    .map(GearTemplate::id)
                    .filter(id -> id.startsWith(templatePrefix))
                    .toList();
        }

        if (args.length == 3) {
            String rarityPrefix = args[2].toLowerCase(Locale.ROOT);
            return Arrays.stream(ItemRarity.values())
                    .map(Enum::name)
                    .map(name -> name.toLowerCase(Locale.ROOT))
                    .filter(name -> name.startsWith(rarityPrefix))
                    .toList();
        }

        return List.of();
    }
}
