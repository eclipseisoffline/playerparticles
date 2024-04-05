package xyz.eclipseisoffline.playerparticles;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;
import xyz.eclipseisoffline.playerparticles.particles.PlayerParticle;
import xyz.eclipseisoffline.playerparticles.particles.PlayerParticles;
import xyz.eclipseisoffline.playerparticles.particles.data.ParticleData;
import xyz.eclipseisoffline.playerparticles.particles.data.ParticleDataType;

public class PlayerParticleManager extends SavedData {
    private static final String SAVE_FILE = "playerparticles";
    private final Map<UUID, PlayerParticleOptions> playerParticles = new HashMap<>();
    private final ServerLevel level;

    private PlayerParticleManager(ServerLevel level) {
        this.level = level;
    }

    public static PlayerParticleManager getInstance(MinecraftServer server) {
        ServerLevel overworld = server.getLevel(Level.OVERWORLD);
        if (overworld == null) {
            throw new IllegalStateException("Overworld level cannot be null");
        }

        return overworld.getDataStorage().computeIfAbsent(managerFactory(overworld), SAVE_FILE);
    }

    private static Factory<PlayerParticleManager> managerFactory(ServerLevel level) {
        return new Factory<>(
                () -> new PlayerParticleManager(level),
                tag -> read(level, tag), null);
    }

    private static PlayerParticleManager read(ServerLevel level, CompoundTag playerParticlesTag) {
        PlayerParticleManager particleManager = new PlayerParticleManager(level);
        ParticleRegistry particleRegistry = ParticleRegistry.getInstance();

        Set<String> uuids = playerParticlesTag.getAllKeys();
        for (String uuid : uuids) {
            CompoundTag particleTag = playerParticlesTag.getCompound(uuid);
            Set<String> slots = particleTag.getAllKeys();
            PlayerParticleOptions playerParticleOptions = new PlayerParticleOptions();
            for (String slotKey : slots) {
                CompoundTag slotTag = particleTag.getCompound(slotKey);
                ParticleSlot slot;
                try {
                    slot = ParticleSlot.valueOf(slotKey);
                } catch (IllegalArgumentException exception) {
                    continue;
                }

                PlayerParticle playerParticle = particleRegistry.fromId(slotTag.getString("particle"));
                if (playerParticle == null) {
                    continue;
                }

                ParticleDataType<?> particleDataType = playerParticle.getParticleDataType();
                ParticleData<?> particleData = null;
                if (particleDataType != null) {
                    if (slotTag.contains("data")) {
                        CompoundTag dataTag = slotTag.getCompound("data");
                        try {
                            particleData = particleDataType.read(level, dataTag);
                        } catch (Exception exception) {
                            continue;
                        }
                    } else {
                        continue;
                    }
                }
                playerParticleOptions.setParticle(slot, new ParticleWithData(playerParticle, particleData));
            }
            try {
                particleManager.playerParticles.put(UUID.fromString(uuid), playerParticleOptions);
            } catch (IllegalArgumentException ignored) {}
        }

        return particleManager;
    }

    @Override
    public @NotNull CompoundTag save(CompoundTag playerParticlesTag) {
        ParticleRegistry particleRegistry = ParticleRegistry.getInstance();

        for (Entry<UUID, PlayerParticleOptions> playerParticleOptions : playerParticles.entrySet()) {
            CompoundTag particleTag = new CompoundTag();
            PlayerParticleOptions particleOptions = playerParticleOptions.getValue();
            for (ParticleSlot slot : ParticleSlot.values()) {
                ParticleWithData particleInfo = particleOptions.getParticle(slot);
                if (particleInfo == null) {
                    continue;
                }
                CompoundTag slotTag = new CompoundTag();
                slotTag.putString("particle", particleRegistry.fromParticle(particleInfo.particle));
                if (particleInfo.data != null) {
                    CompoundTag dataTag = new CompoundTag();
                    particleInfo.data.saveData(level, dataTag);
                    slotTag.put("data", dataTag);
                }
                particleTag.put(slot.toString(), slotTag);
            }
            playerParticlesTag.put(playerParticleOptions.getKey().toString(), particleTag);
        }

        return playerParticlesTag;
    }

    public void tickPlayerParticles(ServerLevel level, ServerPlayer player) {
        for (ParticleSlot slot : ParticleSlot.values()) {
            ParticleWithData particle = getPlayerParticle(player, slot);
            if (particle != null) {
                try {
                    particle.particle.tick(level, player, slot, particle.data);
                } catch (Exception ignored) {}
            }
        }
    }

    private ParticleWithData getPlayerParticle(ServerPlayer player, ParticleSlot slot) {
        if (playerParticles.containsKey(player.getUUID())) {
            PlayerParticleOptions particleOptions = playerParticles.get(player.getUUID());
            if (particleOptions.enabled) {
                return particleOptions.getParticle(slot);
            }
        }
        return null;
    }

    public void setPlayerParticle(ServerPlayer player, ParticleSlot slot,
            PlayerParticle playerParticle, ParticleData<?> data) {
        PlayerParticleOptions playerParticleOptions = getOrCreateParticleOptions(player);
        if (playerParticle == null) {
            playerParticleOptions.setParticle(slot, null);
        } else {
            playerParticleOptions.setParticle(slot, new ParticleWithData(playerParticle, data));
        }
        setDirty();
    }

    public void setPlayerParticlesEnabled(ServerPlayer player, boolean enabled) {
        PlayerParticleOptions playerParticleOptions = getOrCreateParticleOptions(player);
        playerParticleOptions.enabled = enabled;
        setDirty();
    }

    private PlayerParticleOptions getOrCreateParticleOptions(ServerPlayer player) {
        PlayerParticleOptions playerParticleOptions = playerParticles.get(player.getUUID());
        if (playerParticleOptions == null) {
            playerParticleOptions = new PlayerParticleOptions();
            playerParticles.put(player.getUUID(), playerParticleOptions);
        }
        return playerParticleOptions;
    }

    private static class PlayerParticleOptions {
        private final Map<ParticleSlot, ParticleWithData> particles = new HashMap<>();
        private boolean enabled = true;

        private ParticleWithData getParticle(ParticleSlot slot) {
            return particles.get(slot);
        }

        private void setParticle(ParticleSlot slot, ParticleWithData particle) {
            particles.put(slot, particle);
        }
    }

    private record ParticleWithData(PlayerParticle particle, ParticleData<?> data) {}
}
