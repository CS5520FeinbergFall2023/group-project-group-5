package operation;

import operable.FilterOperable;

/**
 * A class that represents blurring operations on images.
 */
public class BlurOperation implements Operation {
  private FilterOperable filterOperable;
  private static final float[][] blurFilter=new float[][]{
      {0.0625f,0.125f,0.0625f},
      {0.125f,0.25f,0.125f},
      {0.0625f,0.125f,0.0625f}
  };

  /** Construct a blur operation on a given FilterOperable.
   *
   * @param filterOperable the image to FilterOperable
   */
  public BlurOperation(FilterOperable filterOperable) {
    this.filterOperable = filterOperable;
  }

  /**
   * Perform the operation.
   */
  @Override
  public FilterOperable perform() {
    return filterOperable.imgFiltering(blurFilter);
  }
}
