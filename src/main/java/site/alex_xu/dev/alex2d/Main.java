package site.alex_xu.dev.alex2d;

import org.apache.commons.io.IOUtils;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import site.alex_xu.dev.alex2d.graphics.*;
import site.alex_xu.dev.alex2d.graphics.Window;
import site.alex_xu.dev.alex2d.system.Clock;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;

import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.opengl.GL11.*;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        Window window = new Window();
        window.setResizable(true);
        window.setVSyncEnabled(true);

        window.setVisible(true);
        Clock clock = new Clock();


        int count = 0;
        float sum = 0;
        Clock timer = new Clock();

        LinkedList<Vector2f> fpsList = new LinkedList<>();

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        Renderer renderer = new Renderer(window);

        Texture texture = new Texture(IOUtils.toByteArray(Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream("img.png"))));

        ArrayList<Vector4f> apples = new ArrayList<>();

        BufferedTexture bufferedTexture = new BufferedTexture(128, 128);

        Renderer br = new Renderer(bufferedTexture);

        while (window.isAlive()) {
            float dt = clock.reset();
            window.render();

            if (apples.size() < 15000) {
                float angle = (float) (Math.random() * Math.PI * 2);
                float mag = (float) (Math.random() * 100 + 100);
                for (int i = 0; i < 20; i++) {
                    apples.add(new Vector4f(
                            (float) (Math.random() * window.getWidth()),
                            (float) (Math.random() * window.getHeight()),
                            (float) (Math.cos(angle) * mag),
                            (float) (Math.sin(angle) * mag)
                    ));
                }
            }

            if (timer.elapsedTime() > 2) {
                count += 1;
                sum += dt;

                float now = timer.elapsedTime();
                fpsList.add(new Vector2f(now, dt));
                while (now - fpsList.getFirst().x > 1) {
                    sum -= fpsList.getFirst().y;
                    count--;
                    fpsList.removeFirst();
                }
            }

            // Background
            {
                renderer.clear(0.2f);

                for (int i = 0; i < apples.size(); i++) {
                    Vector4f apple = apples.get(i);
                    renderer.pushMatrix();
                    renderer.translate(apple.x, apple.y);
                    renderer.scale(2);
                    float now = (float) glfwGetTime();
                    renderer.rotate(((float) ((float) (Math.cos(i) * 100 - (int) (Math.cos(i) * 100)) * Math.PI * 2) + 10 * now * ((float) (Math.cos(i + 0.5) * 50 - (int) (Math.cos(i + 0.5) * 50))) * (i % 2 == 0 ? 1 : -1)));
                    renderer.drawImage(texture, -8, -10);
                    renderer.popMatrix();

                    apple.x += apple.z * dt;
                    apple.y += apple.w * dt;

                    if (apple.x > window.getWidth()) {
                        apple.z = -Math.abs(apple.z);
                    }
                    if (apple.y > window.getHeight()) {
                        apple.w = -Math.abs(apple.w);
                    }
                    if (apple.x < 0) {
                        apple.z = Math.abs(apple.z);
                    }
                    if (apple.y < 0) {
                        apple.w = Math.abs(apple.w);
                    }
                }
            }

            {
                br.pushMatrix();
                br.translate(bufferedTexture.getWidth() / 2f, bufferedTexture.getHeight() / 2f);
                br.rotate((float) glfwGetTime() / 10f);
                br.clear(0, 0.5f);
                br.setColor(1);
                br.fillCircle(0, 0, 30);
                br.setColor(1, 0, 0);
                br.fillCircle(30, 0, 20);
                br.setColor(0, 1, 0);
                br.fillCircle(0, 30, 20);
                br.popMatrix();
                br.pushMatrix();
                br.scale(0.5f);
                br.drawImage(bufferedTexture, 0, 0);
                br.popMatrix();
            }

            renderer.drawImage(
                    bufferedTexture,
                    window.getWidth() / 2f - bufferedTexture.getWidth() / 2f,
                    window.getHeight() / 2f - bufferedTexture.getHeight() / 2f
            );

            renderer.setFont("OpenSans-Regular.ttf", 20);
            renderer.setColor(0, 1, 0);
            renderer.drawText( "FPS: " + Math.round(1 / (sum / count)), 0, 0);
            renderer.drawText( "People Count: " + apples.size(), 0, 20);

            byte[] array = new byte[32]; // length is bounded by 7
            new Random().nextBytes(array);
            String generatedString = new String(array, Charset.forName("UTF-8"));

            renderer.setFont(16);
            renderer.drawText( "Random Characters: " + generatedString, 0, 40);
            renderer.drawText( "Chinese: 你好", 0, 60);

            renderer.setFont("OpenSans-Regular.ttf");
            renderer.setSize((int) (Math.cos(glfwGetTime() * 10) * 4) + 80);
            renderer.setColor(1, 0, 1);
            renderer.drawText("Hello world", window.getWidth() / 2f - renderer.getTextWidth("Hello world") / 2f, window.getHeight() / 2f - renderer.getFontSize() / 2f);

        }

    }
}
