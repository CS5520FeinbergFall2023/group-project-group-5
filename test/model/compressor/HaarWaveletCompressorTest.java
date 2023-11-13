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

  private boolean if3DArraysEqual(float[][][] expected, float[][][] actual, float delta) {
    if (expected.length != actual.length || expected[0].length != actual[0].length) {
      return false;
    }
    for (int i = 0; i < expected.length; i++) {
      assertTrue(if2DArraysEqual(expected[i], actual[i], delta));
    }
    return true;
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

  //-----------------3D compress-----------------------------
  @Test(expected = IllegalArgumentException.class)
  public void testCompress3DEmpty() {
    instance.compress(new float[0][][], 0.5f);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCompress3DRatioTooBig() {
    instance.compress(new float[][]{
        {1, 2, 3},
        {4, 5, 6},
        {7, 8, 9}
    }, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCompress3DRatioTooSmall() {
    instance.compress(new float[][]{
        {1, 2, 3},
        {4, 5, 6},
        {7, 8, 9}
    }, -2);
  }

  //one channel
  @Test
  public void testCompress3DOne() {
    float[][][] originalMatrix = new float[][][]{{
        {1, 2},
        {3, 4},
    }
    };
    float targetRatio = 0f;
    float[][][] result = instance.compress(originalMatrix, targetRatio);
    float[][][] expected = new float[][][]{{{5, -1}, {-2, 0}}};
    assertTrue(if3DArraysEqual(expected, result, 0.001f));
  }

  @Test
  public void testCompress3D() {
    float[][][] originalMatrix = new float[][][]{
        {
            {1, 2},
            {3, 4},
        },
        {
            {5, 6},
            {7, 8},
        }
    };
    float targetRatio = 0f;
    float[][][] result = instance.compress(originalMatrix, targetRatio);
    float[][][] expected = new float[][][]{{{5, -1}, {-2, 0}}, {{13, -1}, {-2, 0}}};
    assertTrue(if3DArraysEqual(expected, result, 0.001f));
  }

  @Test
  public void testCompress3DRatio1() {
    float[][][] originalMatrix = new float[][][]{
        {
            {1, 2},
            {3, 4},
        },
        {
            {5, 6},
            {7, 8},
        }
    };
    float targetRatio = 1f;
    float[][][] result = instance.compress(originalMatrix, targetRatio);
    float[][][] expected = new float[][][]{{{0, 0}, {0, 0}}, {{0, 0}, {0, 0}}};
    assertTrue(if3DArraysEqual(expected, result, 0.001f));
  }

  @Test
  public void testCompress3DRatio05() {
    float[][][] originalMatrix = new float[][][]{
        {
            {1, 2},
            {3, 4},
        },
        {
            {5, 6},
            {7, 8},
        }
    };
    float targetRatio = 0.5f;
    float[][][] result = instance.compress(originalMatrix, targetRatio);
    float[][][] expected = new float[][][]{{{5, 0}, {0, 0}}, {{13, 0}, {0, 0}}};
    assertTrue(if3DArraysEqual(expected, result, 0.001f));
  }

  @Test
  public void testCompress3DOdd() {
    float[][][] originalMatrix = new float[][][]{
        {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        },
        {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        }
    };
    float targetRatio = 0f;
    float[][][] result = instance.compress(originalMatrix, targetRatio);
    float[][][] expected = new float[][][]{{{11.25f, 2.25f, -1, 4.5f},
        {-0.75f, -0.75f, -0.5f, 4.5f},
        {-3f, -1.5f, 0, -1.5f},
        {7.5f, 4.5f, -0.5f, 4.5f}},
        {{11.25f, 2.25f, -1, 4.5f},
            {-0.75f, -0.75f, -0.5f, 4.5f},
            {-3f, -1.5f, 0, -1.5f},
            {7.5f, 4.5f, -0.5f, 4.5f}}};
    assertTrue(if3DArraysEqual(expected, result, 0.001f));
  }

  @Test
  public void testCompress3DOddWidth() {
    float[][][] originalMatrix = new float[][][]{
        {
            {1, 2, 3},
            {4, 5, 6}
        },
        {
            {0.1f, 0.2f, 0.3f},
            {0.4f, 0.5f, 0.6f}
        }
    };
    float targetRatio = 0f;
    float[][][] result = instance.compress(originalMatrix, targetRatio);
    float[][][] expected = new float[][][]{{{5.25f, 0.75f, -1f, 4.5f},
        {5.25f, 0.75f, 0, 0},
        {-3f, -1.5f, 0f, -1.5f},
        {0f, 0f, 0f, 0f}},
        {{0.525f, 0.075f, -0.1f, 0.45f},
            {0.525f, 0.075f, 0, 0},
            {-0.3f, -0.15f, 0f, -0.15f},
            {0f, 0f, 0f, 0f}}};
    assertTrue(if3DArraysEqual(expected, result, 0.001f));
  }

  @Test
  public void testCompress3DOddHeight() {
    float[][][] originalMatrix = new float[][][]{
        {
            {1, 2},
            {4, 5},
            {7, 8}
        },
        {
            {10, 20},
            {40, 50},
            {70, 80}
        }
    };
    float targetRatio = 0f;
    float[][][] result = instance.compress(originalMatrix, targetRatio);
    float[][][] expected = new float[][][]{{{6.75f, 6.75f, -1, 0},
        {-0.75f, -0.75f, -0.5f, 0},
        {-3, 0, 0, 0},
        {7.5f, 0, -0.5f, 0}},
        {{67.5f, 67.5f, -10, 0},
            {-7.5f, -7.5f, -5f, 0},
            {-30, 0, 0, 0},
            {75f, 0, -5f, 0}}};
    assertTrue(if3DArraysEqual(expected, result, 0.001f));
  }

  @Test
  public void testCompress3DEvenNotPower() {
    float[][][] originalMatrix = new float[][][]{
        {
            {1, 2},
            {3, 4},
            {5, 6},
            {7, 8},
            {9, 10},
            {11, 12}
        },
        {
            {1, 2, 3, 4, 5, 6},
            {7, 8, 9, 10, 11, 12},
        }
    };
    float targetRatio = 0f;
    float[][][] result = instance.compress(originalMatrix, targetRatio);
    float[][][] expected = new float[][][]{{{9.75f, 9.75f, 9, 0, -1, 0, 0, 0},
        {-0.75f, -0.75f, 10.5f, 0, -1, 0, 0, 0},
        {-4, 0, -4, 0, -1, 0, 0, 0},
        {10.5f, 0, 10.5f, 0, 0, 0, 0, 0},
        {-2, 0, 0, 0, 0, 0, 0, 0},
        {-2, 0, 0, 0, 0, 0, 0, 0},
        {-2, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0}},
        {{9.75f, 1.25f, -2, 8.5f, -1, -1, -1, 0},
            {9.75f, 1.25f, 0, 0, 0, 0, 0, 0},
            {11, 8.5f, -2, 8.5f, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {-6, -6, -6, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}}};
    assertTrue(if3DArraysEqual(expected, result, 0.001f));
  }

  //-----------------3D decompress---------------------------
  @Test(expected = IllegalArgumentException.class)
  public void testDecompress3DEmpty() {
    instance.decompress(new float[0][][]);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDecompress3DShapeIllegalOne() {
    instance.decompress(new float[][][]{
        {{9.75f, 9.75f, 9, 0, -1, 0, 0, 0},
            {-0.75f, -0.75f, 10.5f, 0, -1, 0, 0, 0},
            {-4, 0, -4, 0, -1, 0, 0, 0}}
    });
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDecompress3DShapeIllegal() {
    instance.decompress(new float[][][]{
        {{9.75f, 9.75f, 9, 0, -1, 0, 0, 0},
            {-0.75f, -0.75f, 10.5f, 0, -1, 0, 0, 0},
            {-4, 0, -4, 0, -1, 0, 0, 0}},
        {{11.25f, 2.25f, -1, 4.5f},
            {-0.75f, -0.75f, -0.5f, 4.5f},
            {-3f, -1.5f, 0, -1.5f},
            {7.5f, 4.5f, -0.5f, 4.5f}}
    });
  }

  @Test
  public void testDecompress3DRatio1() {
    float[][][] originalMatrix = new float[][][]{
        {
            {0, 0},
            {0, 0},
        },
        {
            {0, 0},
            {0, 0},
        }
    };
    float[][][] compressed = new float[][][]{{{0, 0}, {0, 0}}, {{0, 0}, {0, 0}}};
    assertTrue(if3DArraysEqual(originalMatrix, instance.decompress(compressed), 0.001f));
  }

  @Test
  public void testDecompress3DRatio05() {
    float[][][] originalMatrix = new float[][][]{
        {
            {2.5f, 2.5f},
            {2.5f, 2.5f},
        },
        {
            {6.5f, 6.5f},
            {6.5f, 6.5f},
        }
    };

    float[][][] compressed = new float[][][]{{{5, 0}, {0, 0}}, {{13, 0}, {0, 0}}};
    assertTrue(if3DArraysEqual(originalMatrix, instance.decompress(compressed), 0.001f));
  }

  @Test
  public void testDecompress3DOdd() {
    float[][][] originalMatrix = new float[][][]{
        {
            {1, 2, 3, 0},
            {4, 5, 6, 0},
            {7, 8, 9, 0},
            {0, 0, 0, 0}
        },
        {
            {1, 2, 3, 0},
            {4, 5, 6, 0},
            {7, 8, 9, 0},
            {0, 0, 0, 0}
        }
    };

    float[][][] compressed = new float[][][]{{{11.25f, 2.25f, -1, 4.5f},
        {-0.75f, -0.75f, -0.5f, 4.5f},
        {-3f, -1.5f, 0, -1.5f},
        {7.5f, 4.5f, -0.5f, 4.5f}},
        {{11.25f, 2.25f, -1, 4.5f},
            {-0.75f, -0.75f, -0.5f, 4.5f},
            {-3f, -1.5f, 0, -1.5f},
            {7.5f, 4.5f, -0.5f, 4.5f}}};

    assertTrue(if3DArraysEqual(originalMatrix, instance.decompress(compressed), 0.001f));

  }

  @Test
  public void testDecompress3DOddWidth() {
    float[][][] originalMatrix = new float[][][]{
        {
            {1, 2, 3, 0},
            {4, 5, 6, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
        },
        {
            {0.1f, 0.2f, 0.3f, 0},
            {0.4f, 0.5f, 0.6f, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
        }
    };

    float[][][] compressed = new float[][][]{{{5.25f, 0.75f, -1f, 4.5f},
        {5.25f, 0.75f, 0, 0},
        {-3f, -1.5f, 0f, -1.5f},
        {0f, 0f, 0f, 0f}},
        {{0.525f, 0.075f, -0.1f, 0.45f},
            {0.525f, 0.075f, 0, 0},
            {-0.3f, -0.15f, 0f, -0.15f},
            {0f, 0f, 0f, 0f}}};
    assertTrue(if3DArraysEqual(originalMatrix, instance.decompress(compressed), 0.001f));
  }

  @Test
  public void testDecompress3DOddHeight() {
    float[][][] originalMatrix = new float[][][]{
        {
            {1, 2, 0, 0},
            {4, 5, 0, 0},
            {7, 8, 0, 0},
            {0, 0, 0, 0}
        },
        {
            {10, 20, 0, 0},
            {40, 50, 0, 0},
            {70, 80, 0, 0},
            {0, 0, 0, 0}
        }
    };

    float[][][] compressed = new float[][][]{{{6.75f, 6.75f, -1, 0},
        {-0.75f, -0.75f, -0.5f, 0},
        {-3, 0, 0, 0},
        {7.5f, 0, -0.5f, 0}},
        {{67.5f, 67.5f, -10, 0},
            {-7.5f, -7.5f, -5f, 0},
            {-30, 0, 0, 0},
            {75f, 0, -5f, 0}}};
    assertTrue(if3DArraysEqual(originalMatrix, instance.decompress(compressed), 0.001f));
  }

  @Test
  public void testDecompress3DEvenNotPower() {
    float[][][] originalMatrix = new float[][][]{
        {
            {1, 2, 0, 0, 0, 0, 0, 0},
            {3, 4, 0, 0, 0, 0, 0, 0},
            {5, 6, 0, 0, 0, 0, 0, 0},
            {7, 8, 0, 0, 0, 0, 0, 0},
            {9, 10, 0, 0, 0, 0, 0, 0},
            {11, 12, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}
        },
        {
            {1, 2, 3, 4, 5, 6, 0, 0},
            {7, 8, 9, 10, 11, 12, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}
        }
    };

    float[][][] compressed = new float[][][]{{{9.75f, 9.75f, 9, 0, -1, 0, 0, 0},
        {-0.75f, -0.75f, 10.5f, 0, -1, 0, 0, 0},
        {-4, 0, -4, 0, -1, 0, 0, 0},
        {10.5f, 0, 10.5f, 0, 0, 0, 0, 0},
        {-2, 0, 0, 0, 0, 0, 0, 0},
        {-2, 0, 0, 0, 0, 0, 0, 0},
        {-2, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0}},
        {{9.75f, 1.25f, -2, 8.5f, -1, -1, -1, 0},
            {9.75f, 1.25f, 0, 0, 0, 0, 0, 0},
            {11, 8.5f, -2, 8.5f, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {-6, -6, -6, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}}};
    assertTrue(if3DArraysEqual(originalMatrix, instance.decompress(compressed), 0.001f));
  }

  @Test
  public void testDecompress3D() {
    float[][][] compressed = new float[][][]{{{5, -1}, {-2, 0}}, {{13, -1}, {-2, 0}}};
    float[][][] originalMatrix = new float[][][]{
        {
            {1, 2},
            {3, 4},
        },
        {
            {5, 6},
            {7, 8},
        }
    };
    assertTrue(if3DArraysEqual(originalMatrix, instance.decompress(compressed), 0.001f));
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
    float[][] result = instance.compress(originalMatrix, targetRatio);
    float[][] expected = new float[][]
        {
            {11.25f, 2.25f, -1, 4.5f},
            {-0.75f, -0.75f, -0.5f, 4.5f},
            {-3f, -1.5f, 0, -1.5f},
            {7.5f, 4.5f, -0.5f, 4.5f}
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
    float[][] result = instance.compress(originalMatrix, targetRatio);

    float[][] expected = new float[][]{
        {5.25f, 0.75f, -1f, 4.5f},
        {5.25f, 0.75f, 0, 0},
        {-3f, -1.5f, 0f, -1.5f},
        {0f, 0f, 0f, 0f}
    };
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
    float[][] result = instance.compress(originalMatrix, targetRatio);
    float[][] expected = new float[][]
        {
            {6.75f, 6.75f, -1, 0},
            {-0.75f, -0.75f, -0.5f, 0},
            {-3, 0, 0, 0},
            {7.5f, 0, -0.5f, 0}
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
    float[][] result = instance.compress(originalMatrix, targetRatio);

    float[][] expected = new float[][]{
        {34, -4, -1, -1},
        {-16, 0, -1, -1},
        {-4, -4, 0, 0},
        {-4, -4, 0, 0}
    };
    assertTrue(if2DArraysEqual(expected, result, 0.001f));

    result = instance.compress(originalMatrix, 1);
    expected = new float[][]{
        {0, 0, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
    };
    assertTrue(if2DArraysEqual(expected, result, 0.001f));

    result = instance.compress(originalMatrix, 0.2f);
    expected = new float[][]{
        {34, -4, 0, 0},
        {-16, 0, 0, 0},
        {-4, -4, 0, 0},
        {-4, -4, 0, 0}
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
    float[][] result = instance.compress(originalMatrix, targetRatio);
    float[][] expected = new float[][]{
        {9.75f, 9.75f, 9, 0, -1, 0, 0, 0},
        {-0.75f, -0.75f, 10.5f, 0, -1, 0, 0, 0},
        {-4, 0, -4, 0, -1, 0, 0, 0},
        {10.5f, 0, 10.5f, 0, 0, 0, 0, 0},
        {-2, 0, 0, 0, 0, 0, 0, 0},
        {-2, 0, 0, 0, 0, 0, 0, 0},
        {-2, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0}
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
    float[][] result = instance.compress(originalMatrix, targetRatio);
    float[][] expected = new float[][]{
        {9.75f, 1.25f, -2, 8.5f, -1, -1, -1, 0},
        {9.75f, 1.25f, 0, 0, 0, 0, 0, 0},
        {11, 8.5f, -2, 8.5f, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {-6, -6, -6, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0}
    };
    assertTrue(if2DArraysEqual(expected, result, 0.001f));
  }


  //-----------------2D decompress-----------------------------
  @Test(expected = IllegalArgumentException.class)
  public void testDecompressSizeZero() {
    instance.decompress(new float[][]{});
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDecompressSizeOddWidth() {
    instance.decompress(new float[][]{
        {1, 2, 3},
        {4, 5, 6}
    });
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDecompressSizeOddHeight() {
    instance.decompress(new float[][]{
        {1, 2},
        {4, 5},
        {7, 8}
    });
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDecompressSizeOdd() {
    instance.decompress(new float[][]{
        {1, 2, 3},
        {4, 5, 6},
        {7, 8, 9}
    });
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDecompressSizeEvenNotPow() {
    instance.decompress(new float[][]{
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
        {34, -4, -1, -1},
        {-16, 0, -1, -1},
        {-4, -4, 0, 0},
        {-4, -4, 0, 0}
    };
    float[][] decompressed = instance.decompress(compressed);

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
        {9.75f, 9.75f, 9, 0, -1, 0, 0, 0},
        {-0.75f, -0.75f, 10.5f, 0, -1, 0, 0, 0},
        {-4, 0, -4, 0, -1, 0, 0, 0},
        {10.5f, 0, 10.5f, 0, 0, 0, 0, 0},
        {-2, 0, 0, 0, 0, 0, 0, 0},
        {-2, 0, 0, 0, 0, 0, 0, 0},
        {-2, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
    };
    float[][] originalMatrix = new float[][]{
        {1, 2, 0, 0, 0, 0, 0, 0},
        {3, 4, 0, 0, 0, 0, 0, 0},
        {5, 6, 0, 0, 0, 0, 0, 0},
        {7, 8, 0, 0, 0, 0, 0, 0},
        {9, 10, 0, 0, 0, 0, 0, 0},
        {11, 12, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0}
    };
    float[][] decompressed = instance.decompress(compressed);

    assertTrue(if2DArraysEqual(originalMatrix, decompressed, 0.001f));
  }

  @Test
  public void testDecompress2DOriginalEvenNotPowWidth() {
    float[][] compressed = new float[][]{
        {9.75f, 1.25f, -2, 8.5f, -1, -1, -1, 0},
        {9.75f, 1.25f, 0, 0, 0, 0, 0, 0},
        {11, 8.5f, -2, 8.5f, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {-6, -6, -6, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0}
    };
    float[][] originalMatrix = new float[][]{
        {1, 2, 3, 4, 5, 6, 0, 0},
        {7, 8, 9, 10, 11, 12, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0}
    };
    float[][] decompressed = instance.decompress(compressed);
    assertTrue(if2DArraysEqual(originalMatrix, decompressed, 0.001f));
  }

  @Test
  public void testDecompress2DOriginalOddHeight() {
    float[][] compressed = instance.compress(new float[][]{
        {1, 2},
        {4, 5},
        {7, 8},
        {0, 0}
    }, 0);
    float[][] originalMatrix = new float[][]{
        {1, 2, 0, 0},
        {4, 5, 0, 0},
        {7, 8, 0, 0},
        {0, 0, 0, 0}
    };
    float[][] decompressed = instance.decompress(compressed);
    assertTrue(if2DArraysEqual(originalMatrix, decompressed, 0.001f));
  }

  @Test
  public void testDecompress2DOriginalOddWidth() {
    float[][] compressed = instance.compress(new float[][]{
        {1, 2, 3, 0},
        {4, 5, 6, 0}
    }, 0);
    float[][] originalMatrix = new float[][]{
        {1, 2, 3, 0},
        {4, 5, 6, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
    };
    float[][] decompressed = instance.decompress(compressed);
    assertTrue(if2DArraysEqual(originalMatrix, decompressed, 0.001f));
  }

  @Test
  public void testDecompress2DOriginalOdd() {
    float[][] compressed = instance.compress(new float[][]{
        {1, 2, 3},
        {4, 5, 6},
        {7, 8, 9}
    }, 0);
    float[][] decompressed = instance.decompress(compressed);
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
    float[][] decompressed = instance.decompress(compressed);
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
    float[][] decompressed = instance.decompress(compressed);
    float[][] originalMatrix = new float[][]{
        {8.5f, 8.5f, 8.5f, 8.5f},
        {8.5f, 8.5f, 8.5f, 8.5f},
        {8.5f, 8.5f, 8.5f, 8.5f},
        {8.5f, 8.5f, 8.5f, 8.5f}
    };
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
  }

  @Test
  public void testDecompressRatioOne() {
    float[] result = instance.decompress(new float[]{0, 0, 0, 0});
    assertArrayEquals(new float[]{0, 0, 0, 0}, result, 0.001f);
  }

}