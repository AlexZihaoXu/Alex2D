package site.alex_xu.dev.alex2d;

import org.joml.Vector2f;
import site.alex_xu.dev.alex2d.graphics.*;
import site.alex_xu.dev.alex2d.graphics.Window;
import site.alex_xu.dev.alex2d.system.Clock;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception {
        new Main();
    }

    Texture texture;
    ArrayList<Vector2f> vertices = new ArrayList<>();
    Clock clock = new Clock()   ;
    Clock putClock = new Clock()   ;

    public Main() {
        Window window = new Window("Demo Game", 800, 500);
        window.setVisible(true);
        window.setResizable(true);
        window.setVSyncEnabled(false);

        Renderer renderer = new Renderer(window);
        texture = Texture.getFromResources("fill.png");

        window.getMouse().addMouseDownCallback(button -> {
        });

        while (window.isAlive()) {
            window.render();
            render(renderer);
            if (window.getMouse().getButton(0) && putClock.elapsedTime() > 0.001f) {
                vertices.add(window.getMouse().getPos());
                putClock.reset();
            }
        }

        window.close();
    }

    public void render(Renderer renderer) {
        float dt = clock.reset();

        renderer.clear(0.8f);

//        renderer.drawImageTriangle(texture, 10, 10, 500, 100, 10, 500, 1, 4, 2f);

        renderer.pushMatrix();
        renderer.translate(10, 10);
        if (vertices.size() > 2)
            renderer.drawImagePolygon(texture, vertices);
        renderer.popMatrix();

        renderer.setColor(0, 0.65f, 0);
        renderer.drawText("FPS: " + Math.round(1 / dt), 0, 0);
        renderer.drawText("Vertices: " + vertices.size(), 0, 20);
    }
}
