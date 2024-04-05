package xyz.eclipseisoffline.playerparticles.particles.color;

import java.util.List;
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
        assert data instanceof ColorParticleData;

        List<ColorData> colors = (List<ColorData>) data.getData();
        int i = (int) ((level.getGameTime() / COLOR_TIME) % colors.size());

        ColorData color = colors.get(i);
        sendParticles(level,
                new DustParticleOptions(new Vector3f(color.red(), color.green(), color.blue()), 1.0F),
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
