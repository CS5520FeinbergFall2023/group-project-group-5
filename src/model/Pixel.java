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

}
