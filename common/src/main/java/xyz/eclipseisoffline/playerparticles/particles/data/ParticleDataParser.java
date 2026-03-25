package xyz.eclipseisoffline.playerparticles.particles.data;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.commands.CommandSourceStack;

public interface ParticleDataParser<T> extends SuggestionProvider<CommandSourceStack> {

    T parseData(CommandContext<CommandSourceStack> context, String input) throws CommandSyntaxException;
}
