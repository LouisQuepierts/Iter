package quepierts.iter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        getFont();
    }

    public static void getFont() {
        List<String> list = new ArrayList<>();

        Font font = new Font(Font.MONOSPACED, Font.PLAIN, 16);
        System.out.println(font.getNumGlyphs());

        for (int i = 32; i < 256; i++) {
            if (i == 127) {
                continue;
            }

            char c = (char) i;
        }
    }
}
