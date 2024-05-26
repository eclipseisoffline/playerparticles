package xyz.eclipseisoffline.playerparticles.particles.item;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import xyz.eclipseisoffline.playerparticles.ParticleSlot;
import xyz.eclipseisoffline.playerparticles.particles.PlayerParticle;
import xyz.eclipseisoffline.playerparticles.particles.data.ParticleData;
import xyz.eclipseisoffline.playerparticles.particles.data.ParticleDataType;
import xyz.eclipseisoffline.playerparticles.particles.data.types.ItemParticleData;

public class ItemParticle implements PlayerParticle {

    @Override
    public void tick(ServerLevel level, ServerPlayer player, ParticleSlot slot,
            ParticleData<?> data) {
        assert data instanceof ItemParticleData;
        sendParticles(level, player,
                new ItemParticleOption(ParticleTypes.ITEM, ((ItemParticleData) data).getData()),
                defaultParticlePos(player, slot), defaultParticleOffset(slot), 5, 0.05);
    }

    @Override
    public boolean canWear(ParticleSlot slot) {
        return slot == ParticleSlot.AROUND || slot == ParticleSlot.BELOW;
    }

    @Override
    public ParticleDataType<?> getParticleDataType() {
        return ParticleDataType.ITEM;
    }
}
