package xyz.eclipseisoffline.playerparticles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jspecify.annotations.Nullable;
import xyz.eclipseisoffline.playerparticles.particles.PlayerParticle;

public class ParticleRegistry {
    private static final ParticleRegistry INSTANCE = new ParticleRegistry();
    private final Map<String, PlayerParticle<?>> particles = new HashMap<>();
    private final Map<ParticleSlot, List<String>> particlesForSlot = new HashMap<>();

    private ParticleRegistry() {
        for (ParticleSlot particleSlot : ParticleSlot.values()) {
            particlesForSlot.put(particleSlot, new ArrayList<>());
        }
    }

    public void register(String particleId, PlayerParticle<?> particle) {
        particles.put(particleId, particle);
        for (ParticleSlot particleSlot : ParticleSlot.values()) {
            if (particle.canWear(particleSlot)) {
                particlesForSlot.get(particleSlot).add(particleId);
            }
        }
    }

    public String fromParticle(PlayerParticle<?> particle) {
        for (Entry<String, PlayerParticle<?>> particleEntry : particles.entrySet()) {
            if (particleEntry.getValue().equals(particle)) {
                return particleEntry.getKey();
            }
        }
        throw new IllegalArgumentException("Unregistered particle " + particle);
    }

    public @Nullable PlayerParticle<?> fromId(String id) {
        return particles.get(id);
    }

    public List<String> getRegisteredParticles(ParticleSlot slot) {
        return particlesForSlot.get(slot);
    }

    public static ParticleRegistry getInstance() {
        return INSTANCE;
    }
}
