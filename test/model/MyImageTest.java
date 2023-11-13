package model;

import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.image.Image;
import model.image.MyImage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * This class tests MyImage.
 */
public class MyImageTest extends ImageTest {
  private MyImage testImage;
  private MyImage[] testImages;
  private MyImage whiteImage;
  private MyImage blackImage;
  private MyImage redImage;
  private MyImage greenImage;
  private MyImage blueImage;
  private MyImage woGreenImage;
  private MyImage woRedImage;
  private MyImage woBlueImage;
  private MyImage triImage;

  @Before
  public void setUp() {
    whiteImage = new MyImage("test/img/monochromatic/white.ppm");
    blackImage = new MyImage("test/img/monochromatic/black.ppm");
    redImage = new MyImage("test/img/monochromatic/red.ppm");
    greenImage = new MyImage("test/img/monochromatic/green.ppm");
    blueImage = new MyImage("test/img/monochromatic/blue.ppm");
    woGreenImage = new MyImage("test/img/dichromatic/woGreen.ppm");
    woRedImage = new MyImage("test/img/dichromatic/woRed.ppm");
    woBlueImage = new MyImage("test/img/dichromatic/woBlue.ppm");
    triImage = new MyImage("test/img/trichromatic/simple.ppm");
    testImages =
        new MyImage[]{whiteImage, blackImage, redImage, greenImage, blueImage, woGreenImage,
            woRedImage, woBlueImage, triImage};

  }

  //  -------------------Constructor Tests----------------------------

  @Test
  public void testMyImagePathJPGWithBlanks() {
    try {
      testImage = new MyImage("'test/img/car copy.jpg'");
      testImage = new MyImage("test/img/car copy.jpg");
    } catch (IllegalArgumentException e) {
      fail("Load supported jpg file should not throw exception");
    }
  }

  @Test
  public void testMyImagePathPNGWithBlanks() {
    try {
      testImage = new MyImage("test/img/split/rose copy.png");
    } catch (IllegalArgumentException e) {
      fail("Load supported jpg file should not throw exception");
    }
  }

  @Test
  public void testMyImagePathPPMWithBlanks() {
    try {
      testImage = new MyImage("test/img/trichromatic/simple copy.ppm");
    } catch (IllegalArgumentException e) {
      fail("Load supported jpg file should not throw exception");
    }
  }

  @Test
  public void testMyImagePathJPG() {
    try {
      testImage = new MyImage("test/img/car.jpg");
      testImage = new MyImage("test/img/car.jpeg");
    } catch (IllegalArgumentException e) {
      fail("Load supported jpg file should not throw exception");
    }
  }

  @Test
  public void testMyImagePathPNG() {
    try {
      testImage = new MyImage("test/img/city.png");
    } catch (IllegalArgumentException e) {
      fail("Load supported png file should not throw exception");
    }
  }

  @Test
  public void testMyImagePathPPM() {
    try {
      testImage = new MyImage("test/img/split/rose.ppm");
    } catch (IllegalArgumentException e) {
      fail("Load supported ppm file should not throw exception");
    }
  }

  @Test
  public void testMyImagePathMonochromatic() {
    try {
      testImage = new MyImage("test/img/monochromatic/black.jpg");
      testImage = new MyImage("test/img/monochromatic/black.png");
      testImage = new MyImage("test/img/monochromatic/black.ppm");
      testImage = new MyImage("test/img/monochromatic/blue.jpg");
      testImage = new MyImage("test/img/monochromatic/blue.png");
      testImage = new MyImage("test/img/monochromatic/blue.ppm");
      testImage = new MyImage("test/img/monochromatic/green.jpg");
      testImage = new MyImage("test/img/monochromatic/green.png");
      testImage = new MyImage("test/img/monochromatic/green.ppm");
      testImage = new MyImage("test/img/monochromatic/red.jpg");
      testImage = new MyImage("test/img/monochromatic/red.png");
      testImage = new MyImage("test/img/monochromatic/red.ppm");
      testImage = new MyImage("test/img/monochromatic/white.jpg");
      testImage = new MyImage("test/img/monochromatic/white.png");
      testImage = new MyImage("test/img/monochromatic/white.ppm");
    } catch (IllegalArgumentException e) {
      fail("Load supported monochromatic file should not throw exception");
    }
  }

  @Test
  public void testMyImagePathDichromatic() {
    try {
      testImage = new MyImage("test/img/dichromatic/woBlue.jpg");
      testImage = new MyImage("test/img/dichromatic/woBlue.png");
      testImage = new MyImage("test/img/dichromatic/woBlue.ppm");
      testImage = new MyImage("test/img/dichromatic/woGreen.jpg");
      testImage = new MyImage("test/img/dichromatic/woGreen.png");
      testImage = new MyImage("test/img/dichromatic/woGreen.ppm");
      testImage = new MyImage("test/img/dichromatic/woRed.jpg");
      testImage = new MyImage("test/img/dichromatic/woRed.png");
      testImage = new MyImage("test/img/dichromatic/woRed.ppm");
    } catch (IllegalArgumentException e) {
      fail("Load supported dichromatic file should not throw exception");
    }
  }

  @Test
  public void testMyImagePathTrichromatic() {
    try {
      testImage = new MyImage("test/img/trichromatic/all_colors_inter16.jpg");
      testImage = new MyImage("test/img/trichromatic/all_colors_inter16.png");
      testImage = new MyImage("test/img/trichromatic/all_colors_inter16.ppm");
    } catch (IllegalArgumentException e) {
      fail("Load supported trichromatic file should not throw exception");
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMyImagePathNotSupportedFormat() {
    testImage = new MyImage("test/img/cake.webp");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMyImagePathMalformedFile() {
    testImage = new MyImage("test/img/Building.jpg");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMyImagePathMalformedPath() {
    testImage = new MyImage("picture_lib///car.jpg");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMyImagePathNonExistingFile() {
    testImage = new MyImage("test/img/garden.jpg");
  }

  //MyImage constructor with size
  //given reasonable data
  //given illegal input (negative)
  @Test
  public void testMyImageSizeNormal() {
    try {
      testImage = new MyImage(200, 300);
    } catch (IllegalArgumentException e) {
      fail("create an empty image with reasonable size should not throw exception");
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMyImageSizeNegativeHeight() {
    testImage = new MyImage(-200, 300);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMyImageSizeNegativeWidth() {
    testImage = new MyImage(200, -300);
  }

  //----------------------------save---------------------------------------------------------------
  @Test
  public void testSaveJPG() {
    testImage = new MyImage(3, 3);
    assertTrue(testSave(testImage, "testSave.jpg"));
    assertTrue(testSave(testImage, "testSave.jpg"));
    assertTrue(testSave(testImage, "testSave.jpeg"));
    assertTrue(testSave(testImage, "testSave.jpeg"));
  }

  @Test
  public void testSavePNG() {
    testImage = new MyImage(3, 3);
    assertTrue(testSave(testImage, "testSave.png"));
    assertTrue(testSave(testImage, "testSave.png"));
  }

  @Test
  public void testSavePPM() {
    testImage = new MyImage(3, 3);
    assertTrue(testSave(testImage, "testSave.ppm"));
    assertTrue(testSave(testImage, "testSave.ppm"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSaveInvalidExtension() {
    testImage = new MyImage(3, 3);
    assertTrue(testSave(testImage, "testSave.wbep"));
  }

  @Test
  public void testSaveAbsolutePath() {
    testImage = new MyImage(3, 3);
    assertTrue(testSave(testImage, "D:\\Northeasten University\\CS5010 Program Design "
                                   + "Paradigm\\Assignment4\\CS5010_Assignment4\\testSave"
                                   + ".jpg"));
    assertTrue(testSave(testImage, "D:\\Northeasten University\\CS5010 Program Design "
                                   + "Paradigm\\Assignment4\\CS5010_Assignment4"
                                   + "\\testSave.jpeg"));
    assertTrue(testSave(testImage, "D:\\Northeasten University\\CS5010 Program Design "
                                   + "Paradigm\\Assignment4\\CS5010_Assignment4"
                                   + "\\testSave.png"));
    assertTrue(testSave(testImage, "D:\\Northeasten University\\CS5010 Program Design "
                                   + "Paradigm\\Assignment4\\CS5010_Assignment4"
                                   + "\\testSave.ppm"));
  }


  @Test
  public void testGetHeight() {
    testImage = new MyImage(1, 2);
    assertEquals(1, testImage.getHeight());
    testImage = new MyImage(3, 3);
    assertEquals(3, testImage.getHeight());
    testImage = new MyImage(3, 2);
    assertEquals(3, testImage.getHeight());
  }

  @Test
  public void testGetWidth() {
    testImage = new MyImage(1, 2);
    assertEquals(2, testImage.getWidth());
    testImage = new MyImage(3, 3);
    assertEquals(3, testImage.getWidth());
    testImage = new MyImage(3, 2);
    assertEquals(2, testImage.getWidth());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImgArrayAdditionSizeTooSmall() {
    new MyImage(3, 3).imgArrayAddition(new float[]{0, 0});
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImgArrayAdditionSizeTooBig() {
    new MyImage(3, 3).imgArrayAddition(new float[]{0, 0, 1, 2});
  }

  @Test
  public void testImgArrayAddition() {
    testImage = new MyImage(2, 2);
    float[] array = new float[]{-1, 100, 256};
    String expected = "RED:0 GREEN:100 BLUE:255    RED:0 GREEN:100 BLUE:255    \n"
                      + "RED:0 GREEN:100 BLUE:255    RED:0 GREEN:100 BLUE:255    \n";
    assertTrue(super.testImgArrayAddition(testImage, array, expected));

    testImage = testImage.imgArrayAddition(array);
    array = new float[]{2, 5, -50};
    expected = "RED:2 GREEN:105 BLUE:205    RED:2 GREEN:105 BLUE:205    \n"
               + "RED:2 GREEN:105 BLUE:205    RED:2 GREEN:105 BLUE:205    \n";
    assertTrue(super.testImgArrayAddition(testImage, array, expected));

    testImage = new MyImage(2, 2);
    array = new float[]{0, 0, 0};
    expected = "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    \n"
               + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    \n";
    assertTrue(super.testImgArrayAddition(testImage, array, expected));
  }

  //add/minus multiple times so that part of img is brightened/dimmed to max/min while others are
  // not yet
  @Test
  public void testImgArrayAdditionMultipleTimesPositive() {
    //Bottom right node RED:215 GREEN:205 BLUE:105 will first be added to max
    float[] array = new float[]{40, 50, 150};
    String expected = "RED:140 GREEN:135 BLUE:240    RED:70 GREEN:250 BLUE:255    \n"
                      + "RED:255 GREEN:100 BLUE:250    RED:145 GREEN:115 BLUE:225    \n"
                      + "RED:135 GREEN:255 BLUE:235    RED:255 GREEN:255 BLUE:255    \n";
    assertTrue(super.testImgArrayAddition(triImage, array, expected));
    testImage = triImage.imgArrayAddition(array);

    array = new float[]{10, 1, 15};
    expected = "RED:150 GREEN:136 BLUE:255    RED:80 GREEN:251 BLUE:255    \n"
               + "RED:255 GREEN:101 BLUE:255    RED:155 GREEN:116 BLUE:240    \n"
               + "RED:145 GREEN:255 BLUE:250    RED:255 GREEN:255 BLUE:255    \n";
    assertTrue(super.testImgArrayAddition(testImage, array, expected));
  }

  @Test
  public void testImgArrayAdditionMultipleTimesNegative() {
    float[] array = new float[]{-105, -65, -75};
    String expected = "RED:0 GREEN:20 BLUE:15    RED:0 GREEN:135 BLUE:75    \n"
                      + "RED:140 GREEN:0 BLUE:25    RED:0 GREEN:0 BLUE:0    \n"
                      + "RED:0 GREEN:140 BLUE:10    RED:110 GREEN:140 BLUE:30    \n";
    assertTrue(super.testImgArrayAddition(triImage, array, expected));
    testImage = triImage.imgArrayAddition(array);

    array = new float[]{-10, -15, -20};
    expected = "RED:0 GREEN:5 BLUE:0    RED:0 GREEN:120 BLUE:55    \n"
               + "RED:130 GREEN:0 BLUE:5    RED:0 GREEN:0 BLUE:0    \n"
               + "RED:0 GREEN:125 BLUE:0    RED:100 GREEN:125 BLUE:10    \n";
    assertTrue(super.testImgArrayAddition(testImage, array, expected));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFilteringKernelRowEven() {
    testImage = new MyImage(2, 2);
    float[][] kernel = new float[][]{{1, 1}};
    testImage.filtering(kernel);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFilteringKernelColEven() {
    testImage = new MyImage(2, 2);
    float[][] kernel = new float[][]{{1}, {1}};
    testImage.filtering(kernel);
  }

  @Test
  public void testFilteringKernelSmallerSize() {
    testImage = new MyImage(2, 2);
    testImage = testImage.imgArrayAddition(new float[]{10, 20, 30});
    float[][] kernel = new float[][]{{0.1f}};
    String expected = "RED:1 GREEN:2 BLUE:3    RED:1 GREEN:2 BLUE:3    \n"
                      + "RED:1 GREEN:2 BLUE:3    RED:1 GREEN:2 BLUE:3    \n";
    assertTrue(super.testFiltering(testImage, kernel, expected));
  }

  @Test
  public void testFilteringKernelSameSize() {
    testImage = new MyImage(3, 3);
    testImage = testImage.imgArrayAddition(new float[]{10, 20, 30});
    float[][] kernel = new float[][]{{0.1f, 0.2f, 0.3f}, {1, 1, 1}, {0, 0, 0}};
    String expected =
        "RED:20 GREEN:40 BLUE:60    RED:30 GREEN:60 BLUE:90    RED:20 GREEN:40 BLUE:60    \n"
        + "RED:25 GREEN:50 BLUE:75    RED:36 GREEN:72 BLUE:108    RED:23 GREEN:46 BLUE:69    \n"
        + "RED:25 GREEN:50 BLUE:75    RED:36 GREEN:72 BLUE:108    RED:23 GREEN:46 "
        + "BLUE:69    \n";
    assertTrue(super.testFiltering(testImage, kernel, expected));

  }

  @Test
  public void testFilteringKernelLargerSize() {
    testImage = new MyImage(2, 2);
    testImage = testImage.imgArrayAddition(new float[]{10, 20, 30});
    float[][] kernel = new float[][]{{0.1f, 0.2f, 0.3f}, {1, 1, 1}, {0, 0, 0}};
    String expected = "RED:20 GREEN:40 BLUE:60    RED:20 GREEN:40 BLUE:60    \n"
                      + "RED:25 GREEN:50 BLUE:75    RED:23 GREEN:46 BLUE:69    \n";
    assertTrue(super.testFiltering(testImage, kernel, expected));
  }

  @Test
  public void testFilteringKernelLShape() {
    testImage = new MyImage(2, 4);
    testImage = testImage.imgArrayAddition(new float[]{10, 20, 30});
    float[][] kernel = new float[][]{{0.1f, 0.2f, 0.3f}, {1, 1, 1}, {0, 0, 0}};
    System.out.println(testImage);
    String expected =
        "RED:20 GREEN:40 BLUE:60    RED:30 GREEN:60 BLUE:90    "
        + "RED:30 GREEN:60 BLUE:90    RED:20 GREEN:40 BLUE:60    \n"
        + "RED:25 GREEN:50 BLUE:75    RED:36 GREEN:72 BLUE:108    "
        + "RED:36 GREEN:72 BLUE:108    RED:23 GREEN:46 BLUE:69    \n";
    assertTrue(super.testFiltering(testImage, kernel, expected));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testArrayMultiplicationHeightNotMatch() {
    whiteImage.matrixMultiplication(new float[][]{
        {1, 1, 1},
        {2, 2, 2}
    });
  }

  @Test(expected = IllegalArgumentException.class)
  public void testArrayMultiplicationWidthNotMatch() {
    whiteImage.matrixMultiplication(new float[][]{
        {1, 1},
        {2, 2},
        {0, 0}
    });
  }

  @Test
  public void testMatrixMultiplicationIdentity() {
    float[][] identityMatrix = new float[][]{
        {1, 0, 0},
        {0, 1, 0},
        {0, 0, 1}
    };
    for (MyImage img : testImages) {
      String expectedString = img.toString();
      assertTrue(super.testMatrixMultiplication(img, identityMatrix, expectedString));
    }
  }

  @Test
  public void testMatrixMultiplication() {
    float[][] testMatrix = new float[][]{
        {0, 0, 0},
        {-1, 1.1f, 2},
        {1, -0.5f, 0}
    };
    String expected = "RED:0 GREEN:174 BLUE:58    RED:0 GREEN:255 BLUE:0    \n"
                      + "RED:0 GREEN:10 BLUE:220    RED:0 GREEN:117 BLUE:73    \n"
                      + "RED:0 GREEN:255 BLUE:0    RED:0 GREEN:221 BLUE:113    \n";
    assertTrue(super.testMatrixMultiplication(triImage, testMatrix, expected));

    expected =
        "RED:0 GREEN:255 BLUE:128    RED:0 GREEN:255 BLUE:128    RED:0 GREEN:255 BLUE:128    "
        + "RED:0 GREEN:255 BLUE:128    \n"
        + "RED:0 GREEN:255 BLUE:128    RED:0 GREEN:255 BLUE:128    RED:0 GREEN:255 BLUE:128    "
        + "RED:0 GREEN:255 BLUE:128    \n"
        + "RED:0 GREEN:255 BLUE:128    RED:0 GREEN:255 BLUE:128    RED:0 GREEN:255 BLUE:128    "
        + "RED:0 GREEN:255 BLUE:128    \n"
        + "RED:0 GREEN:255 BLUE:128    RED:0 GREEN:255 BLUE:128    RED:0 GREEN:255 BLUE:128 "
        + "   RED:0 GREEN:255 BLUE:128    \n";
    assertTrue(super.testMatrixMultiplication(whiteImage, testMatrix, expected));

    expected =
        "RED:0 GREEN:0 BLUE:255    RED:0 GREEN:0 BLUE:255    RED:0 GREEN:0 BLUE:255    RED:0 "
        + "GREEN:0 BLUE:255    \n"
        + "RED:0 GREEN:0 BLUE:255    RED:0 GREEN:0 BLUE:255    RED:0 GREEN:0 BLUE:255    RED:0 "
        + "GREEN:0 BLUE:255    \n"
        + "RED:0 GREEN:0 BLUE:255    RED:0 GREEN:0 BLUE:255    RED:0 GREEN:0 BLUE:255    RED:0 "
        + "GREEN:0 BLUE:255    \n"
        + "RED:0 GREEN:0 BLUE:255    RED:0 GREEN:0 BLUE:255    RED:0 GREEN:0 BLUE:255    "
        + "RED:0 GREEN:0 BLUE:255    \n";
    assertTrue(super.testMatrixMultiplication(redImage, testMatrix, expected));

    expected =
        "RED:0 GREEN:20 BLUE:100    RED:0 GREEN:20 BLUE:100    RED:0 GREEN:20 BLUE:100    RED:0 "
        + "GREEN:20 BLUE:100    \n"
        + "RED:0 GREEN:20 BLUE:100    RED:0 GREEN:20 BLUE:100    RED:0 GREEN:20 BLUE:100    RED:0"
        + " GREEN:20 BLUE:100    \n"
        + "RED:0 GREEN:20 BLUE:100    RED:0 GREEN:20 BLUE:100    RED:0 GREEN:20 BLUE:100    RED:0"
        + " GREEN:20 BLUE:100    \n"
        + "RED:0 GREEN:20 BLUE:100    RED:0 GREEN:20 BLUE:100    RED:0 GREEN:20 BLUE:100    "
        + "RED:0 GREEN:20 BLUE:100    \n";
    assertTrue(super.testMatrixMultiplication(woBlueImage, testMatrix, expected));

    assertTrue(super.testMatrixMultiplication(blackImage, testMatrix, blackImage.toString()));


  }

  @Test
  public void testChannelSplitRed() {
    assertEquals(redImage, whiteImage.channelSplit(Channel.RED));
    assertEquals(blackImage, blackImage.channelSplit(Channel.RED));
    assertEquals(redImage, redImage.channelSplit(Channel.RED));
    assertEquals(blackImage, greenImage.channelSplit(Channel.RED));
    assertEquals(blackImage, blueImage.channelSplit(Channel.RED));
    assertEquals(blackImage, woRedImage.channelSplit(Channel.RED));
    MyImage expected = new MyImage(4, 4).imgArrayAddition(new float[]{200, 0, 0});
    assertEquals(expected, woGreenImage.channelSplit(Channel.RED));
    assertEquals(expected, woBlueImage.channelSplit(Channel.RED));
    assertEquals("RED:100 GREEN:0 BLUE:0    RED:30 GREEN:0 BLUE:0    \n"
                 + "RED:245 GREEN:0 BLUE:0    RED:105 GREEN:0 BLUE:0    \n"
                 + "RED:95 GREEN:0 BLUE:0    RED:215 GREEN:0 BLUE:0    \n",
        triImage.channelSplit(Channel.RED).toString());
  }

  @Test
  public void testChannelSplitGreen() {
    assertEquals(greenImage, whiteImage.channelSplit(Channel.GREEN));
    assertEquals(blackImage, blackImage.channelSplit(Channel.GREEN));
    assertEquals(blackImage, redImage.channelSplit(Channel.GREEN));
    assertEquals(greenImage, greenImage.channelSplit(Channel.GREEN));
    assertEquals(blackImage, blueImage.channelSplit(Channel.GREEN));
    MyImage expected = new MyImage(4, 4).imgArrayAddition(new float[]{0, 200, 0});
    assertEquals(expected, woRedImage.channelSplit(Channel.GREEN));
    assertEquals(blackImage, woGreenImage.channelSplit(Channel.GREEN));
    assertEquals(expected, woBlueImage.channelSplit(Channel.GREEN));
    System.out.println(triImage.channelSplit(Channel.GREEN).toString());
    assertEquals("RED:0 GREEN:85 BLUE:0    RED:0 GREEN:200 BLUE:0    \n"
                 + "RED:0 GREEN:50 BLUE:0    RED:0 GREEN:65 BLUE:0    \n"
                 + "RED:0 GREEN:205 BLUE:0    RED:0 GREEN:205 BLUE:0    \n",
        triImage.channelSplit(Channel.GREEN).toString());
  }

  @Test
  public void testChannelSplitBlue() {
    assertEquals(blueImage, whiteImage.channelSplit(Channel.BLUE));
    assertEquals(blackImage, blackImage.channelSplit(Channel.BLUE));
    assertEquals(blackImage, redImage.channelSplit(Channel.BLUE));
    assertEquals(blackImage, greenImage.channelSplit(Channel.BLUE));
    assertEquals(blueImage, blueImage.channelSplit(Channel.BLUE));
    MyImage expected = new MyImage(4, 4).imgArrayAddition(new float[]{0, 0, 200});
    assertEquals(expected, woRedImage.channelSplit(Channel.BLUE));
    assertEquals(expected, woGreenImage.channelSplit(Channel.BLUE));
    assertEquals(blackImage, woBlueImage.channelSplit(Channel.BLUE));
    assertEquals("RED:0 GREEN:0 BLUE:90    RED:0 GREEN:0 BLUE:150    \n"
                 + "RED:0 GREEN:0 BLUE:100    RED:0 GREEN:0 BLUE:75    \n"
                 + "RED:0 GREEN:0 BLUE:85    RED:0 GREEN:0 BLUE:105    \n",
        triImage.channelSplit(Channel.BLUE).toString());
    System.out.println(triImage.channelSplit(Channel.BLUE).toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAdditionSizeNotMatchDiffWidth() {
    testImage = new MyImage(3, 3);
    testImage.addition(new MyImage(3, 4));
  }


  @Test(expected = IllegalArgumentException.class)
  public void testAdditionSizeNotMatchDiffHeight() {
    testImage = new MyImage(3, 3);
    testImage.addition(new MyImage(5, 3));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAdditionSizeNotMatchDiffSize() {
    testImage = new MyImage(3, 3);
    try {
      testImage.addition(new MyImage(2, 5));
    } catch (IllegalArgumentException e) {
      testImage.addition(new MyImage(5, 2));
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAdditionSizeNotMatchLarger() {
    testImage = new MyImage(3, 3);
    testImage.addition(new MyImage(4, 4));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAdditionSizeNotMatchSmaller() {
    testImage = new MyImage(3, 3);
    testImage.addition(new MyImage(1, 1));
  }

  @Test
  public void testAdditionSizeOne() {
    //empty with empty
    testImage = new MyImage(1, 1);
    MyImage otherImage = new MyImage(1, 1);
    String expected = "RED:0 GREEN:0 BLUE:0    \n";
    assertTrue(testAddition(testImage, otherImage, expected));
    otherImage = otherImage.imgArrayAddition(new float[]{0, 100, 255});
    expected = "RED:0 GREEN:100 BLUE:255    \n";
    assertTrue(testAddition(testImage, otherImage, expected));
    testImage = testImage.imgArrayAddition(new float[]{0, 100, 255});
    expected = "RED:0 GREEN:200 BLUE:255    \n";
    assertTrue(testAddition(testImage, otherImage, expected));

  }

  @Test
  public void testAdditionSquare() {
    testImage = new MyImage(3, 3);
    MyImage otherImage = new MyImage(3, 3);
    String expected = "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    \n"
                      + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    \n"
                      + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    "
                      + "\n";
    assertTrue(testAddition(testImage, otherImage, expected));
    otherImage = otherImage.imgArrayAddition(new float[]{0, 100, 255});
    expected =
        "RED:0 GREEN:100 BLUE:255    RED:0 GREEN:100 BLUE:255    RED:0 GREEN:100 BLUE:255    \n"
        + "RED:0 GREEN:100 BLUE:255    RED:0 GREEN:100 BLUE:255    RED:0 GREEN:100 BLUE:255    \n"
        + "RED:0 GREEN:100 BLUE:255    RED:0 GREEN:100 BLUE:255    RED:0 GREEN:100 BLUE:255    \n";
    assertTrue(testAddition(testImage, otherImage, expected));
    testImage = testImage.imgArrayAddition(new float[]{0, 100, 255});
    expected =
        "RED:0 GREEN:200 BLUE:255    RED:0 GREEN:200 BLUE:255    RED:0 GREEN:200 BLUE:255    \n"
        + "RED:0 GREEN:200 BLUE:255    RED:0 GREEN:200 BLUE:255    RED:0 GREEN:200 BLUE:255    \n"
        + "RED:0 GREEN:200 BLUE:255    RED:0 GREEN:200 BLUE:255    RED:0 GREEN:200 BLUE:255    \n";
    assertTrue(testAddition(testImage, otherImage, expected));


  }

  //rectangle that height is longer than width
  @Test
  public void testAdditionHeightLong() {
    String expected = "RED:200 GREEN:170 BLUE:180    RED:60 GREEN:255 BLUE:255    \n"
                      + "RED:255 GREEN:100 BLUE:200    RED:210 GREEN:130 BLUE:150    \n"
                      + "RED:190 GREEN:255 BLUE:170    RED:255 GREEN:255 BLUE:210    \n";
    assertTrue(testAddition(triImage, triImage, expected));

  }

  //rectangle that width is long than height
  @Test
  public void testAdditionWidthLong() {
    MyImage testImage = new MyImage(2, 3);
    testImage = testImage.imgArrayAddition(new float[]{99, 7, 34});
    MyImage testImage2 = new MyImage(2, 3);
    testImage2 = testImage2.imgArrayAddition(new float[]{10, 40, 80});
    System.out.println(testImage.addition(testImage2));
    String expected =
        "RED:109 GREEN:47 BLUE:114    RED:109 GREEN:47 BLUE:114    RED:109 GREEN:47 BLUE:114"
        + "    \n"
        + "RED:109 GREEN:47 BLUE:114    RED:109 GREEN:47 BLUE:114    RED:109 "
        + "GREEN:47 BLUE:114    \n";
    assertTrue(testAddition(testImage, testImage2, expected));

  }

  @Test(expected = IllegalArgumentException.class)
  public void testProjectCoordinateSizeNotMatch() {
    testImage = new MyImage(3, 3);
    int[][] matrix = new int[][]{{2}, {3}};
    testImage.projectCoordinate(matrix);
  }

  @Test
  public void testProjectCoordinateNoTranslation() {
    int[][] matrix = new int[][]{{1, 0, 0}, {0, 1, 0}};
    String expected = triImage.toString();
    assertTrue(super.testProjectCoordinate(triImage, matrix, expected));

    matrix = new int[][]{{1, 0, 0}, {0, -1, 0}};
    expected = "RED:100 GREEN:85 BLUE:90    RED:30 GREEN:200 BLUE:150    \n"
               + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    \n"
               + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    \n";
    assertTrue(super.testProjectCoordinate(triImage, matrix, expected));

    matrix = new int[][]{{-1, 0, 0}, {0, 1, 0}};
    expected = "RED:100 GREEN:85 BLUE:90    RED:0 GREEN:0 BLUE:0    \n"
               + "RED:245 GREEN:50 BLUE:100    RED:0 GREEN:0 BLUE:0    \n"
               + "RED:95 GREEN:205 BLUE:85    RED:0 GREEN:0 BLUE:0    \n";
    assertTrue(super.testProjectCoordinate(triImage, matrix, expected));

    matrix = new int[][]{{2, 0, 0}, {0, 1, 0}};
    expected = "RED:215 GREEN:205 BLUE:105    RED:0 GREEN:0 BLUE:0    \n"
               + "RED:245 GREEN:50 BLUE:100    RED:0 GREEN:0 BLUE:0    \n"
               + "RED:95 GREEN:205 BLUE:85    RED:0 GREEN:0 BLUE:0    \n";
    assertTrue(super.testProjectCoordinate(triImage, matrix, expected));

  }

  @Test
  public void testProjectCoordinateOnlyTranslation() {
    int[][] matrix = new int[][]{{1, 1, 1}, {1, 1, -1}};
    String expected = "RED:215 GREEN:205 BLUE:105    RED:0 GREEN:0 BLUE:0    \n"
                      + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    \n"
                      + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    \n";
    assertTrue(super.testProjectCoordinate(triImage, matrix, expected));

  }

  @Test
  public void testProjectCoordinate() {
    int[][] matrix = new int[][]{{1, 0, 1}, {0, -1, -1}};
    String expected = "RED:215 GREEN:205 BLUE:105    RED:0 GREEN:0 BLUE:0    \n"
                      + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    \n"
                      + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    \n";
    assertTrue(super.testProjectCoordinate(triImage, matrix, expected));

    matrix = new int[][]{{-1, 0, -1}, {0, 1, 2}};
    expected = "RED:215 GREEN:205 BLUE:105    RED:0 GREEN:0 BLUE:0    \n"
               + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    \n"
               + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    \n";
    assertTrue(super.testProjectCoordinate(triImage, matrix, expected));

    matrix = new int[][]{{1, 0, 2}, {0, -1, 0}};
    expected = "RED:215 GREEN:205 BLUE:105    RED:0 GREEN:0 BLUE:0    \n"
               + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    \n"
               + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    \n";
    assertTrue(super.testProjectCoordinate(triImage, matrix, expected));

    matrix = new int[][]{{-1, 0, 0}, {0, -1, 1}};
    expected = "RED:245 GREEN:50 BLUE:100    RED:0 GREEN:0 BLUE:0    \n"
               + "RED:100 GREEN:85 BLUE:90    RED:0 GREEN:0 BLUE:0    \n"
               + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    \n";
    assertTrue(super.testProjectCoordinate(triImage, matrix, expected));

  }

  @Test
  public void testMapElementSizeOne() {
    testImage = new MyImage(1, 1);
    testImage = testImage.mapElement(pixel -> pixel.addition(new float[]{-1, 0, 1}));
    assertEquals("RED:0 GREEN:0 BLUE:1    \n", testImage.toString());
    testImage = testImage.mapElement(pixel -> pixel.addition(new float[]{100, 256, 1}));
    assertEquals("RED:100 GREEN:255 BLUE:2    \n", testImage.toString());
  }

  @Test
  public void testMapElementSquare() {
    for (MyImage image : testImages) {
      String originalString = image.toString();
      testImage = image.mapElement(pixel -> pixel);
      assertEquals(image, testImage);
      assertEquals(originalString, image.toString());
    }
  }

  @Test
  public void testMapElementHeightLong() {
    String originalString = triImage.toString();
    testImage = triImage.mapElement(pixel -> pixel.addition(new float[]{-1, 0, 1}));
    String expected = "RED:99 GREEN:85 BLUE:91    RED:29 GREEN:200 BLUE:151    \n"
                      + "RED:244 GREEN:50 BLUE:101    RED:104 GREEN:65 BLUE:76    \n"
                      + "RED:94 GREEN:205 BLUE:86    RED:214 GREEN:205 BLUE:106    \n";
    assertEquals(expected, testImage.toString());
    testImage = testImage.mapElement(pixel -> pixel.addition(new float[]{100, 256, 1}));
    expected = "RED:199 GREEN:255 BLUE:92    RED:129 GREEN:255 BLUE:152    \n"
               + "RED:255 GREEN:255 BLUE:102    RED:204 GREEN:255 BLUE:77    \n"
               + "RED:194 GREEN:255 BLUE:87    RED:255 GREEN:255 BLUE:107    \n";
    assertEquals(expected, testImage.toString());
    assertEquals(originalString, triImage.toString());
  }

  @Test
  public void testMapElementWidthLong() {
    testImage = new MyImage(3, 1);
    String originalString = testImage.toString();
    MyImage result = testImage.mapElement(pixel -> pixel.addition(new float[]{1, 1, 1}));
    String expected = "RED:1 GREEN:1 BLUE:1    \n"
                      + "RED:1 GREEN:1 BLUE:1    \n"
                      + "RED:1 GREEN:1 BLUE:1    \n";
    assertEquals(expected, result.toString());
    result = result.mapElement(pixel -> pixel.addition(new float[]{-1, -1, -1}));
    expected = "RED:0 GREEN:0 BLUE:0    \n"
               + "RED:0 GREEN:0 BLUE:0    \n"
               + "RED:0 GREEN:0 BLUE:0    \n";
    assertEquals(expected, result.toString());
    assertEquals(originalString, testImage.toString());
  }

  @Test
  public void testGetChannels() {
    for (Image img : testImages) {
      assertEquals(new Channel[]{Channel.RED, Channel.GREEN, Channel.BLUE},
          img.getChannels());
    }
  }

  @Test
  public void testIsGreyscale() {
    assertTrue(whiteImage.isGreyscale());
    assertTrue(whiteImage.imgArrayAddition(new float[]{10, 10, 10}).isGreyscale());
    assertTrue(whiteImage.imgArrayAddition(new float[]{0.5f, 0.5f, 0.5f}).isGreyscale());
    assertTrue(blackImage.isGreyscale());
    assertFalse(blackImage.imgArrayAddition(new float[]{1, 2, 3}).isGreyscale());
    assertFalse(redImage.isGreyscale());
    assertFalse(greenImage.isGreyscale());
    assertFalse(blueImage.isGreyscale());
    assertFalse(woRedImage.isGreyscale());
    assertFalse(woGreenImage.isGreyscale());
    assertFalse(woBlueImage.isGreyscale());
    assertFalse(triImage.isGreyscale());
  }

  @Test
  public void testSplitZero() {
    for (MyImage image : testImages) {
      MyImage[] splitImages = image.split(0, Axis.X);
      assertNull(splitImages[0]);
      assertEquals(image, splitImages[1]);
    }
    for (MyImage image : testImages) {
      MyImage[] splitImages = image.split(0, Axis.Y);
      assertNull(splitImages[0]);
      assertEquals(image, splitImages[1]);
    }
  }

  @Test
  public void testSplitOne() {
    for (MyImage image : testImages) {
      MyImage[] splitImages = image.split(1, Axis.X);
      assertNull(splitImages[1]);
      assertEquals(image, splitImages[0]);
    }
    for (MyImage image : testImages) {
      MyImage[] splitImages = image.split(1, Axis.Y);
      assertNull(splitImages[1]);
      assertEquals(image, splitImages[0]);
    }
  }

  @Test
  public void testSplitPreciseX() {
    MyImage[] splitImages = triImage.split(0.5f, Axis.X);
    String firstPart = "RED:100 GREEN:85 BLUE:90    \n"
                       + "RED:245 GREEN:50 BLUE:100    \n"
                       + "RED:95 GREEN:205 BLUE:85    \n";
    String secondPart = "RED:30 GREEN:200 BLUE:150    \n"
                        + "RED:105 GREEN:65 BLUE:75    \n"
                        + "RED:215 GREEN:205 BLUE:105    \n";

    assertEquals(firstPart, splitImages[0].toString());
    assertEquals(secondPart, splitImages[1].toString());
  }

  @Test
  public void testSplitRoundToSmallX() {
    MyImage[] splitImages = redImage.split(0.3f, Axis.X);
    String firstPart = "RED:255 GREEN:0 BLUE:0    \n"
                       + "RED:255 GREEN:0 BLUE:0    \n"
                       + "RED:255 GREEN:0 BLUE:0    \n"
                       + "RED:255 GREEN:0 BLUE:0    \n";
    String secondPart =
        "RED:255 GREEN:0 BLUE:0    RED:255 GREEN:0 BLUE:0    RED:255 GREEN:0 BLUE:0    \n"
        + "RED:255 GREEN:0 BLUE:0    RED:255 GREEN:0 BLUE:0    RED:255 GREEN:0 BLUE:0    \n"
        + "RED:255 GREEN:0 BLUE:0    RED:255 GREEN:0 BLUE:0    RED:255 GREEN:0 BLUE:0    \n"
        + "RED:255 GREEN:0 BLUE:0    RED:255 GREEN:0 BLUE:0    RED:255 GREEN:0 "
        + "BLUE:0    \n";
    assertEquals(firstPart, splitImages[0].toString());
    assertEquals(secondPart, splitImages[1].toString());
  }

  @Test
  public void testSplitRoundToLargeX() {
    MyImage[] splitImages = redImage.split(0.7f, Axis.X);
    System.out.println(splitImages[0]);
    System.out.println(splitImages[1]);
    String firstPart =
        "RED:255 GREEN:0 BLUE:0    RED:255 GREEN:0 BLUE:0    RED:255 GREEN:0 BLUE:0    \n"
        + "RED:255 GREEN:0 BLUE:0    RED:255 GREEN:0 BLUE:0    RED:255 GREEN:0 BLUE:0    \n"
        + "RED:255 GREEN:0 BLUE:0    RED:255 GREEN:0 BLUE:0    RED:255 GREEN:0 BLUE:0    \n"
        + "RED:255 GREEN:0 BLUE:0    RED:255 GREEN:0 BLUE:0    RED:255 GREEN:0 "
        + "BLUE:0    \n";
    String secondPart = "RED:255 GREEN:0 BLUE:0    \n"
                        + "RED:255 GREEN:0 BLUE:0    \n"
                        + "RED:255 GREEN:0 BLUE:0    \n"
                        + "RED:255 GREEN:0 BLUE:0    \n";
    assertEquals(firstPart, splitImages[0].toString());
    assertEquals(secondPart, splitImages[1].toString());
  }

  @Test
  public void testCombineNull() {
    for (MyImage image : testImages) {
      assertEquals(image, image.combineImages(null, Axis.X));
      assertEquals(image, image.combineImages(null, Axis.Y));
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCombineSizeNotMatchX() {
    redImage.combineImages(new MyImage(3, 4), Axis.X);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCombineSizeNotMatchY() {
    redImage.combineImages(new MyImage(4, 3), Axis.Y);
  }

  @Test
  public void testCombineHalf() {
    MyImage[] splitImages = triImage.split(0.5f, Axis.X);
    assertEquals(triImage, splitImages[0].combineImages(splitImages[1], Axis.X));
  }

  @Test
  public void testCombineFirstSmaller() {
    MyImage[] splitImages = triImage.split(0.3f, Axis.X);
    assertEquals(triImage, splitImages[0].combineImages(splitImages[1], Axis.X));
  }

  @Test
  public void testCombineFirstLarger() {
    MyImage[] splitImages = triImage.split(0.7f, Axis.X);
    assertEquals(triImage, splitImages[0].combineImages(splitImages[1], Axis.X));
  }


  /**
   * Check if there's line spanning between every pair of points with the given color in the
   * histogram.
   *
   * @param imagePath   the path of the histogram
   * @param redPoints   the expected indices of red points
   * @param greenPoints the expected indices of green points
   * @param bluePoints  the expected indices of blue points
   * @return if the check passed
   * @throws IOException if there's problem reading in the histogram
   */
  public static boolean checkHistogramLines(String imagePath, int[] redPoints, int[] greenPoints,
                                            int[] bluePoints) throws IOException {
    File file = new File(imagePath);
    if (!file.isAbsolute()) {
      file = new File(System.getProperty("user.dir"), imagePath);
    }
    BufferedImage histogram = ImageIO.read(file);
    for (int i = 0; i < 255; i++) {
      for (int j = 0; j < 256; j++) {
        int pixel = histogram.getRGB(i, j);
        int red = (pixel >> 16) & 0xFF;
        int green = (pixel >> 8) & 0xFF;
        int blue = pixel & 0xFF;
        int redLower = Math.min(redPoints[i], redPoints[i + 1]);
        int redHigher = Math.max(redPoints[i], redPoints[i + 1]);
        int greenLower = Math.min(greenPoints[i], greenPoints[i + 1]);
        int greenHigher = Math.max(greenPoints[i], greenPoints[i + 1]);
        int blueLower = Math.min(bluePoints[i], bluePoints[i + 1]);
        int blueHigher = Math.max(bluePoints[i], bluePoints[i + 1]);
        if (j >= blueLower && j <= blueHigher) {
          if (!(blue == 255 && green == 0 && red == 0)) {
            return false;
          }
        } else if (j >= greenLower && j <= greenHigher) {
          if (!(blue == 0 && green == 255 && red == 0)) {
            return false;
          }
        } else if (j >= redLower && j <= redHigher) {
          if (!(blue == 0 && green == 0 && red == 255)) {
            return false;
          }
        } else {
          if (!(blue == 255 && green == 255 && red == 255)) {
            return false;
          }
        }
      }
    }
    return true;
  }

  @Test
  public void testGetHistogramWhite() throws IOException {
    MyImage histogram = whiteImage.getHistogram();
    String path = "histogram.png";
    histogram.save(path);
    int[] redPoints =
        new int[]{255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 0};
    int[] greenPoints =
        new int[]{255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 0};
    int[] bluePoints =
        new int[]{255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 0};
    assertTrue(checkHistogramLines(path, redPoints, greenPoints, bluePoints));
  }

  @Test
  public void testGetHistogramMono() throws IOException {
    MyImage histogram = redImage.getHistogram();
    String path = "histogram.png";
    histogram.save(path);
    int[] redPoints =
        new int[]{255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 0};
    int[] greenPoints =
        new int[]{0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255};
    int[] bluePoints =
        new int[]{0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255};
    assertTrue(checkHistogramLines(path, redPoints, greenPoints, bluePoints));

  }

  @Test
  public void testGetHistogramDi() throws IOException {
    MyImage histogram = woBlueImage.getHistogram();
    String path = "histogram.png";
    histogram.save(path);
    int[] redPoints =
        new int[]{255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255};
    int[] greenPoints =
        new int[]{255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255};
    int[] bluePoints =
        new int[]{0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255};
    assertTrue(checkHistogramLines(path, redPoints, greenPoints, bluePoints));

  }

  @Test
  public void testGetHistogramBi() throws IOException {
    MyImage histogram = new MyImage("test/img/dichromatic/RedAndGreen.ppm").getHistogram();
    String path = "histogram.png";
    histogram.save(path);
    int[] redPoints =
        new int[]{255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 0};
    int[] greenPoints =
        new int[]{255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 0};
    int[] bluePoints =
        new int[]{0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255};
    assertTrue(checkHistogramLines(path, redPoints, greenPoints, bluePoints));

  }

  @Test
  public void testGetHistogramBlack() throws IOException {
    MyImage histogram = blackImage.getHistogram();
    String path = "histogram.png";
    histogram.save(path);
    int[] redPoints =
        new int[]{0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255};
    int[] greenPoints =
        new int[]{0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255};
    int[] bluePoints =
        new int[]{0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255};
    assertTrue(checkHistogramLines(path, redPoints, greenPoints, bluePoints));

  }

  @Test
  public void testGetHistogram() throws IOException {
    MyImage histogram = new MyImage("test/img/city.png").getHistogram();
    String path = "histogram.png";
    histogram.save(path);
    int[] redPoints =
        new int[]{253, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 254,
            255, 255, 255, 255, 253, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 252, 253,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 247, 255, 255, 255, 253, 255, 255, 255,
            248, 255, 255, 255, 255, 255, 255, 240, 255, 252, 255, 255, 255, 255, 255, 255, 255,
            255, 232, 255, 255, 255, 249, 251, 250, 255, 240, 251, 255, 255, 255, 252, 255, 255,
            230, 255, 248, 255, 250, 236, 255, 255, 217, 255, 251, 255, 255, 255, 255, 255, 205,
            255, 253, 255, 230, 251, 252, 246, 213, 255, 255, 255, 255, 250, 255, 255, 224, 255,
            254, 235, 249, 247, 248, 255, 228, 255, 255, 255, 255, 255, 247, 255, 234, 255, 246,
            240, 252, 244, 246, 255, 167, 237, 255, 255, 246, 249, 252, 255, 125, 250, 247, 244,
            255, 255, 245, 248, 143, 255, 249, 255, 248, 249, 255, 250, 167, 252, 255, 255, 249,
            238, 251, 255, 170, 255, 255, 255, 241, 247, 253, 255, 211, 254, 237, 243, 239, 255,
            247, 248, 228, 238, 255, 254, 255, 255, 253, 248, 251, 247, 238, 248, 255, 250, 255,
            255, 255, 252, 255, 248, 255, 255, 249, 255, 255, 239, 255, 253, 253, 255, 252, 255,
            255, 255, 251, 255, 251, 255, 255, 255, 255, 255, 255, 255, 253, 255, 255, 253, 251,
            250, 255, 255, 255, 255, 252, 255, 255, 255, 255, 255, 255, 245, 255, 249, 255, 255,
            255, 254};
    int[] greenPoints =
        new int[]{253, 255, 255, 255, 255, 255, 255, 255, 255, 255, 253, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 254, 255, 255, 253, 255, 255, 255, 255, 255, 253, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 252, 252, 255, 255, 255, 252, 255, 255, 255, 255,
            255, 255, 255, 248, 255, 255, 255, 255, 251, 255, 255, 255, 251, 247, 255, 249, 255,
            255, 255, 255, 255, 255, 255, 248, 251, 250, 247, 255, 248, 255, 255, 255, 255, 255,
            255, 246, 250, 255, 255, 251, 241, 246, 252, 255, 255, 255, 255, 255, 255, 235, 244,
            252, 242, 255, 255, 255, 250, 251, 219, 255, 255, 255, 250, 249, 255, 255, 206, 252,
            252, 245, 255, 255, 248, 250, 202, 248, 252, 255, 255, 255, 255, 255, 201, 245, 250,
            249, 255, 253, 246, 255, 216, 255, 255, 255, 255, 255, 255, 239, 235, 244, 251, 250,
            249, 255, 244, 255, 255, 242, 255, 242, 255, 249, 252, 255, 255, 248, 248, 255, 246,
            242, 255, 255, 255, 247, 255, 255, 251, 255, 252, 241, 249, 255, 255, 248, 254, 255,
            255, 255, 178, 254, 247, 253, 252, 248, 255, 231, 44, 243, 255, 251, 255, 251, 248, 255,
            0, 255, 252, 255, 255, 255, 255, 255, 180, 255, 241, 243, 250, 248, 250, 255, 239, 255,
            255, 251, 255, 253, 255, 255, 253, 255, 255, 249, 255, 255, 255, 255, 253, 249, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 250, 255, 255, 253, 255, 255, 255, 254};
    int[] bluePoints =
        new int[]{253, 255, 255, 255, 255, 255, 255, 253, 255, 255, 253, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 253, 254, 252, 255, 255, 255,
            255, 255, 255, 252, 255, 251, 255, 255, 255, 255, 255, 255, 255, 255, 251, 255, 255,
            252, 255, 248, 255, 255, 252, 249, 255, 255, 255, 250, 255, 255, 255, 247, 255, 255,
            248, 254, 255, 253, 255, 250, 243, 255, 251, 255, 255, 252, 251, 255, 250, 255, 253,
            250, 255, 249, 251, 244, 250, 253, 250, 253, 252, 255, 255, 255, 246, 255, 255, 252,
            252, 243, 255, 238, 241, 251, 255, 255, 244, 255, 252, 254, 255, 255, 255, 244, 248,
            239, 245, 238, 240, 249, 255, 228, 255, 255, 255, 255, 255, 255, 255, 212, 243, 255,
            251, 232, 252, 252, 255, 205, 255, 255, 255, 251, 255, 254, 248, 210, 253, 236, 247,
            255, 251, 255, 255, 212, 255, 255, 255, 255, 251, 251, 255, 229, 255, 244, 255, 255,
            249, 238, 255, 231, 250, 255, 255, 255, 255, 253, 249, 230, 248, 255, 239, 255, 255,
            251, 255, 223, 255, 255, 252, 252, 252, 255, 255, 229, 255, 254, 253, 255, 255, 242,
            255, 242, 255, 255, 250, 255, 245, 255, 255, 196, 252, 255, 255, 245, 255, 255, 255,
            137, 249, 255, 255, 255, 255, 253, 255, 70, 255, 255, 252, 255, 255, 244, 253, 100, 255,
            255, 255, 255, 247, 255, 255, 234, 248, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            254};
    assertTrue(checkHistogramLines(path, redPoints, greenPoints, bluePoints));
  }

  //all peak fall into the range of [0,10] and [245,255]
  @Test
  public void testColorCorrectMonoPeakAllNeglected() {
    assertEquals(redImage.getHistogram(), redImage.colorCorrect().getHistogram());
    assertEquals(greenImage.getHistogram(), greenImage.colorCorrect().getHistogram());
    assertEquals(blueImage.getHistogram(), blueImage.colorCorrect().getHistogram());
  }

  //some peak fall into the range of [0,10] and [245,255]
  @Test
  public void testColorCorrectMonoPeakSomeNeglected() throws IOException {
    MyImage testImage = new MyImage(4, 4).imgArrayAddition(new float[]{11, 100, 250});
    String path = "histogram-color-correct.png";
    testImage.colorCorrect().getHistogram().save(path);
    int[] redPoints =
        new int[]{255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255};
    int[] greenPoints =
        new int[]{255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255};
    int[] bluePoints =
        new int[]{255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 0};
    assertTrue(checkHistogramLines(path, redPoints, greenPoints, bluePoints));
  }

  //all peak in [11, 254]
  // only one peak in one channel
  @Test
  public void testColorCorrectOnePeak() throws IOException {
    MyImage testImage = new MyImage(4, 4).imgArrayAddition(new float[]{50, 100, 150});
    String path = "histogram-color-correct.png";
    testImage.colorCorrect().getHistogram().save(path);
    int[] redPoints =
        new int[]{255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255};
    int[] greenPoints =
        new int[]{255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255};
    int[] bluePoints =
        new int[]{255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255};
    assertTrue(checkHistogramLines(path, redPoints, greenPoints, bluePoints));
  }

  // has multiple peaks in one single channel
  @Test
  public void testColorCorrectMultiplePeak() throws IOException {
    MyImage firstHalf = new MyImage(2, 2).imgArrayAddition(new float[]{50, 100, 155});
    MyImage secondHalf = new MyImage(2, 2).imgArrayAddition(new float[]{80, 160, 230});
    MyImage testImage = firstHalf.combineImages(secondHalf, Axis.X);
    String path = "histogram-color-correct.png";
    testImage.colorCorrect().getHistogram().save(path);
    int[] redPoints =
        new int[]{255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255};
    int[] greenPoints =
        new int[]{255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255};
    int[] bluePoints =
        new int[]{255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255};
    assertTrue(checkHistogramLines(path, redPoints, greenPoints, bluePoints));
  }

  @Test
  public void testColorCorrectMultiplePeakSomeNeglected() throws IOException {
    MyImage firstHalf = new MyImage(2, 2).imgArrayAddition(new float[]{5, 10, 150});
    MyImage secondHalf = new MyImage(2, 2).imgArrayAddition(new float[]{80, 160, 245});
    MyImage testImage = firstHalf.combineImages(secondHalf, Axis.X);
    String path = "histogram-color-correct.png";
    testImage.colorCorrect().getHistogram().save(path);
    int[] redPoints = new int[]{255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
        255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
        255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
        255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
        255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
        255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
        255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
        255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
        255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
        255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
        255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
        255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
        255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
        255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
        255, 255, 255, 255, 255, 255, 255};
    int[] greenPoints = new int[]{0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
        255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
        255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
        255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
        255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
        255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
        255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
        255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255,
        255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
        255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
        255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
        255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
        255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
        255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
        255, 255, 255, 255, 255, 255, 255, 255};
    int[] bluePoints =
        new int[]{255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255
            , 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255};
    assertTrue(checkHistogramLines(path, redPoints, greenPoints, bluePoints));
  }

  @Test
  public void testColorCorrect() throws IOException {
    float[][] greyscale = new float[][]{
        {0.2126f, 0.7152f, 0.0722f},
        {0.2126f, 0.7152f, 0.0722f},
        {0.2126f, 0.7152f, 0.0722f}
    };
    MyImage image =
        (MyImage) new MyImage("test/img/city_small.png").matrixMultiplication(greyscale)
            .imgArrayAddition(new float[]{10, 0, -5});
    String path = "histogram-color-correct.png";
    image.colorCorrect().getHistogram().save(path);
    int[] redPoints = new int[]{255, 255, 248, 255, 255, 255, 255, 255, 255, 255, 255, 255,
        255, 255, 249, 255, 255, 255, 255, 255, 255, 255, 251, 255, 255, 255, 255, 255, 255, 255,
        249, 255, 248, 255, 255, 255, 255, 255, 255, 255, 255, 255, 233, 255, 255, 255, 255, 255,
        255, 244, 255, 255, 255, 255, 235, 255, 255, 255, 255, 255, 255, 243, 255, 242, 234, 255,
        255, 255, 238, 255, 255, 255, 255, 255, 232, 255, 255, 216, 255, 240, 237, 255, 255, 255,
        242, 241, 255, 255, 243, 225, 243, 255, 247, 255, 247, 234, 255, 210, 248, 255, 206, 245,
        252, 255, 255, 236, 218, 237, 185, 250, 254, 244, 251, 245, 188, 230, 184, 253, 226, 255,
        233, 253, 188, 199, 216, 232, 254, 249, 238, 247, 178, 212, 215, 245, 239, 255, 233, 229,
        209, 208, 246, 233, 255, 248, 244, 252, 204, 235, 236, 240, 230, 255, 255, 186, 255, 255,
        234, 227, 255, 255, 219, 235, 255, 249, 255, 255, 209, 255, 255, 212, 255, 255, 228, 255,
        230, 255, 231, 255, 255, 240, 240, 234, 193, 145, 215, 235, 255, 236, 174, 168, 94, 17, 198,
        222, 205, 252, 146, 229, 0, 111, 72, 207, 208, 241, 236, 165, 205, 165, 226, 250, 255, 221,
        234, 242, 221, 235, 255, 255, 223, 255, 249, 240, 255, 255, 250, 255, 245, 248, 255, 239,
        255, 255, 255, 255, 255, 255, 251, 255, 235, 255, 255, 255, 255, 255, 255, 255, 255, 236,
        255, 255, 255, 255, 255, 255, 255, 255};
    int[] greenPoints =
        new int[]{255, 255, 248, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 249, 255,
            255, 255, 255, 255, 255, 255, 251, 255, 255, 255, 255, 255, 255, 255, 249, 255, 248,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 233, 255, 255, 255, 255, 255, 255, 244,
            255, 255, 255, 255, 235, 255, 255, 255, 255, 255, 255, 243, 255, 242, 234, 255, 255,
            255, 238, 255, 255, 255, 255, 255, 232, 255, 255, 216, 255, 240, 237, 255, 255, 255,
            242, 241, 255, 255, 243, 225, 243, 255, 247, 255, 247, 234, 255, 210, 248, 255, 206,
            245, 252, 255, 255, 236, 218, 237, 185, 250, 254, 244, 251, 245, 188, 230, 184, 253,
            226, 255, 233, 253, 188, 199, 216, 232, 254, 249, 238, 247, 178, 212, 215, 245, 239,
            255, 233, 229, 209, 208, 246, 233, 255, 248, 244, 252, 204, 235, 236, 240, 230, 255,
            255, 186, 255, 255, 234, 227, 255, 255, 219, 235, 255, 249, 255, 255, 209, 255, 255,
            212, 255, 255, 228, 255, 230, 255, 231, 255, 255, 240, 240, 234, 193, 145, 215, 235,
            255, 236, 174, 168, 94, 17, 198, 222, 205, 252, 146, 229, 0, 111, 72, 207, 208, 241,
            236, 165, 205, 165, 226, 250, 255, 221, 234, 242, 221, 235, 255, 255, 223, 255, 249,
            240, 255, 255, 250, 255, 245, 248, 255, 239, 255, 255, 255, 255, 255, 255, 251, 255,
            235, 255, 255, 255, 255, 255, 255, 255, 255, 255, 243, 255, 255, 255, 249, 255, 255,
            254};
    int[] bluePoints =
        new int[]{255, 255, 255, 255, 255, 255, 255, 248, 255, 255, 255, 255, 255, 255, 249, 255,
            255, 255, 255, 255, 255, 255, 251, 255, 255, 255, 255, 255, 255, 255, 249, 255, 248,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 233, 255, 255, 255, 255, 255, 255, 244,
            255, 255, 255, 255, 235, 255, 255, 255, 255, 255, 255, 243, 255, 242, 234, 255, 255,
            255, 238, 255, 255, 255, 255, 255, 232, 255, 255, 216, 255, 240, 237, 255, 255, 255,
            242, 241, 255, 255, 243, 225, 243, 255, 247, 255, 247, 234, 255, 210, 248, 255, 206,
            245, 252, 255, 255, 236, 218, 237, 185, 250, 254, 244, 251, 245, 188, 230, 184, 253,
            226, 255, 233, 253, 188, 199, 216, 232, 254, 249, 238, 247, 178, 212, 215, 245, 239,
            255, 233, 229, 209, 208, 246, 233, 255, 248, 244, 252, 204, 235, 236, 240, 230, 255,
            255, 186, 255, 255, 234, 227, 255, 255, 219, 235, 255, 249, 255, 255, 209, 255, 255,
            212, 255, 255, 228, 255, 230, 255, 231, 255, 255, 240, 240, 234, 193, 145, 215, 235,
            255, 236, 174, 168, 94, 17, 198, 222, 205, 252, 146, 229, 0, 111, 72, 207, 208, 241,
            236, 165, 205, 165, 226, 250, 255, 221, 234, 242, 221, 235, 255, 255, 223, 255, 249,
            240, 255, 255, 250, 255, 245, 248, 255, 239, 255, 255, 255, 255, 255, 255, 251, 255,
            235, 255, 255, 255, 255, 255, 255, 255, 255, 255, 243, 255, 255, 255, 249, 255, 255,
            254};
    assertTrue(checkHistogramLines(path, redPoints, greenPoints, bluePoints));

  }


  @Test
  public void testLevelAdjustmentSame() {
    int black = 0;
    int mid = 128;
    int white = 255;
    for (MyImage image : testImages) {
      MyImage adjusted = image.levelAdjustment(black, mid, white);
      assertEquals(adjusted, image);
    }
  }

  @Test
  public void testLevelAdjustmentLinearSteeper() throws IOException {
    int black = 50;
    int mid = 128;
    int white = 205;

    MyImage allColorImage =
        new MyImage("test/img/trichromatic/all_colors_inter16.ppm").imgArrayAddition(
            new float[]{0, 5, 10});
    MyImage adjusted = allColorImage.levelAdjustment(black, mid, white);
    String path="level-adjustment-histogram.png";
    adjusted.getHistogram().save(path);

    int[] redPoints =new int[]{0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 64};
    int[] greenPoints =new int[]{64, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 64};
    int[] bluePoints =new int[]{64, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 64};
    assertTrue(checkHistogramLines(path, redPoints, greenPoints, bluePoints));

  }

  //black right
  @Test
  public void testLevelAdjustmentBlackRight()  throws IOException{
    int black = 50;
    int mid = 128;
    int white = 255;
    MyImage allColorImage =
        new MyImage("test/img/trichromatic/all_colors_inter16.ppm").imgArrayAddition(
            new float[]{0, 5, 10});
    MyImage adjusted = allColorImage.levelAdjustment(black, mid, white);
    String path="level-adjustment-histogram.png";
    adjusted.getHistogram().save(path);
    int[] redPoints =new int[]{0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255};
    int[] greenPoints =new int[]{64, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255};
    int[] bluePoints =new int[]{64, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255};
    assertTrue(checkHistogramLines(path, redPoints, greenPoints, bluePoints));

  }

  //mid left
  @Test
  public void testLevelAdjustmentMidLeft() throws IOException {
    int black = 0;
    int mid = 200;
    int white = 255;
    MyImage allColorImage =
        new MyImage("test/img/trichromatic/all_colors_inter16.ppm").imgArrayAddition(
            new float[]{0, 5, 10});
    MyImage adjusted = allColorImage.levelAdjustment(black, mid, white);
    String path="level-adjustment-histogram.png";
    adjusted.getHistogram().save(path);
    int[] redPoints =new int[]{0, 255, 255, 255, 255, 255, 255, 219, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 219, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 219, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 219, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 219, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 219, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 219, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 219, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 219, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255};
    int[] greenPoints =new int[]{0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 219, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 219, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 219, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 219, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 219, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 219, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 219, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 219, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 219, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255};
    int[] bluePoints =new int[]{36, 255, 255, 219, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 219, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 219, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 219, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 219, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 219, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 219, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 219, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 219, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 219, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255};
    assertTrue(checkHistogramLines(path, redPoints, greenPoints, bluePoints));

  }

  //mid right
  @Test
  public void testLevelAdjustmentMidRight()  throws IOException{
    int black = 0;
    int mid = 100;
    int white = 255;
    MyImage allColorImage =
        new MyImage("test/img/trichromatic/all_colors_inter16.ppm").imgArrayAddition(
            new float[]{0, 5, 10});
    MyImage adjusted = allColorImage.levelAdjustment(black, mid, white);
    String path="level-adjustment-histogram.png";
    adjusted.getHistogram().save(path);
    int[] redPoints =new int[]{0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255};
    int[] greenPoints =new int[]{255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255};
    int[] bluePoints =new int[]{255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255};
    assertTrue(checkHistogramLines(path, redPoints, greenPoints, bluePoints));
  }

  //white left
  @Test
  public void testLevelAdjustmentWhiteLeft() throws IOException {
    int black = 0;
    int mid = 128;
    int white = 200;
    MyImage allColorImage =
        new MyImage("test/img/trichromatic/all_colors_inter16.ppm").imgArrayAddition(
            new float[]{0, 5, 10});
    MyImage adjusted = allColorImage.levelAdjustment(black, mid, white);
    String path="level-adjustment-histogram.png";
    adjusted.getHistogram().save(path);
    int[] redPoints =new int[]{191, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 64};
    int[] greenPoints =new int[]{255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 64};
    int[] bluePoints =new int[]{255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0};
    assertTrue(checkHistogramLines(path, redPoints, greenPoints, bluePoints));
  }

  @Test
  public void testLevelAdjustment() throws IOException {
    MyImage testImage = new MyImage("test/img/city.png");
    MyImage adjusted = testImage.levelAdjustment(20, 100, 255);
    String path="city-level-adjustment-histogram.png";
    adjusted.save("city-level_adjustment-20-100-255.png");
    adjusted.getHistogram().save(path);
    int[] redPoints =new int[]{250, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 252, 255, 253, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 247, 255, 255, 255, 255, 255, 255, 253, 255, 255, 255, 255, 255, 255, 248, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 240, 255, 255, 255, 252, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 232, 255, 255, 255, 255, 255, 249, 255, 251, 250, 255, 255, 240, 255, 251, 255, 255, 255, 255, 252, 255, 255, 255, 255, 230, 255, 255, 248, 255, 255, 250, 236, 255, 255, 255, 217, 255, 255, 251, 255, 255, 255, 255, 255, 255, 255, 205, 255, 255, 253, 255, 230, 255, 251, 252, 246, 213, 255, 255, 255, 255, 255, 255, 250, 255, 255, 224, 255, 255, 254, 235, 249, 255, 247, 248, 255, 228, 255, 255, 255, 255, 255, 255, 247, 255, 234, 255, 255, 246, 240, 252, 244, 246, 255, 167, 237, 255, 255, 246, 249, 252, 255, 125, 250, 247, 244, 255, 255, 245, 248, 143, 255, 249, 255, 248, 249, 255, 250, 167, 252, 255, 249, 238, 251, 255, 170, 255, 255, 241, 247, 253, 211, 254, 237, 243, 239, 247, 248, 210, 255, 254, 255, 253, 244, 247, 238, 248, 250, 255, 252, 255, 248, 255, 249, 239, 255, 251, 252, 255, 251, 251, 255, 255, 255, 253, 255, 244, 255, 252, 255, 255, 245, 249, 254};
    int[] greenPoints =new int[]{251, 255, 254, 255, 255, 255, 255, 253, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 253, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 252, 255, 252, 255, 255, 255, 255, 255, 255, 252, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 248, 255, 255, 255, 255, 255, 255, 255, 251, 255, 255, 255, 255, 255, 255, 251, 247, 255, 255, 249, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 248, 251, 255, 250, 247, 255, 255, 248, 255, 255, 255, 255, 255, 255, 255, 255, 255, 246, 255, 250, 255, 255, 255, 251, 241, 255, 246, 252, 255, 255, 255, 255, 255, 255, 255, 255, 255, 235, 244, 252, 255, 242, 255, 255, 255, 255, 250, 251, 219, 255, 255, 255, 255, 255, 250, 249, 255, 255, 206, 255, 252, 252, 245, 255, 255, 255, 248, 250, 202, 248, 252, 255, 255, 255, 255, 255, 255, 201, 245, 255, 250, 249, 255, 253, 246, 255, 216, 255, 255, 255, 255, 255, 255, 239, 235, 244, 251, 250, 249, 255, 244, 255, 255, 242, 255, 242, 255, 249, 252, 255, 255, 248, 248, 246, 242, 255, 255, 255, 247, 255, 251, 255, 252, 236, 255, 255, 248, 254, 255, 255, 177, 247, 253, 245, 255, 19, 243, 255, 251, 251, 248, 0, 252, 255, 255, 255, 180, 241, 238, 243, 239, 255, 251, 253, 253, 255, 249, 255, 247, 255, 255, 255, 255, 250, 253, 254};
    int[] bluePoints =new int[]{249, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 253, 255, 254, 255, 252, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 252, 255, 255, 255, 251, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 251, 255, 255, 255, 255, 252, 255, 255, 248, 255, 255, 255, 255, 252, 255, 249, 255, 255, 255, 255, 255, 250, 255, 255, 255, 255, 255, 255, 247, 255, 255, 255, 248, 255, 254, 255, 255, 253, 255, 255, 250, 255, 243, 255, 255, 251, 255, 255, 255, 252, 255, 251, 255, 250, 255, 255, 253, 255, 250, 255, 255, 249, 251, 255, 244, 250, 253, 255, 250, 253, 252, 255, 255, 255, 255, 255, 246, 255, 255, 255, 252, 252, 255, 243, 255, 238, 255, 241, 251, 255, 255, 255, 244, 255, 252, 255, 254, 255, 255, 255, 244, 255, 248, 239, 245, 238, 255, 240, 249, 255, 228, 255, 255, 255, 255, 255, 255, 255, 255, 212, 243, 255, 255, 251, 232, 252, 252, 255, 205, 255, 255, 255, 251, 255, 254, 248, 210, 253, 236, 247, 255, 251, 255, 255, 212, 255, 255, 255, 255, 251, 251, 255, 229, 255, 244, 255, 249, 238, 255, 231, 250, 255, 255, 255, 253, 224, 248, 255, 239, 255, 251, 255, 223, 255, 252, 249, 255, 229, 255, 254, 253, 255, 242, 242, 255, 250, 245, 255, 193, 255, 245, 255, 137, 249, 255, 253, 70, 255, 252, 244, 99, 255, 247, 234, 248, 255, 255, 254};
    assertTrue(checkHistogramLines(path, redPoints, greenPoints, bluePoints));
  }

  @Test
  public void testEquals() {
    MyImage image1 = new MyImage("test/img/trichromatic/simple.ppm");
    MyImage image2 = new MyImage("test/img/trichromatic/simple.ppm");
    assertEquals(image1, image2);
    image1 = new MyImage(3, 3);
    image2 = new MyImage(3, 3);
    assertEquals(image1, image2);
    image1.imgArrayAddition(new float[]{255, 0, 0});
    image1.imgArrayAddition(new float[]{255, 0, 0});
    assertEquals(image1, image2);

  }


}
