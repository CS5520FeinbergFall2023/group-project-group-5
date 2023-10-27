package concreteoperation;

import abstractoperation.LinearTransformOperation;
import model.Image;

/**
 * A class that represents getting value on images.
 * Value: the maximum value of the three components for each pixel.
 */
public class ValueOperation extends LinearTransformOperation {
  private Image image;

  /** Construct a value getting operation on a given image.
   *
   * @param image the image to operate
   */
  public ValueOperation(Image image) {
    this.image = image;
  }

  /**
   * Perform the operation.
   */
  @Override
  public void perform() {

  }
}
