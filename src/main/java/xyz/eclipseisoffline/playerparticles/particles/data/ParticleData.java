package xyz.eclipseisoffline.playerparticles.particles.data;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;

public interface ParticleData<T> extends SuggestionProvider<CommandSourceStack> {

    T getData();

    ParticleData<T> parseData(CommandContext<CommandSourceStack> context,
            String input) throws CommandSyntaxException;

    T readData(ServerLevel level, CompoundTag tag);

    void saveData(ServerLevel level, CompoundTag tag);
}
