package site.alex_xu.dev.alex2d;

import org.apache.commons.io.IOUtils;
import site.alex_xu.dev.alex2d.graphics.*;
import site.alex_xu.dev.alex2d.graphics.Window;
import site.alex_xu.dev.alex2d.sounds.AudioMaster;
import site.alex_xu.dev.alex2d.sounds.Listener;
import site.alex_xu.dev.alex2d.sounds.Sound;
import site.alex_xu.dev.alex2d.sounds.SoundSource;
import site.alex_xu.dev.alex2d.system.Clock;
import sun.nio.ch.IOUtil;

import java.io.IOException;
import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        Window window = new Window();
        window.setVSyncEnabled(true);
        window.setVisible(true);
        window.setResizable(true);

        Texture texture = Texture.getFromResources("img.png");
        Renderer renderer = new Renderer(window);

        window.setIcon("img.png");

        AudioMaster.init();
        SoundSource soundSource = new SoundSource(false, false);
        Sound sound = new Sound(IOUtils.toByteArray(Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream("Alan Walker, K-391, Tungevaag, Mangoo - PLAY (Alan Walker's Video).ogg"))));
        Sound s2 = new Sound(IOUtils.toByteArray(Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream("Alan Walker, K-391, Tungevaag, Mangoo - PLAY (Alan Walker's Video).ogg"))));
        soundSource.setSound(sound);
        Listener listener = Listener.get();
        soundSource.play();

        Clock clock = new Clock();
        Clock dtClock = new Clock();

        float x = 0;
        float y = 0;

        while (window.isAlive()) {
            window.render();
            renderer.clear(0.7f);
            renderer.drawImage(texture, 0, 0);
            renderer.pushMatrix();
            renderer.translate(100, 100);
            renderer.rotate((float) Math.toRadians(45));
            renderer.fillRect(0, 0, 100, 100);
            renderer.popMatrix();

            renderer.setStrokeWidth(5);
            renderer.strokeLine(window.getWidth() / 2f, window.getHeight() / 2f, window.getWidth() - 50, window.getHeight() - 50);
            renderer.strokeRect(10, 10, 100, 100);

            if (clock.elapsedTime() > 60 * 3 + 15) {
                clock.reset();
                soundSource.play();
            }

            float dt = dtClock.reset();

            float speed = 150;
            if (glfwGetKey(window.getID(), GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS) {
                speed = 1000;
            }
            if (glfwGetKey(window.getID(), GLFW_KEY_TAB) == GLFW_PRESS) {
                speed = 5000;
            }
            if (glfwGetKey(window.getID(), GLFW_KEY_LEFT) == GLFW_PRESS) {
                x -= dt * speed;
            }
            if (glfwGetKey(window.getID(), GLFW_KEY_RIGHT) == GLFW_PRESS) {
                x += dt * speed;
            }
            if (glfwGetKey(window.getID(), GLFW_KEY_UP) == GLFW_PRESS) {
                y -= dt * speed;
            }
            if (glfwGetKey(window.getID(), GLFW_KEY_DOWN) == GLFW_PRESS) {
                y += dt * speed;
            }
            if (glfwGetKey(window.getID(), GLFW_KEY_SPACE) == GLFW_PRESS) {
                x += (0 - x) * dt * 10;
                y += (0 - y) * dt * 10;
            }
            if (glfwGetKey(window.getID(), GLFW_KEY_BACKSPACE) == GLFW_PRESS) {
                System.gc();
            }
            if (glfwGetKey(window.getID(), GLFW_KEY_ESCAPE) == GLFW_PRESS) {
                x = (float) (Math.random() * 10000) * (Math.random() > 0.5 ? 1 : -1);
                y = (float) (Math.random() * 10000) * (Math.random() > 0.5 ? 1 : -1);
            }
            listener.setPosition(-x / 100, y / 100, 1);

            renderer.setColor(1, 0, 0);
            renderer.fillCircle(window.getWidth() / 2f + x, window.getHeight() / 2f + y, 10);
            renderer.setColor(1);

        }
    }
}
