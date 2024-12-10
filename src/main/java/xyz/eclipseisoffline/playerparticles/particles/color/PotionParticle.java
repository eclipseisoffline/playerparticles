package xyz.eclipseisoffline.playerparticles.particles.color;

import java.util.List;
import net.minecraft.Util;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import xyz.eclipseisoffline.playerparticles.ParticleSlot;
import xyz.eclipseisoffline.playerparticles.particles.data.ParticleData;
import xyz.eclipseisoffline.playerparticles.particles.data.types.ColorParticleData;
import xyz.eclipseisoffline.playerparticles.particles.data.types.ColorParticleData.ColorData;

public class PotionParticle extends ColorParticle {

    @Override
    public void tick(ServerLevel level, ServerPlayer player, ParticleSlot slot,
            ParticleData<?> data) {
        ColorData color ;
        if (data != null) {
            List<ColorData> colors = ((ColorParticleData) data).getData();
            color = Util.getRandom(colors, player.getRandom());
        } else {
            color = new ColorData(player.getRandom().nextFloat(), player.getRandom().nextFloat(), player.getRandom().nextFloat());
        }

        sendParticles(level, player,
                ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, color.toInt(0.2F)),
                defaultParticlePos(player, slot), defaultParticleOffset(slot),
                5, 1.0);
    }

    @Override
    public boolean canWear(ParticleSlot slot) {
        return slot == ParticleSlot.AROUND;
    }

    @Override
    public boolean particleDataRequired() {
        return false;
    }
}
