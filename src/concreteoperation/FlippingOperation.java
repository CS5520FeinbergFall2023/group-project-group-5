package concreteoperation;

import abstractoperation.AffineTransformOperation;
import model.Image;

/**
 * A class that represents flipping operations on images.
 */
public class FlippingOperation extends AffineTransformOperation {
  private Image image;

  /** Construct a flipping operation on a given image.
   *
   * @param image the image to operate
   */
  public FlippingOperation(Image image) {
    this.image = image;
  }

  /**
   * Perform the operation.
   */
  @Override
  public void perform() {

  }
}
