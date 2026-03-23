package xyz.eclipseisoffline.playerparticles.fabric;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import xyz.eclipseisoffline.playerparticles.PlayerParticlesMod;

import java.util.function.Consumer;

public class PlayerParticlesFabric extends PlayerParticlesMod implements ModInitializer {

    @Override
    public void onInitialize() {
        initialize();
    }

    @Override
    protected String getVersion() {
        return FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow().getMetadata().getVersion().getFriendlyString();
    }

    @Override
    protected void registerCommands(Consumer<CommandDispatcher<CommandSourceStack>> registerer) {
        CommandRegistrationCallback.EVENT.register((dispatcher, _, _) -> registerer.accept(dispatcher));
    }

    @Override
    protected void registerServerStartedHandler(Consumer<MinecraftServer> callback) {
        ServerLifecycleEvents.SERVER_STARTED.register(callback::accept);
    }

    @Override
    protected void registerServerTickHandler(Consumer<MinecraftServer> callback) {
        ServerTickEvents.END_SERVER_TICK.register(callback::accept);
    }
}
