package xyz.eclipseisoffline.playerparticles;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum ParticleSlot implements StringRepresentable {
    BELOW("below", 5, 1.0),
    ABOVE("above", 2, 1.0),
    AROUND("around", 3, 1.0);

    public static final Codec<ParticleSlot> CODEC = StringRepresentable.fromEnum(ParticleSlot::values);

    private final String name;
    private final int defaultCount;
    private final double defaultSpeed;
    ParticleSlot(String name, int defaultCount, double defaultSpeed) {
        this.name = name;
        this.defaultCount = defaultCount;
        this.defaultSpeed = defaultSpeed;
    }

    public int getDefaultCount() {
        return defaultCount;
    }

    public double getDefaultSpeed() {
        return defaultSpeed;
    }

    @Override
    public @NotNull String getSerializedName() {
        return name;
    }
}
