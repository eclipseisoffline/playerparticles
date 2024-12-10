package xyz.eclipseisoffline.playerparticles.particles.color;

import java.util.List;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import xyz.eclipseisoffline.playerparticles.ParticleSlot;
import xyz.eclipseisoffline.playerparticles.particles.PlayerParticle;
import xyz.eclipseisoffline.playerparticles.particles.data.ParticleData;
import xyz.eclipseisoffline.playerparticles.particles.data.ParticleDataType;
import xyz.eclipseisoffline.playerparticles.particles.data.types.ColorParticleData;
import xyz.eclipseisoffline.playerparticles.particles.data.types.ColorParticleData.ColorData;

public class ColorParticle implements PlayerParticle {
    private static final int COLOR_TIME = 20;

    @Override
    public void tick(ServerLevel level, ServerPlayer player, ParticleSlot slot,
            ParticleData<?> data) {
        List<ColorData> colors = ((ColorParticleData) data).getData();
        int i = (int) ((level.getGameTime() / COLOR_TIME) % colors.size());

        int j = i + 1;
        if (j >= colors.size()) {
            j = 0;
        }

        ColorData firstColor = colors.get(i);
        ColorData secondColor = colors.get(j);
        sendParticles(level, player,
                createColorParticle(player, slot, firstColor, secondColor),
                defaultParticlePos(player, slot),
                defaultParticleOffset(slot), slot == ParticleSlot.AROUND ? 2 : 5, 1.0);
    }

    protected ParticleOptions createColorParticle(ServerPlayer player, ParticleSlot slot, ColorData firstColor, ColorData secondColor) {
        return new DustColorTransitionOptions(firstColor.toInt(1.0F), secondColor.toInt(1.0F), 1.0F);
    }

    @Override
    public boolean canWear(ParticleSlot slot) {
        return true;
    }

    @Override
    public ParticleDataType<?> getParticleDataType() {
        return ParticleDataType.COLOR;
    }
}
