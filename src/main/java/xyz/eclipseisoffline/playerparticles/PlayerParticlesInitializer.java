package xyz.eclipseisoffline.playerparticles;

import java.util.List;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.level.ServerPlayer;
import xyz.eclipseisoffline.playerparticles.particles.PlayerParticles;
import xyz.eclipseisoffline.playerparticles.particles.color.ColorParticle;
import xyz.eclipseisoffline.playerparticles.particles.color.FlagParticle;

public class PlayerParticlesInitializer implements ModInitializer {
    public static final String MOD_ID = "playerparticles";
    private PlayerParticleManager particleManager = null;

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            PlayerParticleCommand.register(dispatcher);
        });


        ServerLifecycleEvents.SERVER_STARTED.register(
                server -> particleManager = PlayerParticleManager.getInstance(server));

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            List<ServerPlayer> players = server.getPlayerList().getPlayers();
            if (particleManager == null) {
                return;
            }
            for (ServerPlayer player : players) {
                if (player.isInvisible() || player.isSpectator()) {
                    continue;
                }
                particleManager.tickPlayerParticles(player.serverLevel(), player);
            }
        });

        PlayerParticles.registerPlayerParticles(ParticleRegistry.getInstance());
    }
}
