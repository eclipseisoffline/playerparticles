package xyz.eclipseisoffline.playerparticles.particles.color;

import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import xyz.eclipseisoffline.playerparticles.ParticleSlot;
import xyz.eclipseisoffline.playerparticles.particles.data.types.ColorParticleDataParser;

public class TintedLeavesParticle extends ColorParticle {

    @Override
    protected ParticleOptions createColorParticle(ServerPlayer player, ParticleSlot slot, ColorParticleDataParser.ColorData firstColor, ColorParticleDataParser.ColorData secondColor) {
        return ColorParticleOption.create(ParticleTypes.TINTED_LEAVES, firstColor.toInt(1.0F));
    }

    @Override
    public boolean canWear(ParticleSlot slot) {
        return slot == ParticleSlot.BELOW;
    }
}
