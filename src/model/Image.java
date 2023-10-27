package model;

import java.io.IOException;

import operable.AffineTransformOperable;
import operable.ChannelSplitOperable;
import operable.ComposeOperable;
import operable.FilterOperable;
import operable.LinearTransformOperable;
import operation.Operation;

public interface Image extends AffineTransformOperable, ComposeOperable, FilterOperable,
    LinearTransformOperable, ChannelSplitOperable {
  /**
   *
   * @param operation
   */
  void takeOperation(Operation operation);

  /**
   *
   */
  void performOperations();

  /**
   * Get the image's name.
   * @return the name of the image
   */
  String getName();

  /**
   * Set the image's name.
   */
  void setName(String name);

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

}
