package site.alex_xu.dev.alex2d.graphics;

import site.alex_xu.dev.alex2d.graphics.abstracting.AbstractFrameI;

abstract class BaseRenderer {

    // Stroke

    void strokeLine(float x1, float y1, float x2, float y2) {
        // TODO
    }

    void strokeTriangle(float x1, float y1, float x2, float y2, float x3, float y3) {
        // TODO
    }

    void strokeCircle(float x, float y, float radius) {
        // TODO
    }

    void strokeRect(float x, float y, float w, float h) {
        // TODO
    }

    // Fill

    void fillTriangle(float x1, float y1, float x2, float y2, float x3, float y3) {
        // TODO
    }

    void fillCircle(float x, float y, float radius) {
        // TODO
    }

    void fillRect(float x, float y, float w, float h) {
        // TODO
    }

    // Image

    void drawImage(AbstractFrameI image, float srcX, float srcY, float srcW, float srcH, float dstX, float dstY, float dstW, float dstH) {
        // TODO
    }

    // Transformations

    void translate(float x, float y) {
        // TODO
    }

    void rotate(float r) {
        // TODO
    }

    void scale(float x, float y) {
        // TODO
    }

    // Setters

    void setColor(float r, float g, float b, float a) {
        // TODO
    }

}
