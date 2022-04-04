package site.alex_xu.dev.alex2d;

import site.alex_xu.dev.alex2d.graphics.*;
import site.alex_xu.dev.alex2d.graphics.Window;
import site.alex_xu.dev.alex2d.sounds.Listener;
import site.alex_xu.dev.alex2d.sounds.Sound;
import site.alex_xu.dev.alex2d.sounds.SoundSource;
import site.alex_xu.dev.alex2d.system.Clock;

import java.io.IOException;

import static org.lwjgl.glfw.GLFW.*;

public class Main {
    static float x, y;
    public static void main(String[] args) throws InterruptedException, IOException {
        Window window = new Window();
        window.setVisible(true);

        Renderer renderer = new Renderer(window);

        glfwSetCursorPosCallback(window.getID(), (window1, xpos, ypos) -> {
            x = (float) xpos;
            y = (float) ypos;
        });

        Listener listener = Listener.get();
        Sound sound = Sound.getFromResources("music.ogg");
        SoundSource source = new SoundSource(false, false);

        source.setSound(sound);
        source.play();
//        source.setPitch(2);

        Clock clock = new Clock();
        while (window.isAlive()) {
            window.render();
            renderer.clear(0.8f);

            window.setTitle("Timer: " + String.format("%.1f", clock.elapsedTime()));

            renderer.setColor(1, 0, 0);
            renderer.fillCircle(
                    x, y,
                    10
            );
            renderer.setColor(0, 1, 0);
            renderer.fillCircle(
                    window.getWidth() / 2f, window.getHeight()/2f,
                    20
            );
            listener.setPosition((x - window.getWidth() / 2f) / 100f, (y - window.getHeight() / 2f) / 100f, 1);

        }
    }
}
