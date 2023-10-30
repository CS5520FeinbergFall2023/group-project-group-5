package model;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.function.Function;

import javax.imageio.ImageIO;


/**
 * This class represents 8 bit depth RGB image.
 */
public class MyImage extends Image {
//  private RGBPixel[][] pixels;
//  private int height;
//  private int width;

  public MyImage(String path) throws IllegalArgumentException {
    super(path);
  }

  /**
   * Construct all white image with given height and width
   *
   * @param height height of the image
   * @param width  weight of the image
   */
  public MyImage(int height, int width) {
    super(height, width);
    pixels = new RGBPixel[height][width];
    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        pixels[row][col] = new RGBPixel(0, 0, 0);
      }
    }
  }

  private MyImage(Pixel[][] pixels) {
    int height = pixels.length;
    if (height == 0) {
      throw new IllegalArgumentException("Invalid pixels size.");
    }
    int width = pixels[0].length;
    for (Pixel[] row : pixels) {
      if (row.length != width) {
        throw new IllegalArgumentException("Pixels must be a rectangle");
      }
    }

    for (Pixel[] row : pixels) {
      if (!(row instanceof RGBPixel[])) {
        throw new IllegalArgumentException("MyImage must be consisted of RGBPixels");
      }
    }
    this.height = height;
    this.width = width;
    this.pixels = pixels;
  }

  @Override
  protected void loadPPM(String path) throws IOException, IllegalArgumentException {
    Scanner sc;
    try {
      File file = new File(path);
      if (!file.isAbsolute()) {
        file = new File(System.getProperty("user.dir"), path);
      }
      sc = new Scanner(new FileInputStream(file));
    } catch (FileNotFoundException e) {
      throw new IOException("File " + path + " not found!");
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }
    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());
    String token;
    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
    }
    this.width = sc.nextInt();
    this.height = sc.nextInt();
    sc.nextInt();

    pixels = new RGBPixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        pixels[i][j] = new RGBPixel(r, g, b);
      }
    }
  }

  @Override
  protected void loadJPGPNG(String path) throws IOException, IllegalArgumentException {
    BufferedImage image;
    File file = new File(path);
    if (!file.isAbsolute()) {
      file = new File(System.getProperty("user.dir"), path);
    }
    image = ImageIO.read(file);
    if (image == null) {
      throw new IllegalArgumentException("The image format is not correct");
    }
    width = image.getWidth();
    height = image.getHeight();
    pixels = new RGBPixel[height][width];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int pixel = image.getRGB(x, y);
        int red = (pixel >> 16) & 0xFF;
        int green = (pixel >> 8) & 0xFF;
        int blue = pixel & 0xFF;
        pixels[y][x] = new RGBPixel(red, green, blue);
      }
    }
  }

  @Override
  protected void savePPM(String path) throws IOException, IllegalArgumentException {
    if (!path.endsWith(".ppm")) {
      throw new IllegalArgumentException("The path does not have ppm extension");
    }
    File file = new File(path);
    if (!file.isAbsolute()) {
      file = new File(System.getProperty("user.dir"), path);
    }
    if (!file.exists()) {
      file.getParentFile().mkdirs();
      file.createNewFile();
    }
    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
    // PPM header
    writer.write("P3\n");
    writer.write(width + " " + height + "\n");
    writer.write("255\n");

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        RGBPixel pixel = (RGBPixel) pixels[y][x];
        writer.write(pixel.getRed() + " " + pixel.getGreen() + " " + pixel.getBlue() + " ");
      }
      writer.write("\n");
    }
    writer.close();
  }

  @Override
  protected void saveJPGPNG(String path, String format)
      throws IOException, IllegalArgumentException {
    if (!path.endsWith(".jpg") && !path.endsWith(".jpeg") && !path.endsWith(".png")) {
      throw new IllegalArgumentException("The path does not have ppm extension");
    }
    BufferedImage bufferedImage = new BufferedImage(width, height,
        BufferedImage.TYPE_INT_RGB);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int rgb = (((RGBPixel) pixels[y][x]).getRed() << 16) |
                  (((RGBPixel) pixels[y][x]).getGreen() << 8) |
                  ((RGBPixel) pixels[y][x]).getBlue();
        bufferedImage.setRGB(x, y, rgb);
      }
    }
    File file = new File(path);
    if (!file.isAbsolute()) {
      file = new File(System.getProperty("user.dir"), path);
    }
    if (!file.exists()) {
      file.getParentFile().mkdirs();
      file.createNewFile();
    }
    ImageIO.write(bufferedImage, format, file);
  }

//  protected RGBPixel getPixel(int x, int y) {
//    if (x < 0 || x > this.height || y < 0 || y > this.width) {
//      throw new IllegalArgumentException("The x or y is out of bound.");
//    }
//    return pixels[x][y];
//  }

  @Override
  protected void setPixel(int x, int y, Pixel pixel) {
    if (pixel == null) {
      throw new IllegalArgumentException("The pixel cannot be null.");
    }
    if (x < 0 || x > this.height || y < 0 || y > this.width) {
      throw new IllegalArgumentException("The x or y is out of bound.");
    }
    if (!(pixel instanceof RGBPixel)) {
      throw new IllegalArgumentException("MyImage should only contain RGB pixels");
    }
    pixels[x][y] = pixel;
  }

//  /**
//   * Perform array addition an image with given matrix.
//   *
//   * @param matrix the given matrix (1x3)
//   * @throws IllegalArgumentException when the given matrix does not match
//   */
//  @Override
//  public MyImage imgArrayAddition(float[] matrix) throws IllegalArgumentException{
//    return mapElement(pixel -> pixel.addition(matrix));
//  }

  /**
   * Perform filtering an image with given matrix.
   *
   * @param kernel the given kernel
   * @throws IllegalArgumentException when the given argument is not legal
   */
  public MyImage filtering(float[][] kernel) {
    MyImage result = new MyImage(this.height, this.width);
    return (MyImage) super.filtering(kernel, result);
  }


  /**
   * Split channels of the given image
   *
   * @param channel the channel to split
   * @return the split result
   * @throws IllegalArgumentException when the given channel is illegal
   */
  @Override
  public MyImage channelSplit(Channel channel) throws IllegalArgumentException {
    MyImage result = new MyImage(this.height, this.width);
    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        Pixel pixel = this.getPixel(i, j);
        if (!pixel.containsChannel(channel)) {
          throw new IllegalArgumentException("The image does not have the given channel.");
        }
        result.setPixel(i, j, this.getPixel(i, j).getChannelComponent(channel));
      }
    }

    return result;
  }

  /**
   * Perform addition with another image.
   *
   * @param that the other image to be added
   * @return the add result
   * @throws IllegalArgumentException when image size not match or illegal image type
   */
  @Override
  public MyImage addition(Image that) {
    if(this.width!=that.width ||this.height!=that.height)
    {
      throw new IllegalArgumentException("Image size not match");
    }
    RGBPixel[][] resultPixels = new RGBPixel[height][width];
    for (int i = 0; i < height; i++) {
      System.arraycopy(this.pixels[i], 0, resultPixels[i], 0, width);
    }
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        resultPixels[i][j] = resultPixels[i][j].addition(that.pixels[i][j]);
      }
    }

    return new MyImage(resultPixels);
  }

  /**
   * Project coordinate of the original component element. For example, for a 2d image with height 5
   * and width 3, the originalDimensions are [5,3], and projectMatrix project pixel coordinate (1,0)
   * to (0,1), so the result[1][0] will be [0][1]. Those pixels that fall outside the image area
   * will be said to be project to (-1,-1)
   *
   * @param projectMatrix the matrix to be used to perform the projection
   * @return the project result
   * @throws IllegalArgumentException when the given argument is illegal
   */
  public MyImage projectCoordinate(int[][] projectMatrix)
      throws IllegalArgumentException {

//    if (projectMatrix.length != 2 || projectMatrix[0].length != 3 || projectMatrix[1].length != 3) {
//      throw new IllegalArgumentException("Project matrix should be 2x3 for MyImage");
//    }
    int[][][] projectResult = projectCoordinateCal(projectMatrix);
    RGBPixel[][] resultPixels = new RGBPixel[height][width];
//    //coordinate is reverse of row/column
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
//        int x=j;
//        int y=i;
//        int newX=projectMatrix[0][0] * x + projectMatrix[0][1] * y + projectMatrix[0][2];
//        int newY=projectMatrix[1][0] * x + projectMatrix[1][1] * y + projectMatrix[1][2];
//        if(newY<height && newX<width) {
//          resultPixels[newY][newX] = pixels[y][x];
//        }
//      }
//        projectResult[i][j]=new int[]{newY,newX};
        int newX = projectResult[i][j][1];
        int newY = projectResult[i][j][0];
        resultPixels[newY][newX] = (RGBPixel) pixels[i][j];
      }
    }
    return new MyImage(resultPixels);
  }
//
//  public MyImage arrayMultiplication(float[][] matrix)
//  {
//    return (MyImage) super.arrayMultiplication(matrix);
//  }

  /**
   * Map all pixels in the image with given pixel function.
   *
   * @param function the mapping function
   * @return the mapped result
   */
  public MyImage mapElement(Function<Pixel, Pixel> function) {
    MyImage result = new MyImage(this.height, this.width);
    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        Pixel originalPixel = this.getPixel(i, j);
        Pixel transformedPixel = function.apply(originalPixel);
        result.setPixel(i, j, transformedPixel);
      }
    }
    return result;
  }

  /**
   * Get channels of pixels in the image.
   *
   * @return channels of pixels in the image
   */
  @Override
  public Channel[] getChannels() {
    return new Channel[]{Channel.RED, Channel.GREEN, Channel.BLUE};
  }

  /**
   * Check if the image is monochrome of the given channel.
   *
   * @param channel the channel to check
   * @return if the image is monochrome of the given channel
   */
  @Override
  public boolean isMonochromeOfChannel(Channel channel) {
    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        RGBPixel pixel = (RGBPixel) this.getPixel(i, j);
        if (!pixel.containsChannel(channel) || !pixel.isMonochromeOfChannel(channel)) {
          return false;
        }
      }
    }
    return true;
  }

  // =====================Please double check======================
  // do we need to compare channels?

  /**
   * Check if two images are identical.
   *
   * @param o the other object to compare with
   * @return true if two image have the same size and every pixel are identical
   */
  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }

    if (!(o instanceof MyImage)) {
      return false;
    }

    MyImage otherImage = (MyImage) o;

    if (this.width != otherImage.getWidth() || this.height != otherImage.getHeight()) {
      return false;
    }

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (!pixels[y][x].equals(otherImage.pixels[y][x])) {
          System.out.println("here");
          return false;
        }
      }
    }

    return true;
  }

}
