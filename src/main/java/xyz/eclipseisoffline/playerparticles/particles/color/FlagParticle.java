package xyz.eclipseisoffline.playerparticles.particles.color;

import xyz.eclipseisoffline.playerparticles.particles.data.ParticleDataType;

public class FlagParticle extends ColorParticle {

    @Override
    public ParticleDataType<?> getParticleDataType() {
        return ParticleDataType.FLAG;
    }
}
