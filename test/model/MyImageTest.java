package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class MyImageTest {
  private Image testImage;

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
    assertTrue(ImageTest.testSave(testImage, "testSave.jpg"));
    assertTrue(ImageTest.testSave(testImage, "testSave.jpg"));
    assertTrue(ImageTest.testSave(testImage, "testSave.jpeg"));
    assertTrue(ImageTest.testSave(testImage, "testSave.jpeg"));
  }

  @Test
  public void testSavePNG() {
    testImage = new MyImage(3, 3);
    assertTrue(ImageTest.testSave(testImage, "testSave.png"));
    assertTrue(ImageTest.testSave(testImage, "testSave.png"));
  }

  @Test
  public void testSavePPM() {
    testImage = new MyImage(3, 3);
    assertTrue(ImageTest.testSave(testImage, "testSave.ppm"));
    assertTrue(ImageTest.testSave(testImage, "testSave.ppm"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSaveInvalidExtension() {
    testImage = new MyImage(3, 3);
    assertTrue(ImageTest.testSave(testImage, "testSave.wbep"));
  }

  @Test
  public void testSaveAbsolutePath() {
    testImage = new MyImage(3, 3);
    assertTrue(ImageTest.testSave(testImage, "D:\\Northeasten University\\CS5010 Program Design "
                                             + "Paradigm\\Assignment4\\CS5010_Assignment4\\testSave"
                                             + ".jpg"));
    assertTrue(ImageTest.testSave(testImage, "D:\\Northeasten University\\CS5010 Program Design "
                                             + "Paradigm\\Assignment4\\CS5010_Assignment4"
                                             + "\\testSave.jpeg"));
    assertTrue(ImageTest.testSave(testImage, "D:\\Northeasten University\\CS5010 Program Design "
                                             + "Paradigm\\Assignment4\\CS5010_Assignment4"
                                             + "\\testSave.png"));
    assertTrue(ImageTest.testSave(testImage, "D:\\Northeasten University\\CS5010 Program Design "
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
    testImage = testImage.imgArrayAddition(new float[]{10, 20, 30});
    float[][] kernel = new float[][]{{0.1f}};
    System.out.println(testImage.filtering(kernel));
    String expected = "RED:1 GREEN:2 BLUE:3    RED:1 GREEN:2 BLUE:3    \n"
                      + "RED:1 GREEN:2 BLUE:3    RED:1 GREEN:2 BLUE:3    \n";
    assertEquals(expected, testImage.filtering(kernel).toString());
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
    assertEquals(expected, testImage.filtering(kernel).toString());

  }

  @Test
  public void testFilteringKernelLargerSize() {
    testImage = new MyImage(2, 2);
    testImage = testImage.imgArrayAddition(new float[]{10, 20, 30});
    float[][] kernel = new float[][]{{0.1f, 0.2f, 0.3f}, {1, 1, 1}, {0, 0, 0}};
    String expected = "RED:20 GREEN:40 BLUE:60    RED:20 GREEN:40 BLUE:60    \n"
                      + "RED:25 GREEN:50 BLUE:75    RED:23 GREEN:46 BLUE:69    \n";
    assertEquals(expected, testImage.filtering(kernel).toString());
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
    assertEquals(expected, testImage.filtering(kernel).toString());
  }


}
