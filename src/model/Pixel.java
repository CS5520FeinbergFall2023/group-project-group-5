package model;

public interface Pixel {
  /**
   * Calculate matrix * [r,g,b]
   *
   * @param matrix the matrix to multiply
   * @return the transformed pixel
   * @throws IllegalArgumentException when the given matrix is not legal
   */
  public Pixel linearTransformation(float[][] matrix) throws IllegalArgumentException;

  /**
   * Calculate matrix + [r,g,b]
   *
   * @param matrix the matrix to add
   * @return the transformed pixel
   * @throws IllegalArgumentException when the given matrix is not legal
   */
  public Pixel addition(float[] matrix) throws IllegalArgumentException;
}
