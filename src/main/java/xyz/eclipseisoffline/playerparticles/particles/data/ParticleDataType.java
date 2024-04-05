package xyz.eclipseisoffline.playerparticles.particles.data;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import xyz.eclipseisoffline.playerparticles.particles.data.types.ColorParticleData;
import xyz.eclipseisoffline.playerparticles.particles.data.types.ColorParticleData.ColorData;
import xyz.eclipseisoffline.playerparticles.particles.data.types.FlagParticleData;
import xyz.eclipseisoffline.playerparticles.particles.data.types.ItemParticleData;

public class ParticleDataType<T> implements SuggestionProvider<CommandSourceStack> {
    public static final ParticleDataType<List<ColorData>> COLOR = new ParticleDataType<>(ColorParticleData::new);
    public static final ParticleDataType<List<ColorData>> FLAG = new ParticleDataType<>(FlagParticleData::new);
    public static final ParticleDataType<Item> ITEM = new ParticleDataType<>(ItemParticleData::new);

    private final Function<T, ParticleData<T>> constructor;
    private final ParticleData<T> baseInstance;

    private ParticleDataType(Function<T, ParticleData<T>> constructor) {
        this.constructor = constructor;
        baseInstance = constructor.apply(null);
    }

    public ParticleData<T> createInstance(T data) {
        return constructor.apply(data);
    }

    public ParticleData<T> parseData(CommandContext<CommandSourceStack> context,
            String data) throws CommandSyntaxException {
        return baseInstance.parseData(context, data);
    }

    public ParticleData<T> read(ServerLevel level, CompoundTag tag) {
        return createInstance(baseInstance.readData(level, tag));
    }

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSourceStack> context,
            SuggestionsBuilder builder) throws CommandSyntaxException {
        return baseInstance.getSuggestions(context, builder);
    }
}
