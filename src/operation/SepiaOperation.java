package operation;

import model.Image;
import operable.LinearTransformOperable;
import operable.Operable;

/**
 * A class that represents sepia operations on images.
 */
public class SepiaOperation implements Operation {
  private LinearTransformOperable linearTransformOperable;

  /** Construct a sepia operation on a given LinearTransformOperable.
   *
   * @param linearTransformOperable the LinearTransformOperable to operate
   */
  public SepiaOperation(LinearTransformOperable linearTransformOperable) {
    this.linearTransformOperable = linearTransformOperable;
  }

  /**
   * Perform the operation.
   */
  @Override
  public Operable perform() {
    float[][] matrix=new float[][]
        {
            {0.393f,0.769f,0.189f},
            {0.349f,0.686f,0.168f},
            {0.272f,0.534f,0.131f}
        };
    return linearTransformOperable.arrayMultiplication(matrix);
  }
}
