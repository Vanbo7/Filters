import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Filters {

  public static void main(String[] args)throws IOException {
    // TODO: modify this main method as you wish, to run and test your filter implementations.

    // Read in the image file.
    File f = new File("dog.png");
    BufferedImage img = ImageIO.read(f);

    // For debugging
    System.out.println("Before:");
    System.out.println(Utilities.getRGBArray(0, 0, img)[0]);
    System.out.println(Utilities.getRGBArray(0, 0, img)[1]);
    System.out.println(Utilities.getRGBArray(0, 0, img)[2]);
    // 92 40 27

    // Apply a filter implementation on img.
    // applyGrayscale(img);
    // applyNorak(img);
    // int[] borderC = {0, 0, 0};
    // applyBorder(img, 30, borderC);
    // applyMirror(img);
    applyCustom(img);
    // applyBlur(img);

    // For debugging
    System.out.println("After:");
    System.out.println(Utilities.getRGBArray(0, 0, img)[0]);
    System.out.println(Utilities.getRGBArray(0, 0, img)[1]);
    System.out.println(Utilities.getRGBArray(0, 0, img)[2]);
    // 53 53 53

    // Write the result to a new image file.
    f = new File("dog_filtered.png");
    ImageIO.write(img, "png", f);
  }

  public static void applyGrayscale(BufferedImage img) {
    // TODO
    for (int i=0; i<img.getHeight(); i++) {
      for (int j=0; j<img.getWidth(); j++) {
        int[] rgb = Utilities.getRGBArray(i, j, img);
        int sum = 0;
        int average = 0;
        for (int x=0; x<3; x++) {
          sum += rgb[x];
        }
        average = sum/3;
        for (int y=0; y<3; y++) {
          rgb[y] = average;
        }
        Utilities.setRGB(rgb, i, j, img);
      }
    }
  }

  public static void applyNorak(BufferedImage img) {
    // TODO
    for (int i=0; i<img.getHeight(); i++) {
      for (int j=0; j<img.getWidth(); j++) {
        int[] rgb = Utilities.getRGBArray(i, j, img);
        int sum = 0;
        int average = 0;
        for (int x=0; x<3; x++) {
        sum += rgb[x];
        }
        average = sum/3;
        if (average>153){
          for (int y=0; y<3; y++) {
          rgb[y] = average;
          }
        }
        Utilities.setRGB(rgb, i, j, img);
      }
    }
  }

  public static void applyBorder(BufferedImage img, int borderThickness, int[] borderColor) {
    // TODO
    for (int i=0; i<img.getHeight(); i++) {
      for (int j=0; j<img.getWidth(); j++) {
        if (i<borderThickness || j<borderThickness) {
          Utilities.setRGB(borderColor, i, j, img);
          }
        if (i>=(img.getHeight()-borderThickness) || j>=(img.getWidth()-borderThickness)) {
          Utilities.setRGB(borderColor, i, j, img);
        }
      }
    }
  }

  public static void applyMirror(BufferedImage img){
    // TODO
    for (int i=0; i<img.getHeight(); i++){
      int position = 1;
      for (int j=0; j<img.getWidth()/2; j++){
        // int[] rgb = Utilities.getRGBArray(i, j, img);
        int[] pixel1 = Utilities.getRGBArray(i, j, img);
        int[] pixel2 = Utilities.getRGBArray(i, img.getWidth()-position, img);
        int[] swap = pixel1;
        Utilities.setRGB(pixel2, i, j, img);
        Utilities.setRGB(swap, i, img.getWidth()-position, img);
        position++;
      }
    }
  }

  public static void applyBlur(BufferedImage img) {
    // TODO
    int[][][] pixels = new int[img.getHeight()][img.getWidth()][3];

    for (int i=1; i<img.getHeight()-1; i++) {
      for (int j=1; j<img.getWidth()-1; j++) {
        int sumRed = 0;
        int sumGreen = 0;
        int sumBlue = 0;
        int[][] rgb = new int [9][3]; 
        rgb[0] = Utilities.getRGBArray(i, j, img);
        rgb[1] = Utilities.getRGBArray(i-1, j, img);
        rgb[2] = Utilities.getRGBArray(i+1, j, img);
        rgb[3] = Utilities.getRGBArray(i, j-1, img);
        rgb[4] = Utilities.getRGBArray(i, j+1, img);
        rgb[5] = Utilities.getRGBArray(i-1, j-1, img);
        rgb[6] = Utilities.getRGBArray(i-1, j+1, img);
        rgb[7] = Utilities.getRGBArray(i+1, j-1, img);
        rgb[8] = Utilities.getRGBArray(i+1, j+1, img);
        for (int x=0; x<rgb.length; x++){
          for (int y=0; y<rgb[0].length; y++)
            if (y==0){
              sumRed += rgb[x][y];
            } else if(y==1){
              sumGreen += rgb[x][y];
            } else {
              sumBlue += rgb[x][y];
            }
        }
        pixels[i][j][0] = sumRed/9;
        pixels[i][j][1] = sumGreen/9;
        pixels[i][j][2] = sumBlue/9;
      }
    }
    for (int i=1; i<img.getHeight()-1; i++) {
      for (int j=1; j<img.getWidth()-1; j++) {
        int[] centerPixel = pixels[i][j];
        Utilities.setRGB(centerPixel, i, j, img);
      }
    }
  }

  public static void applyCustom(BufferedImage img) {
    // TODO
    // Saturated & Outline filter
    for (int i=0; i<img.getHeight(); i++) {
      for (int j=0; j<img.getWidth(); j++) {
        int[] rgb = Utilities.getRGBArray(i, j, img);
        if (rgb[0]>rgb[1] && rgb[0]>rgb[2] && rgb[0]<185) {
          rgb[0] += 70;
        } else if (rgb[1]>rgb[0] && rgb[1]>rgb[2] && rgb[1]<185) {
          rgb[1] += 70;
        } else if (rgb[2]<185){
          rgb[2] = 70; 
        }
        Utilities.setRGB(rgb, i, j, img);
      }
    }
    for (int i=1; i<img.getHeight()-1; i++) {
      for (int j=1; j<img.getWidth()-1; j++) {
        int sumRed = 0;
        int sumGreen = 0;
        int sumBlue = 0;
        int average = 0;
        int[][] pixels = new int[9][3];
        pixels[0] = Utilities.getRGBArray(i, j, img);
        pixels[1] = Utilities.getRGBArray(i-1, j, img);
        pixels[2] = Utilities.getRGBArray(i+1, j, img);
        pixels[3] = Utilities.getRGBArray(i, j-1, img);
        pixels[4] = Utilities.getRGBArray(i, j+1, img);
        pixels[5] = Utilities.getRGBArray(i-1, j-1, img);
        pixels[6] = Utilities.getRGBArray(i-1, j+1, img);
        pixels[7] = Utilities.getRGBArray(i+1, j-1, img);
        pixels[8] = Utilities.getRGBArray(i+1, j+1, img);
        for (int x=0; x<pixels.length; x++){
          for (int y=0; y<pixels[0].length; y++)
            if (y==0){
              sumRed += pixels[x][y];
            } else if(y==1){
              sumGreen += pixels[x][y];
            } else {
              sumBlue += pixels[x][y];
            }
        }
        int[] centerPixel = {sumRed/9, sumGreen/9, sumBlue/9};
        Utilities.setRGB(centerPixel, i, j, img);
      }
    }
    for (int i=1; i<img.getHeight(); i++) {
      for (int j=1; j<img.getWidth(); j++) {
        int[] pixel = Utilities.getRGBArray(i, j, img);
        int averagePixel = (pixel[0]+pixel[1]+pixel[2])/3;
        int[] pixelLeft = Utilities.getRGBArray(i, j-1, img);
        int averagePixelLeft = (pixelLeft[0]+pixelLeft[1]+pixelLeft[2])/3;
        if((averagePixelLeft-averagePixel)>5){
          int[] black = {0, 0, 0};
          for (int x=j; x<j+1; x++){
            if(x<img.getWidth()-1){
              Utilities.setRGB(black, i, x, img);
            }
          }
        }
      }
    }
  }
}
