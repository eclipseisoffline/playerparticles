package xyz.eclipseisoffline.playerparticles;

public enum ParticleSlot {
    BELOW("below", 5, 1.0),
    ABOVE("above", 2, 1.0),
    AROUND("around", 3, 1.0);

    private final String displayName;
    private final int defaultCount;
    private final double defaultSpeed;
    ParticleSlot(String displayName, int defaultCount, double defaultSpeed) {
        this.displayName = displayName;
        this.defaultCount = defaultCount;
        this.defaultSpeed = defaultSpeed;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getDefaultCount() {
        return defaultCount;
    }

    public double getDefaultSpeed() {
        return defaultSpeed;
    }
}
