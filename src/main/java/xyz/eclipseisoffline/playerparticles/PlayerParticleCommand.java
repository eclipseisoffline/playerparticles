package xyz.eclipseisoffline.playerparticles;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.concurrent.CompletableFuture;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import xyz.eclipseisoffline.playerparticles.particles.PlayerParticle;
import xyz.eclipseisoffline.playerparticles.particles.data.ParticleData;
import xyz.eclipseisoffline.playerparticles.particles.data.ParticleDataType;

public class PlayerParticleCommand {
    private static final String PERMISSION = PlayerParticlesInitializer.MOD_ID + ".command";

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> command = Commands
                .literal(PlayerParticlesInitializer.MOD_ID)
                .requires(source -> source.isPlayer() && Permissions.check(source, PERMISSION, 2));

        for (ParticleSlot particleSlot : ParticleSlot.values()) {
            registerParticleSlotCommand(particleSlot, command);
        }

        command
                .then(Commands.literal("reset")
                        .executes(context -> {
                            ServerPlayer player = context.getSource().getPlayerOrException();
                            PlayerParticleManager particleManager = PlayerParticleManager.getInstance(context.getSource().getServer());
                            for (ParticleSlot slot : ParticleSlot.values()) {
                                particleManager.setPlayerParticle(player, slot, null, null);
                            }
                            context.getSource().sendSuccess(() -> Component.literal("Reset player particles in all slots"), true);
                            return 0;
                        })
                )
                .then(Commands.literal("off")
                        .executes(context -> {
                            ServerPlayer player = context.getSource().getPlayerOrException();
                            PlayerParticleManager particleManager = PlayerParticleManager.getInstance(context.getSource().getServer());
                            particleManager.setPlayerParticlesEnabled(player, false);
                            context.getSource().sendSuccess(() -> Component.literal("Turned player particles off"), true);
                            return 0;
                        })
                )
                .then(Commands.literal("on")
                        .executes(context -> {
                            ServerPlayer player = context.getSource().getPlayerOrException();
                            PlayerParticleManager particleManager = PlayerParticleManager.getInstance(context.getSource().getServer());
                            particleManager.setPlayerParticlesEnabled(player, true);
                            context.getSource().sendSuccess(() -> Component.literal("Turned player particles on"), true);
                            return 0;
                        })
                )
                .then(Commands.literal("disable-all")
                        .executes(context -> {
                            ServerPlayer player = context.getSource().getPlayerOrException();
                            PlayerParticleManager particleManager = PlayerParticleManager.getInstance(context.getSource().getServer());
                            particleManager.setAllDisabled(player, true);
                            context.getSource().sendSuccess(() -> Component.literal("Turned all player particles off"), true);
                            return 0;
                        })
                )
                .then(Commands.literal("enable-all")
                        .executes(context -> {
                            ServerPlayer player = context.getSource().getPlayerOrException();
                            PlayerParticleManager particleManager = PlayerParticleManager.getInstance(context.getSource().getServer());
                            particleManager.setAllDisabled(player, false);
                            context.getSource().sendSuccess(() -> Component.literal("Turned all player particles on"), true);
                            return 0;
                        })
                );

        dispatcher.register(command);
    }

    private static void registerParticleSlotCommand(ParticleSlot slot,
            LiteralArgumentBuilder<CommandSourceStack> base) {
        base.then(
                Commands.literal(slot.getDisplayName())
                        .then(Commands.argument("particle", StringArgumentType.word())
                                .suggests(new PlayerParticleSuggestionProvider(slot))
                                .then(Commands.argument("data", StringArgumentType.greedyString())
                                        .suggests(new PlayerParticleDataSuggestionProvider())
                                        .executes(new PlayerParticleApplyCommand(slot))
                                )
                                .executes(new PlayerParticleApplyCommand(slot))
                        )
                        .then(Commands.literal("reset")
                                .executes(new PlayerParticleApplyCommand(slot, true))
                        )
        );
    }

    private static class PlayerParticleApplyCommand implements Command<CommandSourceStack> {
        private final ParticleSlot slot;
        private final boolean reset;

        private PlayerParticleApplyCommand(ParticleSlot slot) {
            this.slot = slot;
            reset = false;
        }

        private PlayerParticleApplyCommand(ParticleSlot slot, boolean reset) {
            this.slot = slot;
            this.reset = reset;
        }

        @Override
        public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
            ServerPlayer player = context.getSource().getPlayerOrException();
            PlayerParticleManager particleManager = PlayerParticleManager.getInstance(context.getSource().getServer());
            if (reset) {
                particleManager.setPlayerParticle(player, slot, null, null);
                context.getSource().sendSuccess(() -> Component.literal("Reset particles in slot " + slot.getDisplayName()), true);
                return 0;
            }

            String particleId = StringArgumentType.getString(context, "particle");
            PlayerParticle particle = getParticle(context);

            if (particle == null) {
                throw new SimpleCommandExceptionType(Component.literal("Unknown particle " + particleId)).create();
            } else if (!particle.canWear(slot)) {
                throw new SimpleCommandExceptionType(Component.literal("Cannot wear particle " + particleId + " in slot " + slot.getDisplayName())).create();
            }

            String particleData = getParticleData(context);
            ParticleDataType<?> particleDataType = particle.getParticleDataType();
            ParticleData<?> data = null;

            if  (particleData != null && particleDataType != null) {
                data = particleDataType.parseData(context, particleData);
            } else if (particleData == null && particleDataType != null) {
                throw new SimpleCommandExceptionType(Component.literal("Particle expects data but none was given")).create();
            }

            particleManager.setPlayerParticle(player, slot, particle, data);
            context.getSource().sendSuccess(() -> Component.literal("Applied particle " + particleId + " to slot " + slot.getDisplayName()), true);
            return 0;
        }

        private static PlayerParticle getParticle(CommandContext<CommandSourceStack> context) {
            String particleId = StringArgumentType.getString(context, "particle");
            return ParticleRegistry.getInstance().fromId(particleId);
        }

        private static String getParticleData(CommandContext<CommandSourceStack> context) {
            try {
                return StringArgumentType.getString(context, "data");
            } catch (IllegalArgumentException exception) {
                return null;
            }
        }
    }

    private record PlayerParticleSuggestionProvider(ParticleSlot slot) implements
            SuggestionProvider<CommandSourceStack> {

        @Override
        public CompletableFuture<Suggestions> getSuggestions(
                CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
            return SharedSuggestionProvider.suggest(ParticleRegistry.getInstance()
                    .getRegisteredParticles(slot), builder);
        }
    }

    private static class PlayerParticleDataSuggestionProvider implements
            SuggestionProvider<CommandSourceStack> {

        @Override
        public CompletableFuture<Suggestions> getSuggestions(
                CommandContext<CommandSourceStack> context, SuggestionsBuilder builder)
                throws CommandSyntaxException {
            PlayerParticle particle = PlayerParticleApplyCommand.getParticle(context);

            if (particle == null) {
                return builder.buildFuture();
            }

            if (particle.getParticleDataType() == null) {
                throw new SimpleCommandExceptionType(Component.literal("Particle doesn't accept data")).create();
            }

            return particle.getParticleDataType().getSuggestions(context, builder);
        }
    }
}
