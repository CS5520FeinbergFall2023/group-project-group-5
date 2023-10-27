package model;

public class RGBPixel implements Pixel {
  private float red;
  private float green;
  private float blue;

  //channel num

  /** Construct an RGB pixel. Every value of the channel is in the range of [0,1]
   *
   * @param red the value of the red channel
   * @param green the value of the green channel
   * @param blue the value of the blue channel
   */
  public RGBPixel(float red, float green, float blue) {
    if (red < 0 || green < 0 || blue < 0 || red > 1 || green > 1 || blue > 1) {
      throw new IllegalArgumentException("Invalid color range");
    }
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  /**
   * Calculate matrix * [r,g,b]
   *
   * @param matrix the matrix to multiply
   * @return the transformed pixel
   * @throws IllegalArgumentException when the given matrix is not legal
   */
  @Override
  public RGBPixel linearTransformation(float[][] matrix) {
    if (matrix.length == 3 && matrix[0].length == 3 && matrix[1].length == 3
        && matrix[2].length == 3) {
      return new RGBPixel(red * matrix[0][0] + green * matrix[0][1] + blue * matrix[0][2],
          (red * matrix[1][0] + green * matrix[1][1] + blue * matrix[1][2]),
          (red * matrix[2][0] + green * matrix[2][1] + blue * matrix[2][2]));
    } else {
      throw new IllegalArgumentException("The multiplying array should be 3*3 in size.");
    }
  }

  /**
   * Calculate matrix + [r,g,b]
   *
   * @param matrix the matrix to add
   * @return the transformed pixel
   * @throws IllegalArgumentException when the given matrix is not legal
   */
  @Override
  public Pixel addition(float[] matrix) {
    if (matrix.length == 3 ) {
      return new RGBPixel(red+matrix[0],green+matrix[1],blue+matrix[2]);
    } else {
      throw new IllegalArgumentException("The multiplying array should be 3*3 in size.");
    }
  }
}
