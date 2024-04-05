package xyz.eclipseisoffline.playerparticles.particles.data;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.world.item.Item;

public abstract class SimpleDataHolder<T> implements ParticleData<T> {
    private final T data;

    protected SimpleDataHolder(T data) {
        this.data = data;
    }

    @Override
    public T getData() {
        return data;
    }
}
