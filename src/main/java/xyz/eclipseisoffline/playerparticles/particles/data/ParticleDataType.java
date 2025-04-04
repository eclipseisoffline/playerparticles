package xyz.eclipseisoffline.playerparticles.particles.data;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.mojang.serialization.MapCodec;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.util.Unit;
import net.minecraft.world.item.ItemStack;
import xyz.eclipseisoffline.playerparticles.particles.data.types.ColorParticleDataParser;
import xyz.eclipseisoffline.playerparticles.particles.data.types.ColorParticleDataParser.ColorData;
import xyz.eclipseisoffline.playerparticles.particles.data.types.FlagParticleDataParser;
import xyz.eclipseisoffline.playerparticles.particles.data.types.ItemParticleDataParser;

public class ParticleDataType<T> implements SuggestionProvider<CommandSourceStack> {
    public static final ParticleDataType<Unit> EMPTY = new ParticleDataType<>(new UnitParser(), MapCodec.unit(Unit.INSTANCE));
    public static final ParticleDataType<List<ColorData>> COLOR = new ParticleDataType<>(new ColorParticleDataParser(), ColorParticleDataParser.CODEC);
    public static final ParticleDataType<List<ColorData>> FLAG = new ParticleDataType<>(new FlagParticleDataParser(), ColorParticleDataParser.CODEC);
    public static final ParticleDataType<ItemStack> ITEM = new ParticleDataType<>(new ItemParticleDataParser(), ItemParticleDataParser.CODEC);

    private final ParticleDataParser<T> parser;
    private final MapCodec<T> codec;

    private ParticleDataType(ParticleDataParser<T> parser, MapCodec<T> codec) {
        this.parser = parser;
        this.codec = codec;
    }

    public T parseData(CommandContext<CommandSourceStack> context, String data) throws CommandSyntaxException {
        return parser.parseData(context, data);
    }

    public MapCodec<T> codec() {
        return codec;
    }

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) throws CommandSyntaxException {
        return parser.getSuggestions(context, builder);
    }

    private static class UnitParser implements ParticleDataParser<Unit> {

        @Override
        public Unit parseData(CommandContext<CommandSourceStack> context, String input) {
            return Unit.INSTANCE;
        }

        @Override
        public CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
            return builder.buildFuture();
        }
    }
}
