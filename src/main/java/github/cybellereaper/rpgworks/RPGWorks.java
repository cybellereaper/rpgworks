package github.cybellereaper.rpgworks;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import github.cybellereaper.rpgworks.classes.ClassRegistry;
import github.cybellereaper.rpgworks.classes.Mage;
import github.cybellereaper.rpgworks.clicker.ComboListener;
import github.cybellereaper.rpgworks.clicker.ComboManager;
import github.cybellereaper.rpgworks.clicker.DashSpell;
import github.cybellereaper.rpgworks.clicker.ManaService;
import github.cybellereaper.rpgworks.clicker.SpellCaster;
import github.cybellereaper.rpgworks.clicker.SpellRegistry;
import github.cybellereaper.rpgworks.services.PlayerClassService;

public final class RPGWorks extends JavaPlugin implements Listener {

    private ComboManager comboManager;
    private SpellRegistry spellRegistry;
    private ManaService manaService;
    private SpellCaster spellCaster;
    private PlayerClassService playerClassService;
    private ClassRegistry classRegistry;
    private Mage mage = new Mage();

    @Override
    public void onEnable() {
        setupServices();
        registerSpells();
        registerListeners();

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
