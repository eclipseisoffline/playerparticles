package xyz.eclipseisoffline.playerparticles.particles.color;

import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.FastColor;
import xyz.eclipseisoffline.playerparticles.particles.data.types.ColorParticleData;

public class FlagPotionParticle extends FlagParticle {

    @Override
    protected ParticleOptions createColorParticle(ColorParticleData.ColorData firstColor, ColorParticleData.ColorData secondColor) {
        return ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, FastColor.ARGB32.colorFromFloat(0.58F, firstColor.red(), firstColor.green(), firstColor.blue()));
    }
}
