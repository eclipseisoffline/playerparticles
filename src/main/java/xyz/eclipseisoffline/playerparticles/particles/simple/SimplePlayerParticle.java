package xyz.eclipseisoffline.playerparticles.particles.simple;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Unit;
import xyz.eclipseisoffline.playerparticles.ParticleSlot;
import xyz.eclipseisoffline.playerparticles.particles.PlayerParticle;
import xyz.eclipseisoffline.playerparticles.particles.data.ParticleDataType;

public class SimplePlayerParticle implements PlayerParticle<Unit> {
    private final SimpleParticleSettings settings;

    public SimplePlayerParticle(ParticleOptions particle) {
        this(particle, ParticleSlot.values());
    }

    public SimplePlayerParticle(ParticleOptions particle, ParticleSlot slot,
            int count, double speed) {
        this(new Builder(particle).withSlot(slot, count, speed));
    }

    public SimplePlayerParticle(ParticleOptions particle, ParticleSlot... slots) {
        this(new SimpleParticleSettings(particle, slots));
    }

    public SimplePlayerParticle(Builder builder) {
        this(builder.buildSettings());
    }

    private SimplePlayerParticle(SimpleParticleSettings settings) {
        this.settings = settings;
    }

    @Override
    public void tick(ServerLevel level, ServerPlayer player, ParticleSlot slot, Unit data) {
        if (settings.interval > 1) {
            if (level.getServer().getTickCount() % settings.interval != 0) {
                return;
            }
        }

        sendParticles(level, player, settings.particleOptions,
                defaultParticlePos(player, slot), defaultParticleOffset(slot),
                settings.getCount(slot), settings.getSpeed(slot));
    }

    @Override
    public boolean canWear(ParticleSlot slot) {
        return settings.particleCounts.containsKey(slot);
    }

    @Override
    public ParticleDataType<Unit> particleDataType() {
        return ParticleDataType.EMPTY;
    }

    public static class Builder {

        private final ParticleOptions particle;
        private final Map<ParticleSlot, Integer> particleCounts = new HashMap<>();
        private final Map<ParticleSlot, Double> particleSpeeds = new HashMap<>();
        private int interval = 1;
        public Builder(ParticleOptions particle) {
            this.particle = particle;
        }

        public Builder withSlot(ParticleSlot slot) {
            return withSlot(slot, slot.getDefaultCount(), slot.getDefaultSpeed());
        }

        public Builder withSlot(ParticleSlot slot, int count, double speed) {
            if (count < 1 || speed <= 0) {
                throw new IllegalArgumentException("Illegal particle count or speed (count below 1 or speed <= 0)");
            }
            particleCounts.put(slot, count);
            particleSpeeds.put(slot, speed);
            return this;
        }

        public Builder withInterval(int interval) {
            if (interval < 1) {
                throw new IllegalArgumentException("Illegal interval (below 1)");
            }
            this.interval = interval;
            return this;
        }

        public SimplePlayerParticle build() {
            return new SimplePlayerParticle(buildSettings());
        }

        private SimpleParticleSettings buildSettings() {
            return new SimpleParticleSettings(particle, particleCounts, particleSpeeds, interval);
        }
    }

    private static class SimpleParticleSettings {
        private final ParticleOptions particleOptions;
        private final Map<ParticleSlot, Integer> particleCounts;
        private final Map<ParticleSlot, Double> particleSpeeds;
        private final int interval;

        private SimpleParticleSettings(ParticleOptions particle, ParticleSlot... slots) {
            Map<ParticleSlot, Integer> particleCounts = new HashMap<>();
            Map<ParticleSlot, Double> particleSpeeds = new HashMap<>();
            for (ParticleSlot slot : slots) {
                particleCounts.put(slot, slot.getDefaultCount());
                particleSpeeds.put(slot, slot.getDefaultSpeed());
            }
            particleOptions = particle;
            this.particleCounts = Map.copyOf(particleCounts);
            this.particleSpeeds = Map.copyOf(particleSpeeds);
            interval = 1;
        }

        private SimpleParticleSettings(ParticleOptions particle,
                Map<ParticleSlot, Integer> particleCounts, Map<ParticleSlot, Double> particleSpeeds,
                int interval) {
            particleOptions = particle;
            this.particleCounts = Map.copyOf(particleCounts);
            this.particleSpeeds = Map.copyOf(particleSpeeds);
            this.interval = interval;
        }

        private int getCount(ParticleSlot slot) {
            return particleCounts.get(slot);
        }

        private double getSpeed(ParticleSlot slot) {
            return particleSpeeds.get(slot);
        }
    }
}
