package concreteoperation;

import abstractoperation.ComposeOperation;
import model.Image;

/**
 * A class that represents brightening operations on images.
 */
public class BrightenOperation extends ComposeOperation {
  private Image image;

  /** Construct a brightening operation on a given image.
   *
   * @param image the image to operate
   */
  public BrightenOperation(Image image) {
    this.image = image;
  }

  /**
   * Perform the operation.
   */


  @Override
  public void perform() {

  }
}
