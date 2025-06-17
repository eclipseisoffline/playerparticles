package xyz.eclipseisoffline.playerparticles;

import java.util.List;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.Version;
import net.minecraft.server.level.ServerPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.eclipseisoffline.playerparticles.particles.PlayerParticles;

public class PlayerParticlesInitializer implements ModInitializer {
    public static final String MOD_ID = "playerparticles";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private PlayerParticleManager particleManager = null;

    @Override
    public void onInitialize() {
        Version modVersion = FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow().getMetadata().getVersion();
        LOGGER.info("{} version {} initialising", MOD_ID, modVersion.getFriendlyString());

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            PlayerParticleCommand.register(dispatcher);
        });

        ServerLifecycleEvents.SERVER_STARTED.register(server -> particleManager = PlayerParticleManager.getInstance(server));

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            List<ServerPlayer> players = server.getPlayerList().getPlayers();
            if (particleManager == null) {
                return;
            }
            for (ServerPlayer player : players) {
                if (player.isInvisible() || player.isSpectator()) {
                    continue;
                }
                particleManager.tickPlayerParticles(player.level(), player);
            }
        });

        PlayerParticles.registerPlayerParticles(ParticleRegistry.getInstance());
        LOGGER.info("Registered {} player particles", PlayerParticles.values().length);
    }
}
