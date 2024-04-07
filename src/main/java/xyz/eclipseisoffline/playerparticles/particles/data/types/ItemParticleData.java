package xyz.eclipseisoffline.playerparticles.particles.data.types;

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
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import xyz.eclipseisoffline.playerparticles.particles.data.ParticleData;
import xyz.eclipseisoffline.playerparticles.particles.data.SimpleDataHolder;

public class ItemParticleData extends SimpleDataHolder<Item> {

    public ItemParticleData(Item data) {
        super(data);
    }

    @Override
    public ParticleData<Item> parseData(CommandContext<CommandSourceStack> context, String input)
            throws CommandSyntaxException {
        Registry<Item> itemRegistry = BuiltInRegistries.ITEM;

        ResourceLocation itemId = ResourceLocation.tryParse(input);
        if (itemId == null) {
            throw new SimpleCommandExceptionType(Component.literal("Error parsing item ID " + input)).create();
        }

        Item item = itemRegistry.get(itemId);
        if (item == Items.AIR) {
            throw new SimpleCommandExceptionType(Component.literal("Unknown item ID " + itemId)).create();
        }

        return new ItemParticleData(item);
    }

    @Override
    public Item readData(ServerLevel level, CompoundTag tag) {
        Registry<Item> itemRegistry = BuiltInRegistries.ITEM;
        String itemId = tag.getString("item");
        return itemRegistry.get(ResourceLocation.tryParse(itemId));
    }

    @Override
    public void saveData(ServerLevel level, CompoundTag tag) {
        Registry<Item> itemRegistry = BuiltInRegistries.ITEM;
        String itemId = Objects.requireNonNull(itemRegistry.getKey(getData())).toString();
        tag.putString("item", itemId);
    }

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSourceStack> context,
            SuggestionsBuilder builder) {
        Registry<Item> itemRegistry = BuiltInRegistries.ITEM;
        return SharedSuggestionProvider.suggestResource(itemRegistry.keySet(), builder);
    }
}
