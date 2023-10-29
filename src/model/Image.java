package model;

import java.io.IOException;
import java.util.function.Function;

/**
 * This interface represents images.
 */
public interface Image {

  /**
   * Save image to local file.
   *
   * @param fileName the file path
   * @throws IOException if there's problem with IO
   */
  void save(String fileName) throws IOException;

  /**
   * Get height of the image.
   *
   * @return height of the image
   */
  int getHeight();

  /**
   * Get width of the image.
   *
   * @return width of the image
   */
  int getWidth();

  /**
   * Multiply matrix to the every pixel of the image.
   *
   * @param matrix the multiplying image
   * @return the multiplied result
   */
  Image arrayMultiplication(float[][] matrix);

  /**
   * Split channels of the given image
   *
   * @param channel the channel to split
   * @return the split result
   * @throws IllegalArgumentException when the given channel is illegal
   */
  Image channelSplit(Channel channel) throws IllegalArgumentException;

  /**
   * Perform filtering an image with given matrix. Modification is made in-place.
   *
   * @param kernel the given kernel
   * @throws IllegalArgumentException when the given argument is not legal
   */
  Image filtering(float[][] kernel);

  /**
   * Map all pixels in the image with given pixel function.
   *
   * @param function the mapping function
   * @return the mapped result
   */
  Image mapElement(Function<Pixel, Pixel> function);

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
  Image projectCoordinate(int[][] projectMatrix);

  /**
   * Perform addition with another image.
   *
   * @param that the other image to be added
   * @return the add result
   */
  Image addition(Image that);

  /**
   * Perform array addition an image with given matrix.
   *
   * @param matrix the given matrix (1x3)
   */
  Image imgArrayAddition(float[] matrix);

  /**
   * Get channels of pixels in the image.
   *
   * @return channels of pixels in the image
   */
  Channel[] getChannels();

  /**
   * Check if the image is monochrome of the given channel.
   *
   * @param channel the channel to check
   * @return if the image is monochrome of the given channel
   */
  boolean isMonochromeOfChannel(Channel channel);
}
