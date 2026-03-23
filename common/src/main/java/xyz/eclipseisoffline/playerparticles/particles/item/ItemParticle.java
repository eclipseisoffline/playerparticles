package xyz.eclipseisoffline.playerparticles.particles.item;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import xyz.eclipseisoffline.playerparticles.ParticleSlot;
import xyz.eclipseisoffline.playerparticles.particles.PlayerParticle;
import xyz.eclipseisoffline.playerparticles.particles.data.ParticleDataType;

public class ItemParticle implements PlayerParticle<ItemStack> {

    @Override
    public void tick(ServerLevel level, ServerPlayer player, ParticleSlot slot, ItemStack stack) {
        sendParticles(level, player,
                new ItemParticleOption(ParticleTypes.ITEM, stack),
                defaultParticlePos(player, slot), defaultParticleOffset(slot), 5, 0.05);
    }

    @Override
    public boolean canWear(ParticleSlot slot) {
        return slot == ParticleSlot.AROUND || slot == ParticleSlot.BELOW;
    }

    @Override
    public ParticleDataType<ItemStack> particleDataType() {
        return ParticleDataType.ITEM;
    }
}
