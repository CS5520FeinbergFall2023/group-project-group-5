package model;

import java.io.IOException;
import java.util.function.Function;

import operable.AffineTransformOperable;
import operable.ChannelSplitOperable;
import operable.ComposeOperable;
import operable.FilterOperable;
import operable.LinearTransformOperable;
import operable.MapElementOperable;
import operation.Operation;

public interface Image<T> extends AffineTransformOperable, ComposeOperable, FilterOperable,
    LinearTransformOperable<T>, ChannelSplitOperable<T>,MapElementOperable<T> {

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
