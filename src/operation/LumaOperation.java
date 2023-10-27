package operation;

import model.Image;

/**
 * A class that represents getting luma on images.
 */
public class LumaOperation implements Operation {
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
