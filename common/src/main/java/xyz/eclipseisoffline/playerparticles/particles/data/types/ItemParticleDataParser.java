package xyz.eclipseisoffline.playerparticles.particles.data.types;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import java.util.concurrent.CompletableFuture;

import com.mojang.serialization.MapCodec;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.commands.arguments.item.ItemParser;
import net.minecraft.world.item.ItemStackTemplate;
import xyz.eclipseisoffline.playerparticles.particles.data.ParticleDataParser;

public class ItemParticleDataParser implements ParticleDataParser<ItemStackTemplate> {

    public static final MapCodec<ItemStackTemplate> CODEC = ItemStackTemplate.CODEC.fieldOf("item");

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSourceStack> context,
                                                         SuggestionsBuilder builder) {
        ItemParser itemParser = new ItemParser(context.getSource().getServer().registryAccess());
        return itemParser.fillSuggestions(builder);
    }

    @Override
    public ItemStackTemplate parseData(CommandContext<CommandSourceStack> context, String input)
            throws CommandSyntaxException {
        ItemParser itemParser = new ItemParser(context.getSource().getServer().registryAccess());
        ItemInput parsedInput = itemParser.parse(new StringReader(input));

        return ItemStackTemplate.fromNonEmptyStack(parsedInput.createItemStack(1));
    }
}
