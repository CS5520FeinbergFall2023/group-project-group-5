package model.compressor;

/**
 * This abstract class represents compressors
 */
public interface Compressor {

  /**
   * Compress a 2D double array.
   *
   * @param matrix the given 2D double array to compress
   * @return the compressed result
   */
  double[][] compress2D(double[][] matrix, double ratio);

  /**
   * Decompress a 2D double array.
   *
   * @param compressed the given 2D array to decompress
   * @return the decompressed result
   */
  double[][] decompress2D(double[][] compressed);

  /**
   * Compress an 1D double array.
   *
   * @param nums the given 1D double array to compress
   * @return the compressed result
   */
  double[] compress(double[] nums, double ratio);

  /**
   * Decompress an 1D double array.
   *
   * @param compressed the 1D compressed array to decompress
   * @return the decompressed result
   */
  double[] decompress(double[] compressed);
}
