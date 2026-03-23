package xyz.eclipseisoffline.playerparticles.particles.color;

import xyz.eclipseisoffline.playerparticles.particles.data.ParticleDataType;
import xyz.eclipseisoffline.playerparticles.particles.data.types.ColorParticleDataParser;

import java.util.List;

public class FlagParticle extends ColorParticle {

    @Override
    public ParticleDataType<List<ColorParticleDataParser.ColorData>> particleDataType() {
        return ParticleDataType.FLAG;
    }
}
