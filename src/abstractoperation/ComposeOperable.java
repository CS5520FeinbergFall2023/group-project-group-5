package abstractoperation;// operations implemented by [r1,g1,b1]+[r2,g2,b2]+....
// including compose image from the 3 channel images, lighten and darken
// actually theoretically still belongs to linear operations but...

import model.Image;

/**
 * This abstract class represents all operations implemented by [r1,g1,b1]+[r2,g2,b2]+....,
 * including compose image from the 3 channel images, lighten and darken.
 */
public interface ComposeOperable extends Operable{

  /** Perform array addition an ComposeOperable with given matrix.
   *
   * @param matrix the given matrix (1x3)
   */
  ComposeOperable imgArrayAddition(float[] matrix);
}
