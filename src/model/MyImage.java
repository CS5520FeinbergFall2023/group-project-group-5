package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.function.Function;

import javax.imageio.ImageIO;


/**
 * This class represents 8 bit depth RGB image.
 */
public class MyImage implements Image {
  private RGBPixel[][] pixels;
  private int height;
  private int width;

  public MyImage(String path) throws IllegalArgumentException {
    try {
      if (path.endsWith(".jpg") || path.endsWith(".jpeg") || path.endsWith(".png")) {
        loadJPGPNG(path);
      } else if (path.endsWith(".ppm")) {
        loadPPM(path);
      } else {
        throw new IllegalArgumentException("Extension not supported.");
      }
    } catch (IllegalArgumentException | IOException e) {
      throw new IllegalArgumentException("Path does not exist or something wrong with file format"
                                         + ".");
    }
  }

  /**
   * Construct all white image with given height and width
   *
   * @param height height of the image
   * @param width  weight of the image
   */
  public MyImage(int height, int width) {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Invalid width and height.");
    }

    this.height = height;
    this.width = width;
    pixels = new RGBPixel[height][width];
    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        pixels[row][col] = new RGBPixel(0, 0, 0);
      }
    }
  }

  private MyImage(RGBPixel[][] pixels) {
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
    this.height = height;
    this.width = width;
    this.pixels = pixels;
  }


  private void loadPPM(String path) throws IOException, IllegalArgumentException {
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(path));
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

  private void loadJPGPNG(String path) throws IOException {
    BufferedImage image;
    image = ImageIO.read(new File(path));
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
  // 实现保存图像的代码
  public void save(String path) throws IOException {

  }

  private void savePPM(String path) {

  }

  private void saveJPG(String path) {

  }

  private void savePNG(String path) {

  }


  /**
   * @return
   */
  @Override
  public int getHeight() {
    return this.height;
  }

  /**
   * @return
   */
  @Override
  public int getWidth() {
    return this.width;
  }


  private RGBPixel getPixel(int x, int y) {
    if (x < 0 || x > this.width || y < 0 || y > this.height) {
      throw new IllegalArgumentException("The x or y is out of bound.");
    }
    return pixels[x][y];
  }

  private void setPixel(int x, int y, Pixel pixel) {
    if (pixel == null) {
      throw new IllegalArgumentException("The pixel cannot be null.");
    }
    if (x < 0 || x > this.width || y < 0 || y > this.height) {
      throw new IllegalArgumentException("The x or y is out of bound.");
    }
    if (!(pixel instanceof RGBPixel)) {
      throw new IllegalArgumentException("MyImage should only contain RGB pixels");
    }
    pixels[x][y] = (RGBPixel) pixel;
  }

  /**
   * Perform array addition an image with given matrix.
   *
   * @param matrix the given matrix (1x3)
   */
  @Override
  public MyImage imgArrayAddition(float[] matrix) {
//    MyImage result = new MyImage(this.height, this.width);
//    for (int i = 0; i < this.height; i++) {
//      for (int j = 0; j < this.width; j++) {
//        result.setPixel(i, j, this.getPixel(i, j).addition(matrix));
//      }
//    }
//    return result;
    return mapElement(pixel -> pixel.addition(matrix));
  }

  /**
   * Perform filtering an image with given matrix.
   *
   * @param kernel the given kernel
   * @throws IllegalArgumentException when the given argument is not legal
   */
  @Override
  public MyImage filtering(float[][] kernel) {
    int kernelHeight = kernel.length;
    int kernelWidth = kernel[0].length;
    if (kernel.length % 2 == 0) {
      throw new IllegalArgumentException("The kernel should have odd dimensions.");
    }
    for (float[] k : kernel) {
      if (k.length % 2 == 0) {
        throw new IllegalArgumentException("The kernel should have odd dimensions.");
      }
      if (k.length != kernelWidth) {
        throw new IllegalArgumentException("The kernel should be rectangle.");
      }
    }
    //x, y coordinate of the center of the kernel relative to the coordinate of the kernel
    int kernelCenterRow = kernelHeight / 2;
    int kernelCenterCol = kernelWidth / 2;

    MyImage result = new MyImage(this.height, this.width);

    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        //i,j is where the current kernel center lies relative to the coordinate of the image
        //row start and end are the area the kernel covers relative to the coordinate of the image
        int rowStart = Math.max(0, i - kernelCenterRow);
        int rowEnd = Math.min(this.width - 1, i + kernelCenterRow);
        int colStart = Math.max(0, j - kernelCenterCol);
        int colEnd = Math.min(this.height - 1, j + kernelCenterCol);
        //traver all pixels on the image in this area
        for (int x = rowStart; x <= rowEnd; x++) {
          for (int y = colStart; y <= colEnd; y++) {
            Pixel tmp =
                this.getPixel(x, y)
                    .multiplyNumber(kernel[x - (i - kernelCenterRow)][y - (j - kernelCenterCol)]);
            result.setPixel(i, j,
                result.getPixel(i, j).addition(this.getPixel(i, j).addition(tmp)));
          }
        }
      }
    }
    return result;
  }

  /**
   * Perform linear transformation on an image with given matrix, which means multiplying every
   * pixel with the given matrix.
   *
   * @param matrix the given matrix
   * @return the new image after modification
   */
  @Override
  public MyImage arrayMultiplication(float[][] matrix) {
    MyImage result = new MyImage(this.height, this.width);
    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        result.setPixel(i, j, this.getPixel(i, j).linearTransformation(matrix));
      }
    }
    return result;
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
          throw new IllegalArgumentException("The RGB image does not have the given channel.");
        }
        result.setPixel(i, j, result.getPixel(i, j).getChannelComponent(channel));
      }
    }
    return result;
  }

  /**
   * Perform addition with another image.
   *
   * @param that the other image to be added
   * @return the add result
   */
  @Override
  public MyImage addition(Image that) {
    RGBPixel[][] resultPixels = new RGBPixel[height][width];
    for (int i = 0; i < height; i++) {
      System.arraycopy(this.pixels[i], 0, resultPixels[i], 0, width);
    }
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        resultPixels[i][j] = resultPixels[i][j].addition(((MyImage) that).pixels[i][j]);
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
  @Override
  public MyImage projectCoordinate(int[][] projectMatrix)
      throws IllegalArgumentException {
    int[][][] projected = new int[height][width][2];
    if (projectMatrix.length != 2 || projectMatrix[0].length != 3 || projectMatrix[1].length != 3) {
      throw new IllegalArgumentException("Project matrix should be 2x3 for MyImage");
    }
    //traverse row
    for (int i = 0; i < height; i++) {
      //traverse column
      for (int j = 0; j < width; j++) {
        projected[i][j] =
            new int[]{projectMatrix[0][0] * i + projectMatrix[0][1] * j + projectMatrix[0][2],
                projectMatrix[1][0] * i + projectMatrix[1][1] * j + projectMatrix[1][2]};
      }
    }
    RGBPixel[][] resultPixels = new RGBPixel[height][width];
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        resultPixels[projected[row][col][0]][projected[row][col][1]] = pixels[row][col];
      }
    }
    return new MyImage(resultPixels);
  }

  /**
   * Map all pixels in the image with given pixel function.
   *
   * @param function the mapping function
   * @return the mapped result
   */
  @Override
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
        RGBPixel pixel = this.getPixel(i, j);
        if (!pixel.containsChannel(channel) || !pixel.isMonochromeOfChannel(channel)) {
          return false;
        }
      }
    }
    return true;
  }
}
