package model;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class RGBPixel implements Pixel {
//  private int red;
//  private int green;
//  private int blue;

  private Map<Channel,Integer> channels;

  private final int bitDepth=8;

  /**
   * Construct an RGB pixel. Every value of the channel is in the range of [0,1]
   *
   * @param red   the value of the red channel
   * @param green the value of the green channel
   * @param blue  the value of the blue channel
   */
  public RGBPixel(int red, int green, int blue) {
    //todo:or throw exception?
    //0-255
    red= Math.max(red, 0);
    green= Math.max(green, 0);
    blue= Math.max(blue, 0);
    red=Math.min(red,2<<bitDepth-1);
    green=Math.min(green,2<<bitDepth-1);
    blue=Math.min(blue,2<<bitDepth-1);
    channels=new HashMap<>();
    channels.put(Channel.RED,red);
    channels.put(Channel.GREEN,green);
    channels.put(Channel.BLUE,blue);
  }

  /**
   * Calculate matrix * [r,g,b]
   *
   * @param matrix the matrix to multiply
   * @throws IllegalArgumentException when the given matrix is not legal
   */
  @Override
  public RGBPixel linearTransformation(float[][] matrix) {
    if (matrix.length == 3 && matrix[0].length == 3 && matrix[1].length == 3
        && matrix[2].length == 3) {
//      red=red * matrix[0][0] + green * matrix[0][1] + blue * matrix[0][2];
//      green=red * matrix[1][0] + green * matrix[1][1] + blue * matrix[1][2];
//      blue=red * matrix[2][0] + green * matrix[2][1] + blue * matrix[2][2];
      int red=channels.get(Channel.RED);
      int green=channels.get(Channel.GREEN);
      int blue=channels.get(Channel.BLUE);
      return new RGBPixel(
          Math.round(red * matrix[0][0] + green * matrix[0][1] + blue * matrix[0][2]),
          Math.round(red * matrix[1][0] + green * matrix[1][1] + blue * matrix[1][2]),
          Math.round(red * matrix[2][0] + green * matrix[2][1] + blue * matrix[2][2]));
    } else {
      throw new IllegalArgumentException("The multiplying array should be 3*3 in size.");
    }
  }

  /**
   * Calculate matrix + [r,g,b]
   *
   * @param matrix the matrix to add
   * @throws IllegalArgumentException when the given matrix is not legal
   */
  @Override
  public RGBPixel addition(float[] matrix) {
    if (matrix.length == 3) {
//      red+=matrix[0];
//      green+=matrix[1];
//      blue+=matrix[2];
      int red=channels.get(Channel.RED);
      int green=channels.get(Channel.GREEN);
      int blue=channels.get(Channel.BLUE);
      return new RGBPixel(Math.round(red + matrix[0]), Math.round(green + matrix[1]),
          Math.round(blue + matrix[2]));
    } else {
      throw new IllegalArgumentException("The multiplying array should be 3*3 in size.");
    }
  }

  /**
   * Calculate [r,g,b] + [r',g',b']
   *
   * @param pixel the pixel to add
   * @throws IllegalArgumentException when the given pixel is not legal
   */
  @Override
  public RGBPixel addition(Pixel pixel) throws IllegalArgumentException {
    if (!(pixel instanceof RGBPixel)) {
      throw new IllegalArgumentException("Addition between pixels require them to be of same type");
    }
    int red=channels.get(Channel.RED);
    int green=channels.get(Channel.GREEN);
    int blue=channels.get(Channel.BLUE);
    int thatRed=((RGBPixel) pixel).channels.get(Channel.RED);
    int thatGreen=((RGBPixel) pixel).channels.get(Channel.GREEN);
    int thatBlue=((RGBPixel) pixel).channels.get(Channel.BLUE);
    return new RGBPixel(red + thatRed, green + thatGreen,
        blue+thatBlue);
  }

  /**
   * Multiply the pixel with a number.
   *
   * @param number the number to multiply
   * @return the new pixel after the operation
   * @throws IllegalArgumentException when the given number is not legal
   */
  @Override
  public RGBPixel multiplyNumber(float number) throws IllegalArgumentException {
    if (number < 0) {
      throw new IllegalArgumentException("Can't multiply negative number with a pixel.");
    }
    int red=channels.get(Channel.RED);
    int green=channels.get(Channel.GREEN);
    int blue=channels.get(Channel.BLUE);
    return new RGBPixel(Math.round((red * number > 1) ? 1 : red * number),
        Math.round((green * number > 1) ? 1 : green * number),
        Math.round((blue * number > 1) ? 1 : blue * number));
  }
}
