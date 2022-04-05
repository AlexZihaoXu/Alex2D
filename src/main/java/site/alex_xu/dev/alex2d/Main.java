package site.alex_xu.dev.alex2d;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.Mass;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.world.World;
import site.alex_xu.dev.alex2d.graphics.*;
import site.alex_xu.dev.alex2d.graphics.Window;
import site.alex_xu.dev.alex2d.sounds.Listener;
import site.alex_xu.dev.alex2d.sounds.Sound;
import site.alex_xu.dev.alex2d.sounds.SoundSource;
import site.alex_xu.dev.alex2d.system.Clock;

import java.io.IOException;
import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

class RigidRectangle {
    Body body;
    float colorR, colorG, colorB;
    float x, y, w, h;
    static ArrayList<RigidRectangle> allRectangles = new ArrayList<>();

    public static void drawAll(Renderer renderer) {
        for (RigidRectangle rectangle : allRectangles) {
            rectangle.draw(renderer);
        }
    }

    public RigidRectangle(World<Body> world, float x, float y, float w, float h) {
        allRectangles.add(this);
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        body = new Body();
        body.addFixture(new Rectangle(w, h));
        body.setMass(MassType.INFINITE);
        body.translate(x + w / 2f, y + h / 2f);
        world.addBody(this.body);
        colorR = (float) (Math.random() * 0.8f + 0.2f);
        colorG = (float) (Math.random() * 0.8f + 0.2f);
        colorB = (float) (Math.random() * 0.8f + 0.2f);
    }

    public void draw(Renderer renderer) {
        x = (float) body.getTransform().getTranslationX() - w / 2f;
        y = (float) body.getTransform().getTranslationY() - h / 2f;
        renderer.setColor(colorR, colorG, colorB);
        renderer.fillRect(x, y, w, h);
        renderer.setColor(colorR * 0.2f, colorG * 0.2f, colorB * 0.2f);
        renderer.setStrokeWidth(0.3f);
        renderer.strokeRect(x, y, w, h);
    }

}

class Ball {
    Body body;
    float colorR, colorG, colorB;
    float r;

    public static void add(World<Body> world, float x, float y, float r) {
        new Ball(world, x, y, r);
    }

    static ArrayList<Ball> balls = new ArrayList<>();

    public static void drawAll(Renderer renderer) {
        for (Ball ball : balls) {
            ball.draw(renderer);
        }
    }

    public Ball(World<Body> world, float x, float y, float r) {
        this.r = r;
        body = new Body();
        body.addFixture(new Circle(r), r, 0.1, 0.2);
        body.setMass(MassType.NORMAL);
        body.translate(x, y);
        balls.add(this);
        world.addBody(body);

        colorR = (float) (Math.random() * 0.8f + 0.2f);
        colorG = (float) (Math.random() * 0.8f + 0.2f);
        colorB = (float) (Math.random() * 0.8f + 0.2f);
    }

    public void draw(Renderer renderer) {
        float x = (float) body.getTransform().getTranslationX();
        float y = (float) body.getTransform().getTranslationY();
        float angle = (float) body.getTransform().getRotationAngle();
        renderer.setColor(colorR * 0.2f, colorG * 0.2f, colorB * 0.2f);
        renderer.fillCircle(x, y, r * 2 + 0.5f);
        renderer.setColor(colorR, colorG, colorB);
        renderer.fillCircle(x, y, r * 2);
        renderer.setColor(colorR * 0.2f, colorG * 0.2f, colorB * 0.2f);
        renderer.setStrokeWidth(0.3f);
        renderer.strokeLine(x, y, (float) (x + Math.cos(angle) * r), (float) (y + Math.sin(angle) * r));
    }
}

public class Main {
    static float mouseX = 0, mouseY = 0;

    public static void main(String[] args) throws InterruptedException, IOException {
        Window window = new Window();
        window.setVSyncEnabled(true);
        window.setResizable(true);
        window.setVisible(true);
        window.setTitle("Physics Simulation");

        Renderer renderer = new Renderer(window);
        Clock clock = new Clock();

        // Physics

        World<Body> world = new World<>();
        world.setGravity(0, 9.8 * 1.2);
        for (int x = -10; x < 10; x++) {
            Ball.add(world, (float) x, (float) (-50 + Math.random() * 10), (float) (0.8f + Math.random() * 0.3));
        }

        RigidRectangle rectangle = new RigidRectangle(world, -50, 60, 100, 4);


        //


        glfwSetCursorPosCallback(window.getHandle(), (window1, xpos, ypos) -> {
            mouseX = (float) xpos;
            mouseY = (float) ypos;
        });

        float shiftX = 0;
        float shiftY = 0;

        Clock ballClock = new Clock();

        while (window.isAlive()) {
            if (ballClock.elapsedTime() > 1 / 20f) {
                Ball.add(world, (float) (Math.random() * 10), (float) (-50 + Math.random() * 10), 0.8f);
                ballClock.reset();
            }

            window.render();
            renderer.clear(0.745f, 0.894f, 0.952f);

            float dt = clock.reset();

            // Render World
            renderer.pushMatrix();
            {
                renderer.translate(window.getWidth() / 2f, window.getHeight() / 2f);
                renderer.translate(0, -25);
                renderer.translate(shiftX, shiftY);
                shiftX += ((float) (((mouseX / window.getWidth()) - 0.5) * 2) * 50 - shiftX) * Math.min(1, dt) * 20;
                shiftY += ((float) (((mouseY / window.getHeight()) - 0.5) * 2) * 50 - shiftY) * Math.min(1, dt) * 20;
                renderer.scale(window.getWidth() / 250f);
                RigidRectangle.drawAll(renderer);
                Ball.drawAll(renderer);
            }
            renderer.popMatrix();

            String[] debugStrings = new String[]{
                    "FPS: " + Math.round(1 / dt),
                    "Mouse: " + (int) (mouseX) + ", " + (int) (mouseY),
                    "Ball Count: " + Ball.balls.size()
            };
            // Draw Debug Strings
            {
                int[] widths = new int[debugStrings.length];
                int maxWidth = 0;
                for (int i = 0; i < widths.length; i++) {
                    widths[i] = (int) renderer.getTextWidth(debugStrings[i]);
                    maxWidth = Math.max(maxWidth, widths[i]);
                }
                renderer.setColor(0, 0.6f);
                renderer.fillRect(0, 0, maxWidth, (renderer.getFontSize() + 2) * widths.length);
                renderer.setColor(0, 0.7f, 0);
                for (int i = 0; i < debugStrings.length; i++) {
                    renderer.drawText(debugStrings[i], 0, i * (renderer.getFontSize() + 2));
                }
            }
            world.update(dt, 50);

            {
                ArrayList<Ball> toBeRemoved = new ArrayList<>();
                for (Ball ball : Ball.balls) {
                    if (ball.body.getTransform().getTranslationY() > 150) {
                        toBeRemoved.add(ball);
                    }
                }
                for (Ball ball : toBeRemoved) {
                    Ball.balls.remove(ball);
                    world.removeBody(ball.body);
                }
            }

        }

    }
}
