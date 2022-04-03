package site.alex_xu.dev.alex2d.graphics;

import site.alex_xu.dev.alex2d.graphics.gl.Shader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;

class AwtFontLoader {
    private static final HashMap<String, Font> fontsMap = new HashMap<>();
    private static final HashMap<String, TreeMap<Integer, Font>> fontSizeMap = new HashMap<>();

    private static Font loadFromResource(String path) {
        String key = "res::" + path;
        if (!fontsMap.containsKey(key)) {
            InputStream stream = AwtFontLoader.class.getClassLoader().getResourceAsStream(path);
            try {
                assert stream != null;
                Font font = Font.createFont(Font.TRUETYPE_FONT, stream);
                fontsMap.put(key, font);
                stream.close();
            } catch (FontFormatException | IOException e) {
                e.printStackTrace();
            }
        }
        return fontsMap.get(key);
    }

    public static Font getFontFromResource(String path, int size) {
        String key = path;
        fontSizeMap.putIfAbsent(key, new TreeMap<>());
        if (!fontSizeMap.get(key).containsKey(size)) {
            fontSizeMap.get(key).put(size, loadFromResource(path).deriveFont((float) size));
        }
        return fontSizeMap.get(key).get(size);
    }
}

class TextAtlas {
    Texture texture;
    int[][] posMap = new int[8][3];
}

class FontRenderer { // TODO

    final Font font;
    final String path;
    final int size;
    private static final TreeMap<Integer, Font> defaultFonts = new TreeMap<>();
    private static final HashMap<String, TreeMap<Integer, TreeMap<Integer, TextAtlas>>> cache = new HashMap<>();

    public FontRenderer(String path, int size) {
        this.font = AwtFontLoader.getFontFromResource(path, size);
        this.path = path;
        this.size = size;
    }

    public FontRenderer(int size) {
        defaultFonts.computeIfAbsent(size, s -> new Font(Font.SANS_SERIF, Font.PLAIN, s));
        this.font = defaultFonts.get(size);
        this.size = size;
        this.path = null;
    }

    public TreeMap<Integer, TextAtlas> getCacheMap() {
        cache.putIfAbsent(path, new TreeMap<>());
        cache.get(path).putIfAbsent(size, new TreeMap<>());
        return cache.get(path).get(size);
    }

    public void render(BaseRenderer renderer, String text, float x, float y) {
        int offset = 0;
        for (int i = 0; i < text.length(); i++) {
            offset += renderChar(renderer, text.charAt(i), x + offset, y);
        }
    }

    private void prepareCharacter(char c) {
        TreeMap<Integer, TextAtlas> cache = getCacheMap();
        int index = (int) (c) / 8;
        if (!cache.containsKey(index)) {
            AffineTransform affineTransform = new AffineTransform();
            FontRenderContext frc = new FontRenderContext(affineTransform, true, true);
            TextAtlas atlas = new TextAtlas();

            int begin = index * 8;
            int totalWidth = 0;
            int maxHeight = 0;
            int[] yOffsets = new int[8];
            for (int i = 0; i < 8; i++) {
                int ci = begin + i;
                char character = (char) ci;
                Rectangle2D bound = font.getStringBounds("" + character, frc);
                int width = (int) Math.ceil(bound.getWidth());
                int height = (int) Math.ceil(bound.getHeight());
                maxHeight = Math.max(maxHeight, height);

                atlas.posMap[i][0] = totalWidth;
                atlas.posMap[i][1] = width;
                atlas.posMap[i][2] = height;
                yOffsets[i] = (int) -bound.getY();
                totalWidth += width + 1;
            }

            BufferedImage image = new BufferedImage(totalWidth, maxHeight, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g = (Graphics2D) image.getGraphics();
            g.setFont(font);
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            for (int i = 0; i < 8; i++) {
                int ci = begin + i;
                char character = (char) ci;

                g.setColor(Color.WHITE);
                g.drawString("" + character, atlas.posMap[i][0], yOffsets[i]);
            }

            try {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(image, "png", outputStream);
                Texture atlasTexture = new Texture(outputStream.toByteArray());
                outputStream.close();
                atlas.texture = atlasTexture;
                cache.put(index, atlas);

            } catch (IOException e) {
                e.printStackTrace();
                throw new IllegalStateException("Unable to create font texture atlas");
            }
        }
    }

    public int charWidth(char c) {
        int index = (int) (c) / 8;
        prepareCharacter(c);
        TextAtlas atlas = getCacheMap().get(index);
        return atlas.posMap[c % 8][1];
    }

    int renderChar(BaseRenderer renderer, char c, float x, float y) {

        TreeMap<Integer, TextAtlas> cache = getCacheMap();
        int index = (int) (c) / 8;
        prepareCharacter(c);

        TextAtlas atlas = cache.get(index);
        int left = atlas.posMap[c % 8][0];
        int w = atlas.posMap[c % 8][1];
        int h = atlas.posMap[c % 8][2];

        renderer.drawImage(
                atlas.texture,
                left, h,
                w, -h,
                x, y,
                w, h,
                renderer.r, renderer.g, renderer.b, renderer.a
        );

        return w;
    }
}
