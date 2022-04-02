package site.alex_xu.dev.alex2d.graphics.gl;

import java.util.LinkedList;

import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;

public abstract class Freeable {
    protected static LinkedList<Integer> freedBuffers = new LinkedList<>();
    protected static LinkedList<Integer> freedVAOs = new LinkedList<>();
    protected static LinkedList<Integer> freedShaders = new LinkedList<>();

    public static void gc() {
        while (!freedVAOs.isEmpty()) {
            glDeleteVertexArrays(freedVAOs.pop());
        }
        while (!freedBuffers.isEmpty()) {
            glDeleteBuffers(freedBuffers.pop());
        }
        while (!freedShaders.isEmpty()) {
            glDeleteProgram(freedShaders.pop());
        }
    }

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