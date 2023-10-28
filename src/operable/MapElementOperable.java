package operable;

import java.util.function.Function;

/**
 * This interface is about all operations that can be achieved by mapping each element to a new one.
 */
//todo:matrix addition and multiplications (LinearTransformation and Compose) should also be
// implemented with mapping (doing the same thing for every element)
public interface MapElementOperable<T> extends Operable{
  /** Map all elements in the object.
   *
   * @param function the mapping function
   * @return the mapped result
   */
  MapElementOperable<T> mapElement(Function<T,T> function);
}
