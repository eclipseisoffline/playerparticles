package xyz.eclipseisoffline.playerparticles;

import xyz.eclipseisoffline.filefixutils.api.FileFixHelpers;

public interface PlayerParticlesFileFixers {

    static void bootstrap() {
        FileFixHelpers.registerGlobalDataMoveFileFix(PlayerParticleManager.LEGACY_ID, PlayerParticleManager.ID);
    }
}
