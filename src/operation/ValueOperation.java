package operation;

import java.util.function.Function;

import model.Image;
import operable.LinearTransformOperable;
import operable.Operable;

/**
 * A class that represents getting value on images.
 * Value: the maximum value of the three components for each pixel.
 */
public class ValueOperation<T> implements Operation {
  private LinearTransformOperable<T> linearTransformOperable;
  private Function<T,T> avgFunc;

  /** Construct a getting value operation on a given LinearTransformOperable.
   *
   * @param linearTransformOperable the LinearTransformOperable to operate
   */
  public ValueOperation(LinearTransformOperable<T> linearTransformOperable,
                            Function<T,T> avgFunc) {
    this.linearTransformOperable = linearTransformOperable;
    this.avgFunc=avgFunc;
  }

  /**
   * Perform the operation.
   */
  @Override
  public Operable perform() {
    LinearTransformOperable<T> value=
        (LinearTransformOperable<T>) linearTransformOperable.mapElement(avgFunc);
    //todo:take this array out as public static final
    return value.arrayMultiplication(new float[][]{
        {0.2126f,0.7152f,0.722f},
        {0.2126f,0.7152f,0.722f},
        {0.2126f,0.7152f,0.722f}
    });
  }
}
