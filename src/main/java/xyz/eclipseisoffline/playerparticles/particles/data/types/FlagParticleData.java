package xyz.eclipseisoffline.playerparticles.particles.data.types;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import xyz.eclipseisoffline.playerparticles.particles.data.ParticleData;

public class FlagParticleData extends ColorParticleData {

    public FlagParticleData(List<ColorData> colors) {
        super(colors);
    }

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSourceStack> context,
            SuggestionsBuilder builder) {
        return SharedSuggestionProvider.suggest(
                Arrays.stream(Flag.values())
                        .map(flag -> flag.toString().toLowerCase())
                        .toList(), builder);
    }

    @Override
    public ParticleData<List<ColorData>> parseData(CommandContext<CommandSourceStack> context,
            String input) throws CommandSyntaxException {
        try {
            Flag flag = Flag.valueOf(input.toUpperCase());
            return new FlagParticleData(flag.colors);
        } catch (IllegalArgumentException exception) {
            throw new SimpleCommandExceptionType(Component.literal("Unknown flag " + input)).create();
        }
    }

    private enum Flag {
        PRIDE(0xE40303, 0xFF8C00, 0xFFED00, 0x008026, 0x24408E, 0x732982),
        TRANS(0x5BCEFA, 0xF5A9B8, 0xFFFFFF, 0xF5A9B8, 0x5BCEFA),
        GAY(0x078D70, 0x26CEAA, 0x98E8C1, 0xFFFFFF, 0x7BADE2, 0x5049CC, 0x3D1A78),
        LESBIAN(0xD52D00, 0xEF7627, 0xFF9A56, 0xFFFFFF, 0xD162A4, 0xB55690, 0xA30262),
        BI(0xD60270, 0x9B4F96, 0x0038A8),
        PAN(0xFF218C, 0xFFD800, 0x21B1FF),
        POLY(0xF714BA, 0x01D66A, 0x1594F6),
        ASEXUAL(0x000000, 0xA3A3A3, 0xFFFFFF, 0x800080),
        AROMANTIC(0x3DA542, 0xA7D379, 0xFFFFFF, 0xA9A9A9, 0x000000),
        QUEERPLATONIC(0xFEE900, 0xF89FC9, 0xFFFFFF, 0x7E7E7E, 0x000000),
        NON_BINARY(0xFCF434, 0xFFFFFF, 0x9C59D1, 0x2C2C2C),
        GENDERFLUID(0xFF76A4, 0xFFFFFF, 0xC011D7, 0x000000, 0x2F3CBE),
        GENDERQUEER(0xB57EDC, 0xFFFFFF, 0x4A8123),
        LIBRAFEMININE(0x000000, 0xA3A3A3, 0xFFFFFF, 0xC6568F, 0xFFFFFF, 0xA3A3A3, 0x000000),
        LIBRAMASCULINE(0x000000, 0xA3A3A3, 0xFFFFFF, 0x56C5C5, 0xFFFFFF, 0xA3A3A3, 0x000000),
        AGENDER(0x000000, 0xBCC4C6, 0xFFFFFF, 0xB5F682, 0xFFFFFF, 0xBCC4C6, 0x000000),
        DEMIBOY(0x7F7F7F, 0xC3C3C3, 0x99D9EA, 0xFFFFFF, 0x99D9EA, 0xC3C3C3, 0x7F7F7F),
        DEMIGIRL(0x7F7F7F, 0xC3C3C3, 0xFFB0CA, 0xFFFFFF, 0xFFB0CA, 0xC3C3C3, 0x7F7F7F),
        AROACE(0x38D00, 0xE7C601, 0xFFFFFF, 0x5FAAD7, 0x1F3554);

        private final List<ColorData> colors;
        Flag(int... colors) {
            List<ColorData> colorData = new ArrayList<>();
            for (int color : colors) {
                colorData.add(ColorData.fromInt(color));
            }
            this.colors = List.copyOf(colorData);
        }
    }
}
