package operation;

import model.Axis;
import operable.AffineTransformOperable;

/**
 * A class that represents 2d flipping operations.
 */
public class FlippingOperation implements Operation {
  private AffineTransformOperable affineTransformOperable;
  private Axis axis;
  private int width;
  private int height;

  /** Construct a flipping operation on a given AffineTransformOperable.
   *
   * @param affineTransformOperable the AffineTransformOperable to operate
   * @param axis the axis to flip, it can be Axis.X or Axis.Y
   * @param height the height of the original AffineTransformOperable
   * @param width the width of the original AffineTransformOperable
   */
  public FlippingOperation(AffineTransformOperable affineTransformOperable, Axis axis,
                           int height,int width) {
    this.affineTransformOperable = affineTransformOperable;
    this.axis=axis;
    this.height=height;
    this.width=width;
  }

  /**
   * Perform the operation.
   */
  @Override
  public AffineTransformOperable perform() {
    int[][] matrix;
    if(axis==Axis.X) {
      matrix = new int[][]{
          {-1,0,width-1},
          {0,1,0}
      };
    }
    else{
      matrix = new int[][]{
          {1,0,0},
          {0,-1,height-1}
      };
    }
    return affineTransformOperable.projectCoordinate(matrix);
  }
}
