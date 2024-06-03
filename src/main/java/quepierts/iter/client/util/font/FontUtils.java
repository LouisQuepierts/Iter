package quepierts.iter.client.util.font;

import quepierts.iter.client.renderer.screen.ScreenRenderer;
import quepierts.iter.client.resource.texture.Texture;
import quepierts.iter.reflex.Execute;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class FontUtils {
    private static Map<Character, Glyph> glyphMap = new HashMap<>();
    private static Texture textureFont;

    private static Texture[] textures;

    private static int fontHeight;

    public static Texture getTextureFont() {
        return textureFont;
    }

    public static Texture getTexture(int index) {
        return textures[index];
    }

    public static int getStringLength(CharSequence charSequence, float scale) {
        int length = 0;
        for (int i = 0; i < charSequence.length(); i++) {
            char c = charSequence.charAt(i);
            Glyph glyph = glyphMap.get(c);

            if (glyph != null) {
                length += glyph.width * scale;
            } else {
                length += glyphMap.get((char) 32).width * scale;
            }
        }

        return length / 2;
    }

    public static void drawStringCenter(CharSequence charSequence, float x, float y, int color, float scale) {
        int length = getStringLength(charSequence, scale);
        x -= length / 2;
        drawString(charSequence, x, y, color, scale);
    }

    public static void drawString(CharSequence charSequence, float x, float y, int color, float scale) {
        scale /= 2;
        for (int i = 0; i < charSequence.length(); i++) {
            char c = charSequence.charAt(i);
            x += drawChar(c, x, y, color, scale);
        }
    }

    public static int drawChar(char c, float x, float y, int color, float scale) {
        Glyph glyph = glyphMap.get(c);

        int width, height, index;

        if (glyph != null) {
            width = glyph.width;
            height = glyph.height;

            index = c / 256;
        } else {
            return (int) (4 * scale);
        }

        Texture texture = textures[index];
        ScreenRenderer.glDrawQuadTexture(x, y, width * scale, height * scale, color, texture.getId(),
                (float) glyph.x / 768, (float) glyph.y / 768,
                (float) (glyph.x + glyph.width) / 768, (float) (glyph.y + glyph.height) / 768);

        return (int) (width * scale);
    }

    @Execute(priority = 1)
    private static void loadFont() {
        Font font = new Font(Font.DIALOG, Font.PLAIN, 32);
        int numGlyphs = font.getNumGlyphs();
        int textureAmount = (int) Math.ceil((double) numGlyphs / 256);

        textures = new Texture[textureAmount];

        int textureHeight = 768;
        int textureWidth = 768;
        BufferedImage textureImage;
        Graphics2D graphics2D;
        Texture t;
        for (int count = 0; count < textureAmount; count++) {
            BufferedImage image;
            textureImage = new BufferedImage(textureWidth, textureHeight, BufferedImage.TYPE_INT_ARGB);
            graphics2D = textureImage.createGraphics();

            try {
                for (int y = 0; y < 16; y++) {
                    for (int x = 0; x < 16; x++) {
                        char c = (char) (x + y * 16 + count * 256);
                        if (c < 32 || c == 128) continue;
                        image = createImage(font, c);
                        Glyph glyph = new Glyph(c, image.getWidth(), image.getHeight(), x * 48, (15 - y) * 48);
                        glyphMap.put(c, glyph);
                        graphics2D.drawImage(image, x * 48, y * 48, null);
                    }
                }
            } catch (IllegalArgumentException ignored) {}

            textures[count] = Texture.loadTexture(textureImage);
        }
    }

    private static BufferedImage createImage(Font font, char c) {
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = image.createGraphics();
        graphics2D.setFont(font);
        FontMetrics metrics = graphics2D.getFontMetrics();
        graphics2D.dispose();

        int width = metrics.charWidth(c);
        int height = metrics.getHeight();

        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        graphics2D = image.createGraphics();

        graphics2D.setFont(font);
        graphics2D.setPaint(Color.WHITE);
        graphics2D.drawString(String.valueOf(c), 0, metrics.getAscent());
        graphics2D.dispose();

        return image;
    }
}
