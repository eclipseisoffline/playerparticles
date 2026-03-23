package xyz.eclipseisoffline.playerparticles;

import java.util.List;
import java.util.function.Consumer;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.eclipseisoffline.playerparticles.particles.PlayerParticles;

public abstract class PlayerParticlesMod {
    public static final String MOD_ID = "playerparticles";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private boolean initialized = false;
    private @Nullable PlayerParticleManager particleManager = null;

    public void initialize() {
        if (initialized) {
            throw new IllegalStateException("Tried to initialise Player Particles twice!");
        }
        initialized = true;
        LOGGER.info("{} version {} initialising", MOD_ID, getVersion());

        registerCommands(PlayerParticleCommand::register);

        registerServerStartedHandler(server -> particleManager = PlayerParticleManager.getInstance(server));
        registerServerTickHandler(server -> {
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

    protected abstract String getVersion();

    protected abstract void registerCommands(Consumer<CommandDispatcher<CommandSourceStack>> registerer);

    protected abstract void registerServerStartedHandler(Consumer<MinecraftServer> callback);

    protected abstract void registerServerTickHandler(Consumer<MinecraftServer> callback);

    public static Identifier getModdedIdentifier(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
