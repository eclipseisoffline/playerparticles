package xyz.eclipseisoffline.playerparticles.particles;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;
import xyz.eclipseisoffline.playerparticles.ParticleSlot;
import xyz.eclipseisoffline.playerparticles.PlayerParticleManager;
import xyz.eclipseisoffline.playerparticles.particles.data.ParticleData;
import xyz.eclipseisoffline.playerparticles.particles.data.ParticleDataType;

public interface PlayerParticle {
    Vec3 FLAT_BLOCK_OFFSET = new Vec3(0.125, 0.0, 0.125);
    Vec3 AROUND_LARGE_OFFSET = new Vec3(1, 1, 1);

    void tick(ServerLevel level, ServerPlayer player, ParticleSlot slot, ParticleData<?> data);
    boolean canWear(ParticleSlot slot);

    default void sendParticles(ServerLevel level, ServerPlayer source,
            ParticleOptions particleOptions,
            Vec3 pos, Vec3 offset, int count, double speed) {
        double scale = source.getAttributeValue(Attributes.SCALE);
        if (scale < 1.0) {
            scale = Math.max(1.0, scale + 0.15);
        }
        Vec3 scaledOffset = offset.multiply(scale, scale, scale);
        PlayerParticleManager particleManager = PlayerParticleManager.getInstance(level.getServer());
        level.getPlayers(player -> !particleManager.hasAllDisabled(player)).forEach(player
                -> level.sendParticles(player, particleOptions, false,
                pos.x, pos.y, pos.z, count, scaledOffset.x, scaledOffset.y, scaledOffset.z, speed));
    }

    default ParticleDataType<?> getParticleDataType() {
        return null;
    }

    default boolean particleDataRequired() {
        return getParticleDataType() != null;
    }

    default Vec3 defaultParticlePos(ServerPlayer player, ParticleSlot slot) {
        double scale = player.getAttributeValue(Attributes.SCALE);
        switch (slot) {
            case ABOVE -> {
                return player.position().add(0, 2.2 * scale, 0);
            }
            case BELOW -> {
                return player.position().add(0, 0.05 * scale, 0);
            }
            case AROUND -> {
                return player.position().add(0, scale, 0);
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
