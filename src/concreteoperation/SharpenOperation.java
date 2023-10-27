package concreteoperation;

import abstractoperation.FilterOperation;
import model.Image;

/**
 * A class that represents sharpening operations on images.
 */
public class SharpenOperation extends FilterOperation {
  private Image image;

  /** Construct a sharpening operation on a given image.
   *
   * @param image the image to operate
   */
  public SharpenOperation(Image image) {
    this.image = image;
  }

  /**
   * Perform the operation.
   */
  @Override
  public void perform() {

  }
}
