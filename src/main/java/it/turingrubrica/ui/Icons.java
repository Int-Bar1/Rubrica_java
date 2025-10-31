package it.turingrubrica.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;

public final class Icons {
    private Icons() {}

    //carica e scala a width×height (preservando proporzioni). Ritorna null se non trovata.
    public static Icon loadScaled(String path, int width, int height) {
        BufferedImage src = read(path);
        if (src == null) return null;

        // scala l’immagine
        double s = Math.min((double) width / src.getWidth(), (double) height / src.getHeight());
        int sw = Math.max(1, (int) Math.round(src.getWidth() * s));
        int sh = Math.max(1, (int) Math.round(src.getHeight() * s));
        Image scaled = src.getScaledInstance(sw, sh, Image.SCALE_SMOOTH);

        // inversione colore icone. i file originali sono neri ma li volgiamo bianchi
        BufferedImage dst = new BufferedImage(sw, sh, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = dst.createGraphics();
        g.drawImage(scaled, 0, 0, null);
        g.dispose();

        for (int y = 0; y < dst.getHeight(); y++) {
            for (int x = 0; x < dst.getWidth(); x++) {
                int argb = dst.getRGB(x, y);
                int a = (argb >> 24) & 0xFF;
                if (a == 0) continue; // pixel transp.
                int r = 255 - ((argb >> 16) & 0xFF);
                int g2 = 255 - ((argb >> 8) & 0xFF);
                int b = 255 - (argb & 0xFF);
                dst.setRGB(x, y, (a << 24) | (r << 16) | (g2 << 8) | b);
            }
        }

        return new ImageIcon(dst);
    }

    private static BufferedImage read(String path) {
        try {
            String p = path.startsWith("/") ? path.substring(1) : path;
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            URL url = (cl != null) ? cl.getResource(p) : Icons.class.getClassLoader().getResource(p);
            if (url == null) url = Icons.class.getResource(path.startsWith("/") ? path : "/" + path);
            if (url == null) return null;
            try (InputStream in = url.openStream()) {
                return ImageIO.read(in); // leggepng/jpg/gif
            }
        } catch (Exception ignored) {
            return null;
        }
    }
}
