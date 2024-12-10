package xyz.eclipseisoffline.playerparticles.particles.data.types;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ARGB;
import xyz.eclipseisoffline.playerparticles.particles.data.ParticleData;
import xyz.eclipseisoffline.playerparticles.particles.data.SimpleDataHolder;
import xyz.eclipseisoffline.playerparticles.particles.data.types.ColorParticleData.ColorData;

public class ColorParticleData extends SimpleDataHolder<List<ColorData>> {

    public ColorParticleData(List<ColorData> colors) {
        super(colors);
    }

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSourceStack> context,
            SuggestionsBuilder builder) {
        return SharedSuggestionProvider.suggest(Collections.singleton("#"), builder);
    }

    @Override
    public ParticleData<List<ColorData>> parseData(CommandContext<CommandSourceStack> context,
            String input) throws CommandSyntaxException {
        String[] codes = input.split(" ");
        List<ColorData> colors = new ArrayList<>();

        for (String code : codes) {
            code = code.replace("#", "");

            if (code.length() != 6) {
                throw new SimpleCommandExceptionType(Component.literal(code + ": colour must be a 6-digit hexadecimal code")).create();
            }

            try {
                colors.add(ColorData.fromInt(Integer.valueOf(code, 16)));
            } catch (NumberFormatException exception) {
                throw new SimpleCommandExceptionType(Component.literal(code + ": invalid hexadecimal code")).create();
            }
        }

        return new ColorParticleData(colors);
    }

    @Override
    public List<ColorData> readData(ServerLevel level, CompoundTag tag) {
        ListTag colorsTag = (ListTag) tag.get("colors");
        if (colorsTag == null) {
            return List.of();
        }

        List<ColorData> colors = new ArrayList<>();
        for (int i = 0; i < colorsTag.size(); i++) {
            ListTag colorTag = colorsTag.getList(i);
            float red = colorTag.getFloat(0);
            float green = colorTag.getFloat(1);
            float blue = colorTag.getFloat(2);
            colors.add(new ColorData(red, green, blue));
        }
        return colors;
    }

    @Override
    public void saveData(ServerLevel level, CompoundTag tag) {
        ListTag colors = new ListTag();
        for (ColorData color : getData()) {
            ListTag colorTag = new ListTag();
            colorTag.add(FloatTag.valueOf(color.red));
            colorTag.add(FloatTag.valueOf(color.green));
            colorTag.add(FloatTag.valueOf(color.blue));
            colors.add(colorTag);
        }
        tag.put("colors", colors);
    }

    public record ColorData(float red, float green, float blue) {

        public static ColorData fromInt(int color) {
            int red = color >> 16;
            int green = (color >> 8) & 0xFF;
            int blue = color & 0xFF;
            float fRed = (float) red / 0xFF;
            float fGreen = (float) green / 0xFF;
            float fBlue = (float) blue / 0xFF;
            return new ColorData(fRed, fGreen, fBlue);
        }

        public int toInt(float alpha) {
            return ARGB.colorFromFloat(alpha, red, green, blue);
        }
    }
}
