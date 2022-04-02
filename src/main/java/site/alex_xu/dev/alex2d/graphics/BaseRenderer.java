package site.alex_xu.dev.alex2d.graphics;

import org.joml.Matrix4f;
import site.alex_xu.dev.alex2d.graphics.abstracting.AbstractFrameI;
import site.alex_xu.dev.alex2d.graphics.gl.Shader;
import site.alex_xu.dev.alex2d.graphics.gl.VertexArray;
import site.alex_xu.dev.alex2d.graphics.gl.VertexBuffer;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import static org.lwjgl.opengl.GL11C.*;

abstract class BaseRenderer {

    private static boolean initialized = false;

    // Triangles

    private static Shader trianglesShader;
    private static VertexArray trianglesVAO;

    private static void init() {
        if (initialized)
            return;
        if (trianglesShader == null) {
            trianglesShader = new Shader()
                    .addFromResource("shaders/triangles.frag")
                    .addFromResource("shaders/triangles.vert")
                    .link();
            trianglesVAO = new VertexArray();
            VertexBuffer trianglesVBO = new VertexBuffer(new float[]{0, 1, 2});
            trianglesVAO.configure(trianglesVBO).push(1).apply();
        }
        initialized = true;
    }

    // Cache

    private Window window = null;
    private Matrix4f orthoMatrix = new Matrix4f();
    private float r = 1, g = 1, b = 1, a = 1;

    //
    private void prepareDraw() {
        init();
        if (window != null) {
            window.bindContext();
            glViewport(0, 0, window.getWidth(), window.getHeight());
            orthoMatrix = new Matrix4f().ortho(0, window.getWidth(), window.getHeight(), 0, -1, 1);
        }
    }

    public BaseRenderer(Window window) {
        this.window = window;
    }


    // Stroke

    public void strokeLine(float x1, float y1, float x2, float y2) {
        // TODO
    }

    public void strokeTriangle(float x1, float y1, float x2, float y2, float x3, float y3) {
        // TODO
    }

    public void strokeCircle(float x, float y, float radius) {
        // TODO
    }

    public void strokeRect(float x, float y, float w, float h) {
        // TODO
    }

    // Fill

    public void fillTriangle(float x1, float y1, float x2, float y2, float x3, float y3) {
        prepareDraw();
        trianglesShader.bind();
        trianglesShader.setVec2("pos1", x1, y1);
        trianglesShader.setVec2("pos2", x2, y2);
        trianglesShader.setVec2("pos3", x3, y3);
        trianglesShader.setVec4("color", r, g, b, a);
        trianglesShader.setMat4("windowMat", false, orthoMatrix);
        trianglesShader.setMat4("transMat", false, new Matrix4f());

        trianglesVAO.bind();
        glDrawArrays(GL_TRIANGLES, 0, 3);

        // TODO
    }

    public void fillCircle(float x, float y, float radius) {
        // TODO
    }

    public void fillRect(float x, float y, float w, float h) {
        // TODO
    }

    // Image

    public void drawImage(AbstractFrameI image, float srcX, float srcY, float srcW, float srcH, float dstX, float dstY, float dstW, float dstH) {
        // TODO
    }

    // Transformations

    public void translate(float x, float y) {
        // TODO
    }

    public void rotate(float r) {
        // TODO
    }

    public void scale(float x, float y) {
        // TODO
    }

    public void pushMatrix() {
        // TODO
    }

    public void popMatrix() {
        // TODO
    }

    public void resetMatrix() {
        // TODO
    }

    public Matrix4f copyMatrix() {
        throw new NotImplementedException();
    }

    // Setters

    public void setColor(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

}
