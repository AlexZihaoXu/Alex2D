package site.alex_xu.dev.alex2d;

import org.joml.Vector2f;
import org.joml.Vector3f;
import site.alex_xu.dev.alex2d.graphics.Renderer;
import site.alex_xu.dev.alex2d.graphics.Window;
import site.alex_xu.dev.alex2d.system.Clock;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.lwjgl.opengl.GL11.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Window window = new Window();
        window.setResizable(true);
        window.setVSyncEnabled(true);

        window.setVisible(true);
        Clock clock = new Clock();


        int count = 0;
        float sum = 0;
        Clock timer = new Clock();

        LinkedList<Vector2f> fpsList = new LinkedList<>();
        Renderer renderer = new Renderer(window);

        ArrayList<Vector2f> poses = new ArrayList<>();
        ArrayList<Vector3f> colors = new ArrayList<>();

        while (window.isAlive()) {
            float dt = clock.reset();
            window.render();

            if (timer.elapsedTime() > 2) {
                count += 1;
                sum += dt;

                float now = timer.elapsedTime();
                fpsList.add(new Vector2f(now, dt));
                while (now - fpsList.getFirst().x > 3) {
                    sum -= fpsList.getFirst().y;
                    count--;
                    fpsList.removeFirst();
                }
            }

            // System.out.printf("%.10f \n", dt);
            window.setTitle(String.format("fps: %.2f | time: %5.1f | Samples: %10d", 1 / (sum / count), timer.elapsedTime(), count));

            glClearColor(0, 0, 0, 1);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            for (int i = 0; i < 30; i++) {
                poses.add(new Vector2f(
                        (float) (window.getWidth() * Math.random()),
                        (float) (window.getHeight() * Math.random())
                ));
                colors.add(new Vector3f(
                        (float) (Math.random() * 0.5 + 0.5),
                        (float) (Math.random() * 0.5 + 0.5),
                        (float) (Math.random() * 0.5 + 0.5)
                ));
            }
            for (int i = 0; i < poses.size(); i++) {
                Vector3f color = colors.get(i);
                Vector2f pos = poses.get(i);
                renderer.setColor(color.x, color.y, color.z);
                renderer.fillTriangle(pos.x, pos.y, pos.x + 50, pos.y, pos.x + 50, pos.y + 50);
            }
            if (poses.size() % 25 == 0)
                System.out.println(poses.size());
//            Thread.sleep(0, 10);
        }

    }
}
