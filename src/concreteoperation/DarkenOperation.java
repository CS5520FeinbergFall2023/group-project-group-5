package concreteoperation;

import abstractoperation.ComposeOperation;
import model.Image;

/**
 * A class that represents darkening operations on images.
 */
public class DarkenOperation extends ComposeOperation {
  private Image image;

  /** Construct a darkening operation on a given image.
   *
   * @param image the image to operate
   */
  public DarkenOperation(Image image) {
    this.image = image;
  }

  /**
   * Perform the operation.
   */
  @Override
  public void perform() {

  }
}
