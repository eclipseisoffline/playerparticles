package xyz.eclipseisoffline.playerparticles.particles.simple;

import net.minecraft.core.particles.ParticleOptions;
import xyz.eclipseisoffline.playerparticles.ParticleSlot;

public class AmbientPlusParticle extends SimplePlayerParticle {

    public AmbientPlusParticle(ParticleOptions particleOptions) {
        this(particleOptions, 1.0);
    }

    public AmbientPlusParticle(ParticleOptions particleOptions, double intensity) {
        super(new Builder(particleOptions)
                .withSlot(ParticleSlot.AROUND, (int) (intensity * 25), 0.25)
                .withInterval(15));
    }
}
