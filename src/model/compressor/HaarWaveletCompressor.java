package model.compressor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
   * Compress a 2D float matrix.
   *
   * @param matrix the given 2D float matrix to compress
   * @param ratio  the compression ratio ([0,1])
   * @return the compressed result
   * @throws IllegalArgumentException when given matrix or ratio is illegal
   */
  @Override
  public float[][] compress2D(float[][] matrix, float ratio) throws IllegalArgumentException {
    if (matrix.length == 0) {
      throw new IllegalArgumentException("The given matrix to compress is empty.");
    }
    if (ratio < 0 || ratio > 1) {
      throw new IllegalArgumentException("Ratio cannot be smaller than 0 or larger than 1.");
    }
    int height = matrix.length;
    if (!isPowerOfTwo(matrix.length)) {
      height = 1 << ((int) Math.ceil(log2(matrix.length)));
    }
    int originalWidth = matrix[0].length;
    int width = originalWidth;
    if (!isPowerOfTwo(width)) {
      width = 1 << ((int) Math.ceil(log2(width)));
    }
    //copy the original values. default values are 0.0
    float[][] result = new float[height][width];
    for (int i = 0; i < matrix.length; i++) {
      if (matrix[i].length != originalWidth) {
        throw new IllegalArgumentException("The given matrix to compress is not rectangle.");
      }
      System.arraycopy(matrix[i], 0, result[i], 0, matrix[i].length);
    }
    //compress the rows
    for (int i = 0; i < height; i++) {
      //discard small items after finished transforming the whole matrix.
      result[i] = compress(result[i], 0);
    }
    //then compress the columns
    for (int j = 0; j < width; j++) {
      float[] tmp = new float[height];
      for (int i = 0; i < height; i++) {
        tmp[i] = result[i][j];
      }
      tmp = compress(tmp, 0);
      for (int i = 0; i < height; i++) {
        result[i][j] = tmp[i];
      }
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
   */
  @Override
  public float[][] decompress2D(float[][] compressed) {
    if (compressed.length == 0) {
      throw new IllegalArgumentException("The given matrix to compress is empty.");
    }
    int height = compressed.length;
    int width = compressed[0].length;
    if (!isPowerOfTwo(height) || !isPowerOfTwo(width)) {
      throw new IllegalArgumentException("The given compressed matrix is malformed.");
    }
    float[][] result = new float[height][width];
    //first decompress the columns
    for (int j = 0; j < width; j++) {
      float[] tmp = new float[height];
      for (int i = 0; i < height; i++) {
        tmp[i] = compressed[i][j];
      }
      tmp = decompress(tmp);
      for (int i = 0; i < height; i++) {
        result[i][j] = tmp[i];
      }
    }
    //then decompress the rows
    for (int i = 0; i < height; i++) {
      result[i] = decompress(result[i]);
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
      float[] tmp = transform(Arrays.copyOfRange(result, 0, m), ratio);
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

  private float[] transform(float[] nums, float ratio) {
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
   */
  @Override
  public float[] decompress(float[] compressed) {
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
    List<Float> flattenedList = flattenAbsoluteArray(numsArray);
    Collections.sort(flattenedList);
    int index = (int) Math.ceil(flattenedList.size() * ratio) - 1;
    return flattenedList.get(index);
  }

  private List<Float> flattenAbsoluteArray(Object array) throws IllegalArgumentException {
    List<Float> flattenedList = new ArrayList<>();
    if (array.getClass().isArray()) {
      int length = java.lang.reflect.Array.getLength(array);
      for (int i = 0; i < length; i++) {
        Object element = java.lang.reflect.Array.get(array, i);
        if (element.getClass().isArray()) {
          flattenedList.addAll(flattenAbsoluteArray(element));
        } else {
          flattenedList.add(Math.abs((float) element));
        }
      }git
    } else {
      throw new IllegalArgumentException(
          "The argument needs to be an array of arbitrary dimension.");
    }
    return flattenedList;
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