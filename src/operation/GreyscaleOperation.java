package operation;

import operable.LinearTransformOperable;
import operable.Operable;

public class GreyscaleOperation implements Operation {
  private LinearTransformOperable linearTransformOperable;

  public GreyscaleOperation(LinearTransformOperable linearTransformOperable) {
    this.linearTransformOperable = linearTransformOperable;
  }

  /**
   * Perform the operation.
   */
  @Override
  public Operable perform() {
    float[][] matrix=new float[][]
        {
            {0.2126f,0.7152f,0.0722f},
            {0.2126f,0.7152f,0.0722f},
            {0.2126f,0.7152f,0.0722f}
        };
    return linearTransformOperable.arrayMultiplication(matrix);
  }
}
