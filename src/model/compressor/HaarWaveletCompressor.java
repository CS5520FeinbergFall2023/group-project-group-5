package model.compressor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class represents a compressor that uses Haar Wavelet Transform.
 */
public class HaarWaveletCompressor implements Compressor {
  public static final float sqrt2 = (float) Math.sqrt(2);

  //singleton
  private static HaarWaveletCompressor instance = new HaarWaveletCompressor();

  private HaarWaveletCompressor() {
  }

  /**
   * Get the singleton instance of the compressor.
   */
  public static HaarWaveletCompressor getInstance() {
    return instance;
  }

  /**
   * Compress a 3D float array with given ratio.
   *
   * @param matrix the given 3D float array to compress
   * @return the compressed result
   * @throws IllegalArgumentException when given arguments are illegal
   */
  @Override
  public float[][][] compress(float[][][] matrix, float ratio) throws IllegalArgumentException {
    if (ratio < 0 || ratio > 1) {
      throw new IllegalArgumentException("Ratio cannot be smaller than 0 or larger than 1.");
    }
    int length = matrix.length;
    if (length == 0) {
      throw new IllegalArgumentException("The given matrix to compress is empty");
    }
    float[][][] result = new float[length][][];
    for (int i = 0; i < length; i++) {
      result[i] = compress(matrix[i], 0);
    }
    float threshold = getThreshold(result, ratio);
    for (int i = 0; i < length; i++) {
      for (int j = 0; j < result[i].length; j++) {
        for (int k = 0; k < result[i][j].length; k++) {
          if (Math.abs(result[i][j][k]) <= threshold) {
            result[i][j][k] = 0;
          }
        }
      }
    }
    return result;
  }

  /**
   * Decompress a 3D float array.
   *
   * @param compressed the given 3D array to decompress
   * @return the decompressed result
   * @throws IllegalArgumentException when given argument is illegal (array empty or sub array
   * not with 2^n side length)
   */
  @Override
  public float[][][] decompress(float[][][] compressed) throws IllegalArgumentException {
    int length = compressed.length;
    if (length == 0) {
      throw new IllegalArgumentException("The given array to decompress is empty");
    }
    float[][][] result = new float[length][][];
    for (int i = 0; i < length; i++) {
      result[i] = decompress(compressed[i]);
    }
    return result;
  }


  /**
   * Compress a 2D float array.
   *
   * @param matrix the given 2D float array to compress
   * @param ratio  the compression ratio ([0,1])
   * @return the compressed result
   * @throws IllegalArgumentException when given matrix or ratio is illegal
   */
  @Override
  public float[][] compress(float[][] matrix, float ratio) throws IllegalArgumentException {
    if (matrix.length == 0) {
      throw new IllegalArgumentException("The given matrix to compress is empty.");
    }
    if (ratio < 0 || ratio > 1) {
      throw new IllegalArgumentException("Ratio cannot be smaller than 0 or larger than 1.");
    }
    int length = Math.max(matrix.length, matrix[0].length);
    if (!isPowerOfTwo(length)) {
      length = 1 << ((int) Math.ceil(log2(length)));
    }
    int originalWidth = matrix[0].length;
    //copy the original values. default values are 0.0
    float[][] result = new float[length][length];
    for (int i = 0; i < matrix.length; i++) {
      if (matrix[i].length != originalWidth) {
        throw new IllegalArgumentException("The given matrix to compress is not rectangle.");
      }
      System.arraycopy(matrix[i], 0, result[i], 0, matrix[i].length);
    }
    int c = length;
    while (c > 1) {
      //row
      for (int row = 0; row < c; row++) {
        float[] tmp = Arrays.copyOfRange(result[row], 0, c);
        tmp = transform(tmp);
        System.arraycopy(tmp, 0, result[row], 0, c);
      }
      //column
      for (int col = 0; col < c; col++) {
        float[] tmp = new float[c];
        for (int i = 0; i < c; i++) {
          tmp[i] = result[i][col];
        }
        tmp = transform(tmp);
        for (int i = 0; i < c; i++) {
          result[i][col] = tmp[i];
        }
      }
      c /= 2;
    }
    if (ratio > 0) {
      float threshold = getThreshold(result, ratio);
      for (int i = 0; i < result.length; i++) {
        for (int j = 0; j < result[i].length; j++) {
          if (Math.abs(result[i][j]) <= threshold) {
            result[i][j] = 0;
          }
        }
      }
    }
    return result;
  }


  /**
   * Decompress a 2D float array.
   *
   * @param compressed the given 2D array to decompress
   * @return the decompressed result
   * @throws IllegalArgumentException when given matrix's size is illegal
   */
  @Override
  public float[][] decompress(float[][] compressed) throws IllegalArgumentException {
    if (compressed.length == 0) {
      throw new IllegalArgumentException("The given matrix to compress is empty.");
    }
    int height = compressed.length;
    int width = compressed[0].length;
    if (!isPowerOfTwo(height) || !(height == width)) {
      throw new IllegalArgumentException("The given compressed matrix is malformed.");
    }
    float[][] result = new float[height][width];
    for (int i = 0; i < height; i++) {
      System.arraycopy(compressed[i], 0, result[i], 0, width);
    }
    int c = 2;
    while (c <= height) {
      for (int col = 0; col < c; col++) {
        float[] tmp = new float[c];
        for (int i = 0; i < c; i++) {
          tmp[i] = result[i][col];
        }
        tmp = invert(tmp);
        for (int i = 0; i < c; i++) {
          result[i][col] = tmp[i];
        }
      }
      for (int row = 0; row < c; row++) {
        float[] tmp = Arrays.copyOfRange(result[row], 0, c);
        tmp = invert(tmp);
        System.arraycopy(tmp, 0, result[row], 0, c);
      }
      c *= 2;
    }
    return result;
  }

  /**
   * Compress an 1D float array.
   *
   * @param nums  the given 1D float array to compress
   * @param ratio the compression ratio ([0,1])
   * @return the compressed result
   * @throws IllegalArgumentException when given array or ratio is illegal
   */
  @Override
  public float[] compress(float[] nums, float ratio) throws IllegalArgumentException {
    if (nums.length == 0) {
      throw new IllegalArgumentException("The given list to compress is empty.");
    }
    if (ratio < 0 || ratio > 1) {
      throw new IllegalArgumentException("Ratio cannot be smaller than 0 or larger than 1.");
    }
    int desiredLength = nums.length;
    if (!isPowerOfTwo(nums.length)) {
      desiredLength = 1 << ((int) Math.ceil(log2(nums.length)));
    }
    float[] result = new float[desiredLength];
    System.arraycopy(nums, 0, result, 0, nums.length);
    int m = result.length;
    while (m > 1) {
      float[] tmp = transform(Arrays.copyOfRange(result, 0, m));
      System.arraycopy(tmp, 0, result, 0, m);
      m /= 2;
    }
    if (ratio > 0) {
      float threshold = getThreshold(result, ratio);
      for (int i = 0; i < result.length; i++) {
        if (result[i] <= threshold) {
          result[i] = 0.0f;
        }
      }
    }
    return result;
  }

  private boolean isPowerOfTwo(int n) {
    // A number is a power of 2 if it has only one bit set
    return (n > 0) && ((n & (n - 1)) == 0);
  }

  private float[] transform(float[] nums) {
    if (nums.length == 0) {
      throw new IllegalArgumentException("The given list to transform is empty.");
    }
    int groupCount = (int) Math.ceil(nums.length / 2.0f);
    float[] compressed = new float[2 * groupCount];
    for (int i = 0; i < groupCount - 1; i++) {
      compressed[i] = (nums[2 * i] + nums[2 * i + 1]) / sqrt2;
      compressed[i + groupCount] = ((nums[2 * i] - nums[2 * i + 1]) / sqrt2);
    }
    if (nums.length % 2 == 0) {
      compressed[groupCount - 1] =
          (nums[nums.length - 2] + nums[nums.length - 1]) / sqrt2;
      compressed[2 * groupCount - 1] = (nums[nums.length - 2] - nums[nums.length - 1]) / sqrt2;
    } else {
      compressed[groupCount - 1] = nums[nums.length - 1] / sqrt2;
      compressed[2 * groupCount - 1] = nums[nums.length - 1] / sqrt2;
    }
    return compressed;
  }


  /**
   * Decompress an 1D float array.
   *
   * @param compressed the 1D compressed array to decompress
   * @return the decompressed result
   * @throws IllegalArgumentException when given array's size is illegal
   */
  @Override
  public float[] decompress(float[] compressed) throws IllegalArgumentException {
    if (compressed.length == 0) {
      throw new IllegalArgumentException("The given list to decompress is empty.");
    }
    if (!isPowerOfTwo(compressed.length)) {
      throw new IllegalArgumentException("The given list is not properly compressed with length "
                                         + "power of 2.");
    }
    float[] result = Arrays.copyOfRange(compressed, 0, compressed.length);
    int m = 2;
    while (m <= compressed.length) {
      float[] tmp = invert(Arrays.copyOfRange(result, 0, m));
      System.arraycopy(tmp, 0, result, 0, m);
      m *= 2;
    }

    return result;
  }

  private float getThreshold(Object numsArray, float ratio) throws IllegalArgumentException {
    if (ratio < 0 || ratio > 1) {
      throw new IllegalArgumentException("Ratio cannot be smaller than 0 or larger than 1.");
    }
    if (!numsArray.getClass().isArray()) {
      throw new IllegalArgumentException(
          "The argument needs to be an array of arbitrary dimension.");
    }
    List<Float> flattenedList = new ArrayList<>(flattenAbsoluteArray(numsArray));
    if (flattenedList.isEmpty()) {
      return 0;
    }
    Collections.sort(flattenedList);
    int index = (int) Math.ceil(flattenedList.size() * ratio) - 1;
    if (index < 0) {
      return 0;
    }
    return flattenedList.get(index);
  }

  private Set<Float> flattenAbsoluteArray(Object array) throws IllegalArgumentException {
    Set<Float> flattenedSet = new HashSet<>();
    if (array.getClass().isArray()) {
      int length = java.lang.reflect.Array.getLength(array);
      for (int i = 0; i < length; i++) {
        Object element = java.lang.reflect.Array.get(array, i);
        if (element.getClass().isArray()) {
          flattenedSet.addAll(flattenAbsoluteArray(element));
        } else {
          if ((float) element != 0) {
            flattenedSet.add(Math.abs((float) element));
          }
        }
      }
    } else {
      throw new IllegalArgumentException(
          "The argument needs to be an array of arbitrary dimension.");
    }
    return flattenedSet;
  }

  private float[] invert(float[] nums) {
    if (nums.length == 0) {
      throw new IllegalArgumentException("The given list is empty.");
    }
    if (nums.length % 2 != 0) {
      throw new IllegalArgumentException("Compressed format not correct.");
    }
    float[] inverted = new float[nums.length];
    int groupCount = nums.length / 2;
    for (int i = 0; i < groupCount; i++) {
      float base = nums[i];
      float diff = nums[i + groupCount];
      inverted[2 * i] = (base + diff) / sqrt2;
      inverted[2 * i + 1] = (base - diff) / sqrt2;
    }
    return inverted;
  }

  private float log2(float number) {
    return (float) (Math.log(number) / Math.log(2));
  }

}