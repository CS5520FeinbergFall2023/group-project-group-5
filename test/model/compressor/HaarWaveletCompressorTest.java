package model.compressor;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * This class tests the HaarWaveletCompressor class.
 */
public class HaarWaveletCompressorTest {
  HaarWaveletCompressor instance;

  @Before
  public void setUp() throws Exception {
    instance = HaarWaveletCompressor.getInstance();
  }



  @Test
  public void testGetInstance() {

    assertEquals("model.compressor.HaarWaveletCompressor", instance.getClass().getName());
  }

  @Test
  public void testCompress2DZero() {
    float[][] originalMatrix = new float[][]{
        {1.5f, 2.2f, 3.4f},
        {4.7f, 5.8f, 6.2f},
        {7.9f, 8.6f, 9.1f}
    };
    float targetRatio = 0.1f;
    float[][] result = instance.compress2D(originalMatrix, targetRatio);
    System.out.println(Arrays.deepToString(result));
  }

  @Test
  public void testDecompress2D() {
    float[][] originalMatrix = new float[][]{
        {1.5f, 2.2f, 3.4f},
        {4.7f, 5.8f, 6.2f},
        {7.9f, 8.6f, 9.1f}
    };
    float targetRatio = 0.1f;
    float[][] compressed = instance.compress2D(originalMatrix, targetRatio);
    float[][] decompressed=instance.decompress2D(compressed);
    System.out.println(Arrays.deepToString(decompressed));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCompressEmpty() {
    instance.compress(new float[]{1.0f, 2.0f}, 0);
  }

  @Test
  public void testCompressOddElement() {
    float[] originalArr = new float[]{1.5f, 2.2f, 3.4f};
    float targetRatio = 0.8f;
    float[] result = instance.compress(originalArr, targetRatio);
    System.out.println(Arrays.toString(result));
  }

  @Test
  public void testCompressEvenElement() {
    float[] originalArr = new float[]{1.5f, 2.2f, 3.4f, 9.6f};
    float targetRatio = 0.0f;
    float[] result = instance.compress(originalArr, targetRatio);
    System.out.println(Arrays.toString(result));
  }

  @Test
  public void testCompressEvenElementNotPowOfTwo() {
    float[] originalArr = new float[]{1.5f, 2.2f, 3.4f, 9.6f, 1.0f, 3.0f};
    float targetRatio = 0.6f;
    float[] result = instance.compress(originalArr, targetRatio);
    System.out.println(Arrays.toString(result));
  }

  @Test
  public void testCompressRatioZero() {
    float[] originalArr = new float[]{1.5f, 2.2f, 3.4f, 9.6f};
    float targetRatio = 0f;
    float[] result = instance.compress(originalArr, targetRatio);
    System.out.println(Arrays.toString(result));
  }

  @Test
  public void testCompressRatioOne() {

  }

  @Test
  public void testDecompressEmpty() {
  }

  @Test
  public void testDecompressSizeIllegal() {
  }

  @Test
  public void testDecompress() {
  }

  //decompress from a zero compress ratio compression
  @Test
  public void testDecompressRatioZero() {
  }

  @Test
  public void testDecompressRatioOne() {
  }

}