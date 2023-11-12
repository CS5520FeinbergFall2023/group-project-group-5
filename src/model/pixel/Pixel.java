package model.pixel;

import java.util.Map;

import model.Channel;

/**
 * This interface represents a pixel. Every pixel has a map representing the value of the pixel in
 * each channel.
 */
public interface Pixel {

  /**
   * Checks if the pixel has the given channel.
   *
   * @param channel the channel to check.
   * @return if the pixel has the given channel
   */
  public boolean containsChannel(Channel channel);

  /**
   * Perform linear transformation. E.g. calculate 3x3 matrix * [r,g,b].
   *
   * @param matrix the matrix to multiply
   * @return the result pixel
   * @throws IllegalArgumentException when the given matrix is not legal
   */
  Pixel linearTransformation(float[][] matrix) throws IllegalArgumentException;

  /**
   * Adds matrix to pixel. E.g. calculate pixel[r',g',b'] = matrix[x,y,z] + pixel[r,g,b].
   *
   * @param matrix the matrix to add
   * @return the result pixel
   * @throws IllegalArgumentException when the given matrix is not legal
   */
  Pixel addition(float[] matrix) throws IllegalArgumentException;

  /**
   * Adds two pixels. E.g. calculate [r,g,b] + [r',g',b'].
   *
   * @param pixel the pixel to add
   * @return the result pixel
   * @throws IllegalArgumentException when the given pixel is not legal
   */
  Pixel addition(Pixel pixel) throws IllegalArgumentException;

  /**
   * Get certain channel component of the pixel.
   *
   * @param channel the channel to split
   * @return the component pixel
   */
  Pixel getChannelComponent(Channel channel);

  /**
   * Calculate the max value among all channels of the pixel and get a pixel with all channels this
   * value.
   *
   * @return a pixel with all channels the max value among all channels
   */
  Pixel max();

  /**
   * Calculate the average value among all channels of the pixel and get a pixel with all channels
   * this value.
   *
   * @return a pixel with all channels the average value among all channels
   */
  Pixel avg();

  /**
   * Check if this pixel is greyscale.
   *
   * @return if this pixel is greyscale
   */
  boolean isGreyscale();

}