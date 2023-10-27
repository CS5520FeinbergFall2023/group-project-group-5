package concreteoperation;

import abstractoperation.LinearTransformOperation;
import model.Image;

/**
 * A class that represents getting luma on images.
 */
public class LumaOperation extends LinearTransformOperation {
  private Image image;

  /** Construct a getting luma operation on a given image.
   *
   * @param image the image to operate
   */
  public LumaOperation(Image image) {
    this.image = image;
  }

  /**
   * Perform the operation.
   */
  @Override
  public void perform() {

  }
}
