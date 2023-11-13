package model.compressor;

/**
 * This abstract class represents compressors
 */
public interface Compressor {

  /**
   * Compress a 3D float array with given ratio.
   *
   * @param matrix the given 3D float array to compress
   * @return the compressed result
   */
  float[][][] compress(float[][][] matrix, float ratio);

  /**
   * Compress a 2D float array with given ratio.
   *
   * @param matrix the given 2D float array to compress
   * @return the compressed result
   */
  float[][] compress(float[][] matrix, float ratio);

  /**
   * Compress an 1D float array with given ratio.
   *
   * @param nums the given 1D float array to compress
   * @return the compressed result
   */
  float[] compress(float[] nums, float ratio);


  /**
   * Decompress a 3D float array.
   *
   * @param compressed the given 3D array to decompress
   * @return the decompressed result
   */
  float[][][] decompress(float[][][] compressed);


  /**
   * Decompress a 2D float array.
   *
   * @param compressed the given 2D array to decompress
   * @return the decompressed result
   */
  float[][] decompress(float[][] compressed);

  /**
   * Decompress an 1D float array.
   *
   * @param compressed the 1D compressed array to decompress
   * @return the decompressed result
   */
  float[] decompress(float[] compressed);
}
