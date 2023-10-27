package concreteoperation;

import abstractoperation.ComposeOperation;
import model.Image;

/**
 * A class that represents brightening or darkening operations on images.
 */
public class BrightenDarkenOperation extends ComposeOperation {
  private Image image;
  private int amount;

  /**
   * Construct a brightening operation on a given image.
   *
   * @param image  the image to operate
   * @param amount the amount to brighten (positive) or darken (negative)
   */
  //todo:should we allow float input or int for the amount?
  public BrightenDarkenOperation(Image image, int amount) {
    this.image = image;
    this.amount = amount;
  }

  /**
   * Perform the operation.
   */

  @Override
  public void perform() {
    // This operation is implemented by simply adding a positive constant to the red,
    // green and blue components of all pixels and clamping to the maximum possible value
    // (i.e 255 for an 8-bit representation) if needed. Similarly darkening corresponds to
    // subtracting a positive constant from the values of all pixels and clamping to
    // the minimum value (e.g. 0) if needed.
    float[] matrix=new float[]{amount,amount,amount};
    imgArrayAddition(matrix,image);
  }
}
