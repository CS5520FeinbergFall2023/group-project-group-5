package abstractoperation;

import model.Image;
import model.Pixel;

/**
 * This abstract class represents all operations perform with filtering.
 * Including blurring and sharpening.
 */

public interface FilterOperable extends Operable{

  /** Perform filtering a FilterOperable with given matrix.
   *
   * @param kernel the given kernel
   * @throws IllegalArgumentException when the given argument is not legal
   */
  FilterOperable imgFiltering(float[][] kernel) throws IllegalArgumentException;
}
