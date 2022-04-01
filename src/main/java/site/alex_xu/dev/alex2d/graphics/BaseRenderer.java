package site.alex_xu.dev.alex2d.graphics;

import org.joml.Matrix4f;
import site.alex_xu.dev.alex2d.graphics.abstracting.AbstractFrameI;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

abstract class BaseRenderer {

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
        // TODO
    }

}
