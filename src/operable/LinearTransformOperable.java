package operable;

/**
 * This abstract class represents all operations that are perform by array X image
 * to make every pixel [r,g,b] --> [r',g',b'],
 * including decomposing channels, greyscale, sepia and visualize brightness (with a greyscale
 * result).
 */
public interface LinearTransformOperable extends Operable{
  /** Perform linear transformation on a LinearTransformOperable with given matrix.
   *
   * @param matrix the given matrix
   * @return the new LinearTransformOperable after modification
   */
  LinearTransformOperable arrayMultiplication(float[][] matrix);
}
