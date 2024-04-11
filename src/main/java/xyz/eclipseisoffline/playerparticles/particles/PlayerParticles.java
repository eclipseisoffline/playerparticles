package xyz.eclipseisoffline.playerparticles.particles;

import net.minecraft.core.particles.ParticleTypes;
import xyz.eclipseisoffline.playerparticles.ParticleRegistry;
import xyz.eclipseisoffline.playerparticles.ParticleSlot;
import xyz.eclipseisoffline.playerparticles.particles.color.ColorParticle;
import xyz.eclipseisoffline.playerparticles.particles.color.FlagParticle;
import xyz.eclipseisoffline.playerparticles.particles.color.PotionParticle;
import xyz.eclipseisoffline.playerparticles.particles.item.ItemParticle;
import xyz.eclipseisoffline.playerparticles.particles.simple.AmbientPlusParticle;
import xyz.eclipseisoffline.playerparticles.particles.simple.SimplePlayerParticle;

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
    POTION(new PotionParticle()),
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
    HEART(new AmbientPlusParticle(ParticleTypes.HEART, 0.15)),
    DOLPHIN(new SimplePlayerParticle(ParticleTypes.DOLPHIN, ParticleSlot.AROUND, ParticleSlot.BELOW)),
    SPORE_BLOSSOM(new SimplePlayerParticle(ParticleTypes.SPORE_BLOSSOM_AIR, ParticleSlot.AROUND)),
    CRIMSON(new SimplePlayerParticle.Builder(ParticleTypes.CRIMSON_SPORE)
            .withSlot(ParticleSlot.AROUND, 3, 0.15)
            .build()),
    WARPED(new SimplePlayerParticle.Builder(ParticleTypes.WARPED_SPORE)
            .withSlot(ParticleSlot.AROUND, 3, 0.15)
            .build()),
    ASH(new SimplePlayerParticle.Builder(ParticleTypes.ASH)
            .withSlot(ParticleSlot.AROUND, 3, 0.15)
            .build()),
    ENCHANT(new SimplePlayerParticle.Builder(ParticleTypes.ENCHANT)
            .withSlot(ParticleSlot.AROUND)
            .build()),
    COLOR(new ColorParticle()),
    FLAG(new FlagParticle()),
    ITEM(new ItemParticle());

    private final PlayerParticle particle;
    PlayerParticles(PlayerParticle particle) {
        this.particle = particle;
    }

    public static void registerPlayerParticles(ParticleRegistry registry) {
        for (PlayerParticles normalParticle : values()) {
            registry.register(normalParticle.toString().toLowerCase(), normalParticle.particle);
        }
    }
}
