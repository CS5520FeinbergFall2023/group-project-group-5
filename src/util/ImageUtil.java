package util;

import java.io.IOException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;

import controller.TextScriptController;
import controller.TextScriptControllerImpl;
import model.Image;
import model.MyImage;
import model.RGBPixel;
import service.ImageService;
import view.ImageView;


/**
 * This class contains utility methods to read a PPM image from file and simply print its contents. Feel free to change this method 
 *  as required.
 */
public class ImageUtil {
//  /**
//   * Read an image file in the PPM format and print the colors.
//   *
//   * @param filename the path of the file.
//   */
//  public static MyImage readPPM(String filename) {
//    Scanner sc;
//
//    try {
//      sc = new Scanner(new FileInputStream(filename));
//    } catch (FileNotFoundException e) {
//      System.out.println("File " + filename + " not found!");
//      return null;
//    }
//    StringBuilder builder = new StringBuilder();
//    //read the file line by line, and populate a string. This will throw away any comment lines
//    while (sc.hasNextLine()) {
//      String s = sc.nextLine();
//      if (s.charAt(0) != '#') {
//        builder.append(s + System.lineSeparator());
//      }
//    }
//
//    //now set up the scanner to read from the string we just built
//    sc = new Scanner(builder.toString());
//
//    String token;
//
//    token = sc.next();
//    if (!token.equals("P3")) {
//      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
//      return null;
//    }
//    int width = sc.nextInt();
//    int height = sc.nextInt();
//    int maxValue = sc.nextInt();
//
//    MyImage myImage = new MyImage(height,width);
//    for (int i = 0; i < height; i++) {
//      for (int j = 0; j < width; j++) {
//        int r = sc.nextInt();
//        int g = sc.nextInt();
//        int b = sc.nextInt();
//        RGBPixel pixel = new RGBPixel(r,g,b);
//        myImage.setPixel(i,j,pixel);
//      }
//    }
//    return myImage;
//  }

  //demo main
//  public static void main(String[] args) throws IOException {
//    MyImage myImage = ImageUtil.readPPM("");
//    ImageService imageService = new ImageService();
//    ImageView imageView = new ImageView();
//    TextScriptControllerImpl controller = new TextScriptControllerImpl(imageService, imageView);
//    Scanner scanner = new Scanner(System.in);
//    System.out.println("Please select which mode you want to input the commands: 1. Console "
//          + "2. From file");
//    int choice = scanner.nextInt();
//    if(choice == 1) {
//      controller.start();
//    }
//    else if (choice == 2) {
//      System.out.println("Please enter the file path");
//      String filePath = scanner.next();
//      controller.startFromFile(filePath);
//    }
//    else {
//      System.out.println("Please select the valid choice");
//    }
//  }

}

