package model.image;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Function;

import javax.imageio.ImageIO;

import model.Axis;
import model.Channel;
import model.compressor.Compressor;
import model.pixel.Pixel;
import model.pixel.RGBPixel;


/**
 * This class represents 8 bit depth RGB image, that has height, width, and 2d array of RGB pixels.
 */
public class MyImage extends Image {

  /**
   * Construct all black image with given height and width.
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

  private MyImage(MyImage other) {
    height = other.height;
    width = other.width;
    pixels = new RGBPixel[other.height][other.width];
    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        pixels[row][col] = new RGBPixel(other.getPixel(row, col).getRed(),
            other.getPixel(row, col).getGreen(), other.getPixel(row, col).getBlue());
      }
    }
  }

  /**
   * Construct an image with given local path.
   *
   * @param path the local image path
   * @throws IllegalArgumentException when there's problem with the path
   */
  public MyImage(String path) throws IllegalArgumentException {
    //remove quotes in case there's path with blank so requires to be in quotes
    if ((path.startsWith("\"") && path.endsWith("\"")) || (path.startsWith("'") && path.endsWith(
        "'"))) {
      path = path.substring(1, path.length() - 1);
    }
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

  private void loadPPM(String path) throws IOException, IllegalArgumentException {
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
        builder.append(s).append(System.lineSeparator());
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

  private void loadJPGPNG(String path) throws IOException, IllegalArgumentException {
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

  /**
   * Save image to local file.
   *
   * @param path the file path
   * @throws IllegalArgumentException if there's problem with the path
   */
  @Override
  public void save(String path) throws IllegalArgumentException {
    try {
      if (path.endsWith(".jpg") || path.endsWith(".jpeg")) {
        saveJPGPNG(path, "jpg");
      } else if (path.endsWith(".png")) {
        saveJPGPNG(path, "png");
      } else if (path.endsWith(".ppm")) {
        savePPM(path);
      } else {
        throw new IllegalArgumentException("Extension not supported.");
      }
    } catch (IllegalArgumentException | IOException e) {
      throw new IllegalArgumentException("Path does not exist or something wrong with file format"
                                         + ".");
    }
  }

  private void savePPM(String path) throws IOException, IllegalArgumentException {
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

  private void saveJPGPNG(String path, String format)
      throws IOException, IllegalArgumentException {
    if (!path.endsWith(".jpg") && !path.endsWith(".jpeg") && !path.endsWith(".png")) {
      throw new IllegalArgumentException("The path does not have ppm extension");
    }
    BufferedImage bufferedImage = new BufferedImage(width, height,
        BufferedImage.TYPE_INT_RGB);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int rgb = (((RGBPixel) pixels[y][x]).getRed() << 16)
                  | (((RGBPixel) pixels[y][x]).getGreen() << 8)
                  | ((RGBPixel) pixels[y][x]).getBlue();
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

  @Override
  RGBPixel getPixel(int x, int y) {
    if (x < 0 || x > this.height || y < 0 || y > this.width) {
      throw new IllegalArgumentException("The x or y is out of bound.");
    }
    return (RGBPixel) pixels[x][y];
  }

  @Override
  void setPixel(int x, int y, Pixel pixel) {
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

  /**
   * Perform filtering an image with given matrix.
   *
   * @param kernel the given kernel
   * @throws IllegalArgumentException when the given argument is not legal
   */
  @Override
  public MyImage filtering(float[][] kernel) throws IllegalArgumentException {
    MyImage result = new MyImage(this.height, this.width);
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
    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        //i,j is where the current kernel center lies relative to the coordinate of the image
        //row start and end are the area the kernel covers relative to the coordinate of the image
        int rowStart = Math.max(0, i - kernelCenterRow);
        int rowEnd = Math.min(this.height - 1, i + kernelCenterRow);
        int colStart = Math.max(0, j - kernelCenterCol);
        int colEnd = Math.min(this.width - 1, j + kernelCenterCol);
        float r = 0;
        float g = 0;
        float b = 0;
        //traver all pixels on the image in this area
        for (int x = rowStart; x <= rowEnd; x++) {
          for (int y = colStart; y <= colEnd; y++) {
            RGBPixel tmp = this.getPixel(x, y);
            r += tmp.getRed() * kernel[x - (i - kernelCenterRow)][y - (j - kernelCenterCol)];
            g += tmp.getGreen() * kernel[x - (i - kernelCenterRow)][y - (j - kernelCenterCol)];
            b += tmp.getBlue() * kernel[x - (i - kernelCenterRow)][y - (j - kernelCenterCol)];
          }
        }
        result.setPixel(i, j, new RGBPixel(Math.round(r), Math.round(g), Math.round(b)));
      }
    }
    return result;
  }


  /**
   * Split channels of the given image.
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
        result.setPixel(i, j, pixel.getChannelComponent(channel));
      }
    }

    return result;
  }

  /**
   * Perform array addition an image with given matrix.
   *
   * @param matrix the given matrix (1x3)
   * @return the added result
   * @throws IllegalArgumentException when given matrix size is not legal
   */
  @Override
  public MyImage imgArrayAddition(float[] matrix) throws IllegalArgumentException {
    return (MyImage) super.imgArrayAddition(matrix);
  }

  /**
   * Perform addition with another image.
   *
   * @param that the other image to be added
   * @return the add result
   * @throws IllegalArgumentException when image size not match or illegal image type
   */
  @Override
  public MyImage addition(Image that) throws IllegalArgumentException {
    if (this.width != that.width || this.height != that.height) {
      throw new IllegalArgumentException("Image size not match");
    }
    RGBPixel[][] resultPixels = new RGBPixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        resultPixels[i][j] = (RGBPixel) this.pixels[i][j];
      }
    }
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        resultPixels[i][j] = resultPixels[i][j].addition(that.pixels[i][j]);
      }
    }

    return new MyImage(resultPixels);
  }

  /**
   * Project coordinate of the original component element. For example, 1 0 w 0 -1 h projects (x,y)
   * to (x+w,-y+h), and project accordingly pixels in the old image to the new one.
   *
   * @param projectMatrix the matrix to be used to perform the projection
   * @return the project result
   * @throws IllegalArgumentException when the given argument is illegal
   */
  public MyImage projectCoordinate(int[][] projectMatrix)
      throws IllegalArgumentException {
    if (projectMatrix.length != 2 || projectMatrix[0].length != 3 || projectMatrix[1].length != 3) {
      throw new IllegalArgumentException("Project matrix should be 2x3 for MyImage");
    }
    int[][][] projectResult = projectCoordinateCal(projectMatrix);
    RGBPixel[][] resultPixels = new RGBPixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        resultPixels[i][j] = new RGBPixel(0, 0, 0);
      }
    }
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int newX = projectResult[i][j][1];
        int newY = projectResult[i][j][0];
        if (newY < height && newX < width && newY >= 0 && newX >= 0) {
          resultPixels[newY][newX] = (RGBPixel) pixels[i][j];
        }
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
   * Compress the image with given compressor.
   *
   * @param compressor the given compressor
   * @param ratio      the compress ration ([0,1])
   * @return the compressed image
   */
  @Override
  public Image compress(Compressor compressor, float ratio) {
    if (ratio < 0 || ratio > 1) {
      throw new IllegalArgumentException("Ratio cannot be smaller than 0 or larger than 1.");
    }
    float[][] redPixels = new float[height][width];
    float[][] greenPixels = new float[height][width];
    float[][] bluePixels = new float[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        redPixels[i][j] = getPixel(i, j).getRed();
        greenPixels[i][j] = getPixel(i, j).getGreen();
        bluePixels[i][j] = getPixel(i, j).getBlue();
      }
    }
    float[][] redPixelsCompressed =
        compressor.decompress2D(compressor.compress2D(redPixels, ratio));
    float[][] greenPixelsCompressed = compressor.decompress2D(compressor.compress2D(greenPixels,
        ratio));
    float[][] bluePixelsCompressed =
        compressor.decompress2D(compressor.compress2D(bluePixels, ratio));
    // restore as original size and discard the padding.
    RGBPixel[][] pixels = new RGBPixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        pixels[i][j] = new RGBPixel(Math.round(redPixelsCompressed[i][j]),
            Math.round(greenPixelsCompressed[i][j]),
            Math.round(bluePixelsCompressed[i][j]));
      }
    }
    return new MyImage(pixels);
  }

  /**
   * Split the images to 2 images according to the given percentage.
   *
   * @param percentage the split percentage ([0,1], the first part will be of that percentage)
   * @param axis       the axis to split (X means a vertical line split the images to 2 images
   *                   horizontally with the same height)
   * @return the split images (always with length 2, if one is empty when percentage is 0 or 1, that
   *     object will be null)
   */
  @Override
  public MyImage[] split(float percentage, Axis axis) {
    if (percentage < 0 || percentage > 1) {
      throw new IllegalArgumentException("The split percentage should be within [0,1]");
    }
    MyImage[] result = new MyImage[2];
    int wholeLength = (axis == Axis.X) ? width : height;
    int firstSize =
        (axis == Axis.X) ? Math.round(width * percentage) : Math.round(height * percentage);
    if (firstSize == 0) {
      result[0] = null;
      result[1] = new MyImage(this);
    } else if (firstSize == wholeLength) {
      result[0] = new MyImage(this);
      result[1] = null;
    } else {
      if (axis == Axis.X) {
        //left width, or the position of the split line
        RGBPixel[][] pixels1 = new RGBPixel[height][firstSize];
        for (int i = 0; i < height; i++) {
          for (int j = 0; j < firstSize; j++) {
            pixels1[i][j] = new RGBPixel(getPixel(i, j).getRed(), getPixel(i, j).getGreen(),
                getPixel(i, j).getBlue());
          }
        }
        RGBPixel[][] pixels2 = new RGBPixel[height][width - firstSize];
        for (int i = 0; i < height; i++) {
          for (int j = firstSize; j < width; j++) {
            pixels2[i][j - firstSize] =
                new RGBPixel(getPixel(i, j).getRed(), getPixel(i, j).getGreen(),
                    getPixel(i, j).getBlue());
          }
        }
        result[0] = new MyImage(pixels1);
        result[1] = new MyImage(pixels2);
      } else {
        RGBPixel[][] pixels1 = new RGBPixel[firstSize][width];
        for (int i = 0; i < firstSize; i++) {
          for (int j = 0; j < width; j++) {
            pixels1[i][j] = new RGBPixel(getPixel(i, j).getRed(), getPixel(i, j).getGreen(),
                getPixel(i, j).getBlue());
          }
        }
        RGBPixel[][] pixels2 = new RGBPixel[height - firstSize][width];
        for (int i = firstSize; i < height; i++) {
          for (int j = 0; j < width; j++) {
            pixels2[i - firstSize][j] =
                new RGBPixel(getPixel(i, j).getRed(), getPixel(i, j).getGreen(),
                    getPixel(i, j).getBlue());
          }
        }
        result[0] = new MyImage(pixels1);
        result[1] = new MyImage(pixels2);
      }
    }
    return result;
  }

  /**
   * Combine two images together on the given axis.
   *
   * @param other the other images to combine with this one
   * @param axis  the axis to combine on (X means combine two images with same height horizontally)
   * @return the combined image
   */
  @Override
  public MyImage combineImages(Image other, Axis axis) {
    if (other == null) {
      return this;
    }
    if (!(other instanceof MyImage)) {
      throw new IllegalArgumentException("Images to combine must be of same type.");
    }
    if (axis == Axis.X) {
      if (this.getHeight() != other.getHeight()) {
        throw new IllegalArgumentException("Images to combine are not of same height.");
      }
      RGBPixel[][] newPixels = new RGBPixel[height][width + other.getWidth()];
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          newPixels[i][j] = new RGBPixel(getPixel(i, j).getRed(), getPixel(i, j).getGreen(),
              getPixel(i, j).getBlue());
        }
        for (int j = 0; j < other.getWidth(); j++) {
          newPixels[i][width + j] = new RGBPixel(((RGBPixel) other.getPixel(i, j)).getRed(),
              ((RGBPixel) other.getPixel(i, j)).getGreen(),
              ((RGBPixel) other.getPixel(i, j)).getBlue());
        }
      }
      return new MyImage(newPixels);
    } else {
      if (this.getWidth() != other.getWidth()) {
        throw new IllegalArgumentException("Images to combine are not of same width.");
      }
      RGBPixel[][] newPixels = new RGBPixel[height + other.getHeight()][width];

      for (int j = 0; j < width; j++) {
        for (int i = 0; i < height; i++) {
          newPixels[i][j] = new RGBPixel(getPixel(i, j).getRed(), getPixel(i, j).getGreen(),
              getPixel(i, j).getBlue());
        }
        for (int i = 0; i < other.getHeight(); i++) {
          newPixels[height + i][j] = new RGBPixel(((RGBPixel) other.getPixel(i, j)).getRed(),
              ((RGBPixel) other.getPixel(i, j)).getGreen(),
              ((RGBPixel) other.getPixel(i, j)).getBlue());
        }
      }
      return new MyImage(newPixels);
    }
  }

  /**
   * Get appearance frequency of colors in the image.
   */
  private float[][] getFrequency() {
//    float[][] greyscale = new float[][]{
//        {0.2126f, 0.7152f, 0.0722f},
//        {0.2126f, 0.7152f, 0.0722f},
//        {0.2126f, 0.7152f, 0.0722f}
//    };
    int length = 1 << RGBPixel.bitDepth;
//    MyImage redGreyscale = (MyImage) channelSplit(Channel.RED).matrixMultiplication(greyscale);
//    MyImage greenGreyscale = (MyImage) channelSplit(Channel.GREEN).matrixMultiplication(greyscale);
//    MyImage blueGreyscale = (MyImage) channelSplit(Channel.BLUE).matrixMultiplication(greyscale);
    float[] redCount = new float[length];
    float[] greenCount = new float[length];
    float[] blueCount = new float[length];
    for (int row = 0; row < height; row++) {
      for (int column = 0; column < width; column++) {
        redCount[(getPixel(row, column)).getRed()] += 1;
        greenCount[(getPixel(row, column)).getGreen()] += 1;
        blueCount[(getPixel(row, column)).getBlue()] += 1;
      }
    }

    for (int i = 0; i < length; i++) {
      redCount[i] /= (height*width);
      greenCount[i] /= (height*width);
      blueCount[i] /= (height*width);
    }
    return new float[][]{redCount, greenCount, blueCount};
  }

  private float getMax(float... nums) {
    if (nums == null || nums.length == 0) {
      throw new IllegalArgumentException("Nums are null or empty");
    }
    float max = Float.NEGATIVE_INFINITY;

    for (float value : nums) {
      max = Math.max(max, value);
    }

    return max;
  }

  private float getMin(float... nums) {
    if (nums == null || nums.length == 0) {
      throw new IllegalArgumentException("Nums are null or empty");
    }
    float min = Float.POSITIVE_INFINITY;

    for (float value : nums) {
      min = Math.min(min, value);
    }

    return min;
  }

  private int findIndexOf(float value, float[] arr) {
    for (int i = 0; i < arr.length; i++) {
      if (arr[i] == value) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Get histogram of the current image.
   *
   * @return the histogram of the current image
   */
  @Override
  public MyImage getHistogram() {
    int dimension = 1 << RGBPixel.bitDepth;
    RGBPixel[][] histogramPixels = new RGBPixel[dimension][dimension];
    float[][] freq = getFrequency();
    float[] redFreq = freq[0];
    float[] greenFreq = freq[1];
    float[] blueFreq = freq[2];
    float maxFreq = getMax(getMax(redFreq), getMax(greenFreq), getMax(blueFreq));
    //the length of gap/small grid vertically
    float gap = maxFreq / (dimension - 1);
//    float redGap = (getMax(redFreq) - getMin(redFreq)) / (dimension - 1);
//    float greenGap = (getMax(greenFreq) - getMin(greenFreq)) / (dimension - 1);
//    float blueGap = (getMax(blueFreq) - getMin(blueFreq)) / (dimension - 1);

    int[] redIndices = new int[dimension];
    int[] greenIndices = new int[dimension];
    int[] blueIndices = new int[dimension];
    // 256 x 256
    for (int i = 0; i < dimension; i++) {
      redIndices[i] = dimension - 1 - Math.round(redFreq[i] / gap);
      greenIndices[i] = dimension - 1 - Math.round(greenFreq[i] / gap);
      blueIndices[i] = dimension - 1 - Math.round(blueFreq[i] / gap);
    }
    for (int x = 0; x < dimension; x++) {
      int redIndex = redIndices[x];
      int greenIndex = greenIndices[x];
      int blueIndex = blueIndices[x];
      //todo: overlap with the second color or blend/mix?
      //the final one
      if (x == dimension - 1) {
        histogramPixels[redIndex][x] = new RGBPixel(255, 0, 0);
        histogramPixels[greenIndex][x] = new RGBPixel(0, 255, 0);
        histogramPixels[blueIndex][x] = new RGBPixel(0, 0, 255);
      }
      //draw the vertical line to the same height as next one
      else {
        int redLower = Math.min(redIndex, redIndices[x + 1]);
        int redHigher = Math.max(redIndex, redIndices[x + 1]);
        for (int y = redLower; y < redHigher; y++) {
          histogramPixels[y][x] = new RGBPixel(255, 0, 0);
        }
        int greenLower = Math.min(greenIndex, greenIndices[x + 1]);
        int greenHigher = Math.max(greenIndex, greenIndices[x + 1]);
        for (int y = greenLower; y < greenHigher; y++) {
          histogramPixels[y][x] = new RGBPixel(0, 255, 0);
        }
        int blueLower = Math.min(blueIndex, blueIndices[x + 1]);
        int blueHigher = Math.max(blueIndex, blueIndices[x + 1]);
        for (int y = blueLower; y < blueHigher; y++) {
          histogramPixels[y][x] = new RGBPixel(0, 0, 255);
        }
      }
      //all other spaces are left white
      for (int y = 0; y < dimension; y++) {
        if (histogramPixels[y][x] == null) {
          histogramPixels[y][x] = new RGBPixel(255, 255, 255);
        }
      }
    }
    return new MyImage(histogramPixels);
  }

  /**
   * Color-correct an image by aligning the meaningful peaks of its histogram.
   *
   * @return the color-corrected result
   */
  @Override
  public Image colorCorrect() {
    float[][] freq = getFrequency();
    //  only consider values greater than 10 and lesser than 245 in each channel
    float[] redFreq = Arrays.copyOfRange(freq[0], 11, 245);
    float[] greenFreq = Arrays.copyOfRange(freq[1], 11, 245);
    float[] blueFreq = Arrays.copyOfRange(freq[2], 11, 245);
    // find the peaks (and the values at which they occur) of each channel in the histogram.
    float redFreqHighest = getMax(redFreq);
    int redFreqHighestValue = findIndexOf(redFreqHighest, redFreq);
    float greenFreqHighest = getMax(greenFreq);
    int greenFreqHighestValue = findIndexOf(greenFreqHighest, greenFreq);
    float blueFreqHighest = getMax(blueFreq);
    int blueFreqHighestValue = findIndexOf(blueFreqHighest, blueFreq);
    // Then we compute the average value across peaks.
    float perkAverageValue =
        (redFreqHighestValue + greenFreqHighestValue + blueFreqHighestValue) / 3.0f;
    // align with the average perk
    float redDelta = perkAverageValue - redFreqHighestValue;
    float greenDelta = perkAverageValue - greenFreqHighestValue;
    float blueDelta = perkAverageValue - blueFreqHighestValue;
    return imgArrayAddition(new float[]{redDelta, greenDelta, blueDelta});
  }

  /**
   * Perform level adjustment on the image.
   *
   * @param black the positions of the black (shadow) point on the horizontal axis
   * @param mid   the positions of the middle point on the horizontal axis
   * @param white the positions of the white (highlight) point on the horizontal axis
   * @return the adjusted image
   */
  @Override
  public Image levelAdjustment(float black, float mid, float white) {
    //fitting the curve y = ax^2+bx+c
    float A =
        black * black * (mid - white) - black * (mid * mid - white * white) + white * mid * mid
        - mid * white * white;
    float aa = -black * (128 - 255) + 128 * white - 255 * mid;
    float ab = black * black * (128 - 255) + 255 * mid * mid - 128 * white * white;
    float ac =
        black * black * (255 * mid - 128 * white) - black * (255 * mid * mid - 128 * white * white);
    float a = aa / A;
    float b = ab / A;
    float c = ac / A;
    RGBPixel[][] newPixels = new RGBPixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        RGBPixel pixel = getPixel(i, j);
        int originalRed = pixel.getRed();
        int originalGreen = pixel.getGreen();
        int originalBlue = pixel.getBlue();
        int newRed = Math.round(a * originalRed * originalRed + b * originalRed + c);
        int newGreen = Math.round(a * originalGreen * originalGreen + b * originalGreen + c);
        int newBlue = Math.round(a * originalBlue * originalBlue + b * originalBlue + c);
        newPixels[i][j] = new RGBPixel(newRed, newGreen, newBlue);
      }
    }
    return new MyImage(newPixels);
  }

  /**
   * Check if two objects are identical.
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
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Returns a hash code value for the object.
   *
   * @return a hash code value for this object.
   */
  @Override
  public int hashCode() {
    int result = Objects.hash(height, width);
    result = 31 * result + Arrays.deepHashCode(pixels);
    return result;
  }
}
