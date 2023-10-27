package concreteoperation;

import abstractoperation.LinearTransformOperation;
import model.Image;

/**
 * A class that represents sepia operations on images.
 */
public class SepiaOperation extends LinearTransformOperation {
  private Image image;

  /** Construct a sepia operation on a given image.
   *
   * @param image the image to operate
   */
  public SepiaOperation(Image image) {
    this.image = image;
  }

  /**
   * Perform the operation.
   */
  @Override
  public void perform() {

  }
}
