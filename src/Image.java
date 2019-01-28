import processing.core.PApplet;
import processing.core.PImage;

import java.awt.*;

public class Image {
    PImage image;
    Color average;
    int scaleFactor;

    public Image(String Path, PApplet parent, int scaleFactor){
        this.scaleFactor = scaleFactor;
        PImage i = parent.loadImage(Path);
        image = parent.createImage(i.width/scaleFactor, i.height/scaleFactor, parent.RGB);
        image.copy(i, 0, 0, i.width, i.height, 0, 0, i.width/scaleFactor, i.height/scaleFactor);
        average = getAverageColor(image);
    }


    Color getAverageColor(PImage img) {
        img.loadPixels();
        int r = 0, g = 0, b = 0;
        for (int i=0; i<img.pixels.length; i++) {
            int c = img.pixels[i];
            r += c>>16&0xFF;
            g += c>>8&0xFF;
            b += c&0xFF;
        }
        r /= img.pixels.length;
        g /= img.pixels.length;
        b /= img.pixels.length;
        return new Color(r,g,b);
    }

    @Override
    public String toString() {
        return "Image{" +
                "average=" + average +
                '}';
    }
}
