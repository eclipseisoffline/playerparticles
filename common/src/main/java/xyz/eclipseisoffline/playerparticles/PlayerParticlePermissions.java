package xyz.eclipseisoffline.playerparticles;

import xyz.eclipseisoffline.commonpermissionsapi.api.CommonPermissionNode;
import xyz.eclipseisoffline.commonpermissionsapi.api.CommonPermissions;

public interface PlayerParticlePermissions {
    CommonPermissionNode ROOT_NODE = createNode("command");

    CommonPermissionNode SLOT_BELOW = createNode("below");
    CommonPermissionNode SLOT_ABOVE = createNode("above");
    CommonPermissionNode SLOT_AROUND = createNode("around");

    private static CommonPermissionNode createNode(String name) {
        return CommonPermissions.node(PlayerParticlesMod.getModdedIdentifier(name));
    }

    static void bootstrap() {}
}
