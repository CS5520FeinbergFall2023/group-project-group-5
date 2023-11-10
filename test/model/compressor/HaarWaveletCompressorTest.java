package model.compressor;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class tests the HaarWaveletCompressor class.
 */
public class HaarWaveletCompressorTest {
  HaarWaveletCompressor instance;

  @Before
  public void setUp() throws Exception {
    instance = HaarWaveletCompressor.getInstance();
  }

  private boolean if2DArraysEqual(float[][] expected, float[][] actual, float delta) {
    if (expected.length != actual.length || expected[0].length != actual[0].length) {
      return false;
    }
    for (int i = 0; i < expected.length; i++) {
      assertArrayEquals(expected[i], actual[i], delta);
    }
    return true;
  }

  @Test
  public void testGetInstance() {
    assertEquals("model.compressor.HaarWaveletCompressor", instance.getClass().getName());
  }

  //-----------------2D compress-----------------------------

  //both height and width is odd
  @Test
  public void testCompress2DOdd() {
    float[][] originalMatrix = new float[][]{
        {1, 2, 3},
        {4, 5, 6},
        {7, 8, 9}
    };
    float targetRatio = 0f;
    float[][] result = instance.compress2D(originalMatrix, targetRatio);
    float[][] expected = new float[][]
        {
            {11.25f, 2.25f, -1.5f / HaarWaveletCompressor.sqrt2, 9 / HaarWaveletCompressor.sqrt2},
            {-0.75f, -0.75f, -0.5f / HaarWaveletCompressor.sqrt2, 0},
            {-4.5f / HaarWaveletCompressor.sqrt2, -1.5f / HaarWaveletCompressor.sqrt2, 0, -1.5f},
            {12 / HaarWaveletCompressor.sqrt2, 3 / HaarWaveletCompressor.sqrt2, -0.5f, 4.5f}
        };
    assertTrue(if2DArraysEqual(expected, result, 0.001f));
  }

  @Test
  public void testCompress2DOddWidth() {
    float[][] originalMatrix = new float[][]{
        {1, 2, 3},
        {4, 5, 6}
    };
    float targetRatio = 0f;
    float[][] result = instance.compress2D(originalMatrix, targetRatio);
    float[][] expected = new float[][]{
        {10.5f / HaarWaveletCompressor.sqrt2, 1.5f / HaarWaveletCompressor.sqrt2, -1f, 4.5f},
        {-4.5f / HaarWaveletCompressor.sqrt2, -1.5f / HaarWaveletCompressor.sqrt2, 0, -1.5f}
    };
    System.out.println(Arrays.deepToString(result));
    assertTrue(if2DArraysEqual(expected, result, 0.001f));
  }

  @Test
  public void testCompress2DOddHeight() {
    float[][] originalMatrix = new float[][]{
        {1, 2},
        {4, 5},
        {7, 8}
    };
    float targetRatio = 0f;
    float[][] result = instance.compress2D(originalMatrix, targetRatio);
    float[][] expected = new float[][]
        {
            {13.5f / HaarWaveletCompressor.sqrt2, -1.5f / HaarWaveletCompressor.sqrt2},
            {-1.5f / HaarWaveletCompressor.sqrt2, -0.5f / HaarWaveletCompressor.sqrt2},
            {-3, 0},
            {7.5f, -0.5f}
        };
    assertTrue(if2DArraysEqual(expected, result, 0.001f));
  }


  @Test
  public void testCompress2DEven() {
    float[][] originalMatrix = new float[][]{
        {1, 2, 3, 4},
        {5, 6, 7, 8},
        {9, 10, 11, 12},
        {13, 14, 15, 16}
    };
    float targetRatio = 0f;
    float[][] result = instance.compress2D(originalMatrix, targetRatio);
    float[][] expected = new float[][]{
        {34, -4, -HaarWaveletCompressor.sqrt2, -HaarWaveletCompressor.sqrt2},
        {-16, 0, 0, 0},
        {-8f / HaarWaveletCompressor.sqrt2, 0, 0, 0},
        {-8f / HaarWaveletCompressor.sqrt2, 0, 0, 0}
    };
    assertTrue(if2DArraysEqual(expected, result, 0.001f));

    result = instance.compress2D(originalMatrix, 1);
    expected = new float[][]{
        {0, 0, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
    };
    assertTrue(if2DArraysEqual(expected, result, 0.001f));

    result = instance.compress2D(originalMatrix, 0.5f);
    expected = new float[][]{
        {34, -4, -HaarWaveletCompressor.sqrt2, -HaarWaveletCompressor.sqrt2},
        {-16, 0, 0, 0},
        {-8 / HaarWaveletCompressor.sqrt2, 0, 0, 0},
        {-8 / HaarWaveletCompressor.sqrt2, 0, 0, 0}
    };
    assertTrue(if2DArraysEqual(expected, result, 0.001f));
  }

  @Test
  public void testCompress2DEvenNotPowerHeight() {
    float[][] originalMatrix = new float[][]{
        {1, 2},
        {3, 4},
        {5, 6},
        {7, 8},
        {9, 10},
        {11, 12}
    };
    float targetRatio = 0f;
    float[][] result = instance.compress2D(originalMatrix, targetRatio);
    float[][] expected = new float[][]{
        {19.5f, -1.5f},
        {-1.5f, -0.5f},
        {-8 / HaarWaveletCompressor.sqrt2, 0},
        {21 / HaarWaveletCompressor.sqrt2, -1 / HaarWaveletCompressor.sqrt2},
        {-2, 0},
        {-2, 0},
        {-2, 0},
        {0, 0}
    };
    assertTrue(if2DArraysEqual(expected, result, 0.001f));
  }

  @Test
  public void testCompress2DEvenNotPowerWidth() {
    float[][] originalMatrix = new float[][]{
        {1, 2, 3, 4, 5, 6},
        {7, 8, 9, 10, 11, 12},
    };
    float targetRatio = 0f;
    float[][] result = instance.compress2D(originalMatrix, targetRatio);
    float[][] expected = new float[][]{
        {19.5f, 2.5f, -4 / HaarWaveletCompressor.sqrt2, 17 / HaarWaveletCompressor.sqrt2, -1, -1,
            -1, 0},
        {-9, -3, 0, -6 / HaarWaveletCompressor.sqrt2, 0, 0, 0, 0}
    };
    assertTrue(if2DArraysEqual(expected, result, 0.001f));
  }


  //-----------------2D decompress-----------------------------
  @Test(expected = IllegalArgumentException.class)
  public void testDecompressSizeZero() {
    instance.decompress2D(new float[][]{});
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDecompressSizeOddWidth() {
    instance.decompress2D(new float[][]{
        {1, 2, 3},
        {4, 5, 6}
    });
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDecompressSizeOddHeight() {
    instance.decompress2D(new float[][]{
        {1, 2},
        {4, 5},
        {7, 8}
    });
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDecompressSizeOdd() {
    instance.decompress2D(new float[][]{
        {1, 2, 3},
        {4, 5, 6},
        {7, 8, 9}
    });
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDecompressSizeEvenNotPow() {
    instance.decompress2D(new float[][]{
        {1, 2, 3, 4, 5, 6},
        {1, 2, 3, 4, 5, 6},
        {1, 2, 3, 4, 5, 6},
        {1, 2, 3, 4, 5, 6},
        {1, 2, 3, 4, 5, 6},
        {1, 2, 3, 4, 5, 6}
    });
  }


  @Test
  public void testDecompress2DOriginalEven() {
    float[][] compressed = new float[][]{
        {34, -4, -HaarWaveletCompressor.sqrt2, -HaarWaveletCompressor.sqrt2},
        {-16, 0, 0, 0},
        {-8f / HaarWaveletCompressor.sqrt2, 0, 0, 0},
        {-8f / HaarWaveletCompressor.sqrt2, 0, 0, 0}
    };
    float[][] decompressed = instance.decompress2D(compressed);
    float[][] originalMatrix = new float[][]{
        {1, 2, 3, 4},
        {5, 6, 7, 8},
        {9, 10, 11, 12},
        {13, 14, 15, 16}
    };
    assertTrue(if2DArraysEqual(originalMatrix, decompressed, 0.001f));
  }

  @Test
  public void testDecompress2DOriginalEvenNotPowHeight() {
    float[][] compressed = new float[][]{
        {19.5f, -1.5f},
        {-1.5f, -0.5f},
        {-8 / HaarWaveletCompressor.sqrt2, 0},
        {21 / HaarWaveletCompressor.sqrt2, -1 / HaarWaveletCompressor.sqrt2},
        {-2, 0},
        {-2, 0},
        {-2, 0},
        {0, 0}
    };
    float[][] originalMatrix = new float[][]{
        {1, 2},
        {3, 4},
        {5, 6},
        {7, 8},
        {9, 10},
        {11, 12},
        {0, 0},
        {0, 0}
    };
    float[][] decompressed = instance.decompress2D(compressed);
    assertTrue(if2DArraysEqual(originalMatrix, decompressed, 0.001f));
  }

  @Test
  public void testDecompress2DOriginalEvenNotPowWidth() {
    float[][] compressed = new float[][]{
        {19.5f, 2.5f, -4 / HaarWaveletCompressor.sqrt2, 17 / HaarWaveletCompressor.sqrt2, -1, -1,
            -1, 0},
        {-9, -3, 0, -6 / HaarWaveletCompressor.sqrt2, 0, 0, 0, 0}
    };
    float[][] originalMatrix = new float[][]{
        {1, 2, 3, 4, 5, 6, 0, 0},
        {7, 8, 9, 10, 11, 12, 0, 0},
    };
    float[][] decompressed = instance.decompress2D(compressed);
    assertTrue(if2DArraysEqual(originalMatrix, decompressed, 0.001f));
  }

  @Test
  public void testDecompress2DOriginalOddHeight() {
    float[][] compressed = new float[][]{
        {13.5f / HaarWaveletCompressor.sqrt2, -1.5f / HaarWaveletCompressor.sqrt2},
        {-1.5f / HaarWaveletCompressor.sqrt2, -0.5f / HaarWaveletCompressor.sqrt2},
        {-3, 0},
        {7.5f, -0.5f}
    };
    float[][] originalMatrix = new float[][]{
        {1, 2},
        {4, 5},
        {7, 8},
        {0, 0}
    };
    float[][] decompressed = instance.decompress2D(compressed);
    assertTrue(if2DArraysEqual(originalMatrix, decompressed, 0.001f));
  }

  @Test
  public void testDecompress2DOriginalOddWidth() {
    float[][] compressed = new float[][]{
        {10.5f / HaarWaveletCompressor.sqrt2, 1.5f / HaarWaveletCompressor.sqrt2, -1f, 4.5f},
        {-4.5f / HaarWaveletCompressor.sqrt2, -1.5f / HaarWaveletCompressor.sqrt2, 0, -1.5f}
    };
    float[][] originalMatrix = new float[][]{
        {1, 2, 3, 0},
        {4, 5, 6, 0}
    };
    float[][] decompressed = instance.decompress2D(compressed);
    assertTrue(if2DArraysEqual(originalMatrix, decompressed, 0.001f));
  }

  @Test
  public void testDecompress2DOriginalOdd() {
    float[][] compressed = new float[][]
        {
            {11.25f, 2.25f, -1.5f / HaarWaveletCompressor.sqrt2, 9 / HaarWaveletCompressor.sqrt2},
            {-0.75f, -0.75f, -0.5f / HaarWaveletCompressor.sqrt2, 0},
            {-4.5f / HaarWaveletCompressor.sqrt2, -1.5f / HaarWaveletCompressor.sqrt2, 0, -1.5f},
            {12 / HaarWaveletCompressor.sqrt2, 3 / HaarWaveletCompressor.sqrt2, -0.5f, 4.5f}
        };
    float[][] decompressed = instance.decompress2D(compressed);
    float[][] originalMatrix = new float[][]{
        {1, 2, 3, 0},
        {4, 5, 6, 0},
        {7, 8, 9, 0},
        {0, 0, 0, 0}
    };
    assertTrue(if2DArraysEqual(originalMatrix, decompressed, 0.001f));
  }

  @Test
  public void testDecompress2DRateOne() {
    float[][] compressed = new float[][]
        {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
        };
    float[][] decompressed = instance.decompress2D(compressed);
    float[][] originalMatrix = new float[][]{
        {0, 0, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
    };
    assertTrue(if2DArraysEqual(originalMatrix, decompressed, 0.001f));
  }

  @Test
  public void testDecompress2DRateHalf() {
    float[][] compressed = new float[][]{
        {34, 0, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
    };
    float[][] decompressed = instance.decompress2D(compressed);
    float[][] originalMatrix = new float[][]{
        {8.5f, 8.5f, 8.5f, 8.5f},
        {8.5f, 8.5f, 8.5f, 8.5f},
        {8.5f, 8.5f, 8.5f, 8.5f},
        {8.5f, 8.5f, 8.5f, 8.5f}
    };
    System.out.println(Arrays.deepToString(decompressed));
    assertTrue(if2DArraysEqual(originalMatrix, decompressed, 0.001f));
  }

  //-----------------1D compress-----------------------------

  @Test(expected = IllegalArgumentException.class)
  public void testCompressEmpty() {
    instance.compress(new float[]{}, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCompressRatioNeg() {
    instance.compress(new float[]{1, 2}, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCompressRatioTooLarge() {
    instance.compress(new float[]{1, 2}, 2);
  }

  @Test
  public void testCompressOddElement() {
    float[] originalArr = new float[]{1, 3, 2};
    float[] result = instance.compress(originalArr, 0.5f);
    System.out.println(Arrays.toString(result));
    assertArrayEquals(new float[]{3, 0, 0, 0}, result, 0.001f);
    result = instance.compress(originalArr, 0f);
    assertArrayEquals(new float[]{3, 1, -HaarWaveletCompressor.sqrt2, HaarWaveletCompressor.sqrt2},
        result, 0.001f);
  }

  @Test
  public void testCompressEvenElement() {
    float[] originalArr = new float[]{1, 3, 2, 4};
    float targetRatio = 0.5f;
    float[] result = instance.compress(originalArr, targetRatio);
    assertArrayEquals(new float[]{5, 0, 0, 0}, result, 0.001f);
  }

  @Test
  public void testCompressEvenElementNotPowOfTwo() {
    float[] originalArr = new float[]{1, 3, 2, 4, 5, 7};
    float[] result = instance.compress(originalArr, 0f);
    assertArrayEquals(
        new float[]{11 / HaarWaveletCompressor.sqrt2, -1 / HaarWaveletCompressor.sqrt2,
            -1, 6, -HaarWaveletCompressor.sqrt2, -HaarWaveletCompressor.sqrt2,
            -HaarWaveletCompressor.sqrt2, 0}, result, 0.001f);
  }

  @Test
  public void testCompressRatioZero() {
    float[] originalArr = new float[]{1, 3, 2, 4};
    float targetRatio = 0f;
    float[] result = instance.compress(originalArr, targetRatio);
    assertArrayEquals(
        new float[]{5, -1, -HaarWaveletCompressor.sqrt2, -HaarWaveletCompressor.sqrt2}, result,
        0.001f);
  }

  //ratio too big that actually all will be compressed
  @Test
  public void testCompressRatioBig() {
    float[] originalArr = new float[]{1, 3, 2, 4};
    float targetRatio = 0.9f;
    float[] result = instance.compress(originalArr, targetRatio);
    assertArrayEquals(new float[]{0, 0, 0, 0}, result, 0.001f);
  }

  @Test
  public void testCompressRatioSmall() {
    float[] originalArr = new float[]{1, 3, 2, 4};
    float targetRatio = 0.1f;
    float[] result = instance.compress(originalArr, targetRatio);
    assertArrayEquals(new float[]{5, 0, 0, 0}, result, 0.001f);
  }

  @Test
  public void testCompressRatioOne() {
    float[] originalArr = new float[]{1, 3, 2, 4};
    float targetRatio = 1f;
    float[] result = instance.compress(originalArr, targetRatio);
    assertArrayEquals(new float[]{0, 0, 0, 0}, result, 0.001f);
  }

  //-----------------1D decompress-----------------------------

  @Test(expected = IllegalArgumentException.class)
  public void testDecompressEmpty() {
    instance.decompress(new float[]{});
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDecompressSizeIllegal() {
    instance.decompress(new float[]{1, 2, 3});
  }

  @Test
  public void testDecompress() {
    float[] result = instance.decompress(new float[]{5, -1, 0, 0});
    assertArrayEquals(new float[]{2, 2, 3, 3}, result, 0.001f);
  }

  //decompress from a zero compress ratio compression
  @Test
  public void testDecompressRatioZero() {
    float[] result = instance.decompress(new float[]{5, -1, -HaarWaveletCompressor.sqrt2,
        -HaarWaveletCompressor.sqrt2});
    assertArrayEquals(new float[]{1, 3, 2, 4}, result, 0.001f);
    System.out.println(Arrays.toString(result));
  }

  @Test
  public void testDecompressRatioOne() {
    float[] result = instance.decompress(new float[]{0, 0, 0, 0});
    assertArrayEquals(new float[]{0, 0, 0, 0}, result, 0.001f);
  }

}