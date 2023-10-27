package operation;

import model.Image;

/**
 * A class that represents getting intensity on images.
 */
public class IntensityOperation implements Operation {
  private Image image;

  /** Construct a getting intensity operation on a given image.
   *
   * @param image the image to operate
   */
  public IntensityOperation(Image image) {
    this.image = image;
  }

  /**
   * Perform the operation.
   */
  @Override
  public void perform() {

  }
}
