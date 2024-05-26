package xyz.eclipseisoffline.playerparticles.particles.color;

import java.util.List;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.joml.Vector3f;
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
                new DustColorTransitionOptions(firstColor.toVector(), secondColor.toVector(), 1.0F),
                defaultParticlePos(player, slot),
                defaultParticleOffset(slot), slot == ParticleSlot.AROUND ? 2 : 5, 1.0);
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
