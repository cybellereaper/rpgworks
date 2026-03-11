package github.cybellereaper.rpgworks;

import org.bukkit.plugin.java.JavaPlugin;

import github.cybellereaper.rpgworks.clicker.ComboListener;
import github.cybellereaper.rpgworks.clicker.ComboManager;
import github.cybellereaper.rpgworks.clicker.DashSpell;
import github.cybellereaper.rpgworks.clicker.ManaService;
import github.cybellereaper.rpgworks.clicker.SpellCaster;
import github.cybellereaper.rpgworks.clicker.SpellRegistry;


public final class RPGWorks extends JavaPlugin {

    private ComboManager comboManager;
    private SpellRegistry spellRegistry;
    private ManaService manaService;
    private SpellCaster spellCaster;

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
        comboManager = new ComboManager(800L);
        spellRegistry = new SpellRegistry();
        manaService = new ManaService();
        spellCaster = new SpellCaster(manaService);
    }

    private void registerSpells() {
        spellRegistry.register(new DashSpell());
        // spellRegistry.register(new SlamSpell());
    }

    private void registerListeners() {
        var pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(
                new ComboListener(comboManager, spellRegistry, spellCaster),
                this
        );
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
