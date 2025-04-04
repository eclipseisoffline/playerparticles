package xyz.eclipseisoffline.playerparticles.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.storage.DimensionDataStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xyz.eclipseisoffline.playerparticles.PlayerParticleManager;
import xyz.eclipseisoffline.playerparticles.PlayerParticlesInitializer;

@Mixin(DimensionDataStorage.class)
public abstract class DimensionDataStorageMixin implements AutoCloseable {

    @WrapOperation(method = "readTagFromDisk", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NbtUtils;getDataVersion(Lnet/minecraft/nbt/CompoundTag;I)I"))
    public int dataFixPlayerParticles(CompoundTag tag, int defaultValue, Operation<Integer> original,
                                      @Local(argsOnly = true) String filename, @Local LocalRef<CompoundTag> actualTag) throws Throwable {
        int dataVersion = original.call(tag, defaultValue);
        if (filename.equals(PlayerParticleManager.TYPE.id()) && dataVersion < 4325) { // 1.21.5
            try {
                CompoundTag fixed = PlayerParticleManager.dataFix(tag, dataVersion);
                System.out.println(fixed);
                actualTag.set(fixed);
            } catch (Exception exception) {
                PlayerParticlesInitializer.LOGGER.error("An error occurred while trying to datafix player particle data!", exception);
                PlayerParticlesInitializer.LOGGER.error("Crashing to prevent data loss, please report this to PlayerParticles!");
                throw new Throwable(exception);
            }
        }
        return dataVersion;
    }
}
