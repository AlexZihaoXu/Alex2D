package site.alex_xu.dev.alex2d.graphics.gl;

public abstract class BufferType extends Freeable {

    protected int id;
    protected int length;

    public int length() {
        return length;
    }

    abstract protected void onDispose();

    public int getID() {
        return id;
    }

    abstract protected void bind();

    abstract protected void unbind();

}

