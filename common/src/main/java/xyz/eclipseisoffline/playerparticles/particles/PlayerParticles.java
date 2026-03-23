package xyz.eclipseisoffline.playerparticles.particles;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SculkChargeParticleOptions;
import net.minecraft.core.particles.ShriekParticleOption;
import net.minecraft.core.particles.SimpleParticleType;
import xyz.eclipseisoffline.playerparticles.ParticleRegistry;
import xyz.eclipseisoffline.playerparticles.ParticleSlot;
import xyz.eclipseisoffline.playerparticles.particles.color.ColorParticle;
import xyz.eclipseisoffline.playerparticles.particles.color.FlagParticle;
import xyz.eclipseisoffline.playerparticles.particles.color.FlagPotionParticle;
import xyz.eclipseisoffline.playerparticles.particles.color.PotionParticle;
import xyz.eclipseisoffline.playerparticles.particles.color.TintedLeavesParticle;
import xyz.eclipseisoffline.playerparticles.particles.item.ItemParticle;
import xyz.eclipseisoffline.playerparticles.particles.simple.AmbientPlusParticle;
import xyz.eclipseisoffline.playerparticles.particles.simple.PaleOakLeavesParticle;
import xyz.eclipseisoffline.playerparticles.particles.simple.SimplePlayerParticle;
import xyz.eclipseisoffline.playerparticles.particles.simple.SonicBoomParticle;

public enum PlayerParticles {
    NOTE(new SimplePlayerParticle.Builder(ParticleTypes.NOTE)
            .withSlot(ParticleSlot.ABOVE, 1, 1.0)
            .withSlot(ParticleSlot.AROUND)
            .withInterval(20)
            .build()),
    CHERRY(new SimplePlayerParticle(ParticleTypes.CHERRY_LEAVES, ParticleSlot.BELOW)),
    SOUL(new SimplePlayerParticle.Builder(ParticleTypes.SOUL)
            .withSlot(ParticleSlot.AROUND, 2, 0.1)
            .withSlot(ParticleSlot.BELOW, 5, 0.01)
            .build()),
    SCULK_SOUL(new SimplePlayerParticle.Builder(ParticleTypes.SCULK_SOUL)
            .withSlot(ParticleSlot.AROUND, 2, 0.1)
            .withSlot(ParticleSlot.BELOW, 5, 0.01)
            .build()),
    END(new SimplePlayerParticle(ParticleTypes.PORTAL, ParticleSlot.AROUND)),
    NECTAR(new SimplePlayerParticle.Builder(ParticleTypes.FALLING_NECTAR)
            .withSlot(ParticleSlot.AROUND, 1, 0.01)
            .withSlot(ParticleSlot.BELOW)
            .build()),
    CLOUD(new SimplePlayerParticle.Builder(ParticleTypes.CLOUD)
            .withSlot(ParticleSlot.AROUND, 1, 0.01)
            .withSlot(ParticleSlot.BELOW, 1, 0.01)
            .withSlot(ParticleSlot.ABOVE, 1, 0.01)
            .withInterval(10)
            .build()),
    END_ROD(new SimplePlayerParticle.Builder(ParticleTypes.END_ROD)
            .withSlot(ParticleSlot.AROUND, 2, 0.05)
            .withSlot(ParticleSlot.BELOW, 1, 0.05)
            .withSlot(ParticleSlot.ABOVE, 1, 0.05)
            .build()),
    COMPOSTER(new AmbientPlusParticle(ParticleTypes.COMPOSTER)),
    GLOW(new AmbientPlusParticle(ParticleTypes.GLOW)),
    ELECTRIC_SPARK(new AmbientPlusParticle(ParticleTypes.ELECTRIC_SPARK)),
    HEART(new SimplePlayerParticle.Builder(ParticleTypes.HEART)
            .withSlot(ParticleSlot.AROUND, 4, 0.25)
            .withSlot(ParticleSlot.ABOVE, 1, 0.25)
            .withInterval(15)
            .build()),
    DOLPHIN(new SimplePlayerParticle(ParticleTypes.DOLPHIN, ParticleSlot.AROUND, ParticleSlot.BELOW)),
    SPORE_BLOSSOM(new SimplePlayerParticle(ParticleTypes.SPORE_BLOSSOM_AIR, ParticleSlot.AROUND)),
    CRIMSON(new SimplePlayerParticle(ParticleTypes.CRIMSON_SPORE, ParticleSlot.AROUND, 3, 0.15)),
    WARPED(new SimplePlayerParticle(ParticleTypes.WARPED_SPORE, ParticleSlot.AROUND, 3, 0.15)),
    ASH(new SimplePlayerParticle(ParticleTypes.ASH, ParticleSlot.AROUND, 3, 0.15)),
    ENCHANT(new SimplePlayerParticle(ParticleTypes.ENCHANT, ParticleSlot.AROUND)),
    INFESTED(new SimplePlayerParticle.Builder(ParticleTypes.INFESTED)
            .withSlot(ParticleSlot.AROUND, 1, 1.0)
            .withSlot(ParticleSlot.ABOVE)
            .build()),
    SMALL_GUST(new SimplePlayerParticle.Builder(ParticleTypes.SMALL_GUST)
            .withSlot(ParticleSlot.AROUND)
            .withSlot(ParticleSlot.ABOVE, 1, 1.0)
            .withInterval(20)
            .build()),
    RED_OMEN(new SimplePlayerParticle(ParticleTypes.RAID_OMEN, ParticleSlot.AROUND, 1, 1.0)),
    BLUE_OMEN(new SimplePlayerParticle(ParticleTypes.TRIAL_OMEN, ParticleSlot.AROUND, 1, 1.0)),
    OMINOUS_SPAWNING(new SimplePlayerParticle(ParticleTypes.OMINOUS_SPAWNING, ParticleSlot.AROUND, 3, 0.5)),
    RED_BAR(new SimplePlayerParticle.Builder(ParticleTypes.TRIAL_SPAWNER_DETECTED_PLAYER)
            .withSlot(ParticleSlot.AROUND, 1, 0.01)
            .withSlot(ParticleSlot.ABOVE, 1, 0.01)
            .build()),
    BLUE_BAR(new SimplePlayerParticle.Builder(ParticleTypes.TRIAL_SPAWNER_DETECTED_PLAYER_OMINOUS)
            .withSlot(ParticleSlot.AROUND, 1, 0.01)
            .withSlot(ParticleSlot.ABOVE, 1, 0.01)
            .build()),
    DRIPPING_HONEY(new SimplePlayerParticle(ParticleTypes.FALLING_HONEY, ParticleSlot.BELOW)),
    DRIPPING_LAVA(new SimplePlayerParticle(ParticleTypes.DRIPPING_LAVA, ParticleSlot.BELOW, 1, 1.0)),
    DRIPPING_OBSIDIAN(new SimplePlayerParticle(ParticleTypes.FALLING_OBSIDIAN_TEAR, ParticleSlot.BELOW)),
    DRIPPING_WATER(new SimplePlayerParticle(ParticleTypes.DRIPPING_WATER, ParticleSlot.BELOW, 1, 1.0)),
    FIREWORK(new SimplePlayerParticle.Builder(ParticleTypes.FIREWORK)
            .withSlot(ParticleSlot.BELOW, 1, 0.01)
            .withSlot(ParticleSlot.AROUND, 1, 0.1)
            .build()),
    FLAME(flamePlayerParticle(ParticleTypes.FLAME)),
    SOUL_FIRE_FLAME(flamePlayerParticle(ParticleTypes.SOUL_FIRE_FLAME)),
    COPPER_FIRE_FLAME(flamePlayerParticle(ParticleTypes.COPPER_FIRE_FLAME)),
    LAVA(new SimplePlayerParticle.Builder(ParticleTypes.LAVA)
            .withSlot(ParticleSlot.AROUND, 1, 0.1)
            .withSlot(ParticleSlot.ABOVE, 1, 0.1)
            .withInterval(10)
            .build()),
    SCRAPE(new AmbientPlusParticle(ParticleTypes.SCRAPE, 0.25)),
    SCULK_CHARGE(new SimplePlayerParticle(new SculkChargeParticleOptions(0.0F), ParticleSlot.BELOW, 5, 0.01)),
    TOTEM(new SimplePlayerParticle(ParticleTypes.TOTEM_OF_UNDYING, ParticleSlot.BELOW, 5, 0.1)),
    SHRIEK(new SimplePlayerParticle.Builder(new ShriekParticleOption(0))
            .withSlot(ParticleSlot.ABOVE)
            .build()),
    WAX_OFF(new AmbientPlusParticle(ParticleTypes.WAX_OFF, 0.25)),
    WAX_ON(new AmbientPlusParticle(ParticleTypes.WAX_ON, 0.25)),
    FIREFLY(new SimplePlayerParticle.Builder(ParticleTypes.FIREFLY)
            .withSlot(ParticleSlot.AROUND, 1, 1.0)
            .withInterval(2)
            .build()),
    PALE_OAK_LEAVES(new PaleOakLeavesParticle()),
    TINTED_LEAVES(new TintedLeavesParticle()),
    SONIC_BOOM(new SonicBoomParticle()),
    POTION(new PotionParticle()),
    COLOR(new ColorParticle()),
    FLAG(new FlagParticle()),
    FLAG_POTION(new FlagPotionParticle()),
    ITEM(new ItemParticle());

    private final PlayerParticle<?> particle;
    PlayerParticles(PlayerParticle<?> particle) {
        this.particle = particle;
    }

    public static void registerPlayerParticles(ParticleRegistry registry) {
        for (PlayerParticles normalParticle : values()) {
            registry.register(normalParticle.name().toLowerCase(), normalParticle.particle);
        }
    }

    private static SimplePlayerParticle flamePlayerParticle(SimpleParticleType type) {
        return new SimplePlayerParticle.Builder(type)
                .withSlot(ParticleSlot.AROUND, 1, 0.1)
                .withSlot(ParticleSlot.ABOVE, 1, 0.01)
                .withInterval(3)
                .build();
    }
}
