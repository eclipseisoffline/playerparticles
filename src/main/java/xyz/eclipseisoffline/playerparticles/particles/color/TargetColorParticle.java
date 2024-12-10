package xyz.eclipseisoffline.playerparticles.particles.color;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.TrailParticleOption;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import xyz.eclipseisoffline.playerparticles.ParticleSlot;
import xyz.eclipseisoffline.playerparticles.particles.data.types.ColorParticleData;

public class TargetColorParticle extends ColorParticle {
    private static final int CIRCLE_COUNT = 15;
    private static final double CIRCLE = 2.0 * Math.PI;
    private static final double INTERVAL = CIRCLE / CIRCLE_COUNT;

    @Override
    protected ParticleOptions createColorParticle(ServerPlayer player, ParticleSlot slot, ColorParticleData.ColorData firstColor, ColorParticleData.ColorData secondColor) {
        return new TrailParticleOption(Vec3.ZERO, firstColor.toInt(1.0F), 300);
    }

    @Override
    public void sendParticles(ServerLevel level, ServerPlayer source, ParticleOptions particleOptions, Vec3 pos, Vec3 offset, int count, double speed) {
        int color = ((TrailParticleOption) particleOptions).color();
        Vec3 base = source.position();
        for (double i = 0.0; i < CIRCLE; i += INTERVAL) {
            double xOffset = Math.cos(i) * 10;
            double zOffset = Math.sin(i) * 10;
            super.sendParticles(level, source, new TrailParticleOption(base.add(0.0, 1.0, 0.0), color, 300), base.add(xOffset, 0.0, zOffset), Vec3.ZERO, 1, 1.0);
        }
    }
}
