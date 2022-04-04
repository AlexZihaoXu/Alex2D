package site.alex_xu.dev.alex2d;

import site.alex_xu.dev.alex2d.graphics.*;
import site.alex_xu.dev.alex2d.graphics.Window;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        Window window = new Window();
        window.setVSyncEnabled(true);
        window.setVisible(true);

        Texture texture = Texture.getFromResources("img.png");
        Renderer renderer = new Renderer(window);

        window.setIcon("img.png");
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
            renderer.strokeLine(window.getWidth()/2f, window.getHeight()/2f, window.getWidth() - 50, window.getHeight() - 50);
            renderer.strokeRect(10, 10, 100, 100);
        }

    }
}
