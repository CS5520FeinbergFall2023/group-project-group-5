package model.compressor;

import java.util.List;

/**
 * This abstract class represents compressors
 */
public interface Compressor {

  /** Compress a 2D double matrix.
   * @param matrix the given 2D double matrix to compress
   * @return the compressed result
   */
  double[][] compress2D(double[][] matrix,double ratio);

  //todo:move ratio to compress
  double[][] decompress2D(double[][] compressed);

  /**
   * Compress an object;
   */
  double[] compress(double[] nums, double ratio);

  /**
   * Decompress an object;
   */
  double[] decompress(double[] compressed);
}
