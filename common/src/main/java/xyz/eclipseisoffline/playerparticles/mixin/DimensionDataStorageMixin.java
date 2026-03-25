package xyz.eclipseisoffline.playerparticles.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.SavedDataStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import xyz.eclipseisoffline.playerparticles.PlayerParticleManager;
import xyz.eclipseisoffline.playerparticles.PlayerParticlesMod;

import java.nio.file.Path;

@Mixin(SavedDataStorage.class)
public abstract class DimensionDataStorageMixin implements AutoCloseable {

    @Shadow
    protected abstract Path getDataFile(Identifier id);

    @WrapOperation(method = "readTagFromDisk", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NbtUtils;getDataVersion(Lnet/minecraft/nbt/CompoundTag;I)I"))
    public int dataFixPlayerParticles(CompoundTag tag, int defaultValue, Operation<Integer> original,
                                      @Local(argsOnly = true) Path dataFile, @Local(name = "tag") LocalRef<CompoundTag> actualTag) throws Throwable {
        int dataVersion = original.call(tag, defaultValue);
        if (dataFile.equals(getDataFile(PlayerParticleManager.ID)) && dataVersion < 4325) { // 1.21.5
            try {
                CompoundTag fixed = PlayerParticleManager.dataFix(tag, dataVersion);
                actualTag.set(fixed);
            } catch (Exception exception) {
                PlayerParticlesMod.LOGGER.error("An error occurred while trying to datafix player particle data!", exception);
                PlayerParticlesMod.LOGGER.error("Crashing to prevent data loss, please report this to PlayerParticles!");
                throw new Throwable(exception);
            }
        }
        return dataVersion;
    }
}
