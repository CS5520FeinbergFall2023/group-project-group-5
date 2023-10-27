package operable;

// all the name linear and affine transformation here are describing the pixel components, not the
// image
// e.g. the pixel changes its original point if we take the whole image as the canvas/coordinate
// for flipping, so it's categorized as affine transformation.

/**
 * This abstract class represents all operations that are performed with 2d Affine transform.
 * Includes flipping.
 */
public interface AffineTransformOperable extends Operable{
  /** Project coordinate of the original component element.
   * For example, for a 2d image with height 5 and width 3, the originalDimensions are [5,3], and
   * projectMatrix project pixel coordinate (1,0) to (0,1) by project than translation, so the
   * result[1][0] will be [0][1].
   * Those pixels that fall outside the image area will be said to be project to (-1,-1)
   *
   * @param projectMatrix the matrix to be used to perform the projection
   * @return the project result
   */

  AffineTransformOperable projectCoordinate(int[][] projectMatrix);
}
