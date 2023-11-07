package model.compressor;

import java.util.List;

/**
 * This abstract class represents compressors
 */
public interface Compressor {

  /**
   * Compress an object;
   */
  double[] compress(double[] nums);

  /**
   * Decompress an object;
   */
  double[] decompress(double[] compressed, double ratio);
}
