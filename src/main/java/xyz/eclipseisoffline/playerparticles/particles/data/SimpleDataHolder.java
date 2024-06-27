package xyz.eclipseisoffline.playerparticles.particles.data;

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
