package xyz.eclipseisoffline.playerparticles;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;
import xyz.eclipseisoffline.commonpermissionsapi.api.CommonPermissionNode;

public enum ParticleSlot implements StringRepresentable {
    BELOW("below", 5, 1.0, PlayerParticlePermissions.SLOT_BELOW),
    ABOVE("above", 2, 1.0, PlayerParticlePermissions.SLOT_ABOVE),
    AROUND("around", 3, 1.0, PlayerParticlePermissions.SLOT_AROUND);

    public static final Codec<ParticleSlot> CODEC = StringRepresentable.fromEnum(ParticleSlot::values);

    private final String name;
    private final int defaultCount;
    private final double defaultSpeed;
    private final CommonPermissionNode permissionNode;

    ParticleSlot(String name, int defaultCount, double defaultSpeed, CommonPermissionNode permissionNode) {
        this.name = name;
        this.defaultCount = defaultCount;
        this.defaultSpeed = defaultSpeed;
        this.permissionNode = permissionNode;
    }

    public int getDefaultCount() {
        return defaultCount;
    }

    public double getDefaultSpeed() {
        return defaultSpeed;
    }

    public CommonPermissionNode getPermissionNode() {
        return permissionNode;
    }

    @Override
    public String getSerializedName() {
        return name;
    }
}
