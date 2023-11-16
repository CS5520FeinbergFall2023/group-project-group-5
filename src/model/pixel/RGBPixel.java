package model.pixel;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

import model.Channel;

/**
 * This class represents 8 bit depth RGB pixel. Every pixel has a map representing the value of the
 * pixel in each channel (red, green, blue).
 */
public class RGBPixel implements Pixel {
  public static final int BITDEPTH = 8;
  private final Map<Channel, Integer> channelsMap;

  /**
   * Construct an RGB pixel. Every value of the channel is in the range of [0,1]. If the input is
   * larger than 2^-1, the corresponding channel will be automatically set to 2^-1;
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
    red = Math.min(red, (1 << BITDEPTH ) - 1);
    green = Math.min(green, (1 << BITDEPTH ) - 1);
    blue = Math.min(blue, (1 << BITDEPTH ) - 1);
    channelsMap = new EnumMap<>(Channel.class);
    channelsMap.put(Channel.RED, red);
    channelsMap.put(Channel.GREEN, green);
    channelsMap.put(Channel.BLUE, blue);
  }

  private RGBPixel(Map<Channel, Integer> channelsMap) {
    this.channelsMap = channelsMap;
  }

  /**
   * Checks if the pixel has the given channel.
   *
   * @param channel the channel to check.
   * @return if the pixel has the given channel
   */
  public boolean containsChannel(Channel channel) {
    return channelsMap.containsKey(channel);
  }

  /**
   * Check if this pixel is greyscale.
   *
   * @return if this pixel is greyscale
   */
  @Override
  public boolean isGreyscale() {
    return (getRed() == getBlue() && getRed() == getGreen());
  }

  /**
   * Get certain channel component of the pixel.
   *
   * @param channel the channel to split
   * @return the component pixel
   * @throws IllegalArgumentException when the given channel is not in the pixel
   */
  @Override
  public Pixel getChannelComponent(Channel channel) throws IllegalArgumentException {
    if (!containsChannel(channel)) {
      throw new IllegalArgumentException("The pixel does not contain the channel");
    }
    Map<Channel, Integer> channels = new EnumMap<>(Channel.class);
    for (Channel key : this.channelsMap.keySet()) {
      if (key == channel) {
        channels.put(channel, this.channelsMap.get(channel));
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
      int red = channelsMap.get(Channel.RED);
      int green = channelsMap.get(Channel.GREEN);
      int blue = channelsMap.get(Channel.BLUE);
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
  public RGBPixel addition(float[] matrix) throws IllegalArgumentException {
    if (matrix.length == 3) {
      int red = channelsMap.get(Channel.RED);
      int green = channelsMap.get(Channel.GREEN);
      int blue = channelsMap.get(Channel.BLUE);
      return new RGBPixel(Math.round(red + matrix[0]), Math.round(green + matrix[1]),
          Math.round(blue + matrix[2]));
    } else {
      throw new IllegalArgumentException("The multiplying array should be 3*3 in size.");
    }
  }

  /**
   * Calculate pixel[r,g,b] + pixel[r',g',b'].
   *
   * @param pixel the pixel to add
   * @throws IllegalArgumentException when the given pixel is not legal
   */
  @Override
  public RGBPixel addition(Pixel pixel) throws IllegalArgumentException {
    if (!(pixel instanceof RGBPixel)) {
      throw new IllegalArgumentException("Addition between pixels require them to be of same type");
    }
    int red = channelsMap.get(Channel.RED);
    int green = channelsMap.get(Channel.GREEN);
    int blue = channelsMap.get(Channel.BLUE);
    int thatRed = ((RGBPixel) pixel).getRed();
    int thatGreen = ((RGBPixel) pixel).getGreen();
    int thatBlue = ((RGBPixel) pixel).getBlue();
    return new RGBPixel(red + thatRed, green + thatGreen,
        blue + thatBlue);
  }

  /**
   * Calculate the max value among all channelsMap of the pixel and get a pixel with all channelsMap
   * this value.
   *
   * @return a pixel with all channelsMap the max value among all channelsMap
   */
  @Override
  public RGBPixel max() {
    int max = 0;
    for (Channel channel : channelsMap.keySet()) {
      int tmp = this.channelsMap.getOrDefault(channel, 0);
      if (tmp > max) {
        max = tmp;
      }
    }
    return new RGBPixel(max, max, max);
  }

  /**
   * Calculate the average value among all channelsMap of the pixel and get a pixel with all
   * channelsMap this value.
   *
   * @return a pixel with all channelsMap the average value among all channelsMap
   */
  @Override
  public RGBPixel avg() {
    int sum = 0;
    for (Channel channel : channelsMap.keySet()) {
      int tmp = this.channelsMap.getOrDefault(channel, 0);
      sum += tmp;
    }
    return new RGBPixel(Math.round(sum / 3f), Math.round(sum / 3f), Math.round(sum / 3f));
  }

  /**
   * Get the value of the red channel of the image.
   *
   * @return the value of the red channel of the image
   */
  public int getRed() {
    return channelsMap.get(Channel.RED);
  }

  /**
   * Get the value of the blue channel of the image.
   *
   * @return the value of the blue channel of the image
   */
  public int getBlue() {
    return channelsMap.get(Channel.BLUE);
  }

  /**
   * Get the value of the green channel of the image.
   *
   * @return the value of the green channel of the image
   */
  public int getGreen() {
    return channelsMap.get(Channel.GREEN);
  }


  /**
   * Compare if two objects are equal.
   *
   * @param o the other object to compare to
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
    return Objects.equals(channelsMap, rgbPixel.channelsMap);
  }


  /**
   * Get the hashcode of the object.
   *
   * @return the hashcode of the object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(channelsMap);
  }

  /**
   * Returns a string representation of the object.
   *
   * @return a string representation of the object.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Channel channel : channelsMap.keySet()) {
      sb.append(channel);
      sb.append(":");
      sb.append(channelsMap.get(channel));
      sb.append(" ");
    }
    return sb.toString();
  }

}
