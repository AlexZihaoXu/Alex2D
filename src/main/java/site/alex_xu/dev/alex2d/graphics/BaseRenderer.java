package site.alex_xu.dev.alex2d.graphics;

import org.joml.Matrix4f;
import site.alex_xu.dev.alex2d.graphics.abstracting.AbstractFrameI;
import site.alex_xu.dev.alex2d.graphics.gl.Shader;
import site.alex_xu.dev.alex2d.graphics.gl.VertexArray;
import site.alex_xu.dev.alex2d.graphics.gl.VertexBuffer;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Stack;

import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

abstract class BaseRenderer {

    private static boolean initialized = false;

    // Triangles

    private static Shader trianglesShader;
    private static VertexArray trianglesVAO;

    private static Shader circleShader;
    private static VertexArray circleVAO;

    private static Shader textureShader;
    private static VertexArray textureVAO;

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
        if (circleShader == null) {
            circleShader = new Shader()
                    .addFromResource("shaders/circle.frag")
                    .addFromResource("shaders/circle.vert")
                    .link();
            circleVAO = new VertexArray();
            VertexBuffer trianglesVBO = new VertexBuffer(new float[]{
                    -1, -1, 1, -1, 1, 1,
                    1, 1, -1, 1, -1, -1
            });
            circleVAO.configure(trianglesVBO).push(2).apply();
        }
        if (textureShader == null) {
            textureShader = new Shader()
                    .addFromResource("shaders/texture.frag")
                    .addFromResource("shaders/texture.vert")
                    .link();
            textureVAO = new VertexArray();
            VertexBuffer textureVBO = new VertexBuffer(new float[]{
                    0, 0, 0, 1, 1, 1,
                    1, 1, 1, 0, 0, 0
            });
            textureVAO.configure(textureVBO).push(2).apply();
        }
        initialized = true;
    }

    // Cache

    private Window window = null;
    private BufferedTexture bufferedTexture = null;
    private Matrix4f orthoMatrix = new Matrix4f();
    private float r = 1, g = 1, b = 1, a = 1;
    private final Stack<Matrix4f> matrixStack = new Stack<>();

    //
    private void prepareDraw() {
        init();
        AbstractFrameI frame;
        if (window != null) {
            window.bindContext();
            frame = window;
        } else {
            bufferedTexture.bindContext();
            frame = bufferedTexture;
        }
        glViewport(0, 0, frame.getWidth(), frame.getHeight());
        orthoMatrix = new Matrix4f().ortho(0, frame.getWidth(), frame.getHeight(), 0, -1, 1);
    }

    public BaseRenderer(Window window) {
        this.window = window;
        matrixStack.push(new Matrix4f());
    }

    public BaseRenderer(BufferedTexture bufferedTexture) {
        this.bufferedTexture = bufferedTexture;
        matrixStack.push(new Matrix4f());
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

    // Clear

    public void clear(float r, float g, float b, float a) {
        prepareDraw();
        glClearColor(r, g, b, a);
        glClear(GL_COLOR_BUFFER_BIT);
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
        trianglesShader.setMat4("transMat", false, getTransformationsMatrix());

        trianglesVAO.bind();
        glDrawArrays(GL_TRIANGLES, 0, 3);
    }

    public void fillCircle(float x, float y, float radius) {
        prepareDraw();
        circleShader.bind();
        circleShader.setVec3("circle", x, y, radius);
        circleShader.setVec4("color", r, g, b, a);
        circleShader.setMat4("windowMat", false, orthoMatrix);
        circleShader.setMat4("transMat", false, getTransformationsMatrix());

        circleVAO.bind();
        glDrawArrays(GL_TRIANGLES, 0, 6);

        // TODO: Optimize to triangle instead of rectangle
    }

    public void fillRect(float x, float y, float w, float h) {
        prepareDraw();
        trianglesShader.bind();
        trianglesShader.setVec4("color", r, g, b, a);
        trianglesShader.setMat4("windowMat", false, orthoMatrix);
        trianglesShader.setMat4("transMat", false, getTransformationsMatrix());

        trianglesShader.setVec2("pos1", x, y + h);
        trianglesShader.setVec2("pos2", x + w, y);

        trianglesVAO.bind();

        trianglesShader.setVec2("pos3", x, y);
        glDrawArrays(GL_TRIANGLES, 0, 3);
        trianglesShader.setVec2("pos3", x + w, y + h);
        glDrawArrays(GL_TRIANGLES, 0, 3);
    }

    // Image

    public void drawImage(AbstractFrameI image, float srcX, float srcY, float srcW, float srcH, float dstX, float dstY, float dstW, float dstH) {
        if (image instanceof Texture) {
            prepareDraw();

            textureShader.bind();
            textureShader.setMat4("windowMat", false, orthoMatrix);
            textureShader.setMat4("transMat", false, getTransformationsMatrix());

            textureShader.setVec4("rect", dstX, dstY, dstW, dstH);
            textureShader.setVec4("srcRect", srcX, srcY, srcW, srcH);
            textureShader.setFloat("texWidth", image.getWidth());
            textureShader.setFloat("texHeight", image.getHeight());
            textureShader.setInt("texture0", 0);

            glActiveTexture(GL_TEXTURE0);
            ((Texture) image).bind();

            textureVAO.bind();
            glDrawArrays(GL_TRIANGLES, 0, 6);
        } else if (image instanceof BufferedTexture) {
            prepareDraw();

            textureShader.bind();
            textureShader.setMat4("windowMat", false, orthoMatrix);
            textureShader.setMat4("transMat", false, getTransformationsMatrix());

            textureShader.setVec4("rect", dstX, dstY, dstW, dstH);
            textureShader.setVec4("srcRect", srcX, srcY, srcW, srcH);
            textureShader.setFloat("texWidth", image.getWidth() + 0.5f);
            textureShader.setFloat("texHeight", image.getHeight());
            textureShader.setInt("texture0", 0);

            glActiveTexture(GL_TEXTURE0);
            ((BufferedTexture) image).bind();

            textureVAO.bind();
            glDrawArrays(GL_TRIANGLES, 0, 6);
        }
    }

    // Transformations

    public void translate(float x, float y) {
        this.matrixStack.peek().translate(x, y, 0);
    }

    public void rotate(float r) {
        this.matrixStack.peek().rotate(r, 0, 0, 1);
    }

    public void scale(float x, float y) {
        this.matrixStack.peek().scale(x, y, 0);
    }

    public void pushMatrix() {
        this.matrixStack.push(new Matrix4f(this.matrixStack.peek()));
    }

    public void popMatrix() {
        this.matrixStack.pop();
    }

    public void resetMatrix() {
        this.matrixStack.pop();
        this.matrixStack.push(new Matrix4f());
    }

    public Matrix4f getTransformationsMatrix() {
        return this.matrixStack.peek();
    }

    // Setters

    public void setColor(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

}
