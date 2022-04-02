package site.alex_xu.dev.alex2d;

import org.apache.commons.io.IOUtils;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import site.alex_xu.dev.alex2d.graphics.Renderer;
import site.alex_xu.dev.alex2d.graphics.Texture;
import site.alex_xu.dev.alex2d.graphics.Window;
import site.alex_xu.dev.alex2d.system.Clock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

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

            for (int i = 0; i < 40; i++) {
                apples.add(new Vector4f(
                        (float) (Math.random() * window.getWidth()),
                        (float) (Math.random() * window.getHeight()),
                        (float) (Math.random() * Math.PI * 2),
                        (float) (Math.random() * 2 + 0.5)
                ));
            }

            for (Vector4f apple : apples) {
                renderer.pushMatrix();

                renderer.translate(apple.x, apple.y);
                renderer.scale(3);
                renderer.rotate(apple.z);
                renderer.translate(-8, -8);
                renderer.drawImage(texture, 0, 0);
                apple.z += apple.w * dt;

                renderer.popMatrix();
            }

            if (apples.size() % 40 == 0) {
                System.out.println("size = " + apples.size());
            }
        }

    }
}
