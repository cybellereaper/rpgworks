package github.cybellereaper.rpgworks;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import github.cybellereaper.rpgworks.classes.ClassRegistry;
import github.cybellereaper.rpgworks.classes.Mage;
import github.cybellereaper.rpgworks.clicker.DashSpell;
import github.cybellereaper.rpgworks.clicker.combos.ComboListener;
import github.cybellereaper.rpgworks.clicker.combos.ComboManager;
import github.cybellereaper.rpgworks.clicker.spells.SpellCaster;
import github.cybellereaper.rpgworks.clicker.spells.SpellRegistry;
import github.cybellereaper.rpgworks.services.ManaService;
import github.cybellereaper.rpgworks.services.PlayerClassService;
import github.cybellereaper.rpgworks.gear.DefaultGearTemplateRegistry;
import github.cybellereaper.rpgworks.gear.GearCommand;
import github.cybellereaper.rpgworks.gear.GearGrantService;
import github.cybellereaper.rpgworks.gear.GearItemStackFactory;
import github.cybellereaper.rpgworks.gear.GearTemplateRegistry;
import github.cybellereaper.rpgworks.gear.MinecraftGearFactory;

public final class RPGWorks extends JavaPlugin implements Listener {

    private ComboManager comboManager;
    private SpellRegistry spellRegistry;
    private ManaService manaService;
    private SpellCaster spellCaster;
    private PlayerClassService playerClassService;
    private ClassRegistry classRegistry;
    private Mage mage = new Mage();
    private GearTemplateRegistry gearTemplateRegistry;
    private GearGrantService gearGrantService;

    @Override
    public void onEnable() {
        setupServices();
        registerSpells();
        registerListeners();
        registerCommands();

        getLogger().info("RPGFramework enabled.");

    }

    @Override
    public void onDisable() {
        getLogger().info("RPGFramework disabled.");
    }

    private void setupServices() {
        classRegistry = new ClassRegistry();
        classRegistry.register(mage);
        playerClassService = new PlayerClassService(classRegistry);

        comboManager = new ComboManager(800L);
        spellRegistry = new SpellRegistry();
        manaService = new ManaService();
        spellCaster = new SpellCaster(manaService);

        gearTemplateRegistry = new DefaultGearTemplateRegistry();
        gearGrantService = new GearGrantService(
                new MinecraftGearFactory(),
                gearTemplateRegistry,
                new GearItemStackFactory()
        );

    }

    private void registerSpells() {
        spellRegistry.register(new DashSpell());
        // spellRegistry.register(new SlamSpell());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        playerClassService.setPlayerClass(event.getPlayer(), mage.id());
    }

    private void registerListeners() {
        var pluginManager = getServer().getPluginManager();
        var comboListener = new ComboListener(comboManager, playerClassService, spellCaster);
        pluginManager.registerEvents(comboListener, this);
        pluginManager.registerEvents(this, this);
    }

    private void registerCommands() {
        GearCommand gearCommand = new GearCommand(gearGrantService, gearTemplateRegistry);
        var command = getCommand("gear");
        if (command == null) {
            getLogger().warning("Command 'gear' is missing from plugin.yml");
            return;
        }
        command.setExecutor(gearCommand);
        command.setTabCompleter(gearCommand);
    }

    public ComboManager comboManager() {
        return comboManager;
    }

    public SpellRegistry spellRegistry() {
        return spellRegistry;
    }

    public ManaService manaService() {
        return manaService;
    }

    public SpellCaster spellCaster() {
        return spellCaster;
    }
}
