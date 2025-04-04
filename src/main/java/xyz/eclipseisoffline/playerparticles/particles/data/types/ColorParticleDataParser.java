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

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ARGB;
import xyz.eclipseisoffline.playerparticles.particles.data.ParticleDataParser;
import xyz.eclipseisoffline.playerparticles.particles.data.types.ColorParticleDataParser.ColorData;

public class ColorParticleDataParser implements ParticleDataParser<List<ColorData>> {

    public static final MapCodec<List<ColorData>> CODEC = ColorData.CODEC.listOf().fieldOf("colors");

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSourceStack> context,
            SuggestionsBuilder builder) {
        return SharedSuggestionProvider.suggest(Collections.singleton("#"), builder);
    }

    @Override
    public List<ColorData> parseData(CommandContext<CommandSourceStack> context,
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

        return colors;
    }

    public record ColorData(float red, float green, float blue) {
        public static final Codec<ColorData> CODEC = Codec.FLOAT.listOf(3, 3).xmap(ColorData::fromList, ColorData::toList);

        public static ColorData fromList(List<Float> floats) {
            if (floats.size() != 3) {
                throw new IllegalArgumentException("Must be exactly 3 floats");
            }
            return new ColorData(floats.get(0), floats.get(1), floats.get(2));
        }

        public List<Float> toList() {
            return List.of(red, green, blue);
        }

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
