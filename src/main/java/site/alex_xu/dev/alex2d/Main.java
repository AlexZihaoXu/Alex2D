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
    public static void main(String[] args) throws InterruptedException, IOException {
        Window window = new Window();
        window.setVSyncEnabled(true);
        window.setResizable(true);
        window.setVisible(true);
        window.setTitle("Physics Simulation");

        Renderer renderer = new Renderer(window);

        BufferedTexture texture = new BufferedTexture(128, 128);
        Renderer rd2 = texture.createRenderer();


        Clock clock = new Clock();
        while (window.isAlive()) {
            window.render();
            renderer.clear(0.8f);

            renderer.setColor(0, 0.7f, 0);
            renderer.drawText("Good morning", 5, 5);

            {
                rd2.clear(0, 0, 0, 0.5f);
                rd2.setColor(0.5f, 0.8f, 0.3f);
                rd2.fillRect(0, 0, 20, 20);
                rd2.pushMatrix();
                rd2.scale(0.8f);
                rd2.drawImage(texture, 0, 0);
                rd2.popMatrix();
            }

            renderer.drawImage(texture, 200, 200);
        }
        
    }
}
