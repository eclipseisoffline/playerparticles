package xyz.eclipseisoffline.playerparticles.particles;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import xyz.eclipseisoffline.playerparticles.ParticleSlot;
import xyz.eclipseisoffline.playerparticles.particles.data.ParticleData;
import xyz.eclipseisoffline.playerparticles.particles.data.ParticleDataType;

public interface PlayerParticle {
    Vec3 FLAT_BLOCK_OFFSET = new Vec3(0.125, 0.0, 0.125);
    Vec3 AROUND_LARGE_OFFSET = new Vec3(1, 1, 1);

    void tick(ServerLevel level, ServerPlayer player, ParticleSlot slot, ParticleData<?> data);
    boolean canWear(ParticleSlot slot);

    default void sendParticles(ServerLevel level, ParticleOptions particleOptions,
            Vec3 pos, Vec3 offset, int count, double speed) {
        level.sendParticles(particleOptions, pos.x, pos.y, pos.z,
                count, offset.x, offset.y, offset.z, speed);
    }

    default ParticleDataType<?> getParticleDataType() {
        return null;
    }

    default Vec3 defaultParticlePos(ServerPlayer player, ParticleSlot slot) {
        switch (slot) {
            case ABOVE -> {
                return player.position().add(0, 2.15, 0);
            }
            case BELOW -> {
                return player.position().add(0, 0.05, 0);
            }
            case AROUND -> {
                return player.position().add(0, 1.0, 0);
            }
            default -> {
                return player.position();
            }
        }
    }

    default Vec3 defaultParticleOffset(ParticleSlot slot) {
        if (slot == ParticleSlot.AROUND) {
            return AROUND_LARGE_OFFSET;
        } else {
            return FLAT_BLOCK_OFFSET;
        }
    }
}
