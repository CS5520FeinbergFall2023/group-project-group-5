package image;

import java.io.IOException;

public interface Image {
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
   * @return
   */
  String getName();

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
