package model.compressor;

/**
 * This abstract class represents compressors
 */
public interface Compressor {

  /**
   * Compress a 2D float array.
   *
   * @param matrix the given 2D float array to compress
   * @return the compressed result
   */
  float[][] compress2D(float[][] matrix, float ratio);

  /**
   * Decompress a 2D float array.
   *
   * @param compressed the given 2D array to decompress
   * @return the decompressed result
   */
  float[][] decompress2D(float[][] compressed);

  /**
   * Compress an 1D float array.
   *
   * @param nums the given 1D float array to compress
   * @return the compressed result
   */
  float[] compress(float[] nums, float ratio);

  /**
   * Decompress an 1D float array.
   *
   * @param compressed the 1D compressed array to decompress
   * @return the decompressed result
   */
  float[] decompress(float[] compressed);
}
