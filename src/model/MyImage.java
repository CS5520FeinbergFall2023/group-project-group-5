package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import abstractoperation.Operation;

public class MyImage implements Image {
  private List<Operation> operationList;
  private Pixel[][] pixels;
  private int height;
  private int width;
  private String name;

  public MyImage(int height, int width, String name) {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Invalid width and height.");
    }
    if (name == null) {
      throw new IllegalArgumentException("The name cannot be null");
    }
    this.height = height;
    this.width = width;
    this.name = name;
    operationList = new ArrayList<>();
    pixels = new RGBPixel[height][width];
    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        pixels[row][col] = new RGBPixel(0, 0, 0);
      }
    }
  }

  public MyImage(int height, int width, String name, Pixel[][] pixels) {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Invalid width and height.");
    }
    if (name == null) {
      throw new IllegalArgumentException("The name cannot be null");
    }
    if (pixels == null) {
      throw new IllegalArgumentException("Pixels cannot be null");
    }
    this.height = height;
    this.width = width;
    this.name = name;
    this.pixels = pixels;
    operationList = new ArrayList<>();
  }

  @Override
  // 实现加载图像的代码
  public void load(String fileName) throws IOException {

  }

  @Override
  // 实现保存图像的代码
  public void save(String fileName) throws IOException {

  }

  /**
   * @return
   */
  @Override
  public int getHeight() {
    return this.height;
  }

  /**
   * @return
   */
  @Override
  public int getWidth() {
    return this.width;
  }

  public void takeOperation(Operation operation) {
    operationList.add(operation);
  }

  public void performOperations() {
    for (Operation operation : operationList) {
      operation.perform();
    }
    operationList.clear();
  }

  /**
   * Get the image's name.
   *
   * @return the image's name.
   */
  @Override
  public String getName() {
    return this.name;
  }

  /**
   * Set the image's name.
   */
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public Pixel getPixel(int x, int y) {
    if (x < 0 || x > this.width || y < 0 || y > this.height) {
      throw new IllegalArgumentException("The x or y is out of bound.");
    }
    return pixels[x][y];

  }

  @Override
  public void setPixel(int x, int y, Pixel pixel) {
    if (pixel == null) {
      throw new IllegalArgumentException("The pixel cannot be null.");
    }
    if (x < 0 || x > this.width || y < 0 || y > this.height) {
      throw new IllegalArgumentException("The x or y is out of bound.");
    }
    pixels[x][y] = pixel;
  }




}
