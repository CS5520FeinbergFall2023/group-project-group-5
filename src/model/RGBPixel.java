package model;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/**
 * This class represents 8 bit depth RGB pixel.
 */
public class RGBPixel extends Pixel {
  private final int bitDepth = 8;

  /**
   * Construct an RGB pixel. Every value of the channel is in the range of [0,1]. If the input is
   * larger than 2^bitDepth-1, the corresponding channel will be automatically set to 2^bitDepth-1;
   * if the input is  smaller than 0, the corresponding channel will be automatically set to 0
   *
   * @param red   the value of the red channel
   * @param green the value of the green channel
   * @param blue  the value of the blue channel
   */
  public RGBPixel(int red, int green, int blue) {
    //0-255
    super();
    red = Math.max(red, 0);
    green = Math.max(green, 0);
    blue = Math.max(blue, 0);
    red = Math.min(red, (1 << bitDepth) - 1);
    green = Math.min(green, (1 << bitDepth) - 1);
    blue = Math.min(blue, (1 << bitDepth) - 1);
    channels = new EnumMap<>(Channel.class);
    channels.put(Channel.RED, red);
    channels.put(Channel.GREEN, green);
    channels.put(Channel.BLUE, blue);
  }

  private RGBPixel(Map<Channel, Integer> channels) {
    super(channels);
  }

  /**
   * Get certain channel component of the pixel.
   *
   * @param channel the channel to split
   * @return the component pixel
   * @throws IllegalArgumentException when the given channel is not in the pixel
   */
  @Override
  Pixel getChannelComponent(Channel channel) throws IllegalArgumentException {
    if (!containsChannel(channel)) {
      throw new IllegalArgumentException("The pixel does not contain the channel");
    }

    Map<Channel, Integer> channels = new EnumMap<>(Channel.class);
    for (Channel key : this.channels.keySet()) {
      if (key == channel) {
        channels.put(channel, this.channels.get(channel));
      } else {
        channels.put(key, 0);
      }
    }
    return new RGBPixel(channels);
  }

  /**
   * Calculate 3x3 matrix * [r,g,b].
   *
   * @param matrix the matrix to multiply
   * @throws IllegalArgumentException when the given matrix is not legal
   */
  @Override
  public RGBPixel linearTransformation(float[][] matrix) throws IllegalArgumentException {
    if (matrix.length == 3 && matrix[0].length == 3 && matrix[1].length == 3
        && matrix[2].length == 3) {
      int red = channels.get(Channel.RED);
      int green = channels.get(Channel.GREEN);
      int blue = channels.get(Channel.BLUE);
      return new RGBPixel(
          Math.round(red * matrix[0][0] + green * matrix[0][1] + blue * matrix[0][2]),
          Math.round(red * matrix[1][0] + green * matrix[1][1] + blue * matrix[1][2]),
          Math.round(red * matrix[2][0] + green * matrix[2][1] + blue * matrix[2][2]));
    } else {
      throw new IllegalArgumentException("The multiplying array should be 3*3 in size.");
    }
  }

  /**
   * Calculate pixel[r',g',b'] = matrix[x,y,z] + pixel[r,g,b].
   *
   * @param matrix the matrix to add
   * @return the result pixel
   * @throws IllegalArgumentException when the given matrix is not legal
   */
  @Override
  public RGBPixel addition(float[] matrix) {
    if (matrix.length == 3) {
      int red = channels.get(Channel.RED);
      int green = channels.get(Channel.GREEN);
      int blue = channels.get(Channel.BLUE);
      return new RGBPixel(Math.round(red + matrix[0]), Math.round(green + matrix[1]),
          Math.round(blue + matrix[2]));
    } else {
      throw new IllegalArgumentException("The multiplying array should be 3*3 in size.");
    }
  }

  /**
   * Calculate pixel[r,g,b] + pixel[r',g',b']
   *
   * @param pixel the pixel to add
   * @throws IllegalArgumentException when the given pixel is not legal
   */
  @Override
  public RGBPixel addition(Pixel pixel) throws IllegalArgumentException {
    if (!(pixel instanceof RGBPixel)) {
      throw new IllegalArgumentException("Addition between pixels require them to be of same type");
    }
    int red = channels.get(Channel.RED);
    int green = channels.get(Channel.GREEN);
    int blue = channels.get(Channel.BLUE);
    int thatRed = pixel.channels.get(Channel.RED);
    int thatGreen = pixel.channels.get(Channel.GREEN);
    int thatBlue = pixel.channels.get(Channel.BLUE);
    return new RGBPixel(red + thatRed, green + thatGreen,
        blue + thatBlue);
  }

  /**
   * Calculate the max value among all channels of the pixel and get a pixel with all channels this
   * value.
   *
   * @return a pixel with all channels the max value among all channels
   */
  public RGBPixel max() {
    int max = 0;
    for (Channel channel : channels.keySet()) {
      int tmp = this.channels.getOrDefault(channel, 0);
      if (tmp > max) {
        max = tmp;
      }
    }
    return new RGBPixel(max, max, max);
  }

  /**
   * Calculate the average value among all channels of the pixel and get a pixel with all channels
   * this value.
   *
   * @return a pixel with all channels the average value among all channels
   */
  public RGBPixel avg() {
    int sum = 0;
    for (Channel channel : channels.keySet()) {
      int tmp = this.channels.getOrDefault(channel, 0);
      sum += tmp;
    }
    return new RGBPixel(Math.round(sum / 3f), Math.round(sum / 3f), Math.round(sum / 3f));
  }

  /**
   * Get the value of the red channel of the image.
   *
   * @return the value of the red channel of the image
   */
  int getRed() {
    return channels.get(Channel.RED);
  }

  /**
   * Get the value of the blue channel of the image.
   *
   * @return the value of the blue channel of the image
   */
  int getBlue() {
    return channels.get(Channel.BLUE);
  }

  /**
   * Get the value of the green channel of the image.
   *
   * @return the value of the green channel of the image
   */
  int getGreen() {
    return channels.get(Channel.GREEN);
  }

  /**
   * Compare if two objects are equal.
   *
   * @param o the othe object to compare to
   * @return if the objects are equal
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RGBPixel rgbPixel = (RGBPixel) o;
    return Objects.equals(channels, rgbPixel.channels);
  }

}
