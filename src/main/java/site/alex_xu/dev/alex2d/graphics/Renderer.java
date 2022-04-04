package site.alex_xu.dev.alex2d.graphics;

import org.joml.Vector2f;
import site.alex_xu.dev.alex2d.graphics.abstracting.AbstractFrameI;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Renderer extends BaseRenderer {
    public Renderer(Window window) {
        super(window);
    }

    public Renderer(BufferedTexture bufferedTexture) {
        super(bufferedTexture);
    }

    // Stroke

    public void strokeLine(Vector2f p1, Vector2f p2) {
        strokeLine(p1.x, p1.y, p2.x, p2.y);
    }

    public void strokeTriangle(Vector2f p1, Vector2f p2, Vector2f p3) {
        strokeTriangle(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y);
    }
    public void strokeRect(Rectangle rect) {
        strokeRect(rect.x, rect.y, rect.width, rect.height);
    }

    public void strokeRect(Rectangle2D rect) {
        strokeRect((float) rect.getX(), (float) rect.getY(), (float) rect.getWidth(), (float) rect.getHeight());
    }

    public void strokeRect(Rectangle2D.Float rect) {
        strokeRect(rect.x, rect.y, rect.width, rect.height);
    }

    public void strokeRect(Vector2f pos, float width, float height) {
        strokeRect(pos.x, pos.y, width, height);
    }

    // Clear

    public void clear(float brightness) {
        clear(brightness, 1);
    }

    public void clear(float brightness, float alpha) {
        clear(brightness, brightness, brightness, alpha);
    }

    public void clear(float r, float g, float b) {
        clear(r, g, b, 1);
    }

    // Fill

    public void fillTriangle(Vector2f p1, Vector2f p2, Vector2f p3) {
        fillTriangle(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y);
    }

    public void fillCircle(Vector2f center, float radius) {
        fillCircle(center.x, center.y, radius);
    }

    public void fillRect(Rectangle rect) {
        fillRect(rect.x, rect.y, rect.width, rect.height);
    }

    public void fillRect(Rectangle2D rect) {
        fillRect((float) rect.getX(), (float) rect.getY(), (float) rect.getWidth(), (float) rect.getHeight());
    }

    public void fillRect(Rectangle2D.Float rect) {
        fillRect(rect.x, rect.y, rect.width, rect.height);
    }

    public void fillRect(Vector2f pos, float width, float height) {
        fillRect(pos.x, pos.y, width, height);
    }

    // Image

    public void drawImage(AbstractFrameI image, float srcX, float srcY, float srcW, float srcH, float dstX, float dstY) {
        drawImage(image, srcX, srcY, srcW, srcH, dstX, dstY, srcW, srcH, 1, 1, 1, 1);
    }

    public void drawImage(AbstractFrameI image, float x, float y) {
        drawImage(image, 0, 0, image.getWidth(), image.getHeight(), x, y, image.getWidth(), image.getHeight(), 1, 1, 1, 1);
    }

    // Transformations

    public void translate(Vector2f xy) {
        translate(xy.x, xy.y);
    }

    public void scale(Vector2f xy) {
        scale(xy.x, xy.y);
    }

    public void scale(float scale) {
        scale(scale, scale);
    }

    // Setters

    public void setColor(float r, float g, float b) {
        setColor(r, g, b, 1);
    }

    public void setColor(float brightness, float alpha) {
        setColor(brightness, brightness, brightness, alpha);
    }

    public void setColor(float brightness) {
        setColor(brightness, 1);
    }


}
