package model;

public interface Pixel {
  /**
   * Calculate matrix * [r,g,b]
   *
   * @param matrix the matrix to multiply
   * @return the result pixel
   * @throws IllegalArgumentException when the given matrix is not legal
   */
  Pixel linearTransformation(float[][] matrix) throws IllegalArgumentException;

  /**
   * Calculate matrix + [r,g,b]
   *
   * @param matrix the matrix to add
   * @return the result pixel
   * @throws IllegalArgumentException when the given matrix is not legal
   */
  Pixel addition(float[] matrix) throws IllegalArgumentException;

  /**
   * Calculate [r,g,b] + [r',g',b']
   *
   * @param pixel the pixel to add
   * @return the result pixel
   * @throws IllegalArgumentException when the given pixel is not legal
   */
  Pixel addition(Pixel pixel) throws IllegalArgumentException;

  /**
   * Multiply the pixel with a number.
   *
   * @param number the number to multiply
   * @return the new pixel after the operation
   * @throws IllegalArgumentException when the given number is not legal
   */
  Pixel multiplyNumber(float number) throws IllegalArgumentException;

  /** Checks if the pixel has the given channel.
   *
   * @param channel the channel to check.
   * @return if the pixel has the given channel
   */
  boolean containsChannel(Channel channel);


  /** Get certain channel component of the pixel.
   *
   * @param channel the channel to split
   * @return the component pixel
   */
  Pixel getChannelComponent(Channel channel);

  /** Calculate the max value among all channels of the pixel and get a pixel with all channels this
   * value.
   * @return a pixel with all channels the max value among all channels
   */
  Pixel max();

  /** Calculate the average value among all channels of the pixel and get a pixel with all channels
   * this value.
   * @return a pixel with all channels the average value among all channels
   */
  Pixel avg();

//  Pixel luma();

}
