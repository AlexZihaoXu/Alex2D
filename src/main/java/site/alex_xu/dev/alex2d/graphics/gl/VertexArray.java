package site.alex_xu.dev.alex2d.graphics.gl;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL30.*;

public class VertexArray extends BufferType {

    private final ArrayList<Object> cachedObjects = new ArrayList<>();

    public static class VertexArrayLayout {
        private final VertexArray vao;
        private final VertexBuffer vbo;
        private int stride = 0;
        private final ArrayList<Integer> pushedList = new ArrayList<>();

        protected VertexArrayLayout(VertexArray vao, VertexBuffer vbo) {
            this.vao = vao;
            this.vbo = vbo;
        }

        public VertexArrayLayout push(int count) {
            pushedList.add(count);
            stride += count * 4;

            return this;
        }

        public void apply() {
            vao.bind();
            vbo.bind();

            int offset = 0;

            for (int i = 0; i < pushedList.size(); i++) {
                glEnableVertexAttribArray(i);
                glVertexAttribPointer(i, pushedList.get(i), GL_FLOAT, false, stride, offset);
                offset += pushedList.get(i) * 4;
            }
        }
    }

    public VertexArray() {
        id = glGenVertexArrays();
        if (id == 0) {
            String message = "Unable create vertex array object!";
            System.err.println(message);
            throw new IllegalStateException(message);
        }
        bind();
    }

    public VertexArrayLayout configure(VertexBuffer vbo) {
        cachedObjects.add(vbo);
        return new VertexArrayLayout(this, vbo);
    }

    @Override
    protected void onDispose() {
        freedVAOs.add(id);
    }

    private static VertexArray boundVAO = null;
    @Override
    public void bind() {
        if (boundVAO != this) {
            glBindVertexArray(id);
            boundVAO = this;
        }
    }

    @Override
    protected void unbind() {
        glBindVertexArray(0);
        boundVAO = null;
    }
}