package site.alex_xu.dev.alex2d.graphics.gl;

public abstract class Freeable {
    private boolean freed = false;

    abstract protected void onDispose();

    public final boolean isFreed() {
        return freed;
    }

    public final void free() {
        if (isFreed())
            return;
        onDispose();
        freed = true;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        free();
    }
}