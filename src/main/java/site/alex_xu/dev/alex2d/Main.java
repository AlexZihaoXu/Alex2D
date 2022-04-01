package site.alex_xu.dev.alex2d;

import site.alex_xu.dev.alex2d.graphics.Window;
import site.alex_xu.dev.alex2d.system.Clock;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Window window = new Window();
        window.setResizable(true);
        window.setVSyncEnabled(false);

        window.setVisible(true);
        Clock clock = new Clock();
        Clock[] clocks = new Clock[10000];
        for (int i = 0; i < clocks.length; i++) {
            clocks[i] = new Clock();
        }

        int count = 0;
        float sum = 0;
        Clock timer = new Clock();

        while (window.isAlive()) {
            window.render();

            float dt = clock.reset();
            for (Clock clock1 : clocks) {
                clock1.reset();
            }
            if (timer.elapsedTime() > 10) {
                count += 1;
                sum += dt;
            }

            System.out.printf("%.10f \n", dt);
            window.setTitle(String.format("fps: %.2f | time: %5.1f | Samples: %10d", 1 / (sum / count), timer.elapsedTime(), count));

            glClearColor(1, 1, 1, 1);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        }

    }
}
