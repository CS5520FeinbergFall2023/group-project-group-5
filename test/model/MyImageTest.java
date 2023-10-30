package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
  //MyImage constructor with path
  // remember to test both JPEG & JPG extension
  // test malformed path
  // well-formed path, but the img does not exist
  // img exist but not PPM,JPG or PNG extension
  // PPM, JPG & PNG, monochromatic(black,white,red,green,blue), dichromatic or trichromatic. With
  // each value 0,(a number in between 0-255),255.

  @Test
  public void testMyImagePathJPEG() {
    try {
      testImage = new MyImage("test/img/car.jpg");
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
      testImage = new MyImage("test/img/rose.ppm");
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
      fail("Load supported monochromatic file should throw exception");
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
      fail("Load supported dichromatic file should throw exception");
    }
  }

  @Test
  public void testMyImagePathTrichromatic() {
    try {
      testImage = new MyImage("test/img/trichromatic/all_colors_inter16.jpg");
      testImage = new MyImage("test/img/trichromatic/all_colors_inter16.png");
      testImage = new MyImage("test/img/trichromatic/all_colors_inter16.ppm");
    } catch (IllegalArgumentException e) {
      fail("Load supported trichromatic file should throw exception");
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
    assertEquals(expected, testImage.imgArrayAddition(array).toString());
    testImage = new MyImage(2, 2);
    array = new float[]{0, 0, 0};
    expected = "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    \n"
               + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    \n";
    assertEquals(expected, testImage.imgArrayAddition(array).toString());
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
    testImage = (MyImage) testImage.imgArrayAddition(new float[]{10, 20, 30});
    float[][] kernel = new float[][]{{0.1f}};
    System.out.println(testImage.filtering(kernel));
    String expected = "RED:1 GREEN:2 BLUE:3    RED:1 GREEN:2 BLUE:3    \n"
                      + "RED:1 GREEN:2 BLUE:3    RED:1 GREEN:2 BLUE:3    \n";
    assertEquals(expected, testImage.filtering(kernel).toString());
  }

  @Test
  public void testFilteringKernelSameSize() {
    testImage = new MyImage(3, 3);
    testImage = (MyImage) testImage.imgArrayAddition(new float[]{10, 20, 30});
    float[][] kernel = new float[][]{{0.1f, 0.2f, 0.3f}, {1, 1, 1}, {0, 0, 0}};
    String expected =
        "RED:20 GREEN:40 BLUE:60    RED:30 GREEN:60 BLUE:90    RED:20 GREEN:40 BLUE:60    \n"
        + "RED:25 GREEN:50 BLUE:75    RED:36 GREEN:72 BLUE:108    RED:23 GREEN:46 BLUE:69    \n"
        + "RED:25 GREEN:50 BLUE:75    RED:36 GREEN:72 BLUE:108    RED:23 GREEN:46 "
        + "BLUE:69    \n";
    assertEquals(expected, testImage.filtering(kernel).toString());

  }

  @Test
  public void testFilteringKernelLargerSize() {
    testImage = new MyImage(2, 2);
    testImage = (MyImage) testImage.imgArrayAddition(new float[]{10, 20, 30});
    float[][] kernel = new float[][]{{0.1f, 0.2f, 0.3f}, {1, 1, 1}, {0, 0, 0}};
    String expected = "RED:20 GREEN:40 BLUE:60    RED:20 GREEN:40 BLUE:60    \n"
                      + "RED:25 GREEN:50 BLUE:75    RED:23 GREEN:46 BLUE:69    \n";
    assertEquals(expected, testImage.filtering(kernel).toString());
  }

  @Test
  public void testFilteringKernelLShape() {
    testImage = new MyImage(2, 4);
    testImage = (MyImage) testImage.imgArrayAddition(new float[]{10, 20, 30});
    float[][] kernel = new float[][]{{0.1f, 0.2f, 0.3f}, {1, 1, 1}, {0, 0, 0}};
    System.out.println(testImage);
    String expected =
        "RED:20 GREEN:40 BLUE:60    RED:30 GREEN:60 BLUE:90    "
        + "RED:30 GREEN:60 BLUE:90    RED:20 GREEN:40 BLUE:60    \n"
        + "RED:25 GREEN:50 BLUE:75    RED:36 GREEN:72 BLUE:108    "
        + "RED:36 GREEN:72 BLUE:108    RED:23 GREEN:46 BLUE:69    \n";
    assertEquals(expected, testImage.filtering(kernel).toString());
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
  public void testArrayMultiplication() {
    float[][] identityMatrix = new float[][]{
        {1, 0, 0},
        {0, 1, 0},
        {0, 0, 1}
    };
    for (MyImage img : testImages) {
      MyImage result = (MyImage) img.matrixMultiplication(identityMatrix);
      assertEquals(img, result);
    }

    float[][] testMatrix = new float[][]{
        {0, 0, 0},
        {-1, 1.1f, 2},
        {1, -0.5f, 0}
    };
    String expected = "RED:0 GREEN:174 BLUE:58    RED:0 GREEN:255 BLUE:0    \n"
                      + "RED:0 GREEN:10 BLUE:220    RED:0 GREEN:117 BLUE:73    \n"
                      + "RED:0 GREEN:255 BLUE:0    RED:0 GREEN:221 BLUE:113    \n";
    assertEquals(expected, triImage.matrixMultiplication(testMatrix).toString());
    expected =
        "RED:0 GREEN:255 BLUE:128    RED:0 GREEN:255 BLUE:128    RED:0 GREEN:255 BLUE:128    RED:0 GREEN:255 BLUE:128    \n"
        + "RED:0 GREEN:255 BLUE:128    RED:0 GREEN:255 BLUE:128    RED:0 GREEN:255 BLUE:128    RED:0 GREEN:255 BLUE:128    \n"
        + "RED:0 GREEN:255 BLUE:128    RED:0 GREEN:255 BLUE:128    RED:0 GREEN:255 BLUE:128    RED:0 GREEN:255 BLUE:128    \n"
        + "RED:0 GREEN:255 BLUE:128    RED:0 GREEN:255 BLUE:128    RED:0 GREEN:255 BLUE:128 "
        + "   RED:0 GREEN:255 BLUE:128    \n";
    assertEquals(expected, whiteImage.matrixMultiplication(testMatrix).toString());
    expected =
        "RED:0 GREEN:0 BLUE:255    RED:0 GREEN:0 BLUE:255    RED:0 GREEN:0 BLUE:255    RED:0 GREEN:0 BLUE:255    \n"
        + "RED:0 GREEN:0 BLUE:255    RED:0 GREEN:0 BLUE:255    RED:0 GREEN:0 BLUE:255    RED:0 GREEN:0 BLUE:255    \n"
        + "RED:0 GREEN:0 BLUE:255    RED:0 GREEN:0 BLUE:255    RED:0 GREEN:0 BLUE:255    RED:0 GREEN:0 BLUE:255    \n"
        + "RED:0 GREEN:0 BLUE:255    RED:0 GREEN:0 BLUE:255    RED:0 GREEN:0 BLUE:255    "
        + "RED:0 GREEN:0 BLUE:255    \n";
    assertEquals(expected, redImage.matrixMultiplication(testMatrix).toString());
    expected =
        "RED:0 GREEN:20 BLUE:100    RED:0 GREEN:20 BLUE:100    RED:0 GREEN:20 BLUE:100    RED:0 GREEN:20 BLUE:100    \n"
        + "RED:0 GREEN:20 BLUE:100    RED:0 GREEN:20 BLUE:100    RED:0 GREEN:20 BLUE:100    RED:0 GREEN:20 BLUE:100    \n"
        + "RED:0 GREEN:20 BLUE:100    RED:0 GREEN:20 BLUE:100    RED:0 GREEN:20 BLUE:100    RED:0 GREEN:20 BLUE:100    \n"
        + "RED:0 GREEN:20 BLUE:100    RED:0 GREEN:20 BLUE:100    RED:0 GREEN:20 BLUE:100    "
        + "RED:0 GREEN:20 BLUE:100    \n";
    assertEquals(expected, woBlueImage.matrixMultiplication(testMatrix).toString());
    assertEquals(blackImage, blackImage.matrixMultiplication(testMatrix));

  }

  @Test
  public void testChannelSplitRed() {
    assertEquals(redImage, whiteImage.channelSplit(Channel.RED));
    assertEquals(blackImage, blackImage.channelSplit(Channel.RED));
    assertEquals(redImage, redImage.channelSplit(Channel.RED));
    assertEquals(blackImage, greenImage.channelSplit(Channel.RED));
    assertEquals(blackImage, blueImage.channelSplit(Channel.RED));
    assertEquals(blackImage, woRedImage.channelSplit(Channel.RED));
    MyImage expected = (MyImage) new MyImage(4, 4).imgArrayAddition(new float[]{200, 0, 0});
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
    MyImage expected = (MyImage) new MyImage(4, 4).imgArrayAddition(new float[]{0, 200, 0});
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
    assertEquals(redImage, redImage.channelSplit(Channel.BLUE));
    assertEquals(blackImage, greenImage.channelSplit(Channel.BLUE));
    assertEquals(blackImage, blueImage.channelSplit(Channel.BLUE));
    assertEquals(blackImage, woRedImage.channelSplit(Channel.BLUE));
    MyImage expected = (MyImage) new MyImage(4, 4).imgArrayAddition(new float[]{200, 0, 0});
    assertEquals(expected, woGreenImage.channelSplit(Channel.BLUE));
    assertEquals(expected, woBlueImage.channelSplit(Channel.BLUE));
    assertEquals("RED:100 GREEN:0 BLUE:0    RED:30 GREEN:0 BLUE:0    \n"
                 + "RED:245 GREEN:0 BLUE:0    RED:105 GREEN:0 BLUE:0    \n"
                 + "RED:95 GREEN:0 BLUE:0    RED:215 GREEN:0 BLUE:0    \n",
        triImage.channelSplit(Channel.BLUE).toString());
    System.out.println(triImage.channelSplit(Channel.GREEN).toString());

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
    otherImage = (MyImage) otherImage.imgArrayAddition(new float[]{0, 100, 255});
    expected = "RED:0 GREEN:100 BLUE:255    \n";
    assertTrue(testAddition(testImage, otherImage, expected));
    testImage = (MyImage) testImage.imgArrayAddition(new float[]{0, 100, 255});
    expected = "RED:0 GREEN:200 BLUE:255    \n";
    assertTrue(testAddition(testImage, otherImage, expected));

  }

  @Test
  public void testAdditionSquare() {
    testImage = new MyImage(3, 3);
    MyImage otherImage = new MyImage(3, 3);
    String expected = "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    \n"
                      + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    \n"
                      + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    \n";
    assertTrue(testAddition(testImage, otherImage, expected));
    otherImage = (MyImage) otherImage.imgArrayAddition(new float[]{0, 100, 255});
    expected = "RED:0 GREEN:100 BLUE:255    RED:0 GREEN:100 BLUE:255    RED:0 GREEN:100 BLUE:255    \n"
               + "RED:0 GREEN:100 BLUE:255    RED:0 GREEN:100 BLUE:255    RED:0 GREEN:100 BLUE:255    \n"
               + "RED:0 GREEN:100 BLUE:255    RED:0 GREEN:100 BLUE:255    RED:0 GREEN:100 BLUE:255    \n";
    assertTrue(testAddition(testImage, otherImage, expected));
    testImage = (MyImage) testImage.imgArrayAddition(new float[]{0, 100, 255});
    expected = "RED:0 GREEN:200 BLUE:255    RED:0 GREEN:200 BLUE:255    RED:0 GREEN:200 BLUE:255    \n"
               + "RED:0 GREEN:200 BLUE:255    RED:0 GREEN:200 BLUE:255    RED:0 GREEN:200 BLUE:255    \n"
               + "RED:0 GREEN:200 BLUE:255    RED:0 GREEN:200 BLUE:255    RED:0 GREEN:200 BLUE:255    \n";
    assertTrue(testAddition(testImage, otherImage, expected));


  }

  //rectangle that height is longer than width
  @Test
  public void testAdditionHeightLong() {
    String expected="RED:200 GREEN:170 BLUE:180    RED:60 GREEN:255 BLUE:255    \n"
                    + "RED:255 GREEN:100 BLUE:200    RED:210 GREEN:130 BLUE:150    \n"
                    + "RED:190 GREEN:255 BLUE:170    RED:255 GREEN:255 BLUE:210    \n";
    assertTrue(testAddition(triImage, triImage, expected));

  }

  //rectangle that width is long than height
  @Test
  public void testAdditionWidthLong() {
    MyImage testImage = new MyImage(2,3);
    testImage= (MyImage) testImage.imgArrayAddition(new float[]{99,7,34});
    MyImage testImage2 = new MyImage(2,3);
    testImage2= (MyImage) testImage2.imgArrayAddition(new float[]{10,40,80});
    System.out.println(testImage.addition(testImage2));
    String expected="RED:109 GREEN:47 BLUE:114    RED:109 GREEN:47 BLUE:114    RED:109 GREEN:47 BLUE:114"
             + "    \n"
                          + "RED:109 GREEN:47 BLUE:114    RED:109 GREEN:47 BLUE:114    RED:109 "
                          + "GREEN:47 BLUE:114    \n";
    assertTrue(testAddition(testImage, testImage2, expected));

  }

  @Test
  public void testProjectCoordinateNoTranslation() {

  }

  @Test
  public void testProjectCoordinateOnlyTranslation() {

  }

  @Test
  public void testProjectCoordinate() {

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
      //todo:check did not change original
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
  public void testIsMonochromeOfChannelRed() {
    assertFalse(whiteImage.isMonochromeOfChannel(Channel.RED));
    assertTrue(blackImage.isMonochromeOfChannel(Channel.RED));
    assertTrue(redImage.isMonochromeOfChannel(Channel.RED));
    assertFalse(greenImage.isMonochromeOfChannel(Channel.RED));
    assertFalse(blueImage.isMonochromeOfChannel(Channel.RED));
    assertFalse(woRedImage.isMonochromeOfChannel(Channel.RED));
    assertFalse(woGreenImage.isMonochromeOfChannel(Channel.RED));
    assertFalse(woBlueImage.isMonochromeOfChannel(Channel.RED));
    assertFalse(triImage.isMonochromeOfChannel(Channel.RED));
  }

  @Test
  public void testIsMonochromeOfChannelGreen() {
    assertFalse(whiteImage.isMonochromeOfChannel(Channel.GREEN));
    assertTrue(blackImage.isMonochromeOfChannel(Channel.GREEN));
    assertFalse(redImage.isMonochromeOfChannel(Channel.GREEN));
    assertTrue(greenImage.isMonochromeOfChannel(Channel.GREEN));
    assertFalse(blueImage.isMonochromeOfChannel(Channel.GREEN));
    assertFalse(woRedImage.isMonochromeOfChannel(Channel.GREEN));
    assertFalse(woGreenImage.isMonochromeOfChannel(Channel.GREEN));
    assertFalse(woBlueImage.isMonochromeOfChannel(Channel.GREEN));
    assertFalse(triImage.isMonochromeOfChannel(Channel.GREEN));
  }

  @Test
  public void testIsMonochromeOfChannelBlue() {
    assertFalse(whiteImage.isMonochromeOfChannel(Channel.BLUE));
    assertTrue(blackImage.isMonochromeOfChannel(Channel.BLUE));
    assertFalse(redImage.isMonochromeOfChannel(Channel.BLUE));
    assertFalse(greenImage.isMonochromeOfChannel(Channel.BLUE));
    assertTrue(blueImage.isMonochromeOfChannel(Channel.BLUE));
    assertFalse(woRedImage.isMonochromeOfChannel(Channel.BLUE));
    assertFalse(woGreenImage.isMonochromeOfChannel(Channel.BLUE));
    assertFalse(woBlueImage.isMonochromeOfChannel(Channel.BLUE));
    assertFalse(triImage.isMonochromeOfChannel(Channel.BLUE));
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
