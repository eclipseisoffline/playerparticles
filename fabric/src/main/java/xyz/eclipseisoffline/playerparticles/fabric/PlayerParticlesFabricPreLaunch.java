package xyz.eclipseisoffline.playerparticles.fabric;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import xyz.eclipseisoffline.playerparticles.PlayerParticlesFileFixers;

public class PlayerParticlesFabricPreLaunch implements PreLaunchEntrypoint {

    @Override
    public void onPreLaunch() {
        PlayerParticlesFileFixers.bootstrap();
    }
}
