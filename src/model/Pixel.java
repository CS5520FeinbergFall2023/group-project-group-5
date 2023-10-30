package model;

import java.util.Map;
import java.util.Objects;

/**
 * This interface represents a pixel.
 */
public abstract class Pixel {
  Map<Channel, Integer> channels;

  public Pixel() {
  }

  protected Pixel(Map<Channel, Integer> channels) {
    this.channels = channels;
  }

  /**
   * Checks if the pixel has the given channel.
   *
   * @param channel the channel to check.
   * @return if the pixel has the given channel
   */
  public boolean containsChannel(Channel channel) {
    return channels.containsKey(channel);
  }

  /**
   * Calculate matrix * [r,g,b,otherChanel...]
   *
   * @param matrix the matrix to multiply
   * @return the result pixel
   * @throws IllegalArgumentException when the given matrix is not legal
   */
  public abstract Pixel linearTransformation(float[][] matrix) throws IllegalArgumentException;

  /**
   * Calculate matrix + [r,g,b,otherChanel...]
   *
   * @param matrix the matrix to add
   * @return the result pixel
   * @throws IllegalArgumentException when the given matrix is not legal
   */
  public abstract Pixel addition(float[] matrix) throws IllegalArgumentException;

  /**
   * Calculate [r,g,b] + [r',g',b']
   *
   * @param pixel the pixel to add
   * @return the result pixel
   * @throws IllegalArgumentException when the given pixel is not legal
   */
  public abstract Pixel addition(Pixel pixel) throws IllegalArgumentException;

  /**
   * Multiply the pixel with a number.
   *
   * @param number the number to multiply
   * @return the new pixel after the operation
   * @throws IllegalArgumentException when the given number is not legal
   */
  public abstract Pixel multiplyNumber(float number) throws IllegalArgumentException;

  /**
   * Get certain channel component of the pixel.
   *
   * @param channel the channel to split
   * @return the component pixel
   */
  public abstract Pixel getChannelComponent(Channel channel);

  /**
   * Calculate the max value among all channels of the pixel and get a pixel with all channels this
   * value.
   *
   * @return a pixel with all channels the max value among all channels
   */
  public abstract Pixel max();

  /**
   * Calculate the average value among all channels of the pixel and get a pixel with all channels
   * this value.
   *
   * @return a pixel with all channels the average value among all channels
   */
  public abstract Pixel avg();

  /**
   * Check if the pixel is monochrome of the given channel.
   *
   * @param channel the channel to check
   * @return if the pixel is monochrome of the given channel
   */
  public boolean isMonochromeOfChannel(Channel channel)
  {
    if (containsChannel(channel)) {
      for (Channel c : channels.keySet()) {
        if (c == channel) {
          continue;
        }
        if (channels.get(c) != 0) {
          return false;
        }
      }
      return true;
    }
    return false;
  }

  /** Get the hashcode of the object.
   * @return the hashcode of the object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(channels);
  }

  // Used for debugging, can remove or keep
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Channel channel : channels.keySet()) {
      sb.append(channel);
      sb.append(":");
      sb.append(channels.get(channel));
      sb.append(" ");
    }
    return sb.toString();
  }

}
