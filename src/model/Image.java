package model;

import java.io.IOException;
import java.util.function.Function;

public interface Image {
  /**
   *
   * @param fileName
   * @throws IOException
   */
  void load(String fileName) throws IOException;

  /**
   *
   * @param fileName
   * @throws IOException
   */
  void save(String fileName) throws IOException;

  /**
   *
   * @return
   */
  int getHeight();

  /**
   *
   * @return
   */
  int getWidth();

  /**
   *
   * @param x
   * @param y
   * @return
   */
  Pixel getPixel(int x, int y);

  /**
   *
   * @param x
   * @param y
   * @param pixel
   */
  void setPixel(int x, int y, Pixel pixel);

  Image arrayMultiplication(float[][] matrix);

  Image channelSplit(Channel channel) throws IllegalArgumentException;
  Image filtering(float[][] kernel);
  Image mapElement(Function<Pixel,Pixel> function);
  Image projectCoordinate(int[][] projectMatrix);
  Image addition(Image that);
  Image imgArrayAddition(float[] matrix);

  /** Get channels of pixels in the image.
   * @return channels of pixels in the image
   */
  Channel[] getChannels();
  boolean isMonochromeOfChannel(Channel channel);
}
