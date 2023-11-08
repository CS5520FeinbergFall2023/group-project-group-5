package model.image;

import java.util.function.Function;

import model.Channel;
import model.compressor.Compressor;
import model.compressor.HaarWaveletCompressor;
import model.pixel.Pixel;

/**
 * This interface represents images, that has height, width, and 2d array of pixels.
 */
public abstract class Image {
  Pixel[][] pixels;
  int height;
  int width;

  /**
   * Save image to local file.
   *
   * @param path the file path
   * @throws IllegalArgumentException if there's problem with the path
   */
  public abstract void save(String path) throws IllegalArgumentException;

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

  Pixel getPixel(int x, int y) throws IllegalArgumentException {
    if (x < 0 || x > this.height || y < 0 || y > this.width) {
      throw new IllegalArgumentException("The x or y is out of bound.");
    }
    return pixels[x][y];
  }

  void setPixel(int x, int y, Pixel pixel) throws IllegalArgumentException {
    if (pixel == null) {
      throw new IllegalArgumentException("The pixel cannot be null.");
    }
    if (x < 0 || x > this.height || y < 0 || y > this.width) {
      throw new IllegalArgumentException("The x or y is out of bound.");
    }
    pixels[x][y] = pixel;
  }


  /**
   * Check if this is a greyscale image.
   *
   * @return if this is a greyscale image
   */
  public boolean isGreyscale() {
    for (Pixel[] row : pixels) {
      for (Pixel p : row) {
        if (!p.isGreyscale()) {
          return false;
        }
      }
    }
    return true;
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
   * Split channels of the given image.
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
   * Map all pixels in the image with given pixel function.
   *
   * @param function the mapping function
   * @return the mapped result
   */
  public abstract Image mapElement(Function<Pixel, Pixel> function);

  /**
   * Project coordinate of the original component element and actually move the pixels. For example,
   * 1 0 w 0 -1 h projects (x,y) to (x+w,-y+h), and project accordingly pixels in the old image to
   * the new one.
   *
   * @param projectMatrix the matrix to be used to perform the projection
   * @return the project result
   * @throws IllegalArgumentException when the given argument is illegal
   */
  public abstract Image projectCoordinate(int[][] projectMatrix);

  /**
   * Project coordinate of the original component element but only calculate the coordinate
   * projection relations. projectResult[y][x] = [newY,newX]
   *
   * @param projectMatrix the matrix to be used to perform the projection
   * @return the project matrix
   * @throws IllegalArgumentException when the given argument is illegal
   */
  int[][][] projectCoordinateCal(int[][] projectMatrix) throws IllegalArgumentException {
    int[][][] projectResult = new int[height][width][2];
    if (projectMatrix.length != 2 || projectMatrix[0].length != 3 || projectMatrix[1].length != 3) {
      throw new IllegalArgumentException("Project matrix should be 2x3 for 2d image");
    }
    //coordinate is reverse of row/column
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int newX = projectMatrix[0][0] * j + projectMatrix[0][1] * i + projectMatrix[0][2];
        int newY = projectMatrix[1][0] * j + projectMatrix[1][1] * i + projectMatrix[1][2];
        if (newY < height && newX < width) {
          // int x=j;
          // int y=i;
          // resultPixels[newY][newX] = pixels[y][x];
          projectResult[i][j] = new int[]{newY, newX};
        } else {
          projectResult[i][j] = new int[]{-1, -1};
        }
      }
    }
    return projectResult;
  }

  /**
   * Perform addition with another image.
   *
   * @param that the other image to be added
   * @return the add result
   * @throws IllegalArgumentException when given argument is illegal
   */
  public abstract Image addition(Image that) throws IllegalArgumentException;

  /**
   * Perform array addition an image with given matrix.
   *
   * @param matrix the given matrix (1x3)
   * @return the added result
   * @throws IllegalArgumentException when given argument is illegal
   */
  public Image imgArrayAddition(float[] matrix) throws IllegalArgumentException {
    return mapElement(pixel -> pixel.addition(matrix));
  }

  /**
   * Compress the image with given compressor.
   *
   * @param compressor the given compressor
   * @param ratio      the compress ration ([0,1])
   * @return the compressed image
   */
  public abstract Image compress(Compressor compressor, float ratio);

  /**
   * Get channels of pixels in the image.
   *
   * @return channels of pixels in the image
   */
  public abstract Channel[] getChannels();

  /**
   * Returns a string representation of the object.
   *
   * @return a string representation of the object.
   */
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
