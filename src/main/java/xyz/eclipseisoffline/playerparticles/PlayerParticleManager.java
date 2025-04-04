package xyz.eclipseisoffline.playerparticles;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import xyz.eclipseisoffline.playerparticles.particles.PlayerParticle;

public class PlayerParticleManager extends SavedData {
    public static final Codec<PlayerParticleManager> CODEC = Codec.unboundedMap(UUIDUtil.STRING_CODEC, PlayerParticleOptions.CODEC)
            .xmap(PlayerParticleManager::new, manager -> manager.playerParticles);
    public static final SavedDataType<PlayerParticleManager> TYPE = new SavedDataType<>("playerparticles", PlayerParticleManager::new, CODEC, null);

    private final Map<UUID, PlayerParticleOptions> playerParticles = new HashMap<>();

    private PlayerParticleManager(Map<UUID, PlayerParticleOptions> playerParticles) {
        this.playerParticles.putAll(playerParticles);
    }

    private PlayerParticleManager() {}

    public static PlayerParticleManager getInstance(MinecraftServer server) {
        return server.overworld().getDataStorage().computeIfAbsent(TYPE);
    }

    public static CompoundTag dataFix(CompoundTag tag, int dataVersion) {
        CompoundTag fixed = new CompoundTag();

        CompoundTag data = tag.getCompound("data").orElseThrow();
        CompoundTag fixedData = new CompoundTag();
        Set<String> uuids = data.keySet();

        for (String uuid : uuids) {
            CompoundTag playerData = data.getCompoundOrEmpty(uuid);

            CompoundTag fixedPlayerData = new CompoundTag();
            CompoundTag particleData = new CompoundTag();

            Set<String> slots = playerData.keySet();
            for (String slotKey : slots) {
                ParticleSlot slot;
                try {
                    slot = ParticleSlot.valueOf(slotKey);
                } catch (IllegalArgumentException exception) {
                    continue;
                }

                CompoundTag slotTag = playerData.getCompound(slotKey).orElseThrow();
                CompoundTag fixedParticle = new CompoundTag();
                fixedParticle.putString("particle", slotTag.getString("particle").orElseThrow());
                if (slotTag.contains("data")) {
                    CompoundTag dataTag = slotTag.getCompoundOrEmpty("data");
                    for (String dataKey : dataTag.keySet()) {
                        fixedParticle.put(dataKey, dataTag.get(dataKey));
                    }
                }

                particleData.put(slot.getSerializedName(), fixedParticle);
            }
            fixedPlayerData.put("particles", particleData);

            fixedPlayerData.putBoolean("enabled", playerData.getBooleanOr("enabled", true));
            fixedPlayerData.putBoolean("all_disabled", playerData.getBooleanOr("allDisabled", false));

            fixedData.put(uuid, fixedPlayerData);
        }

        fixed.put("data", fixedData);
        return NbtUtils.addDataVersion(fixed, dataVersion);
    }

    public void tickPlayerParticles(ServerLevel level, ServerPlayer player) {
        for (ParticleSlot slot : ParticleSlot.values()) {
            ParticleWithData<?> particle = getPlayerParticle(player, slot);
            if (particle != null) {
                try {
                    particle.tick(level, player, slot);
                } catch (Exception ignored) {}
            }
        }
    }

    private ParticleWithData<?> getPlayerParticle(ServerPlayer player, ParticleSlot slot) {
        if (playerParticles.containsKey(player.getUUID())) {
            PlayerParticleOptions particleOptions = playerParticles.get(player.getUUID());
            if (particleOptions.enabled) {
                return particleOptions.getParticle(slot);
            }
        }
        return null;
    }

    public <T> void setPlayerParticle(ServerPlayer player, ParticleSlot slot, PlayerParticle<T> playerParticle, T data) {
        PlayerParticleOptions playerParticleOptions = getOrCreateParticleOptions(player);
        if (playerParticle == null) {
            playerParticleOptions.setParticle(slot, null);
        } else {
            playerParticleOptions.setParticle(slot, new ParticleWithData<>(playerParticle, data));
        }
        setDirty();
    }

    public void setPlayerParticlesEnabled(ServerPlayer player, boolean enabled) {
        PlayerParticleOptions playerParticleOptions = getOrCreateParticleOptions(player);
        playerParticleOptions.enabled = enabled;
        setDirty();
    }

    public boolean hasAllDisabled(ServerPlayer player) {
        return getOrCreateParticleOptions(player).allDisabled;
    }

    public void setAllDisabled(ServerPlayer player, boolean disabled) {
        PlayerParticleOptions playerParticleOptions = getOrCreateParticleOptions(player);
        playerParticleOptions.allDisabled = disabled;
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
        private static final Codec<Map<ParticleSlot, ParticleWithData<?>>> PARTICLES_CODEC = Codec.unboundedMap(ParticleSlot.CODEC, ParticleWithData.CODEC);
        private static final Codec<PlayerParticleOptions> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        PARTICLES_CODEC.optionalFieldOf("particles", Map.of()).forGetter(options -> options.particles),
                        Codec.BOOL.optionalFieldOf("enabled", true).forGetter(options -> options.enabled),
                        Codec.BOOL.optionalFieldOf("all_disabled", false).forGetter(options -> options.allDisabled)
                ).apply(instance, PlayerParticleOptions::new)
        );

        private final Map<ParticleSlot, ParticleWithData<?>> particles = new HashMap<>();
        private boolean enabled = true;
        private boolean allDisabled = false;

        private PlayerParticleOptions(Map<ParticleSlot, ParticleWithData<?>> particles, boolean enabled, boolean allDisabled) {
            this.particles.putAll(particles);
            this.enabled = enabled;
            this.allDisabled = allDisabled;
        }

        private PlayerParticleOptions() {}

        private ParticleWithData<?> getParticle(ParticleSlot slot) {
            return particles.get(slot);
        }

        private void setParticle(ParticleSlot slot, ParticleWithData<?> particle) {
            if (particle == null) {
                particles.remove(slot);
            } else {
                particles.put(slot, particle);
            }
        }
    }
}
