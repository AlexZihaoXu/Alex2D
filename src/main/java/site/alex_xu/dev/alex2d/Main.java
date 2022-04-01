package site.alex_xu.dev.alex2d;

import site.alex_xu.dev.alex2d.graphics.Window;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Window window = new Window();
        window.setResizable(true);
        window.setVSyncEnabled(false);

        window.setVisible(true);
        long before = System.nanoTime();


        while (window.isAlive()) {
            window.render();

            long now = System.nanoTime();
            float dt = (System.nanoTime() - before) / 1e9f;
            before = now;

            System.out.println(dt);
            window.setTitle(Math.round(1 / dt) + " fps");

            glClearColor(1, 1, 1, 1);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        }

    }
}
