package xyz.eclipseisoffline.playerparticles;

import net.minecraft.resources.Identifier;
import xyz.eclipseisoffline.filefixutils.api.FileFixHelpers;
import xyz.eclipseisoffline.filefixutils.api.FileFixInitializer;

public class PlayerParticlesFileFixes implements FileFixInitializer {

    @Override
    public void onFileFixPopulate() {
        FileFixHelpers.registerGlobalDataMoveFileFix("playerparticles", Identifier.fromNamespaceAndPath("playerparticles", "data"));
    }
}
