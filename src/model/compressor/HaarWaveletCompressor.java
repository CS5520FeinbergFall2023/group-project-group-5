package model.compressor;

import java.util.Arrays;

/**
 * This class represents a compressor that uses Haar Wavelet Transform.
 */
public class HaarWaveletCompressor implements Compressor {
  private static final double sqrt2 = Math.sqrt(2);

  /**
   * Compress a 2D double matrix.
   *
   * @param matrix the given 2D double matrix to compress
   * @return the compressed result
   */
  @Override
  public double[][] compress2D(double[][] matrix, double ratio) {
    int height = matrix.length % 2 == 0 ? matrix.length : matrix.length + 1;
    int width = matrix[0].length % 2 == 0 ? matrix[0].length : matrix[0].length + 1;
    if (height == 0) {
      throw new IllegalArgumentException("The given matrix to compress is empty.");
    }
    //default values are 0.0
    double[][] result = new double[height][width];
    for (int i = 0; i < matrix.length; i++) {
      System.arraycopy(matrix[i], 0, result[i], 0, matrix[i].length);
    }
    //compress the rows
    for (int i = 0; i < height; i++) {
      int rowWidth = matrix[i].length % 2 == 0 ? matrix[i].length : matrix[i].length + 1;
      if (rowWidth != width) {
        throw new IllegalArgumentException("The given matrix to compress is not rectangle.");
      }
      result[i] = compress(matrix[i],ratio);
    }
    //then compress the columns
    for (int j = 0; j < width; j++) {
      double[] tmp = new double[height];
      for (int i = 0; i < height; i++) {
        tmp[i] = matrix[i][j];
      }
      tmp = compress(tmp,ratio);
      for (int i = 0; i < height; i++) {
        result[i][j] = tmp[i];
      }
    }
    return result;
  }



  /**
   * Compress an 1D integer array. Forward transformation with Haar Wavelet Transform.
   */
  @Override
  public double[] compress(double[] nums, double ratio) {
    if (nums.length == 0) {
      throw new IllegalArgumentException("The given list to compress is empty.");
    }
    int desiredLength = nums.length;
    if (!isPowerOfTwo(nums.length)) {
      desiredLength = 1 << ((int) Math.ceil(Math.log(nums.length)));
    }
    double[] result = new double[desiredLength];
    System.arraycopy(nums, 0, result,
        0, nums.length);
    if (nums.length < desiredLength) {
      for (int i = nums.length; i < desiredLength; i++) {
        result[i] = 0.0;
      }
    }
    int m = result.length;
    while (m > 1) {
      double[] tmp = transform(Arrays.copyOfRange(result, 0, m),ratio);
      if (m < result.length) {
        System.arraycopy(result, m, tmp, m, result.length - m);
      }
      result = tmp;
      m /= 2;
    }
    return result;
  }

  private boolean isPowerOfTwo(int n) {
    // A number is a power of 2 if it has only one bit set
    return (n > 0) && ((n & (n - 1)) == 0);
  }

  private double[] transform(double[] nums,double ratio) {
    if (nums.length == 0) {
      throw new IllegalArgumentException("The given list to transform is empty.");
    }
    int groupCount = (int) Math.ceil(nums.length / 2.0f);
    double[] compressed = new double[2 * groupCount];
    for (int i = 0; i < groupCount - 1; i++) {
      compressed[i] = (nums[2 * i] + nums[2 * i + 1]) / sqrt2;
      compressed[i + groupCount] = ((nums[2 * i] - nums[2 * i + 1]) / sqrt2);
    }
    if (nums.length % 2 == 0) {
      compressed[groupCount - 1] =
          (nums[nums.length - 2] + nums[nums.length - 1]) / sqrt2;
      compressed[compressed.length - 1] = (nums[nums.length - 2] - nums[nums.length - 1]) / sqrt2;
    } else {
      compressed[groupCount - 1] = nums[nums.length - 1] / sqrt2;
      compressed[compressed.length - 1] = nums[nums.length - 1] / sqrt2;
    }
    double threshold = getThreshold(compressed, ratio);
    for (int i = 0; i < compressed.length; i++) {
      if (compressed[i] <= threshold) {
        compressed[i] = 0.0;
      }
    }
    return compressed;
  }


  /**
   * Decompress a double list.
   */
  @Override
  public double[] decompress(double[] compressed) {
    if (compressed.length == 0) {
      throw new IllegalArgumentException("The given list to decompress is empty.");
    }
    if (!isPowerOfTwo(compressed.length)) {
      throw new IllegalArgumentException("The given list is not properly compressed with length "
                                         + "power of 2.");
    }
    double[] result = Arrays.copyOfRange(compressed, 0, compressed.length);
    int m = 2;
    while (m <= compressed.length) {
      double[] tmp = invert(Arrays.copyOfRange(result, 0, m));
      if (m < result.length) {
        System.arraycopy(result, m, tmp, m, result.length - m);

      }
      result = tmp;
      m *= 2;
    }

    return result;
  }

  private double getThreshold(double[] nums, double ratio) {
    if (ratio < 0 || ratio > 1) {
      throw new IllegalArgumentException("Ratio cannot be smaller than 0 or larger than 1.");
    }
    double[] sorted = Arrays.copyOfRange(nums, 0, nums.length);
    Arrays.sort(sorted);
    int index = (int) Math.ceil(sorted.length * ratio) - 1;
    return sorted[index];
  }

  private double[] invert(double[] nums) {
    if (nums.length == 0) {
      throw new IllegalArgumentException("The given list is empty.");
    }
    if (nums.length % 2 != 0) {
      throw new IllegalArgumentException("Compressed format not correct.");
    }
    double[] inverted = new double[nums.length];
    int groupCount = nums.length / 2;
    for (int i = 0; i < groupCount; i++) {
      double base = nums[i];
      double diff = nums[i + groupCount];
      inverted[2 * i] = (base + diff) / sqrt2;
      inverted[2 * i + 1] = (base - diff) / sqrt2;
    }
    return inverted;
  }
}