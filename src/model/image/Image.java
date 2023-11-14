package model.image;

import java.util.function.Function;

import model.Axis;
import model.Channel;
import model.compressor.Compressor;
import model.pixel.Pixel;

/**
 * This interface represents images, that has height, width, and 2d array of pixels.
 */
public interface Image {

  /**
   * Save image to local file.
   *
   * @param path the file path
   * @throws IllegalArgumentException if there's problem with the path
   */
  void save(String path) throws IllegalArgumentException;

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
   * Check if this is a greyscale image.
   *
   * @return if this is a greyscale image
   */
  boolean isGreyscale();

  /**
   * Multiply matrix to the every pixel of the image.
   *
   * @param matrix the multiplying image
   * @return the multiplied result
   * @throws IllegalArgumentException when the given matrix is illegal
   */
  Image matrixMultiplication(float[][] matrix) throws IllegalArgumentException;

  /**
   * Split channels of the given image.
   *
   * @param channel the channel to split
   * @return the split result
   * @throws IllegalArgumentException when the given channel is illegal
   */
  Image channelSplit(Channel channel) throws IllegalArgumentException;

  /**
   * Perform filtering an image with given matrix.
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
   * Project coordinate of the original component element and actually move the pixels. For example,
   * 1 0 w 0 -1 h projects (x,y) to (x+w,-y+h), and project accordingly pixels in the old image to
   * the new one.
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
   * @throws IllegalArgumentException when given argument is illegal
   */
  Image addition(Image that) throws IllegalArgumentException;

  /**
   * Perform array addition an image with given matrix.
   *
   * @param matrix the given matrix (1x3)
   * @return the added result
   * @throws IllegalArgumentException when given argument is illegal
   */
  Image imgArrayAddition(float[] matrix) throws IllegalArgumentException;

  /**
   * Compress the image with given compressor.
   *
   * @param compressor the given compressor
   * @param ratio      the compress ration ([0,1])
   * @return the compressed image
   * @throws IllegalArgumentException when given argument is illegal
   */
  Image compress(Compressor compressor, float ratio) throws IllegalArgumentException;

  /**
   * Get channels of pixels in the image.
   *
   * @return channels of pixels in the image
   */
  Channel[] getChannels();


  /**
   * Split the images to 2 images according to the given percentage on the given axis.
   *
   * @param percentage the split percentage ([0,1], the first part will be of that percentage)
   * @param axis       the axis to split (X means a vertical line split the images to 2 images
   *                   horizontally with the same height)
   * @return the split images (always with length 2, if one is empty when percentage is 0 or 1, that
   *     object will be null)
   * @throws IllegalArgumentException when given argument is illegal
   */
  Image[] split(float percentage, Axis axis) throws IllegalArgumentException;

  /**
   * Combine two images together on the given axis.
   *
   * @param other the other images to combine with this one
   * @param axis  the axis to combine on (X means combine two images with same height horizontally)
   * @return the combined image
   * @throws IllegalArgumentException when given arguments are illegal
   */
  Image combineImages(Image other, Axis axis) throws IllegalArgumentException;

  /**
   * Get histogram of the current image.
   *
   * @return the histogram of the current image
   */
  Image getHistogram();

  /**
   * Color-correct an image by aligning the meaningful peaks of its histogram.
   *
   * @return the color-corrected result
   */
  Image colorCorrect();

  /**
   * Perform level adjustment on the image.
   *
   * @param black     the positions of the black (shadow) point on the horizontal axis
   * @param mid       the positions of the middle point on the horizontal axis
   * @param highlight the positions of the white (highlight) point on the horizontal axis
   * @return the adjusted image
   */
  Image levelAdjustment(float black, float mid, float highlight);


}
