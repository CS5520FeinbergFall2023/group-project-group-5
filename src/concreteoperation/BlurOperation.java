package concreteoperation;

import abstractoperation.FilterOperation;
import model.Image;

/**
 * A class that represents blurring operations on images.
 */
public class BlurOperation extends FilterOperation {
  private Image image;
  private static final float[][] blurFilter=new float[][]{
      {0.0625f,0.125f,0.0625f},
      {0.125f,0.25f,0.125f},
      {0.0625f,0.125f,0.0625f}
  };

  /** Construct a blur operation on a given image.
   *
   * @param image the image to operate
   */
  public BlurOperation(Image image) {
    this.image = image;
  }

  /**
   * Perform the operation.
   */
  @Override
  public void perform() {
    imgFiltering(blurFilter,image);
  }
}
