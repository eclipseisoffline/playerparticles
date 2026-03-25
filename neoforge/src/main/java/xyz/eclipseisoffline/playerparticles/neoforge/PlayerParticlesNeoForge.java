package xyz.eclipseisoffline.playerparticles.neoforge;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import xyz.eclipseisoffline.playerparticles.PlayerParticlesMod;

import java.util.function.Consumer;

@Mod(PlayerParticlesMod.MOD_ID)
public class PlayerParticlesNeoForge extends PlayerParticlesMod {
    private final String version;

    public PlayerParticlesNeoForge(ModContainer mod) {
        version = mod.getModInfo().getVersion().getQualifier();
        initialize();
    }

    @Override
    protected String getVersion() {
        return version;
    }

    @Override
    protected void registerCommands(Consumer<CommandDispatcher<CommandSourceStack>> registerer) {
        NeoForge.EVENT_BUS.addListener(RegisterCommandsEvent.class, event -> registerer.accept(event.getDispatcher()));
    }

    @Override
    protected void registerServerStartedHandler(Consumer<MinecraftServer> callback) {
        NeoForge.EVENT_BUS.addListener(ServerStartedEvent.class, event -> callback.accept(event.getServer()));
    }

    @Override
    protected void registerServerTickHandler(Consumer<MinecraftServer> callback) {
        NeoForge.EVENT_BUS.addListener(ServerTickEvent.Post.class, event -> callback.accept(event.getServer()));
    }
}
