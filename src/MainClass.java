import processing.core.PApplet;
import processing.core.PImage;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class MainClass extends PApplet {
    public static void main(String[] args) {
        PApplet.main("MainClass");
    }

    ArrayList<Image> allImages = new ArrayList<Image>();
    PImage immagine;
    PImage smaller;
    int scaleFactor = 7;
    int magnifyFactor = 10;
    int w;
    int h;

    public void settings(){
        immagine = loadImage("../lol.jpg");
        size(immagine.width*magnifyFactor, immagine.height*magnifyFactor);
    }

    public void setup(){

        w = immagine.width/scaleFactor;
        h = immagine.height/scaleFactor;
        smaller = createImage(w, h, RGB);
        smaller.copy(immagine, 0, 0, immagine.width, immagine.height, 0, 0, w, h);

        System.out.println("image loaded");

        File[] files = listFiles("data/images");
        for(File f : files){
            allImages.add(new Image(f.toString(),this, 1));
        }



    }

    public void draw(){
        background(0);
        smaller.loadPixels();
        for(int x=0; x<w; x++){
            for(int y=0; y<h; y++){
                int index = x + y * w;

                int pixel = smaller.pixels[index];

                int r = pixel>>16&0xFF;
                int g = pixel>>8&0xFF;
                int b = pixel&0xFF;

                Color cPixel = new Color(r,g,b);

                double cDistance = 999999999;
                Image closer = null;
                for(Image i : allImages){
                    double d = ColorDistance(cPixel, i.average);
                    if(d<cDistance){
                        cDistance=d;
                        closer = i;
                    }
                }

//                noStroke();
//                fill(cPixel.getRGB());
//                rect(x*scaleFactor, y*scaleFactor, scaleFactor, scaleFactor);


                image(closer.image , x*magnifyFactor*scaleFactor, y*magnifyFactor*scaleFactor, magnifyFactor*scaleFactor, magnifyFactor*scaleFactor);
//               allImages.remove(closer);

            }
        }

        save("image.jpg");
        System.out.println("done");
        noLoop();
    }

    File[] fileList(String path) {
        File folder = new File(path);
        if(folder.isDirectory()){
            File[] files = folder.listFiles();
            return files;
        }else{
            return null;
        }
    }

    public double ColorDistance(Color c1, Color c2)
    {
        double rmean = ( c1.getRed() + c2.getRed() )/2;
        int r = c1.getRed() - c2.getRed();
        int g = c1.getGreen() - c2.getGreen();
        int b = c1.getBlue() - c2.getBlue();
        double weightR = 2 + rmean/256;
        double weightG = 4.0;
        double weightB = 2 + (255-rmean)/256;
        return Math.sqrt(weightR*r*r + weightG*g*g + weightB*b*b);
    }
}
