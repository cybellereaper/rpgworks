package github.cybellereaper.rpgworks;

import github.cybellereaper.rpgworks.classes.ClassRegistry;
import github.cybellereaper.rpgworks.classes.Mage;
import github.cybellereaper.rpgworks.clicker.combos.ComboListener;
import github.cybellereaper.rpgworks.clicker.combos.ComboManager;
import github.cybellereaper.rpgworks.clicker.spells.SpellCaster;
import github.cybellereaper.rpgworks.services.ManaService;
import github.cybellereaper.rpgworks.services.PlayerClassService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class RPGWorks extends JavaPlugin implements Listener {

    private ComboManager comboManager;
    private ManaService manaService;
    private SpellCaster spellCaster;
    private PlayerClassService playerClassService;
    private final Mage mage = new Mage();

    @Override
    public void onEnable() {
        setupServices();
        registerListeners();
        getLogger().info("RPGFramework enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("RPGFramework disabled.");
    }

    private void setupServices() {
        ClassRegistry classRegistry = new ClassRegistry();
        classRegistry.register(mage);

        playerClassService = new PlayerClassService(classRegistry);
        comboManager = new ComboManager(800L);
        manaService = new ManaService();
        spellCaster = new SpellCaster(manaService);
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

    public ManaService manaService() {
        return manaService;
    }

    public SpellCaster spellCaster() {
        return spellCaster;
    }
}
