package xyz.eclipseisoffline.playerparticles.particles.simple;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Unit;
import xyz.eclipseisoffline.playerparticles.ParticleSlot;
import xyz.eclipseisoffline.playerparticles.particles.PlayerParticle;
import xyz.eclipseisoffline.playerparticles.particles.data.ParticleDataType;

public class PaleOakLeavesParticle implements PlayerParticle<Unit> {

    @Override
    public void tick(ServerLevel level, ServerPlayer player, ParticleSlot slot, Unit data) {
        if (slot == ParticleSlot.BELOW) {
            sendParticles(level, player, ParticleTypes.PALE_OAK_LEAVES,
                    defaultParticlePos(player, slot), defaultParticleOffset(slot), 3, 1.0);
        } else if (level.getServer().getTickCount() % 2 == 0) {
            sendParticles(level, player, ParticleTypes.PALE_OAK_LEAVES,
                    defaultParticlePos(player, slot), defaultParticleOffset(slot).multiply(2.0, 2.0, 2.0),
                    1, 1.0);
        }
    }

    @Override
    public boolean canWear(ParticleSlot slot) {
        return slot == ParticleSlot.BELOW || slot == ParticleSlot.AROUND;
    }

    @Override
    public ParticleDataType<Unit> particleDataType() {
        return ParticleDataType.EMPTY;
    }
}
