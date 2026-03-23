package xyz.eclipseisoffline.playerparticles;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import xyz.eclipseisoffline.playerparticles.particles.PlayerParticle;

public record ParticleWithData<T>(PlayerParticle<T> particle, T data) {

    public static final Codec<ParticleWithData<?>> CODEC = PlayerParticle.CODEC.dispatch("particle", ParticleWithData::particle, ParticleWithData::mapDataCodec);

    public void tick(ServerLevel level, ServerPlayer player, ParticleSlot slot) {
        particle.tick(level, player, slot, data);
    }

    private static <T> MapCodec<ParticleWithData<T>> mapDataCodec(PlayerParticle<T> particle) {
        return particle.particleDataType().codec().xmap(data -> new ParticleWithData<>(particle, data), ParticleWithData::data);
    }
}
