package operation;

import java.util.function.Function;

import model.Image;
import operable.LinearTransformOperable;
import operable.Operable;

/**
 * A class that represents getting intensity on images.
 */
public class IntensityOperation<T> implements Operation {
  private LinearTransformOperable<T> linearTransformOperable;
  private Function<T,T> maxFunc;

  /** Construct a getting intensity operation on a given LinearTransformOperable.
   *
   * @param linearTransformOperable the LinearTransformOperable to operate
   */
  public IntensityOperation(LinearTransformOperable<T> linearTransformOperable,
                            Function<T,T> maxFunc) {
    this.linearTransformOperable = linearTransformOperable;
    this.maxFunc=maxFunc;
  }

  /**
   * Perform the operation.
   */
  @Override
  public Operable perform() {
    LinearTransformOperable<T> max=
        (LinearTransformOperable<T>) linearTransformOperable.mapElement(maxFunc);
    return max.arrayMultiplication(new float[][]{
        {0.2126f,0.7152f,0.722f},
        {0.2126f,0.7152f,0.722f},
        {0.2126f,0.7152f,0.722f}
    });
  }
}
