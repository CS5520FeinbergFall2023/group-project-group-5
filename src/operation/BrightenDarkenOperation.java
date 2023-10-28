package operation;

import operable.ComposeOperable;

/**
 * A class that represents brightening or darkening operations on images.
 */
public class BrightenDarkenOperation implements Operation {
  private ComposeOperable composeOperable;
  private int amount;

  /**
   * Construct a brightening operation on a given ComposeOperable.
   *
   * @param composeOperable  the ComposeOperable to operate
   * @param amount the amount to brighten (positive) or darken (negative)
   */
  //todo:should we allow float input or int for the amount?
  public BrightenDarkenOperation(ComposeOperable composeOperable, int amount) {
    this.composeOperable = composeOperable;
    this.amount = amount;
  }

  /**
   * Perform the operation.
   */

  @Override
  public ComposeOperable perform() {
    // This operation is implemented by simply adding a positive constant to the red,
    // green and blue components of all pixels and clamping to the maximum possible value
    // (i.e 255 for an 8-bit representation) if needed. Similarly darkening corresponds to
    // subtracting a positive constant from the values of all pixels and clamping to
    // the minimum value (e.g. 0) if needed.
    float[] matrix=new float[]{amount,amount,amount};
    return composeOperable.imgArrayAddition(matrix);
  }
}
