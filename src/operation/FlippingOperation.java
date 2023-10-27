package operation;

import abstractoperation.AffineTransformOperable;

/**
 * A class that represents flipping operations on images.
 */
public class FlippingOperation implements Operation {
  private AffineTransformOperable affineTransformOperable;

  /** Construct a flipping operation on a given AffineTransformOperable.
   *
   * @param affineTransformOperable the AffineTransformOperable to operate
   */
  public FlippingOperation(AffineTransformOperable affineTransformOperable) {
    this.affineTransformOperable = affineTransformOperable;
  }

  /**
   * Perform the operation.
   */
  @Override
  public void perform() {

  }
}
