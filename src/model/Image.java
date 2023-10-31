package model;

import java.io.IOException;
import java.util.function.Function;

/**
 * This interface represents images.
 */
public abstract class Image {
  protected Pixel[][] pixels;
  protected int height;
  protected int width;

  public Image() {
  }

  public Image(int height, int width) {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Invalid width and height.");
    }
    this.height = height;
    this.width = width;
  }


  public Image(String path) throws IllegalArgumentException {
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

  protected abstract void loadPPM(String path) throws IOException, IllegalArgumentException;

  protected abstract void loadJPGPNG(String path) throws IOException, IllegalArgumentException;

  /**
   * Save image to local file.
   *
   * @param path the file path
   * @throws IOException if there's problem with IO
   */
  public void save(String path) throws IOException {
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

  protected abstract void savePPM(String path) throws IOException, IllegalArgumentException;

  protected abstract void saveJPGPNG(String path, String format) throws IOException,
      IllegalArgumentException;

  /**
   * Get height of the image.
   *
   * @return height of the image
   */
  public int getHeight() {
    return this.height;
  }

  /**
   * Get width of the image.
   *
   * @return width of the image
   */
  public int getWidth() {
    return this.width;
  }

  protected Pixel getPixel(int x, int y) {
    if (x < 0 || x > this.height || y < 0 || y > this.width) {
      throw new IllegalArgumentException("The x or y is out of bound.");
    }
    return pixels[x][y];
  }

  protected void setPixel(int x, int y, Pixel pixel) {
    if (pixel == null) {
      throw new IllegalArgumentException("The pixel cannot be null.");
    }
    if (x < 0 || x > this.height || y < 0 || y > this.width) {
      throw new IllegalArgumentException("The x or y is out of bound.");
    }
    pixels[x][y] = pixel;
  }

  /**
   * Multiply matrix to the every pixel of the image.
   *
   * @param matrix the multiplying image
   * @return the multiplied result
   * @throws IllegalArgumentException when the given matrix is illegal
   */
  public Image matrixMultiplication(float[][] matrix) throws IllegalArgumentException {
    return mapElement(pixel -> pixel.linearTransformation(matrix));
  }

  /**
   * Split channels of the given image
   *
   * @param channel the channel to split
   * @return the split result
   * @throws IllegalArgumentException when the given channel is illegal
   */
  public abstract Image channelSplit(Channel channel) throws IllegalArgumentException;

  /**
   * Perform filtering an image with given matrix.
   *
   * @param kernel the given kernel
   * @throws IllegalArgumentException when the given argument is not legal
   */
  public abstract Image filtering(float[][] kernel);

  /**
   * Perform filtering an image with given matrix. Put result on the given result argument.
   *
   * @param kernel the given kernel
   * @param result the result Image to put the changes to
   * @return the filtering result
   * @throws IllegalArgumentException when the given argument is not legal
   */
  protected Image filtering(float[][] kernel, Image result) {
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
        //traver all pixels on the image in this area
        for (int x = rowStart; x <= rowEnd; x++) {
          for (int y = colStart; y <= colEnd; y++) {
            Pixel tmp =
                this.getPixel(x, y)
                    .multiplyNumber(kernel[x - (i - kernelCenterRow)][y - (j - kernelCenterCol)]);
            result.setPixel(i, j, result.getPixel(i, j).addition(tmp));
          }
        }
      }
    }
    return result;
  }

  public abstract Image mapElement(Function<Pixel, Pixel> function);

  public abstract Image projectCoordinate(int[][] projectMatrix);

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
  protected int[][][] projectCoordinateCal(int[][] projectMatrix) throws IllegalArgumentException {
    int[][][] projectResult = new int[height][width][2];
    if (projectMatrix.length != 2 || projectMatrix[0].length != 3 || projectMatrix[1].length != 3) {
      throw new IllegalArgumentException("Project matrix should be 2x3 for 2d image");
    }
//    Pixel[][] resultPixels = new Pixel[height][width];
    //coordinate is reverse of row/column
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int newX = projectMatrix[0][0] * j + projectMatrix[0][1] * i + projectMatrix[0][2];
        int newY = projectMatrix[1][0] * j + projectMatrix[1][1] * i + projectMatrix[1][2];
        if (newY < height && newX < width) {
          //        int x=j;
          //        int y=i;
//          resultPixels[newY][newX] = pixels[y][x];
          projectResult[i][j] = new int[]{newY, newX};
        }
      }
    }
//    return new MyImage(resultPixels);
    return projectResult;
  }

  /**
   * Perform addition with another image.
   *
   * @param that the other image to be added
   * @return the add result
   */
  public abstract Image addition(Image that);

  /**
   * Perform array addition an image with given matrix.
   *
   * @param matrix the given matrix (1x3)
   */
  public Image imgArrayAddition(float[] matrix) {
    return mapElement(pixel -> pixel.addition(matrix));
  }

  /**
   * Get channels of pixels in the image.
   *
   * @return channels of pixels in the image
   */
  public abstract Channel[] getChannels();

  /**
   * Check if the image is monochrome of the given channel.
   *
   * @param channel the channel to check
   * @return if the image is monochrome of the given channel
   */
  public abstract boolean isMonochromeOfChannel(Channel channel);

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        sb.append(pixels[y][x].toString());
        sb.append("   ");
      }
      sb.append("\n");
    }
    return sb.toString();
  }
}
