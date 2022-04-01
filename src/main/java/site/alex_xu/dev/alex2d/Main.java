package site.alex_xu.dev.alex2d;

import site.alex_xu.dev.alex2d.graphics.GameWindow;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class Main {
    public static void main(String[] args) {
        GameWindow window = new GameWindow();
        window.setResizable(true);
        window.setVSyncEnabled(true);

        window.setVisible(true);
        long before = System.nanoTime();

        ArrayList<float[]> fps = new ArrayList<>();

        while (window.isAlive()) {
            window.render();

            long now = System.nanoTime();
            float dt = (System.nanoTime() - before) / 1e9f;
            before = now;

            window.setTitle(Math.round(1 / dt) + " fps");

            glClearColor(1, 1, 1, 1);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        }

        window.free();

    }
}
