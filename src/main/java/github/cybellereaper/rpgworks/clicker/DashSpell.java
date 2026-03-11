package github.cybellereaper.rpgworks.clicker;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import github.cybellereaper.rpgworks.clicker.spells.Spell;
import github.cybellereaper.rpgworks.clicker.spells.SpellCombo;

public final class DashSpell implements Spell {

    @Override
    public String id() {
        return "dash";
    }

    @Override
    public SpellCombo combo() {
        return SpellCombo.LLL;
    }

    @Override
    public int manaCost() {
        return 20;
    }

    @Override
    public long cooldownMillis() {
        return 3000;
    }

    @Override
    public boolean cast(Player player) {
        Vector direction = player.getLocation().getDirection().normalize().multiply(1.4);
        player.setVelocity(direction);
        player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation(), 20, 0.4, 0.2, 0.4, 0.03);
        player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 1f, 1.4f);
        return true;
    }
}
