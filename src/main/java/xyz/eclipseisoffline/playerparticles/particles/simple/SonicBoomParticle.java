package xyz.eclipseisoffline.playerparticles.particles.simple;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import xyz.eclipseisoffline.playerparticles.ParticleSlot;
import xyz.eclipseisoffline.playerparticles.particles.PlayerParticle;
import xyz.eclipseisoffline.playerparticles.particles.data.ParticleData;

public class SonicBoomParticle implements PlayerParticle {
    private static final int ITERATIONS = 10;
    private static final int GAP = 2;
    private static final int INTERVAL = 40;

    @Override
    public void tick(ServerLevel level, ServerPlayer player, ParticleSlot slot,
            ParticleData<?> data) {
        if (level.getServer().getTickCount() % INTERVAL != 0) {
            return;
        }

        Vec3 start = defaultParticlePos(player, slot);
        Vec3 looking = player.getLookAngle();
        for (int i = 1; i <= ITERATIONS; i++) {
            Vec3 pos = start.add(looking.multiply(new Vec3(GAP * i, GAP * i, GAP * i)));
            sendParticles(level, ParticleTypes.SONIC_BOOM, pos, Vec3.ZERO, 1, 1.0);
        }
    }

    @Override
    public boolean canWear(ParticleSlot slot) {
        return slot == ParticleSlot.AROUND;
    }
}
