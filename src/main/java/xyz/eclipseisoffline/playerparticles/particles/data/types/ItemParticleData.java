package xyz.eclipseisoffline.playerparticles.particles.data.types;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.commands.arguments.item.ItemParser;
import net.minecraft.commands.arguments.item.ItemParser.ItemResult;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import xyz.eclipseisoffline.playerparticles.particles.data.ParticleData;
import xyz.eclipseisoffline.playerparticles.particles.data.SimpleDataHolder;

public class ItemParticleData extends SimpleDataHolder<ItemStack> {

    public ItemParticleData(ItemStack data) {
        super(data);
    }

    @Override
    public ParticleData<ItemStack> parseData(CommandContext<CommandSourceStack> context, String input)
            throws CommandSyntaxException {
        ItemParser itemParser = new ItemParser(context.getSource().getServer().registryAccess());
        ItemResult resultParsed = itemParser.parse(new StringReader(input));
        ItemInput parsedInput = new ItemInput(resultParsed.item(), resultParsed.components());
        
        return new ItemParticleData(parsedInput.createItemStack(1, false));
    }

    @Override
    public ItemStack readData(ServerLevel level, CompoundTag tag) {
        return ItemStack.parse(level.registryAccess(), tag.get("item")).orElseThrow();
    }

    @Override
    public void saveData(ServerLevel level, CompoundTag tag) {
        tag.put("item", getData().save(level.registryAccess()));
    }

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSourceStack> context,
            SuggestionsBuilder builder) {
        ItemParser itemParser = new ItemParser(context.getSource().getServer().registryAccess());
        return itemParser.fillSuggestions(builder);
    }
}
