package operable;// operations implemented by [r1,g1,b1]+[r2,g2,b2]+....
// including compose image from the 3 channel images, lighten and darken
// actually theoretically still belongs to linear operations but...

/**
 * This abstract class represents all operations implemented by [r1,g1,b1]+[r2,g2,b2]+....,
 * including compose image from the 3 channel images, lighten and darken.
 */
public interface ComposeOperable extends Operable{

  /** Perform array addition an ComposeOperable with given matrix.
   *
   * @param matrix the given matrix (1x3)
   * @return the add result
   */
  ComposeOperable imgArrayAddition(float[] matrix);

//  /** Perform addition between two ComposeOperables.
//   * @param composeOperable the other ComposeOperable to be added
//   * @return the add result
//   */
//  ComposeOperable addition(ComposeOperable composeOperable);

  /** Perform addition with a few other ComposeOperables.
   * @param composeOperables the other ComposeOperables to be added
   * @return the add result
   */
  ComposeOperable addition(Iterable<ComposeOperable> composeOperables);
}
