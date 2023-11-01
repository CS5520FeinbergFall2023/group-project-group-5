package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class tests the RGB Pixel class.
 */
public class RGBPixelTest {
  RGBPixel whitePixel;
  RGBPixel blackPixel;
  RGBPixel redPixel;
  RGBPixel greenPixel;
  RGBPixel bluePixel;
  RGBPixel brownPixel;
  RGBPixel purplePixel;
  RGBPixel yellowPixel;
  RGBPixel threeColorRedMaxPixel;
  RGBPixel threeColorGreenMaxPixel;
  RGBPixel threeColorBlueMaxPixel;

  RGBPixel threeColorSamePixel;

  RGBPixel[] allTestPixels;

  @Before
  public void setUp() {
    whitePixel = new RGBPixel(0, 0, 0);
    blackPixel = new RGBPixel(255, 255, 255);
    redPixel = new RGBPixel(255, 0, 0);
    greenPixel = new RGBPixel(0, 255, 0);
    bluePixel = new RGBPixel(0, 0, 255);
    brownPixel = new RGBPixel(255, 255, 0);
    purplePixel = new RGBPixel(255, 0, 255);
    yellowPixel = new RGBPixel(0, 255, 255);
    threeColorSamePixel = new RGBPixel(100, 100, 100);
    threeColorRedMaxPixel = new RGBPixel(100, 50, 50);
    threeColorGreenMaxPixel = new RGBPixel(90, 100, 20);
    threeColorBlueMaxPixel = new RGBPixel(30, 100, 200);
    allTestPixels =
        new RGBPixel[]{whitePixel, blackPixel, redPixel, greenPixel, bluePixel, brownPixel,
            purplePixel, yellowPixel, threeColorSamePixel, threeColorRedMaxPixel,
            threeColorGreenMaxPixel
            , threeColorBlueMaxPixel};
  }

  @Test
  public void testConstructorTooLarge() {
    RGBPixel pixel = new RGBPixel(256, 256, 256);
    assertEquals(255, pixel.getRed());
    assertEquals(255, pixel.getGreen());
    assertEquals(255, pixel.getBlue());
  }

  @Test
  public void testConstructorTooSmall() {
    RGBPixel pixel = new RGBPixel(-1, -2, -10);
    assertEquals(0, pixel.getRed());
    assertEquals(0, pixel.getGreen());
    assertEquals(0, pixel.getBlue());
  }

  @Test
  public void testConstructor() {
    RGBPixel pixel = new RGBPixel(0, 128, 255);
    assertEquals(0, pixel.getRed());
    assertEquals(128, pixel.getGreen());
    assertEquals(255, pixel.getBlue());
  }

  @Test
  public void containsChannel() {
    for (Pixel testPixel : allTestPixels) {
      assertTrue(testPixel.containsChannel(Channel.RED));
      assertTrue(testPixel.containsChannel(Channel.GREEN));
      assertTrue(testPixel.containsChannel(Channel.BLUE));
    }
  }

  @Test
  public void getChannelComponentRed() {
    RGBPixel expected = new RGBPixel(0, 0, 0);
    assertEquals(expected, whitePixel.getChannelComponent(Channel.RED));
    expected = new RGBPixel(255, 0, 0);
    assertEquals(expected, blackPixel.getChannelComponent(Channel.RED));
    expected = new RGBPixel(255, 0, 0);
    assertEquals(expected, redPixel.getChannelComponent(Channel.RED));
    expected = new RGBPixel(0, 0, 0);
    assertEquals(expected, greenPixel.getChannelComponent(Channel.RED));
    expected = new RGBPixel(0, 0, 0);
    assertEquals(expected, bluePixel.getChannelComponent(Channel.RED));
    expected = new RGBPixel(255, 0, 0);
    assertEquals(expected, brownPixel.getChannelComponent(Channel.RED));
    expected = new RGBPixel(255, 0, 0);
    assertEquals(expected, purplePixel.getChannelComponent(Channel.RED));
    expected = new RGBPixel(0, 0, 0);
    assertEquals(expected, yellowPixel.getChannelComponent(Channel.RED));
    expected = new RGBPixel(100, 0, 0);
    assertEquals(expected, threeColorSamePixel.getChannelComponent(Channel.RED));
    expected = new RGBPixel(100, 0, 0);
    assertEquals(expected, threeColorRedMaxPixel.getChannelComponent(Channel.RED));
    expected = new RGBPixel(90, 0, 0);
    assertEquals(expected, threeColorGreenMaxPixel.getChannelComponent(Channel.RED));
    expected = new RGBPixel(30, 0, 0);
    assertEquals(expected, threeColorBlueMaxPixel.getChannelComponent(Channel.RED));
  }

  @Test
  public void getChannelComponentGreen() {
    RGBPixel expected = new RGBPixel(0, 0, 0);
    assertEquals(expected, whitePixel.getChannelComponent(Channel.GREEN));
    expected = new RGBPixel(0, 255, 0);
    assertEquals(expected, blackPixel.getChannelComponent(Channel.GREEN));
    expected = new RGBPixel(0, 0, 0);
    assertEquals(expected, redPixel.getChannelComponent(Channel.GREEN));
    expected = new RGBPixel(0, 255, 0);
    assertEquals(expected, greenPixel.getChannelComponent(Channel.GREEN));
    expected = new RGBPixel(0, 0, 0);
    assertEquals(expected, bluePixel.getChannelComponent(Channel.GREEN));
    expected = new RGBPixel(0, 255, 0);
    assertEquals(expected, brownPixel.getChannelComponent(Channel.GREEN));
    expected = new RGBPixel(0, 0, 0);
    assertEquals(expected, purplePixel.getChannelComponent(Channel.GREEN));
    expected = new RGBPixel(0, 255, 0);
    assertEquals(expected, yellowPixel.getChannelComponent(Channel.GREEN));
    expected = new RGBPixel(0, 100, 0);
    assertEquals(expected, threeColorSamePixel.getChannelComponent(Channel.GREEN));
    expected = new RGBPixel(0, 50, 0);
    assertEquals(expected, threeColorRedMaxPixel.getChannelComponent(Channel.GREEN));
    expected = new RGBPixel(0, 100, 0);
    assertEquals(expected, threeColorGreenMaxPixel.getChannelComponent(Channel.GREEN));
    expected = new RGBPixel(0, 100, 0);
    assertEquals(expected, threeColorBlueMaxPixel.getChannelComponent(Channel.GREEN));
  }

  @Test
  public void getChannelComponentBlue() {
    RGBPixel expected = new RGBPixel(0, 0, 0);
    assertEquals(expected, whitePixel.getChannelComponent(Channel.BLUE));
    expected = new RGBPixel(0, 0, 255);
    assertEquals(expected, blackPixel.getChannelComponent(Channel.BLUE));
    expected = new RGBPixel(0, 0, 0);
    assertEquals(expected, redPixel.getChannelComponent(Channel.BLUE));
    expected = new RGBPixel(0, 0, 0);
    assertEquals(expected, greenPixel.getChannelComponent(Channel.BLUE));
    expected = new RGBPixel(0, 0, 255);
    assertEquals(expected, bluePixel.getChannelComponent(Channel.BLUE));
    expected = new RGBPixel(0, 0, 0);
    assertEquals(expected, brownPixel.getChannelComponent(Channel.BLUE));
    expected = new RGBPixel(0, 0, 255);
    assertEquals(expected, purplePixel.getChannelComponent(Channel.BLUE));
    expected = new RGBPixel(0, 0, 255);
    assertEquals(expected, yellowPixel.getChannelComponent(Channel.BLUE));
    expected = new RGBPixel(0, 0, 100);
    assertEquals(expected, threeColorSamePixel.getChannelComponent(Channel.BLUE));
    expected = new RGBPixel(0, 0, 50);
    assertEquals(expected, threeColorRedMaxPixel.getChannelComponent(Channel.BLUE));
    expected = new RGBPixel(0, 0, 20);
    assertEquals(expected, threeColorGreenMaxPixel.getChannelComponent(Channel.BLUE));
    expected = new RGBPixel(0, 0, 200);
    assertEquals(expected, threeColorBlueMaxPixel.getChannelComponent(Channel.BLUE));
  }


  @Test(expected = IllegalArgumentException.class)
  public void linearTransformationHeightNotMatch() {
    whitePixel.linearTransformation(new float[][]{
        {1, 1, 1},
        {2, 2, 2}
    });
  }

  @Test(expected = IllegalArgumentException.class)
  public void linearTransformationWidthNotMatch() {
    whitePixel.linearTransformation(new float[][]{
        {1, 1},
        {2, 2},
        {3, 3}
    });
  }

  @Test
  public void linearTransformation() {
    float[][] identityMatrix = new float[][]{
        {1, 0, 0},
        {0, 1, 0},
        {0, 0, 1}
    };
    for (RGBPixel testPixel : allTestPixels) {
      RGBPixel result = testPixel.linearTransformation(identityMatrix);
      assertEquals(testPixel.getRed(), result.getRed());
      assertEquals(testPixel.getGreen(), result.getGreen());
      assertEquals(testPixel.getBlue(), result.getBlue());
    }

    float[][] matrix = new float[][]{
        {0, 0, 0},
        {-0.1f, -0.2f, -0.3f},
        {0.3f, 0.2f, 0.1f}
    };
    for (RGBPixel testPixel : allTestPixels) {
      RGBPixel result = testPixel.linearTransformation(matrix);
      assertEquals(0, result.getRed());
      assertEquals(0, result.getGreen());
      assertEquals(Math.round(
              0.3 * testPixel.getRed() + 0.2 * testPixel.getGreen() + 0.1 * testPixel.getBlue()),
          result.getBlue());
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void additionSizeTooSmall() {
    whitePixel.addition(new float[]{0, 0});
  }

  @Test(expected = IllegalArgumentException.class)
  public void additionSizeTooBig() {
    whitePixel.addition(new float[]{1, 1, 1, 1});
  }


  @Test
  public void testPixelAddition() {
    RGBPixel result = whitePixel.addition(whitePixel);
    assertEquals(0, result.getRed());
    assertEquals(0, result.getGreen());
    assertEquals(0, result.getBlue());
    result = blackPixel.addition(blackPixel);
    assertEquals(255, result.getRed());
    assertEquals(255, result.getGreen());
    assertEquals(255, result.getBlue());
    result = redPixel.addition(greenPixel);
    assertEquals(255, result.getRed());
    assertEquals(255, result.getGreen());
    assertEquals(0, result.getBlue());
    result = threeColorSamePixel.addition(threeColorBlueMaxPixel);
    assertEquals(130, result.getRed());
    assertEquals(200, result.getGreen());
    assertEquals(255, result.getBlue());
  }

  @Test
  public void testArrayAddition() {
    float[] array = new float[]{-2, 0, 10};
    RGBPixel result = whitePixel.addition(array);
    assertEquals(0, result.getRed());
    assertEquals(0, result.getGreen());
    assertEquals(10, result.getBlue());

    result = blackPixel.addition(array);
    assertEquals(253, result.getRed());
    assertEquals(255, result.getGreen());
    assertEquals(255, result.getBlue());

    result = threeColorSamePixel.addition(array);
    assertEquals(98, result.getRed());
    assertEquals(100, result.getGreen());
    assertEquals(110, result.getBlue());
  }

  @Test
  public void max() {
    assertEquals(new RGBPixel(0, 0, 0), whitePixel.max());
    assertEquals(new RGBPixel(255, 255, 255), blackPixel.max());
    assertEquals(new RGBPixel(255, 255, 255), redPixel.max());
    assertEquals(new RGBPixel(255, 255, 255), greenPixel.max());
    assertEquals(new RGBPixel(255, 255, 255), bluePixel.max());
    assertEquals(new RGBPixel(255, 255, 255), brownPixel.max());
    assertEquals(new RGBPixel(255, 255, 255), purplePixel.max());
    assertEquals(new RGBPixel(255, 255, 255), yellowPixel.max());
    assertEquals(new RGBPixel(100, 100, 100), threeColorSamePixel.max());
    assertEquals(new RGBPixel(100, 100, 100), threeColorRedMaxPixel.max());
    assertEquals(new RGBPixel(100, 100, 100), threeColorGreenMaxPixel.max());
    assertEquals(new RGBPixel(200, 200, 200), threeColorBlueMaxPixel.max());
  }

  @Test
  public void avg() {
    assertEquals(new RGBPixel(0, 0, 0), whitePixel.avg());
    assertEquals(new RGBPixel(255, 255, 255), blackPixel.avg());
    assertEquals(new RGBPixel(85, 85, 85), redPixel.avg());
    assertEquals(new RGBPixel(85, 85, 85), greenPixel.avg());
    assertEquals(new RGBPixel(85, 85, 85), bluePixel.avg());
    assertEquals(new RGBPixel(170, 170, 170), brownPixel.avg());
    assertEquals(new RGBPixel(170, 170, 170), purplePixel.avg());
    assertEquals(new RGBPixel(170, 170, 170), yellowPixel.avg());
    assertEquals(new RGBPixel(100, 100, 100), threeColorSamePixel.avg());
    assertEquals(new RGBPixel(67, 67, 67), threeColorRedMaxPixel.avg());
    assertEquals(new RGBPixel(70, 70, 70), threeColorGreenMaxPixel.avg());
    assertEquals(new RGBPixel(110, 110, 110), threeColorBlueMaxPixel.avg());
  }

  @Test
  public void getRed() {
    assertEquals(0, whitePixel.getRed());
    assertEquals(255, blackPixel.getRed());
    assertEquals(255, redPixel.getRed());
    assertEquals(0, greenPixel.getRed());
    assertEquals(0, bluePixel.getRed());
    assertEquals(255, brownPixel.getRed());
    assertEquals(255, purplePixel.getRed());
    assertEquals(0, yellowPixel.getRed());
    assertEquals(100, threeColorSamePixel.getRed());
    assertEquals(100, threeColorRedMaxPixel.getRed());
    assertEquals(90, threeColorGreenMaxPixel.getRed());
    assertEquals(30, threeColorBlueMaxPixel.getRed());
  }

  @Test
  public void getBlue() {
    assertEquals(0, whitePixel.getBlue());
    assertEquals(255, blackPixel.getBlue());
    assertEquals(0, redPixel.getBlue());
    assertEquals(0, greenPixel.getBlue());
    assertEquals(255, bluePixel.getBlue());
    assertEquals(0, brownPixel.getBlue());
    assertEquals(255, purplePixel.getBlue());
    assertEquals(255, yellowPixel.getBlue());
    assertEquals(100, threeColorSamePixel.getBlue());
    assertEquals(50, threeColorRedMaxPixel.getBlue());
    assertEquals(20, threeColorGreenMaxPixel.getBlue());
    assertEquals(200, threeColorBlueMaxPixel.getBlue());
  }

  @Test
  public void getGreen() {
    assertEquals(0, whitePixel.getGreen());
    assertEquals(255, blackPixel.getGreen());
    assertEquals(0, redPixel.getGreen());
    assertEquals(255, greenPixel.getGreen());
    assertEquals(0, bluePixel.getGreen());
    assertEquals(255, brownPixel.getGreen());
    assertEquals(0, purplePixel.getGreen());
    assertEquals(255, yellowPixel.getGreen());
    assertEquals(100, threeColorSamePixel.getGreen());
    assertEquals(50, threeColorRedMaxPixel.getGreen());
    assertEquals(100, threeColorGreenMaxPixel.getGreen());
    assertEquals(100, threeColorBlueMaxPixel.getGreen());
  }

  @Test
  public void testGreyscale() {
    assertTrue(whitePixel.isGreyscale());
    assertTrue(blackPixel.isGreyscale());
    assertTrue(threeColorSamePixel.isGreyscale());
    assertFalse(redPixel.isGreyscale());
    assertFalse(greenPixel.isGreyscale());
    assertFalse(bluePixel.isGreyscale());
    assertFalse(brownPixel.isGreyscale());
    assertFalse(purplePixel.isGreyscale());
    assertFalse(yellowPixel.isGreyscale());
    assertFalse(threeColorRedMaxPixel.isGreyscale());
    assertFalse(threeColorGreenMaxPixel.isGreyscale());
    assertFalse(threeColorBlueMaxPixel.isGreyscale());
  }

  @Test
  public void testEquals() {
    assertEquals(new RGBPixel(0, 0, 0), whitePixel);
    assertEquals(new RGBPixel(255, 255, 255), blackPixel);
    assertEquals(new RGBPixel(255, 0, 0), redPixel);
    assertEquals(new RGBPixel(0, 255, 0), greenPixel);
    assertEquals(new RGBPixel(0, 0, 255), bluePixel);
    assertEquals(new RGBPixel(255, 255, 0), brownPixel);
    assertEquals(new RGBPixel(255, 0, 255), purplePixel);
    assertEquals(new RGBPixel(0, 255, 255), yellowPixel);
    assertEquals(new RGBPixel(100, 100, 100), threeColorSamePixel);
    assertEquals(new RGBPixel(100, 50, 50), threeColorRedMaxPixel);
    assertEquals(new RGBPixel(90, 100, 20), threeColorGreenMaxPixel);
    assertEquals(new RGBPixel(30, 100, 200), threeColorBlueMaxPixel);
  }

  @Test
  public void testNotEquals() {
    for (int i = 0; i < allTestPixels.length; i++) {
      for (int j = 0; j < allTestPixels.length; j++) {
        if (i != j) {
          assertNotEquals(allTestPixels[i], allTestPixels[j]);
        }
      }
    }
  }

  @Test
  public void testHashCode() {
    assertEquals(new RGBPixel(0, 0, 0).hashCode(), whitePixel.hashCode());
    assertEquals(new RGBPixel(255, 255, 255).hashCode(), blackPixel.hashCode());
    assertEquals(new RGBPixel(255, 0, 0).hashCode(), redPixel.hashCode());
    assertEquals(new RGBPixel(0, 255, 0).hashCode(), greenPixel.hashCode());
    assertEquals(new RGBPixel(0, 0, 255).hashCode(), bluePixel.hashCode());
    assertEquals(new RGBPixel(255, 255, 0).hashCode(), brownPixel.hashCode());
    assertEquals(new RGBPixel(255, 0, 255).hashCode(), purplePixel.hashCode());
    assertEquals(new RGBPixel(0, 255, 255).hashCode(), yellowPixel.hashCode());
    assertEquals(new RGBPixel(100, 100, 100).hashCode(), threeColorSamePixel.hashCode());
    assertEquals(new RGBPixel(100, 50, 50).hashCode(), threeColorRedMaxPixel.hashCode());
    assertEquals(new RGBPixel(90, 100, 20).hashCode(), threeColorGreenMaxPixel.hashCode());
    assertEquals(new RGBPixel(30, 100, 200).hashCode(), threeColorBlueMaxPixel.hashCode());
  }
}