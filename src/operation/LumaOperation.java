package operation;

import java.util.function.Function;

import model.Image;
import operable.LinearTransformOperable;
import operable.Operable;

/**
 * A class that represents getting luma on images.
 */
public class LumaOperation<T> implements Operation {
  private LinearTransformOperable<T> linearTransformOperable;
  private Function<T,T> lumaFunc;

  /** Construct a lumaOperation on given LinearTransformOperable.
   *
   * @param linearTransformOperable the given LinearTransformOperable
   * @param lumaFunc the function to calculate luma on the LinearTransformOperable
   */
  public LumaOperation(LinearTransformOperable<T> linearTransformOperable,
                       Function<T, T> lumaFunc) {
    this.linearTransformOperable = linearTransformOperable;
    this.lumaFunc = lumaFunc;
  }

  /**
   * Perform the operation.
   */
  @Override
  public Operable perform() {
    LinearTransformOperable<T> max=
        (LinearTransformOperable<T>) linearTransformOperable.mapElement(lumaFunc);
    return max.arrayMultiplication(new float[][]{
        {0.2126f,0.7152f,0.722f},
        {0.2126f,0.7152f,0.722f},
        {0.2126f,0.7152f,0.722f}
    });
  }
}
