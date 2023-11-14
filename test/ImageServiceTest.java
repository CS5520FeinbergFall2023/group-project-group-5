import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import model.Axis;
import model.Channel;
import model.image.Image;
import model.image.MyImage;
import service.ImageService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class is to test ImageService.
 */
public class ImageServiceTest {
  private ImageService imageService;

  @Before
  public void setUp() {
    imageService = new ImageService();
  }

  //test null input
  @Test(expected = IllegalArgumentException.class)
  public void testSplitComponentNullChannel() {
    imageService.splitComponent(new MyImage(3, 3), null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSplitComponentNull() {
    imageService.splitComponent(null, Channel.RED);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetValueNull() {
    imageService.getValue(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBlurNull() {
    imageService.blur(null, 1, Axis.X);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetIntensityNull() {
    imageService.getIntensity(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetLumaNull() {
    imageService.getLuma(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFlipNull() {
    imageService.flip(null, Axis.X);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFlipNullAxis() {
    imageService.flip(new MyImage(3, 3), null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBrightenNull() {
    imageService.brighten(null, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSplitChannelNull() {
    imageService.splitChannel(null);
  }

  @Test(expected = NullPointerException.class)
  public void testCombineChannelsNullChannels() {
    imageService.combineChannels(null, new MyImage[]{new MyImage(3, 3)});
  }

  @Test(expected = NullPointerException.class)
  public void testCombineChannelsNullImages() {
    imageService.combineChannels(new Channel[]{Channel.GREEN}, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSharpenNull() {
    imageService.sharpen(null, 1, Axis.X);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetSepiaNull() {
    imageService.getSepia(null, 1, Axis.X);
  }


  //---------------Create images that visualize individual R,G,B components of an image.------------


  @Test
  public void testSplitComponentRed() {
    Image testImage = new MyImage("test/img/rose.ppm");
    Image testImageRed = imageService.splitComponent(testImage, Channel.RED);
    Image expectedRed = new MyImage("res/rose_onlyRed.png");
    assertEquals(testImageRed, expectedRed);
  }

  @Test
  public void testSplitComponentGreen() {
    Image testImage = new MyImage("test/img/rose.ppm");
    Image testImageGreen = imageService.splitComponent(testImage, Channel.GREEN);
    Image expectedGreen = new MyImage("res/rose_onlyGreen.png");
    assertEquals(testImageGreen, expectedGreen);
  }

  @Test
  public void testSplitComponentBlue() {
    Image testImage = new MyImage("test/img/rose.ppm");
    Image testImageBlue = imageService.splitComponent(testImage, Channel.BLUE);
    Image expectedBlue = new MyImage("res/rose_onlyBlue.png");
    assertEquals(testImageBlue, expectedBlue);
  }

  // -------------Create images that visualize the value, intensity or luma of an image--------
  //different formats
  // gets the value
  // intensity
  // luma
  // of monochromatic, dichromatic and trichromatic images. Of different values for the pixels or
  // same value for every pixel.

  @Test
  public void testGetValueMonochromatic() {
    Image testImageBlack = new MyImage("test/img/black.ppm");
    Image testImageWhite = new MyImage("test/img/white.ppm");
    Image testImageRed = new MyImage("test/img/red.ppm");
    Image testImageGreen = new MyImage("test/img/green.ppm");
    Image testImageBlue = new MyImage("test/img/blue.ppm");

    Image resultImageBlack = imageService.getValue(testImageBlack);
    Image resultImageWhite = imageService.getValue(testImageWhite);
    Image resultImageRed = imageService.getValue(testImageRed);
    Image resultImageGreen = imageService.getValue(testImageGreen);
    Image resultImageBlue = imageService.getValue(testImageBlue);

    String expectedNonBlack =
        "RED:255 GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255  "
        + "  RED:255 GREEN:255 BLUE:255    \n"
        + "RED:255 GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255"
        + "    RED:255 GREEN:255 BLUE:255    \n"
        + "RED:255 GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255"
        + "    RED:255 GREEN:255 BLUE:255    \n"
        + "RED:255 GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255"
        + "    RED:255 GREEN:255 BLUE:255    \n";
    String expectedBlack =
        "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 "
        + "BLUE:0    \n"
        + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 "
        + "BLUE:0    \n"
        + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 "
        + "BLUE:0    \n"
        + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 "
        + "BLUE:0    \n";
    assertEquals(expectedBlack, resultImageBlack.toString());
    assertEquals(expectedNonBlack, resultImageWhite.toString());
    assertEquals(expectedNonBlack, resultImageRed.toString());
    assertEquals(expectedNonBlack, resultImageGreen.toString());
    assertEquals(expectedNonBlack, resultImageBlue.toString());
  }

  @Test
  public void testGetValueDuoColor() {
    Image testImage = new MyImage("test/img/duoColor.png");
    Image resultImage = imageService.getValue(testImage);

    String expected = "RED:250 GREEN:250 BLUE:250    RED:250 GREEN:250 BLUE:250    \n"
                      + "RED:250 GREEN:250 BLUE:250    RED:250 GREEN:250 BLUE:250    \n";

    assertEquals(expected, resultImage.toString());
  }

  @Test
  public void testGetValueDichromatic() {
    Image testImageWoBlue = new MyImage("test/img/woBlue.ppm");
    Image testImageWoRed = new MyImage("test/img/woRed.ppm");
    Image testImageWoGreen = new MyImage("test/img/woGreen.ppm");

    Image resultImageWoBlue = imageService.getValue(testImageWoBlue);
    Image resultImageWoRed = imageService.getValue(testImageWoRed);
    Image resultImageWoGreen = imageService.getValue(testImageWoGreen);

    String expected =
        "RED:200 GREEN:200 BLUE:200    RED:200 GREEN:200 BLUE:200    RED:200 GREEN:200 BLUE:200  "
        + "  RED:200 GREEN:200 BLUE:200    \n"
        + "RED:200 GREEN:200 BLUE:200    RED:200 GREEN:200 BLUE:200    RED:200 GREEN:200 BLUE:200"
        + "    RED:200 GREEN:200 BLUE:200    \n"
        + "RED:200 GREEN:200 BLUE:200    RED:200 GREEN:200 BLUE:200    RED:200 GREEN:200 BLUE:200"
        + "    RED:200 GREEN:200 BLUE:200    \n"
        + "RED:200 GREEN:200 BLUE:200    RED:200 GREEN:200 BLUE:200    RED:200 "
        + "GREEN:200 BLUE:200    RED:200 GREEN:200 BLUE:200    \n";

    assertEquals(expected, resultImageWoBlue.toString());
    assertEquals(expected, resultImageWoRed.toString());
    assertEquals(expected, resultImageWoGreen.toString());
  }

  @Test
  public void testGetValueTrichromatic() {
    Image testImage = new MyImage("test/img/simple.ppm");
    Image resultImage = imageService.getValue(testImage);
    String expected = "RED:100 GREEN:100 BLUE:100    RED:200 GREEN:200 BLUE:200    \n"
                      + "RED:245 GREEN:245 BLUE:245    RED:105 GREEN:105 BLUE:105    \n"
                      + "RED:205 GREEN:205 BLUE:205    RED:215 GREEN:215 BLUE:215    \n";
    assertEquals(expected, resultImage.toString());
  }

  @Test
  public void testGetIntensityMonochromatic() {
    Image testImageBlack = new MyImage("test/img/black.ppm");
    Image testImageWhite = new MyImage("test/img/white.ppm");
    Image testImageRed = new MyImage("test/img/red.ppm");
    Image testImageGreen = new MyImage("test/img/green.ppm");
    Image testImageBlue = new MyImage("test/img/blue.ppm");

    Image resultImageBlack = imageService.getIntensity(testImageBlack);
    Image resultImageWhite = imageService.getIntensity(testImageWhite);
    Image resultImageRed = imageService.getIntensity(testImageRed);
    Image resultImageGreen = imageService.getIntensity(testImageGreen);
    Image resultImageBlue = imageService.getIntensity(testImageBlue);

    String blackExpected =
        "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 "
        + "BLUE:0    \n"
        + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 "
        + "BLUE:0    \n"
        + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 "
        + "BLUE:0    \n"
        + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 "
        + "BLUE:0    \n";
    String whiteExpected =
        "RED:255 GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255  "
        + "  RED:255 GREEN:255 BLUE:255    \n"
        + "RED:255 GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255"
        + "    RED:255 GREEN:255 BLUE:255    \n"
        + "RED:255 GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255"
        + "    RED:255 GREEN:255 BLUE:255    \n"
        + "RED:255 GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255"
        + "    RED:255 GREEN:255 BLUE:255    \n";
    String otherExpected =
        "RED:85 GREEN:85 BLUE:85    RED:85 GREEN:85 BLUE:85    RED:85 GREEN:85 BLUE:85    RED:85 "
        + "GREEN:85 BLUE:85    \n"
        + "RED:85 GREEN:85 BLUE:85    RED:85 GREEN:85 BLUE:85    RED:85 GREEN:85 BLUE:85    "
        + "RED:85 GREEN:85 BLUE:85    \n"
        + "RED:85 GREEN:85 BLUE:85    RED:85 GREEN:85 BLUE:85    RED:85 GREEN:85 BLUE:85    "
        + "RED:85 GREEN:85 BLUE:85    \n"
        + "RED:85 GREEN:85 BLUE:85    RED:85 GREEN:85 BLUE:85    RED:85 GREEN:85 BLUE:85    "
        + "RED:85 GREEN:85 BLUE:85    \n";

    assertEquals(blackExpected, resultImageBlack.toString());
    assertEquals(whiteExpected, resultImageWhite.toString());
    assertEquals(otherExpected, resultImageRed.toString());
    assertEquals(otherExpected, resultImageGreen.toString());
    assertEquals(otherExpected, resultImageBlue.toString());
  }

  @Test
  public void testGetIntensityDuoColor() {
    Image testImage = new MyImage("test/img/duoColor.png");
    Image resultImage = imageService.getIntensity(testImage);

    String expected = "RED:183 GREEN:183 BLUE:183    RED:183 GREEN:183 BLUE:183    \n"
                      + "RED:183 GREEN:183 BLUE:183    RED:183 GREEN:183 BLUE:183    \n";

    assertEquals(expected, resultImage.toString());
  }

  @Test
  public void testGetIntensityDichromatic() {
    Image testImageWoBlue = new MyImage("test/img/woBlue.ppm");
    Image testImageWoRed = new MyImage("test/img/woRed.ppm");
    Image testImageWoGreen = new MyImage("test/img/woGreen.ppm");

    Image resultImageWoBlue = imageService.getIntensity(testImageWoBlue);
    Image resultImageWoRed = imageService.getIntensity(testImageWoRed);
    Image resultImageWoGreen = imageService.getIntensity(testImageWoGreen);

    String expected =
        "RED:133 GREEN:133 BLUE:133    RED:133 GREEN:133 BLUE:133    RED:133 GREEN:133 BLUE:133  "
        + "  RED:133 GREEN:133 BLUE:133    \n"
        + "RED:133 GREEN:133 BLUE:133    RED:133 GREEN:133 BLUE:133    RED:133 GREEN:133 BLUE:133"
        + "    RED:133 GREEN:133 BLUE:133    \n"
        + "RED:133 GREEN:133 BLUE:133    RED:133 GREEN:133 BLUE:133    RED:133 GREEN:133 BLUE:133"
        + "    RED:133 GREEN:133 BLUE:133    \n"
        + "RED:133 GREEN:133 BLUE:133    RED:133 GREEN:133 BLUE:133    RED:133 GREEN:133 BLUE:133"
        + "    RED:133 GREEN:133 BLUE:133    \n";
    assertEquals(expected, resultImageWoBlue.toString());
    assertEquals(expected, resultImageWoRed.toString());
    assertEquals(expected, resultImageWoGreen.toString());
  }

  @Test
  public void testGetIntensityTrichromatic() {
    Image testImage = new MyImage("test/img/simple.ppm");
    Image resultImage = imageService.getIntensity(testImage);
    String expected = "RED:92 GREEN:92 BLUE:92    RED:127 GREEN:127 BLUE:127    \n"
                      + "RED:132 GREEN:132 BLUE:132    RED:82 GREEN:82 BLUE:82    \n"
                      + "RED:128 GREEN:128 BLUE:128    RED:175 GREEN:175 BLUE:175    \n";
    assertEquals(expected, resultImage.toString());
  }


  @Test
  public void testGetLumaMonochromatic() {
    Image testImageBlack = new MyImage("test/img/black.ppm");
    Image testImageWhite = new MyImage("test/img/white.ppm");
    Image testImageRed = new MyImage("test/img/red.ppm");
    Image testImageGreen = new MyImage("test/img/green.ppm");
    Image testImageBlue = new MyImage("test/img/blue.ppm");

    Image resultImageBlack = imageService.getLuma(testImageBlack);
    Image resultImageWhite = imageService.getLuma(testImageWhite);
    Image resultImageRed = imageService.getLuma(testImageRed);
    Image resultImageGreen = imageService.getLuma(testImageGreen);
    Image resultImageBlue = imageService.getLuma(testImageBlue);

    String blackExpected =
        "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 "
        + "BLUE:0    \n"
        + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 "
        + "BLUE:0    \n"
        + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 "
        + "BLUE:0    \n"
        + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 "
        + "BLUE:0    \n";
    String whiteExpected =
        "RED:255 GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255  "
        + "  RED:255 GREEN:255 BLUE:255    \n"
        + "RED:255 GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255"
        + "    RED:255 GREEN:255 BLUE:255    \n"
        + "RED:255 GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255"
        + "    RED:255 GREEN:255 BLUE:255    \n"
        + "RED:255 GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255"
        + "    RED:255 GREEN:255 BLUE:255    \n";
    String redExpected =
        "RED:54 GREEN:54 BLUE:54    RED:54 GREEN:54 BLUE:54    RED:54 GREEN:54 BLUE:54    RED:54 "
        + "GREEN:54 BLUE:54    \n"
        + "RED:54 GREEN:54 BLUE:54    RED:54 GREEN:54 BLUE:54    RED:54 GREEN:54 BLUE:54    "
        + "RED:54 GREEN:54 BLUE:54    \n"
        + "RED:54 GREEN:54 BLUE:54    RED:54 GREEN:54 BLUE:54    RED:54 GREEN:54 BLUE:54    "
        + "RED:54 GREEN:54 BLUE:54    \n"
        + "RED:54 GREEN:54 BLUE:54    RED:54 GREEN:54 BLUE:54    RED:54 GREEN:54 BLUE:54    "
        + "RED:54 GREEN:54 BLUE:54    \n";
    String greenExpected =
        "RED:182 GREEN:182 BLUE:182    RED:182 GREEN:182 BLUE:182    RED:182 GREEN:182 BLUE:182  "
        + "  RED:182 GREEN:182 BLUE:182    \n"
        + "RED:182 GREEN:182 BLUE:182    RED:182 GREEN:182 BLUE:182    RED:182 GREEN:182 BLUE:182"
        + "    RED:182 GREEN:182 BLUE:182    \n"
        + "RED:182 GREEN:182 BLUE:182    RED:182 GREEN:182 BLUE:182    RED:182 GREEN:182 BLUE:182"
        + "    RED:182 GREEN:182 BLUE:182    \n"
        + "RED:182 GREEN:182 BLUE:182    RED:182 GREEN:182 BLUE:182    RED:182 GREEN:182 BLUE:182"
        + "    RED:182 GREEN:182 BLUE:182    \n";
    String blueExpected =
        "RED:18 GREEN:18 BLUE:18    RED:18 GREEN:18 BLUE:18    RED:18 GREEN:18 BLUE:18    RED:18 "
        + "GREEN:18 BLUE:18    \n"
        + "RED:18 GREEN:18 BLUE:18    RED:18 GREEN:18 BLUE:18    RED:18 GREEN:18 BLUE:18    "
        + "RED:18 GREEN:18 BLUE:18    \n"
        + "RED:18 GREEN:18 BLUE:18    RED:18 GREEN:18 BLUE:18    RED:18 GREEN:18 BLUE:18    "
        + "RED:18 GREEN:18 BLUE:18    \n"
        + "RED:18 GREEN:18 BLUE:18    RED:18 GREEN:18 BLUE:18    RED:18 GREEN:18 BLUE:18    "
        + "RED:18 GREEN:18 BLUE:18    \n";

    assertEquals(blackExpected, resultImageBlack.toString());
    assertEquals(whiteExpected, resultImageWhite.toString());
    assertEquals(redExpected, resultImageRed.toString());
    assertEquals(greenExpected, resultImageGreen.toString());
    assertEquals(blueExpected, resultImageBlue.toString());
  }


  @Test
  public void testGetLumaDuoColor() {
    Image testImage = new MyImage("test/img/duoColor.png");
    Image resultImage = imageService.getLuma(testImage);

    String expected = "RED:182 GREEN:182 BLUE:182    RED:182 GREEN:182 BLUE:182    \n"
                      + "RED:139 GREEN:139 BLUE:139    RED:139 GREEN:139 BLUE:139    \n";

    assertEquals(expected, resultImage.toString());
  }


  @Test
  public void testGetLumaDichromatic() {
    Image testImageWoBlue = new MyImage("test/img/woBlue.ppm");
    Image testImageWoRed = new MyImage("test/img/woRed.ppm");
    Image testImageWoGreen = new MyImage("test/img/woGreen.ppm");

    Image resultImageWoBlue = imageService.getLuma(testImageWoBlue);
    Image resultImageWoRed = imageService.getLuma(testImageWoRed);
    Image resultImageWoGreen = imageService.getLuma(testImageWoGreen);

    String expected =
        "RED:186 GREEN:186 BLUE:186    RED:186 GREEN:186 BLUE:186    RED:186 GREEN:186 BLUE:186  "
        + "  RED:186 GREEN:186 BLUE:186    \n"
        + "RED:186 GREEN:186 BLUE:186    RED:186 GREEN:186 BLUE:186    RED:186 GREEN:186 BLUE:186"
        + "    RED:186 GREEN:186 BLUE:186    \n"
        + "RED:186 GREEN:186 BLUE:186    RED:186 GREEN:186 BLUE:186    RED:186 GREEN:186 BLUE:186"
        + "    RED:186 GREEN:186 BLUE:186    \n"
        + "RED:186 GREEN:186 BLUE:186    RED:186 GREEN:186 BLUE:186    RED:186 "
        + "GREEN:186 BLUE:186    RED:186 GREEN:186 BLUE:186    \n";
    assertEquals(expected, resultImageWoBlue.toString());
    expected =
        "RED:157 GREEN:157 BLUE:157    RED:157 GREEN:157 BLUE:157    RED:157 GREEN:157 BLUE:157  "
        + "  RED:157 GREEN:157 BLUE:157    \n"
        + "RED:157 GREEN:157 BLUE:157    RED:157 GREEN:157 BLUE:157    RED:157 GREEN:157 BLUE:157"
        + "    RED:157 GREEN:157 BLUE:157    \n"
        + "RED:157 GREEN:157 BLUE:157    RED:157 GREEN:157 BLUE:157    RED:157 GREEN:157 BLUE:157"
        + "    RED:157 GREEN:157 BLUE:157    \n"
        + "RED:157 GREEN:157 BLUE:157    RED:157 GREEN:157 BLUE:157    RED:157 GREEN:157 BLUE:157"
        + "    RED:157 GREEN:157 BLUE:157    \n";
    assertEquals(expected, resultImageWoRed.toString());
    expected =
        "RED:57 GREEN:57 BLUE:57    RED:57 GREEN:57 BLUE:57    RED:57 GREEN:57 BLUE:57    RED:57 "
        + "GREEN:57 BLUE:57    \n"
        + "RED:57 GREEN:57 BLUE:57    RED:57 GREEN:57 BLUE:57    RED:57 GREEN:57 BLUE:57    "
        + "RED:57 GREEN:57 BLUE:57    \n"
        + "RED:57 GREEN:57 BLUE:57    RED:57 GREEN:57 BLUE:57    RED:57 GREEN:57 BLUE:57    "
        + "RED:57 GREEN:57 BLUE:57    \n"
        + "RED:57 GREEN:57 BLUE:57    RED:57 GREEN:57 BLUE:57    RED:57 GREEN:57 BLUE:57    "
        + "RED:57 GREEN:57 BLUE:57    \n";
    assertEquals(expected, resultImageWoGreen.toString());
  }

  @Test
  public void testGetLumaTrichromatic() {
    Image testImage = new MyImage("test/img/simple.ppm");
    Image resultImage = imageService.getLuma(testImage);
    String expected = "RED:89 GREEN:89 BLUE:89    RED:160 GREEN:160 BLUE:160    \n"
                      + "RED:95 GREEN:95 BLUE:95    RED:74 GREEN:74 BLUE:74    \n"
                      + "RED:173 GREEN:173 BLUE:173    RED:200 GREEN:200 BLUE:200    \n";
    assertEquals(expected, resultImage.toString());
  }

  //  Flip an image horizontally or vertically.

  //different formats
  //flip horizontally
  //flip vertically
  //combination of the above two.
  //flip for multiple times (more than 2)
  @Test
  public void testFlipHorizontally() {
    Image testImagePPM = new MyImage("res/car.png");
    Image flippedImagePPM = imageService.flip(testImagePPM, Axis.Y);
    Image expectedImagePPM = new MyImage("res/car_horizontallyFlipped.png");
    assertEquals(flippedImagePPM, expectedImagePPM);
  }

  @Test
  public void testFlipVertically() {
    Image testImagePPM = new MyImage("res/car.png");
    Image flippedImagePPM = imageService.flip(testImagePPM, Axis.X);
    Image expectedImagePPM = new MyImage("res/car_verticallyFlipped.png");
    assertEquals(flippedImagePPM, expectedImagePPM);
  }

  @Test
  public void testFlipHorizontallyTwice() {
    Image testImagePPM = new MyImage("res/car.png");
    Image flippedImagePPM = imageService.flip(testImagePPM, Axis.Y);
    Image doubleFlippedImagePPM = imageService.flip(flippedImagePPM, Axis.Y);
    Image expectedImagePPM = new MyImage("res/car.png");
    assertEquals(doubleFlippedImagePPM, expectedImagePPM);
  }

  @Test
  public void testFlipVerticallyTwice() {
    Image testImagePPM = new MyImage("res/car.png");
    Image flippedImagePPM = imageService.flip(testImagePPM, Axis.X);
    Image doubleFlippedImagePPM = imageService.flip(flippedImagePPM, Axis.X);
    Image expectedImagePPM = new MyImage("res/car.png");
    assertEquals(doubleFlippedImagePPM, expectedImagePPM);
  }

  @Test
  public void testFlipMultipleTimes() {
    Image testImagePPM = new MyImage("res/car.png");
    Image flippedImagePPM = imageService.flip(testImagePPM, Axis.Y);
    Image doubleFlippedImagePPM = imageService.flip(flippedImagePPM, Axis.Y);
    Image tripleFlippedImagePPM = imageService.flip(doubleFlippedImagePPM, Axis.Y);
    Image expectedImagePPM = new MyImage("res/car_horizontallyFlipped.png");
    assertEquals(tripleFlippedImagePPM, expectedImagePPM);
  }


  //  Brighten or darken an image.
  //different formats
  //brighten
  //darken
  //pixel that's already the darkest
  //pixel that's already the lightest
  //brighten or darken an image for multiple times & in combinations

  @Test
  public void testBrightenOnce() {
    Image testImage = new MyImage("test/img/simple.ppm");
    Image resultImage = imageService.brighten(testImage, 2);

    String expected = "RED:102 GREEN:87 BLUE:92    RED:32 GREEN:202 BLUE:152    \n"
                      + "RED:247 GREEN:52 BLUE:102    RED:107 GREEN:67 BLUE:77    \n"
                      + "RED:97 GREEN:207 BLUE:87    RED:217 GREEN:207 BLUE:107    \n";
    assertEquals(expected, resultImage.toString());
  }

  @Test
  public void testBrightenTwice() {
    Image testImage = new MyImage("test/img/simple.ppm");
    Image resultImage = imageService.brighten(testImage, 2);
    resultImage = imageService.brighten(resultImage, 2);

    String expected = "RED:104 GREEN:89 BLUE:94    RED:34 GREEN:204 BLUE:154    \n"
                      + "RED:249 GREEN:54 BLUE:104    RED:109 GREEN:69 BLUE:79    \n"
                      + "RED:99 GREEN:209 BLUE:89    RED:219 GREEN:209 BLUE:109    \n";
    assertEquals(expected, resultImage.toString());
  }

  @Test
  public void testBrightenWhite() {
    Image testImage = new MyImage("test/img/white.ppm");
    Image resultImage = imageService.brighten(testImage, 2);
    Image expectedImage = new MyImage("test/img/white.ppm");
    assertEquals(expectedImage, resultImage);
  }

  //part of the image/channel is already max while others are not
  @Test
  public void testBrightenPartiallyMax() {
    Image testImage = new MyImage("test/img/simple.ppm");
    Image resultImage = imageService.brighten(testImage, 150);
    String expected = "RED:250 GREEN:235 BLUE:240    RED:180 GREEN:255 BLUE:255    \n"
                      + "RED:255 GREEN:200 BLUE:250    RED:255 GREEN:215 BLUE:225    \n"
                      + "RED:245 GREEN:255 BLUE:235    RED:255 GREEN:255 BLUE:255    \n";
    assertEquals(expected, resultImage.toString());
    expected = "RED:255 GREEN:240 BLUE:245    RED:185 GREEN:255 BLUE:255    \n"
               + "RED:255 GREEN:205 BLUE:255    RED:255 GREEN:220 BLUE:230    \n"
               + "RED:250 GREEN:255 BLUE:240    RED:255 GREEN:255 BLUE:255    \n";
    resultImage = imageService.brighten(resultImage, 5);
    assertEquals(expected, resultImage.toString());
  }


  @Test
  public void testDarkenOnce() {
    Image testImage = new MyImage("test/img/simple.ppm");
    Image resultImage = imageService.brighten(testImage, -2);

    String expected = "RED:98 GREEN:83 BLUE:88    RED:28 GREEN:198 BLUE:148    \n"
                      + "RED:243 GREEN:48 BLUE:98    RED:103 GREEN:63 BLUE:73    \n"
                      + "RED:93 GREEN:203 BLUE:83    RED:213 GREEN:203 BLUE:103    \n";
    assertEquals(expected, resultImage.toString());
  }

  @Test
  public void testDarkenTwice() {
    Image testImage = new MyImage("test/img/simple.ppm");
    Image resultImage = imageService.brighten(testImage, -2);
    resultImage = imageService.brighten(resultImage, -2);

    String expected = "RED:96 GREEN:81 BLUE:86    RED:26 GREEN:196 BLUE:146    \n"
                      + "RED:241 GREEN:46 BLUE:96    RED:101 GREEN:61 BLUE:71    \n"
                      + "RED:91 GREEN:201 BLUE:81    RED:211 GREEN:201 BLUE:101    \n";
    assertEquals(expected, resultImage.toString());
  }

  @Test
  public void testDarkenBlack() {
    Image testImage = new MyImage("test/img/black.ppm");
    Image resultImage = imageService.brighten(testImage, -2);
    Image expectedImage = new MyImage("test/img/black.ppm");
    assertEquals(expectedImage, resultImage);
  }

  //part of the image/channel is already max while others are not
  @Test
  public void testDarkenPartiallyMin() {
    Image testImage = new MyImage("test/img/simple.ppm");
    Image resultImage = imageService.brighten(testImage, -100);
    String expected = "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:100 BLUE:50    \n"
                      + "RED:145 GREEN:0 BLUE:0    RED:5 GREEN:0 BLUE:0    \n"
                      + "RED:0 GREEN:105 BLUE:0    RED:115 GREEN:105 BLUE:5    \n";
    assertEquals(expected, resultImage.toString());
    expected = "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:95 BLUE:45    \n"
               + "RED:140 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    \n"
               + "RED:0 GREEN:100 BLUE:0    RED:110 GREEN:100 BLUE:0    \n";
    resultImage = imageService.brighten(resultImage, -5);
    assertEquals(expected, resultImage.toString());
  }

  @Test
  public void testDarkenThenBrighten() {
    Image testImage = new MyImage("test/img/simple.ppm");
    Image resultImage = imageService.brighten(testImage, -2);
    resultImage = imageService.brighten(resultImage, 2);
    Image expectedImage = new MyImage("test/img/simple.ppm");
    assertEquals(expectedImage, resultImage);
  }

  @Test
  public void testBrightenTwiceThenDarken() {
    Image testImage = new MyImage("test/img/simple.ppm");
    Image resultImage = imageService.brighten(testImage, 2);
    resultImage = imageService.brighten(resultImage, 2);
    resultImage = imageService.brighten(resultImage, -2);
    String expected =
        "RED:102 GREEN:87 BLUE:92    RED:32 GREEN:202 BLUE:152    \n"
        + "RED:247 GREEN:52 BLUE:102    RED:107 GREEN:67 BLUE:77    \n"
        + "RED:97 GREEN:207 BLUE:87    RED:217 GREEN:207 BLUE:107    \n";
    assertEquals(expected, resultImage.toString());
  }


  //  Split a single image into 3 images representing each of the three channels.
  // different formats
  // of monochromatic, dichromatic and trichromatic images
  // repeatedly split
  @Test
  public void testSplitChannelMonochromatic() {
    Image testImage = new MyImage("test/img/red.ppm");
    Image[] resultImages = imageService.splitChannel(testImage);

    String expectedR = "RED:255 GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255    RED:255 "
                       + "GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255    \n"
                       + "RED:255 GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255    RED:255 "
                       + "GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255    \n"
                       + "RED:255 GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255    RED:255 "
                       + "GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255    \n"
                       + "RED:255 GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255    RED:255 "
                       + "GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255    \n";
    String expectedG = "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    "
                       + "RED:0 GREEN:0 BLUE:0    \n"
                       + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    "
                       + "RED:0 GREEN:0 BLUE:0    \n"
                       + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    "
                       + "RED:0 GREEN:0 BLUE:0    \n"
                       + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    "
                       + "RED:0 GREEN:0 BLUE:0    \n";
    String expectedB = "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    "
                       + "RED:0 GREEN:0 BLUE:0    \n"
                       + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    "
                       + "RED:0 GREEN:0 BLUE:0    \n"
                       + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    "
                       + "RED:0 GREEN:0 BLUE:0    \n"
                       + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    "
                       + "RED:0 GREEN:0 BLUE:0    \n";
    assertEquals(expectedR, resultImages[0].toString());
    assertEquals(expectedG, resultImages[1].toString());
    assertEquals(expectedB, resultImages[2].toString());
  }


  @Test
  public void testSplitChannelDuoColor() {
    Image testImage = new MyImage("test/img/duoColor.png");
    Image[] resultImages = imageService.splitChannel(testImage);
    String expectedR = "RED:100 GREEN:100 BLUE:100    RED:100 GREEN:100 BLUE:100    \n"
                       + "RED:250 GREEN:250 BLUE:250    RED:250 GREEN:250 BLUE:250    \n";
    String expectedG = "RED:200 GREEN:200 BLUE:200    RED:200 GREEN:200 BLUE:200    \n"
                       + "RED:100 GREEN:100 BLUE:100    RED:100 GREEN:100 BLUE:100    \n";
    String expectedB = "RED:250 GREEN:250 BLUE:250    RED:250 GREEN:250 BLUE:250    \n"
                       + "RED:200 GREEN:200 BLUE:200    RED:200 GREEN:200 BLUE:200    \n";
    assertEquals(expectedR, resultImages[0].toString());
    assertEquals(expectedG, resultImages[1].toString());
    assertEquals(expectedB, resultImages[2].toString());
  }


  @Test
  public void testSplitChannelDichromatic() {
    Image testImage = new MyImage("test/img/woBlue.ppm");
    Image[] resultImages = imageService.splitChannel(testImage);
    String expectedR = "RED:200 GREEN:200 BLUE:200    RED:200 GREEN:200 BLUE:200    RED:200 "
                       + "GREEN:200 BLUE:200    RED:200 GREEN:200 BLUE:200    \n"
                       + "RED:200 GREEN:200 BLUE:200    RED:200 GREEN:200 BLUE:200    RED:200 "
                       + "GREEN:200 BLUE:200    RED:200 GREEN:200 BLUE:200    \n"
                       + "RED:200 GREEN:200 BLUE:200    RED:200 GREEN:200 BLUE:200    RED:200 "
                       + "GREEN:200 BLUE:200    RED:200 GREEN:200 BLUE:200    \n"
                       + "RED:200 GREEN:200 BLUE:200    RED:200 GREEN:200 BLUE:200    RED:200 "
                       + "GREEN:200 BLUE:200    RED:200 GREEN:200 BLUE:200    \n";
    String expectedG = "RED:200 GREEN:200 BLUE:200    RED:200 GREEN:200 BLUE:200    RED:200 "
                       + "GREEN:200 BLUE:200    RED:200 GREEN:200 BLUE:200    \n"
                       + "RED:200 GREEN:200 BLUE:200    RED:200 GREEN:200 BLUE:200    RED:200 "
                       + "GREEN:200 BLUE:200    RED:200 GREEN:200 BLUE:200    \n"
                       + "RED:200 GREEN:200 BLUE:200    RED:200 GREEN:200 BLUE:200    RED:200 "
                       + "GREEN:200 BLUE:200    RED:200 GREEN:200 BLUE:200    \n"
                       + "RED:200 GREEN:200 BLUE:200    RED:200 GREEN:200 BLUE:200    RED:200 "
                       + "GREEN:200 BLUE:200    RED:200 GREEN:200 BLUE:200    \n";
    String expectedB = "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    "
                       + "RED:0 GREEN:0 BLUE:0    \n"
                       + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    "
                       + "RED:0 GREEN:0 BLUE:0    \n"
                       + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    "
                       + "RED:0 GREEN:0 BLUE:0    \n"
                       + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    "
                       + "RED:0 GREEN:0 BLUE:0    \n";
    assertEquals(expectedR, resultImages[0].toString());
    assertEquals(expectedG, resultImages[1].toString());
    assertEquals(expectedB, resultImages[2].toString());
  }

  @Test
  public void testSplitChannelTrichromatic() {
    Image testImage = new MyImage("test/img/simple.ppm");
    Image[] resultImages = imageService.splitChannel(testImage);

    String expectedR = "RED:100 GREEN:100 BLUE:100    RED:30 GREEN:30 BLUE:30    \n"
                       + "RED:245 GREEN:245 BLUE:245    RED:105 GREEN:105 BLUE:105    \n"
                       + "RED:95 GREEN:95 BLUE:95    RED:215 GREEN:215 BLUE:215    \n";
    String expectedG = "RED:85 GREEN:85 BLUE:85    RED:200 GREEN:200 BLUE:200    \n"
                       + "RED:50 GREEN:50 BLUE:50    RED:65 GREEN:65 BLUE:65    \n"
                       + "RED:205 GREEN:205 BLUE:205    RED:205 GREEN:205 BLUE:205    \n";
    String expectedB = "RED:90 GREEN:90 BLUE:90    RED:150 GREEN:150 BLUE:150    \n"
                       + "RED:100 GREEN:100 BLUE:100    RED:75 GREEN:75 BLUE:75    \n"
                       + "RED:85 GREEN:85 BLUE:85    RED:105 GREEN:105 BLUE:105    \n";

    assertEquals(expectedR, resultImages[0].toString());
    assertEquals(expectedG, resultImages[1].toString());
    assertEquals(expectedB, resultImages[2].toString());
  }

  @Test
  public void testSplitChannelTrichromaticTwice() {
    Image testImage = new MyImage("test/img/simple.ppm");
    Image[] resultImages = imageService.splitChannel(testImage);
    resultImages = imageService.splitChannel(resultImages[0]);

    String expectedR = "RED:100 GREEN:100 BLUE:100    RED:30 GREEN:30 BLUE:30    \n"
                       + "RED:245 GREEN:245 BLUE:245    RED:105 GREEN:105 BLUE:105    \n"
                       + "RED:95 GREEN:95 BLUE:95    RED:215 GREEN:215 BLUE:215    \n";
    String expectedG = "RED:100 GREEN:100 BLUE:100    RED:30 GREEN:30 BLUE:30    \n"
                       + "RED:245 GREEN:245 BLUE:245    RED:105 GREEN:105 BLUE:105    \n"
                       + "RED:95 GREEN:95 BLUE:95    RED:215 GREEN:215 BLUE:215    \n";
    String expectedB = "RED:100 GREEN:100 BLUE:100    RED:30 GREEN:30 BLUE:30    \n"
                       + "RED:245 GREEN:245 BLUE:245    RED:105 GREEN:105 BLUE:105    \n"
                       + "RED:95 GREEN:95 BLUE:95    RED:215 GREEN:215 BLUE:215    \n";

    assertEquals(expectedR, resultImages[0].toString());
    assertEquals(expectedG, resultImages[1].toString());
    assertEquals(expectedB, resultImages[2].toString());
  }

  //  Combine three greyscale image into a single color image whose R,G,B values come from the
  //  three images.
  // (at least one of the)input is not legal greyscale image.
  // different formats
  // result color is black/white/red/green/blue/mixed color
  @Test
  public void testCombineChannels() {
    Image image = new MyImage("test/img/red.ppm");
    Image resultImages =
        imageService.combineChannels(new Channel[]{Channel.RED, Channel.GREEN, Channel.BLUE},
            imageService.splitChannel(image));
    assertEquals(image, resultImages);

    image = new MyImage("test/img/woBlue.ppm");
    resultImages =
        imageService.combineChannels(new Channel[]{Channel.RED, Channel.GREEN, Channel.BLUE},
            imageService.splitChannel(image));
    assertEquals(image, resultImages);

    //rectangle
    image = new MyImage("test/img/simple.ppm");
    resultImages =
        imageService.combineChannels(new Channel[]{Channel.RED, Channel.GREEN, Channel.BLUE},
            imageService.splitChannel(image));
    assertEquals(image, resultImages);
  }

  @Test
  public void testCombineChannelsRose() {
    Image expectedImage = new MyImage("test/img/rose.ppm");
    Image resultImages =
        imageService.combineChannels(new Channel[]{Channel.RED, Channel.GREEN, Channel.BLUE},
            imageService.splitChannel(expectedImage));
    assertEquals(expectedImage, resultImages);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCombineChannelsIllegalInputNotGreyscale() {
    Image imageR = new MyImage("test/img/white.ppm");
    Image imageG = new MyImage("test/img/green.ppm");
    Image imageB = new MyImage("test/img/blue.ppm");

    imageService.combineChannels(new Channel[]{Channel.RED, Channel.GREEN, Channel.BLUE},
        new Image[]{imageR, imageG, imageB});
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCombineChannelsIllegalInputNoChannel() {
    imageService.combineChannels(new Channel[]{}, new Image[]{});
  }

  //  Blur or sharpen an image as defined above.
  // different formats
  // of monochromatic, dichromatic and trichromatic images
  // repeatedly sharpen
  @Test
  public void testBlurSimple() {
    Image testImage = new MyImage("test/img/simple.ppm");
    Image resultImages = imageService.blur(testImage, 1, Axis.X);
    String expected =
        "RED:66 GREEN:57 BLUE:58    RED:48 GREEN:72 BLUE:64    \n"
        + "RED:114 GREEN:82 BLUE:72    RED:100 GREEN:91 BLUE:74    \n"
        + "RED:88 GREEN:87 BLUE:52    RED:94 GREEN:88 BLUE:53    \n";
    assertEquals(expected, resultImages.toString());
  }

  @Test
  public void testBlurSimplePercentage() {
    Image testImage = new MyImage("test/img/simple.ppm");
    Image resultImage = imageService.blur(testImage, 0.5f, Axis.X);
    String expectedImage = "RED:56 GREEN:28 BLUE:35    RED:30 GREEN:200 BLUE:150    \n"
                           + "RED:86 GREEN:49 BLUE:47    RED:105 GREEN:65 BLUE:75    \n"
                           + "RED:54 GREEN:58 BLUE:34    RED:215 GREEN:205 BLUE:105    \n";
    assertEquals(expectedImage, resultImage.toString());
  }

  @Test
  public void testBlurSimplePercentageSmall() {
    Image testImage = new MyImage("test/img/red.png");
    Image resultImage = imageService.blur(testImage, 0.3f, Axis.X);
    String expectedImage =
        "RED:96 GREEN:0 BLUE:0    RED:255 GREEN:0 BLUE:0    "
        + "RED:255 GREEN:0 BLUE:0    RED:255 GREEN:0 BLUE:0    \n"
        + "RED:128 GREEN:0 BLUE:0    RED:255 GREEN:0 BLUE:0    "
        + "RED:255 GREEN:0 BLUE:0    RED:255 GREEN:0 BLUE:0    \n"
        + "RED:128 GREEN:0 BLUE:0    RED:255 GREEN:0 BLUE:0    "
        + "RED:255 GREEN:0 BLUE:0    RED:255 GREEN:0 BLUE:0    \n"
        + "RED:96 GREEN:0 BLUE:0    RED:255 GREEN:0 BLUE:0    "
        + "RED:255 GREEN:0 BLUE:0    RED:255 GREEN:0 BLUE:0    \n";
    assertEquals(expectedImage, resultImage.toString());
  }

  @Test
  public void testBlurSimplePercentageBig() {
    Image testImage = new MyImage("test/img/red.png");
    Image resultImage = imageService.blur(testImage, 0.7f, Axis.X);
    String expectedImage =
        "RED:143 GREEN:0 BLUE:0    RED:191 GREEN:0 BLUE:0    "
        + "RED:143 GREEN:0 BLUE:0    RED:255 GREEN:0 BLUE:0    \n"
        + "RED:191 GREEN:0 BLUE:0    RED:255 GREEN:0 BLUE:0    "
        + "RED:191 GREEN:0 BLUE:0    RED:255 GREEN:0 BLUE:0    \n"
        + "RED:191 GREEN:0 BLUE:0    RED:255 GREEN:0 BLUE:0    "
        + "RED:191 GREEN:0 BLUE:0    RED:255 GREEN:0 BLUE:0    \n"
        + "RED:143 GREEN:0 BLUE:0    RED:191 GREEN:0 BLUE:0    "
        + "RED:143 GREEN:0 BLUE:0    RED:255 GREEN:0 BLUE:0    \n";
    assertEquals(expectedImage, resultImage.toString());
  }

  @Test
  public void testBlurOnce() {
    Image testImage = new MyImage("res/cupcake.png");
    Image resultImages = imageService.blur(testImage, 1, Axis.X);
    Image expectedImage = new MyImage("res/cupcake_blurOnce.png");
    assertEquals(expectedImage, resultImages);
  }


  @Test
  public void testSharpenSimple() {
    Image testImage = new MyImage("test/img/simple.ppm");
    Image resultImages = imageService.sharpen(testImage, 1, Axis.X);
    String expected =
        "RED:156 GREEN:113 BLUE:148    RED:104 GREEN:199 BLUE:193    \n"
        + "RED:255 GREEN:240 BLUE:226    RED:255 GREEN:251 BLUE:208    \n"
        + "RED:220 GREEN:249 BLUE:125    RED:255 GREEN:249 BLUE:140    \n";
    assertEquals(expected, resultImages.toString());
  }

  @Test
  public void testSharpenSimplePercentage() {
    Image testImage = new MyImage("test/img/simple.ppm");
    Image resultImage = imageService.sharpen(testImage, 0.5f, Axis.X);
    String expectedImage = "RED:149 GREEN:72 BLUE:104    RED:30 GREEN:200 BLUE:150    \n"
                           + "RED:255 GREEN:123 BLUE:144    RED:105 GREEN:65 BLUE:75    \n"
                           + "RED:144 GREEN:207 BLUE:99    RED:215 GREEN:205 BLUE:105    \n";
    assertEquals(expectedImage, resultImage.toString());
  }

  @Test
  public void testSharpenSimplePercentageSmall() {
    Image testImage = new MyImage("test/img/woBlue.png");
    Image resultImage = imageService.sharpen(testImage, 0.3f, Axis.X);
    String expectedImage =
        "RED:225 GREEN:225 BLUE:0    RED:200 GREEN:200 BLUE:0    "
        + "RED:200 GREEN:200 BLUE:0    RED:200 GREEN:200 BLUE:0    \n"
        + "RED:255 GREEN:255 BLUE:0    RED:200 GREEN:200 BLUE:0    "
        + "RED:200 GREEN:200 BLUE:0    RED:200 GREEN:200 BLUE:0    \n"
        + "RED:255 GREEN:255 BLUE:0    RED:200 GREEN:200 BLUE:0    "
        + "RED:200 GREEN:200 BLUE:0    RED:200 GREEN:200 BLUE:0    \n"
        + "RED:225 GREEN:225 BLUE:0    RED:200 GREEN:200 BLUE:0    "
        + "RED:200 GREEN:200 BLUE:0    RED:200 GREEN:200 BLUE:0    \n";
    assertEquals(expectedImage, resultImage.toString());
  }

  @Test
  public void testSharpenSimplePercentageBig() {
    Image testImage = new MyImage("test/img/woBlue.png");
    Image resultImage = imageService.sharpen(testImage, 0.7f, Axis.X);
    String expectedImage =
        "RED:225 GREEN:225 BLUE:0    RED:255 GREEN:255 BLUE:0    "
        + "RED:225 GREEN:225 BLUE:0    RED:200 GREEN:200 BLUE:0    \n"
        + "RED:255 GREEN:255 BLUE:0    RED:255 GREEN:255 BLUE:0    "
        + "RED:255 GREEN:255 BLUE:0    RED:200 GREEN:200 BLUE:0    \n"
        + "RED:255 GREEN:255 BLUE:0    RED:255 GREEN:255 BLUE:0    "
        + "RED:255 GREEN:255 BLUE:0    RED:200 GREEN:200 BLUE:0    \n"
        + "RED:225 GREEN:225 BLUE:0    RED:255 GREEN:255 BLUE:0    "
        + "RED:225 GREEN:225 BLUE:0    RED:200 GREEN:200 BLUE:0    \n";
    assertEquals(expectedImage, resultImage.toString());
  }

  @Test
  public void testSharpenOnce() {
    Image testImage = new MyImage("res/cupcake.png");
    Image resultImages = imageService.sharpen(testImage, 1, Axis.X);
    Image expectedImage = new MyImage("res/cupcake_sharpenOnce.png");
    assertEquals(expectedImage, resultImages);
  }

  //  Convert an image into sepia as defined above.
  // different formats
  // of monochromatic, dichromatic and trichromatic images
  // repeatedly made sepia
  @Test
  public void testGetSepiaSimple() {
    Image testImage = new MyImage("test/img/simple.ppm");
    Image resultImages = imageService.getSepia(testImage, 1, Axis.X);
    String expected =
        "RED:122 GREEN:108 BLUE:84    RED:194 GREEN:173 BLUE:135    \n"
        + "RED:154 GREEN:137 BLUE:106    RED:105 GREEN:94 BLUE:73    \n"
        + "RED:211 GREEN:188 BLUE:146    RED:255 GREEN:233 BLUE:182    \n";
    assertEquals(expected, resultImages.toString());
  }

  @Test
  public void testGetSepiaSimplePercentage() {
    Image testImage = new MyImage("test/img/simple.ppm");
    Image resultImage = imageService.getSepia(testImage, 1, Axis.X);
    String expectedImage = "RED:122 GREEN:108 BLUE:84    RED:194 GREEN:173 BLUE:135    \n"
                           + "RED:154 GREEN:137 BLUE:106    RED:105 GREEN:94 BLUE:73    \n"
                           + "RED:211 GREEN:188 BLUE:146    RED:255 GREEN:233 BLUE:182    \n";
    assertEquals(expectedImage, resultImage.toString());
  }

  @Test
  public void testGetSepiaCity() {
    Image testImage = new MyImage("res/city_small.png");
    Image resultImages = imageService.getSepia(testImage, 1, Axis.X);
    Image expectedImage = new MyImage("res/city_small_sepia.png");
    assertEquals(expectedImage, resultImages);
  }

  // test greyscale
  @Test
  public void testGreyscaleMonochromatic() {
    Image testImageBlack = new MyImage("test/img/black.ppm");
    Image testImageWhite = new MyImage("test/img/white.ppm");
    Image testImageRed = new MyImage("test/img/red.ppm");
    Image testImageGreen = new MyImage("test/img/green.ppm");
    Image testImageBlue = new MyImage("test/img/blue.ppm");

    Image resultImageBlack = imageService.greyscale(testImageBlack, 1, Axis.X);
    Image resultImageWhite = imageService.greyscale(testImageWhite, 1, Axis.X);
    Image resultImageRed = imageService.greyscale(testImageRed, 1, Axis.X);
    Image resultImageGreen = imageService.greyscale(testImageGreen, 1, Axis.X);
    Image resultImageBlue = imageService.greyscale(testImageBlue, 1, Axis.X);

    String blackExpected =
        "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 "
        + "BLUE:0    \n"
        + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 "
        + "BLUE:0    \n"
        + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 "
        + "BLUE:0    \n"
        + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 "
        + "BLUE:0    \n";
    String whiteExpected =
        "RED:255 GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255  "
        + "  RED:255 GREEN:255 BLUE:255    \n"
        + "RED:255 GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255"
        + "    RED:255 GREEN:255 BLUE:255    \n"
        + "RED:255 GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255"
        + "    RED:255 GREEN:255 BLUE:255    \n"
        + "RED:255 GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255"
        + "    RED:255 GREEN:255 BLUE:255    \n";
    String redExpected =
        "RED:54 GREEN:54 BLUE:54    RED:54 GREEN:54 BLUE:54    RED:54 GREEN:54 BLUE:54    RED:54 "
        + "GREEN:54 BLUE:54    \n"
        + "RED:54 GREEN:54 BLUE:54    RED:54 GREEN:54 BLUE:54    RED:54 GREEN:54 BLUE:54    "
        + "RED:54 GREEN:54 BLUE:54    \n"
        + "RED:54 GREEN:54 BLUE:54    RED:54 GREEN:54 BLUE:54    RED:54 GREEN:54 BLUE:54    "
        + "RED:54 GREEN:54 BLUE:54    \n"
        + "RED:54 GREEN:54 BLUE:54    RED:54 GREEN:54 BLUE:54    RED:54 GREEN:54 BLUE:54    "
        + "RED:54 GREEN:54 BLUE:54    \n";
    String greenExpected =
        "RED:182 GREEN:182 BLUE:182    RED:182 GREEN:182 BLUE:182    RED:182 GREEN:182 BLUE:182  "
        + "  RED:182 GREEN:182 BLUE:182    \n"
        + "RED:182 GREEN:182 BLUE:182    RED:182 GREEN:182 BLUE:182    RED:182 GREEN:182 BLUE:182"
        + "    RED:182 GREEN:182 BLUE:182    \n"
        + "RED:182 GREEN:182 BLUE:182    RED:182 GREEN:182 BLUE:182    RED:182 GREEN:182 BLUE:182"
        + "    RED:182 GREEN:182 BLUE:182    \n"
        + "RED:182 GREEN:182 BLUE:182    RED:182 GREEN:182 BLUE:182    RED:182 GREEN:182 BLUE:182"
        + "    RED:182 GREEN:182 BLUE:182    \n";
    String blueExpected =
        "RED:18 GREEN:18 BLUE:18    RED:18 GREEN:18 BLUE:18    RED:18 GREEN:18 BLUE:18    RED:18 "
        + "GREEN:18 BLUE:18    \n"
        + "RED:18 GREEN:18 BLUE:18    RED:18 GREEN:18 BLUE:18    RED:18 GREEN:18 BLUE:18    "
        + "RED:18 GREEN:18 BLUE:18    \n"
        + "RED:18 GREEN:18 BLUE:18    RED:18 GREEN:18 BLUE:18    RED:18 GREEN:18 BLUE:18    "
        + "RED:18 GREEN:18 BLUE:18    \n"
        + "RED:18 GREEN:18 BLUE:18    RED:18 GREEN:18 BLUE:18    RED:18 GREEN:18 BLUE:18    "
        + "RED:18 GREEN:18 BLUE:18    \n";

    assertEquals(blackExpected, resultImageBlack.toString());
    assertEquals(whiteExpected, resultImageWhite.toString());
    assertEquals(redExpected, resultImageRed.toString());
    assertEquals(greenExpected, resultImageGreen.toString());
    assertEquals(blueExpected, resultImageBlue.toString());
  }


  @Test
  public void testGreyscaleDuoColor() {
    Image testImage = new MyImage("test/img/duoColor.png");
    Image resultImage = imageService.greyscale(testImage, 1, Axis.X);

    String expected = "RED:182 GREEN:182 BLUE:182    RED:182 GREEN:182 BLUE:182    \n"
                      + "RED:139 GREEN:139 BLUE:139    RED:139 GREEN:139 BLUE:139    \n";

    assertEquals(expected, resultImage.toString());
  }


  @Test
  public void testGreyscaleDichromatic() {
    Image testImageWoBlue = new MyImage("test/img/woBlue.ppm");
    Image testImageWoRed = new MyImage("test/img/woRed.ppm");
    Image testImageWoGreen = new MyImage("test/img/woGreen.ppm");

    Image resultImageWoBlue = imageService.greyscale(testImageWoBlue, 1, Axis.X);
    Image resultImageWoRed = imageService.greyscale(testImageWoRed, 1, Axis.X);
    Image resultImageWoGreen = imageService.greyscale(testImageWoGreen, 1, Axis.X);

    String expected =
        "RED:186 GREEN:186 BLUE:186    RED:186 GREEN:186 BLUE:186    RED:186 GREEN:186 BLUE:186  "
        + "  RED:186 GREEN:186 BLUE:186    \n"
        + "RED:186 GREEN:186 BLUE:186    RED:186 GREEN:186 BLUE:186    RED:186 GREEN:186 BLUE:186"
        + "    RED:186 GREEN:186 BLUE:186    \n"
        + "RED:186 GREEN:186 BLUE:186    RED:186 GREEN:186 BLUE:186    RED:186 GREEN:186 BLUE:186"
        + "    RED:186 GREEN:186 BLUE:186    \n"
        + "RED:186 GREEN:186 BLUE:186    RED:186 GREEN:186 BLUE:186    RED:186 "
        + "GREEN:186 BLUE:186    RED:186 GREEN:186 BLUE:186    \n";
    assertEquals(expected, resultImageWoBlue.toString());
    expected =
        "RED:157 GREEN:157 BLUE:157    RED:157 GREEN:157 BLUE:157    RED:157 GREEN:157 BLUE:157  "
        + "  RED:157 GREEN:157 BLUE:157    \n"
        + "RED:157 GREEN:157 BLUE:157    RED:157 GREEN:157 BLUE:157    RED:157 GREEN:157 BLUE:157"
        + "    RED:157 GREEN:157 BLUE:157    \n"
        + "RED:157 GREEN:157 BLUE:157    RED:157 GREEN:157 BLUE:157    RED:157 GREEN:157 BLUE:157"
        + "    RED:157 GREEN:157 BLUE:157    \n"
        + "RED:157 GREEN:157 BLUE:157    RED:157 GREEN:157 BLUE:157    RED:157 GREEN:157 BLUE:157"
        + "    RED:157 GREEN:157 BLUE:157    \n";
    assertEquals(expected, resultImageWoRed.toString());
    expected =
        "RED:57 GREEN:57 BLUE:57    RED:57 GREEN:57 BLUE:57    RED:57 GREEN:57 BLUE:57    RED:57 "
        + "GREEN:57 BLUE:57    \n"
        + "RED:57 GREEN:57 BLUE:57    RED:57 GREEN:57 BLUE:57    RED:57 GREEN:57 BLUE:57    "
        + "RED:57 GREEN:57 BLUE:57    \n"
        + "RED:57 GREEN:57 BLUE:57    RED:57 GREEN:57 BLUE:57    RED:57 GREEN:57 BLUE:57    "
        + "RED:57 GREEN:57 BLUE:57    \n"
        + "RED:57 GREEN:57 BLUE:57    RED:57 GREEN:57 BLUE:57    RED:57 GREEN:57 BLUE:57    "
        + "RED:57 GREEN:57 BLUE:57    \n";
    assertEquals(expected, resultImageWoGreen.toString());
  }

  @Test
  public void testGreyscaleTrichromatic() {
    Image testImage = new MyImage("test/img/simple.ppm");
    Image resultImage = imageService.greyscale(testImage, 1, Axis.X);
    String expected = "RED:89 GREEN:89 BLUE:89    RED:160 GREEN:160 BLUE:160    \n"
                      + "RED:95 GREEN:95 BLUE:95    RED:74 GREEN:74 BLUE:74    \n"
                      + "RED:173 GREEN:173 BLUE:173    RED:200 GREEN:200 BLUE:200    \n";
    assertEquals(expected, resultImage.toString());
  }

  @Test
  public void testGreyscaleTrichromaticPercentage() {
    Image testImage = new MyImage("test/img/simple.ppm");
    Image resultImage = imageService.greyscale(testImage, 0.5f, Axis.X);
    String expected = "RED:89 GREEN:89 BLUE:89    RED:30 GREEN:200 BLUE:150    \n"
                      + "RED:95 GREEN:95 BLUE:95    RED:105 GREEN:65 BLUE:75    \n"
                      + "RED:173 GREEN:173 BLUE:173    RED:215 GREEN:205 BLUE:105    \n";
    assertEquals(expected, resultImage.toString());
  }

  @Test
  public void testGreyscaleTrichromaticPercentageSmall() {
    Image testImage = new MyImage("test/img/red.png");
    Image resultImage = imageService.greyscale(testImage, 0.3f, Axis.X);
    String expected =
        "RED:54 GREEN:54 BLUE:54    RED:255 GREEN:0 BLUE:0    "
        + "RED:255 GREEN:0 BLUE:0    RED:255 GREEN:0 BLUE:0    \n"
        + "RED:54 GREEN:54 BLUE:54    RED:255 GREEN:0 BLUE:0    "
        + "RED:255 GREEN:0 BLUE:0    RED:255 GREEN:0 BLUE:0    \n"
        + "RED:54 GREEN:54 BLUE:54    RED:255 GREEN:0 BLUE:0    "
        + "RED:255 GREEN:0 BLUE:0    RED:255 GREEN:0 BLUE:0    \n"
        + "RED:54 GREEN:54 BLUE:54    RED:255 GREEN:0 BLUE:0    "
        + "RED:255 GREEN:0 BLUE:0    RED:255 GREEN:0 BLUE:0    \n";
    assertEquals(expected, resultImage.toString());
  }

  @Test
  public void testGreyscaleTrichromaticPercentageBig() {
    Image testImage = new MyImage("test/img/red.png");
    Image resultImage = imageService.greyscale(testImage, 0.7f, Axis.X);
    String expected =
        "RED:54 GREEN:54 BLUE:54    RED:54 GREEN:54 BLUE:54    "
        + "RED:54 GREEN:54 BLUE:54    RED:255 GREEN:0 BLUE:0    \n"
        + "RED:54 GREEN:54 BLUE:54    RED:54 GREEN:54 BLUE:54    "
        + "RED:54 GREEN:54 BLUE:54    RED:255 GREEN:0 BLUE:0    \n"
        + "RED:54 GREEN:54 BLUE:54    RED:54 GREEN:54 BLUE:54    "
        + "RED:54 GREEN:54 BLUE:54    RED:255 GREEN:0 BLUE:0    \n"
        + "RED:54 GREEN:54 BLUE:54    RED:54 GREEN:54 BLUE:54    "
        + "RED:54 GREEN:54 BLUE:54    RED:255 GREEN:0 BLUE:0    \n";
    assertEquals(expected, resultImage.toString());
  }


  // Histogram
  @Test(expected = IllegalArgumentException.class)
  public void testGetHistogramNull() {
    Image resultHistogram = imageService.getHistogram(null);
  }


  @Test
  public void testGetHistogramWhite() throws IOException {
    MyImage testImage = new MyImage("test/img/white.png");
    Image histogram = imageService.getHistogram(testImage);
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
    assertTrue(MyImageTest.checkHistogramLines(path, redPoints, greenPoints, bluePoints));
  }

  @Test
  public void testGetHistogramMono() throws IOException {
    MyImage testImage = new MyImage("test/img/red.png");
    Image histogram = imageService.getHistogram(testImage);
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
    assertTrue(MyImageTest.checkHistogramLines(path, redPoints, greenPoints, bluePoints));
  }

  @Test
  public void testGetHistogramDi() throws IOException {
    MyImage testImage = new MyImage("test/img/woBlue.png");
    Image histogram = imageService.getHistogram(testImage);
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
    assertTrue(MyImageTest.checkHistogramLines(path, redPoints, greenPoints, bluePoints));
  }

  @Test
  public void testGetHistogramBi() throws IOException {
    MyImage testImage = new MyImage("test/img/duoColor.png");
    Image histogram = imageService.getHistogram(testImage);
    String path = "histogram.png";
    histogram.save(path);
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
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255};
    int[] greenPoints =
        new int[]{255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0,
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
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255};
    assertTrue(MyImageTest.checkHistogramLines(path, redPoints, greenPoints, bluePoints));
  }

  @Test
  public void testGetHistogramBlack() throws IOException {
    MyImage testImage = new MyImage("test/img/black.png");
    Image histogram = imageService.getHistogram(testImage);
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
    assertTrue(MyImageTest.checkHistogramLines(path, redPoints, greenPoints, bluePoints));
  }

  @Test
  public void testGetHistogram() throws IOException {
    MyImage testImage = new MyImage("res/city.png");
    Image histogram = imageService.getHistogram(testImage);
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
    assertTrue(MyImageTest.checkHistogramLines(path, redPoints, greenPoints, bluePoints));
  }

  // test for Color Correct
  @Test(expected = IllegalArgumentException.class)
  public void testColorCorrectNull() {
    Image resultHistogram = imageService.colorCorrect(null, 1, Axis.X);
  }

  //all peak fall into the range of [0,10] and [245,255]
  @Test
  public void testColorCorrectMonoPeakAllNeglected() {
    MyImage redImage = new MyImage("test/img/red.png");
    MyImage greenImage = new MyImage("test/img/green.png");
    MyImage blueImage = new MyImage("test/img/blue.png");
    assertEquals(redImage.getHistogram(),
        imageService.colorCorrect(redImage, 1, Axis.X).getHistogram());
    assertEquals(greenImage.getHistogram(),
        imageService.colorCorrect(greenImage, 1, Axis.X).getHistogram());
    assertEquals(blueImage.getHistogram(),
        imageService.colorCorrect(blueImage, 1, Axis.X).getHistogram());
  }

  //some peak fall into the range of [0,10] and [245,255]
  @Test
  public void testColorCorrectMonoPeakSomeNeglected() throws IOException {
    MyImage testImage = new MyImage(4, 4).imgArrayAddition(new float[]{11, 100, 250});
    String path = "histogram-color-correct.png";
    imageService.colorCorrect(testImage, 1, Axis.X).getHistogram().save(path);
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
    assertTrue(MyImageTest.checkHistogramLines(path, redPoints, greenPoints, bluePoints));
  }

  //all peak in [11, 254]
  // only one peak in one channel
  @Test
  public void testColorCorrectOnePeak() throws IOException {
    MyImage testImage = new MyImage(4, 4).imgArrayAddition(new float[]{50, 100, 150});
    String path = "histogram-color-correct.png";
    imageService.colorCorrect(testImage, 1, Axis.X).getHistogram().save(path);
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
    assertTrue(MyImageTest.checkHistogramLines(path, redPoints, greenPoints, bluePoints));
  }

  // has multiple peaks in one single channel
  @Test
  public void testColorCorrectMultiplePeak() throws IOException {
    MyImage firstHalf = new MyImage(2, 2).imgArrayAddition(new float[]{50, 100, 155});
    MyImage secondHalf = new MyImage(2, 2).imgArrayAddition(new float[]{80, 160, 230});
    MyImage testImage = firstHalf.combineImages(secondHalf, Axis.X);
    String path = "histogram-color-correct.png";
    imageService.colorCorrect(testImage, 1, Axis.X).getHistogram().save(path);
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
    assertTrue(MyImageTest.checkHistogramLines(path, redPoints, greenPoints, bluePoints));
  }

  @Test
  public void testColorCorrectMultiplePeakSomeNeglected() throws IOException {
    MyImage firstHalf = new MyImage(2, 2).imgArrayAddition(new float[]{5, 10, 150});
    MyImage secondHalf = new MyImage(2, 2).imgArrayAddition(new float[]{80, 160, 245});
    MyImage testImage = firstHalf.combineImages(secondHalf, Axis.X);
    String path = "histogram-color-correct.png";
    imageService.colorCorrect(testImage, 1, Axis.X).getHistogram().save(path);
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
        new int[]{255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
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
    assertTrue(MyImageTest.checkHistogramLines(path, redPoints, greenPoints, bluePoints));
  }

  @Test
  public void testColorCorrect() throws IOException {
    float[][] greyscale = new float[][]{
        {0.2126f, 0.7152f, 0.0722f},
        {0.2126f, 0.7152f, 0.0722f},
        {0.2126f, 0.7152f, 0.0722f}
    };
    MyImage image =
        (MyImage) new MyImage("res/city_small.png").matrixMultiplication(greyscale)
            .imgArrayAddition(new float[]{10, 0, -5});
    String path = "histogram-color-correct.png";
    imageService.colorCorrect(image, 1, Axis.X).getHistogram().save(path);
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
    assertTrue(MyImageTest.checkHistogramLines(path, redPoints, greenPoints, bluePoints));
  }

  @Test
  public void testColorCorrectPercentage() throws IOException {
    float[][] greyscale = new float[][]{
        {0.2126f, 0.7152f, 0.0722f},
        {0.2126f, 0.7152f, 0.0722f},
        {0.2126f, 0.7152f, 0.0722f}
    };
    MyImage image =
        (MyImage) new MyImage("res/city_small.png").matrixMultiplication(greyscale)
            .imgArrayAddition(new float[]{10, 0, -5});
    String path = "histogram-color-correct.png";
    imageService.colorCorrect(image, 0.5f, Axis.X).getHistogram().save(path);
    int[] redPoints =
        new int[]{255, 255, 254, 255, 255, 255, 255, 255, 255, 255, 250, 255, 255, 255, 254, 255,
            255, 255, 255, 255, 255, 255, 250, 255, 255, 255, 255, 255, 255, 255, 252, 255, 252,
            255, 255, 255, 255, 255, 251, 255, 252, 255, 244, 255, 255, 255, 255, 255, 255, 252,
            247, 255, 255, 255, 248, 255, 255, 249, 255, 255, 255, 249, 244, 251, 247, 255, 255,
            255, 249, 250, 255, 248, 244, 255, 245, 255, 247, 234, 255, 249, 245, 255, 246, 255,
            252, 235, 255, 248, 245, 242, 249, 255, 242, 250, 250, 248, 249, 226, 247, 255, 229,
            251, 251, 244, 255, 227, 238, 245, 203, 248, 254, 249, 254, 241, 208, 242, 193, 253,
            242, 252, 244, 251, 199, 218, 208, 242, 242, 252, 235, 251, 192, 218, 222, 241, 252,
            253, 240, 241, 203, 212, 236, 241, 245, 251, 239, 241, 210, 227, 247, 246, 241, 253,
            252, 223, 236, 243, 237, 233, 247, 255, 245, 220, 255, 252, 245, 242, 232, 255, 234,
            234, 255, 254, 244, 255, 230, 255, 244, 230, 255, 245, 230, 242, 193, 186, 222, 249,
            255, 241, 213, 241, 166, 114, 208, 227, 230, 247, 208, 172, 125, 105, 172, 217, 211,
            246, 181, 212, 110, 163, 150, 238, 240, 239, 235, 209, 216, 200, 247, 252, 233, 239,
            239, 244, 228, 244, 253, 255, 246, 251, 252, 235, 255, 255, 253, 255, 249, 253, 254,
            255, 251, 255, 255, 255, 255, 255, 253, 255, 242, 248, 255, 255, 255, 255, 255, 255,
            255, 245};
    int[] greenPoints =
        new int[]{250, 255, 254, 255, 255, 255, 255, 255, 255, 255, 255, 255, 251, 255, 254, 255,
            255, 255, 255, 255, 253, 255, 254, 255, 255, 255, 255, 255, 251, 255, 251, 255, 252,
            255, 255, 255, 255, 255, 255, 255, 247, 255, 244, 255, 255, 255, 255, 249, 255, 252,
            255, 255, 244, 255, 248, 255, 255, 255, 255, 250, 255, 243, 244, 251, 247, 255, 247,
            255, 249, 255, 255, 255, 246, 255, 245, 242, 255, 227, 250, 249, 245, 255, 247, 250,
            252, 248, 249, 243, 246, 242, 247, 255, 248, 244, 250, 226, 253, 238, 233, 251, 231,
            251, 253, 245, 238, 243, 214, 244, 221, 249, 251, 245, 227, 236, 201, 247, 206, 254,
            232, 254, 218, 230, 215, 225, 232, 241, 249, 248, 214, 226, 209, 241, 222, 249, 242,
            244, 226, 222, 234, 230, 248, 241, 252, 251, 230, 240, 220, 239, 241, 253, 241, 228,
            255, 223, 245, 242, 247, 244, 234, 246, 245, 246, 255, 252, 239, 255, 232, 230, 255,
            243, 243, 255, 232, 255, 236, 255, 244, 252, 255, 240, 240, 216, 193, 175, 232, 244,
            228, 179, 162, 157, 155, 127, 201, 239, 156, 247, 95, 175, 88, 178, 171, 227, 213, 206,
            217, 169, 242, 223, 238, 238, 241, 238, 224, 238, 253, 248, 249, 255, 230, 248, 253,
            249, 253, 255, 247, 253, 252, 251, 255, 241, 255, 255, 255, 255, 253, 255, 240, 255,
            251, 255, 255, 255, 255, 255, 255, 255, 248, 255, 251, 255, 253, 255, 252, 255, 255,
            254};
    int[] bluePoints =
        new int[]{250, 255, 255, 255, 255, 255, 255, 250, 255, 255, 255, 255, 255, 255, 254, 253,
            255, 255, 255, 255, 255, 255, 254, 251, 255, 252, 255, 255, 255, 255, 254, 255, 252,
            255, 255, 247, 255, 255, 255, 255, 255, 255, 238, 255, 255, 255, 255, 244, 255, 252,
            255, 255, 255, 255, 243, 255, 248, 244, 255, 255, 255, 241, 255, 251, 247, 255, 255,
            246, 249, 255, 242, 255, 248, 250, 245, 255, 255, 226, 250, 249, 245, 249, 243, 250,
            252, 246, 255, 253, 239, 242, 227, 253, 250, 237, 246, 248, 255, 238, 242, 238, 226,
            224, 252, 255, 252, 246, 236, 218, 207, 227, 253, 236, 254, 240, 224, 219, 194, 243,
            235, 255, 244, 249, 222, 201, 207, 231, 252, 242, 246, 242, 209, 223, 210, 248, 245,
            255, 244, 241, 235, 218, 236, 233, 244, 244, 249, 253, 203, 250, 248, 243, 228, 255,
            255, 202, 246, 255, 246, 244, 255, 239, 245, 247, 230, 252, 255, 243, 232, 243, 255,
            234, 255, 255, 240, 255, 241, 252, 218, 244, 244, 245, 237, 215, 139, 135, 142, 236,
            243, 226, 213, 173, 163, 0, 157, 151, 214, 238, 232, 222, 136, 156, 137, 222, 226, 246,
            238, 203, 245, 199, 227, 254, 255, 236, 251, 246, 247, 248, 255, 253, 233, 249, 251,
            249, 255, 255, 253, 255, 252, 251, 255, 240, 255, 242, 255, 255, 255, 255, 254, 255,
            251, 255, 255, 248, 255, 255, 255, 253, 255, 255, 251, 255, 254, 255, 252, 255, 255,
            255};
    assertTrue(MyImageTest.checkHistogramLines(path, redPoints, greenPoints, bluePoints));
  }

  @Test
  public void testColorCorrectPercentageSmall() throws IOException {
    float[][] greyscale = new float[][]{
        {0.2126f, 0.7152f, 0.0722f},
        {0.2126f, 0.7152f, 0.0722f},
        {0.2126f, 0.7152f, 0.0722f}
    };
    MyImage image =
        (MyImage) new MyImage("res/city_small.png").matrixMultiplication(greyscale)
            .imgArrayAddition(new float[]{10, 0, -5});
    String path = "histogram-color-correct.png";
    imageService.colorCorrect(image, 0.3f, Axis.X).getHistogram().save(path);
    int[] redPoints =
        new int[]{255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 249, 255, 255, 255, 254, 255,
            255, 255, 255, 255, 255, 255, 250, 255, 255, 255, 255, 255, 255, 255, 252, 255, 254,
            255, 255, 255, 255, 255, 250, 255, 249, 255, 252, 255, 255, 255, 255, 255, 255, 252,
            239, 255, 255, 255, 252, 255, 255, 248, 255, 255, 255, 252, 240, 253, 253, 255, 255,
            255, 251, 247, 255, 246, 239, 255, 250, 255, 244, 247, 255, 251, 250, 255, 240, 255,
            254, 228, 255, 247, 241, 249, 252, 255, 245, 243, 253, 251, 248, 226, 245, 255, 233,
            254, 250, 241, 255, 220, 243, 250, 208, 247, 252, 250, 255, 241, 213, 242, 191, 251,
            248, 250, 245, 248, 196, 227, 200, 245, 236, 254, 237, 253, 194, 214, 221, 243, 252,
            251, 238, 243, 197, 214, 231, 240, 244, 253, 239, 238, 213, 218, 250, 241, 251, 251,
            249, 230, 225, 239, 236, 240, 237, 255, 250, 213, 255, 255, 241, 234, 239, 255, 228,
            237, 255, 250, 249, 255, 224, 255, 249, 223, 255, 252, 228, 245, 194, 219, 224, 252,
            255, 240, 228, 245, 188, 115, 211, 226, 232, 243, 190, 173, 127, 89, 173, 218, 212, 246,
            177, 213, 78, 154, 141, 236, 236, 239, 236, 197, 213, 188, 241, 251, 235, 237, 238, 247,
            226, 239, 253, 255, 247, 254, 250, 233, 255, 255, 252, 255, 247, 249, 255, 253, 253,
            255, 255, 255, 255, 255, 252, 255, 240, 254, 255, 255, 255, 255, 255, 255, 255, 239};
    int[] greenPoints =
        new int[]{249, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 250, 255, 254, 255,
            255, 255, 255, 255, 252, 255, 255, 255, 255, 255, 255, 255, 250, 255, 249, 255, 254,
            255, 255, 255, 255, 255, 255, 255, 239, 255, 252, 255, 255, 255, 255, 248, 255, 252,
            255, 255, 240, 255, 252, 255, 255, 255, 255, 247, 255, 243, 239, 253, 253, 255, 244,
            255, 251, 255, 255, 255, 240, 255, 250, 228, 255, 239, 244, 251, 250, 255, 245, 243,
            254, 255, 248, 235, 244, 249, 245, 255, 250, 241, 253, 220, 250, 247, 226, 247, 238,
            254, 255, 243, 230, 241, 206, 246, 235, 249, 251, 244, 215, 234, 198, 251, 214, 255,
            236, 254, 209, 215, 219, 234, 239, 243, 245, 247, 204, 218, 215, 244, 227, 255, 241,
            239, 220, 214, 240, 236, 249, 245, 249, 251, 221, 239, 227, 241, 234, 254, 251, 218,
            255, 232, 241, 234, 251, 252, 228, 243, 250, 245, 255, 255, 231, 255, 239, 223, 255,
            250, 237, 255, 235, 255, 233, 255, 249, 245, 251, 244, 239, 186, 189, 204, 240, 241,
            207, 176, 152, 126, 170, 161, 210, 238, 155, 239, 63, 160, 105, 198, 180, 227, 216, 195,
            213, 164, 239, 237, 244, 237, 238, 238, 224, 234, 254, 253, 247, 255, 230, 245, 255,
            252, 252, 255, 246, 249, 254, 253, 255, 243, 255, 255, 255, 255, 252, 255, 239, 255,
            253, 255, 255, 255, 255, 255, 255, 255, 245, 255, 254, 255, 250, 255, 255, 255, 255,
            254};
    int[] bluePoints =
        new int[]{249, 255, 255, 255, 255, 255, 255, 250, 255, 255, 255, 255, 255, 255, 254, 252,
            255, 255, 255, 255, 255, 255, 255, 250, 255, 249, 255, 255, 255, 255, 255, 255, 254,
            255, 255, 239, 255, 255, 255, 255, 255, 255, 245, 255, 255, 255, 255, 240, 255, 252,
            255, 255, 255, 255, 244, 255, 246, 239, 255, 255, 255, 242, 255, 253, 253, 255, 255,
            240, 251, 255, 228, 255, 247, 244, 250, 255, 255, 238, 243, 251, 250, 248, 235, 247,
            254, 248, 255, 250, 238, 249, 222, 250, 255, 227, 245, 248, 255, 247, 241, 230, 230,
            213, 251, 254, 250, 248, 241, 210, 216, 215, 253, 231, 255, 240, 236, 213, 193, 237,
            237, 255, 245, 245, 230, 201, 203, 229, 246, 242, 250, 242, 217, 225, 203, 253, 240,
            255, 244, 243, 240, 220, 233, 235, 243, 235, 251, 255, 204, 253, 252, 240, 230, 255,
            255, 205, 243, 255, 246, 252, 255, 231, 250, 250, 223, 255, 255, 237, 239, 240, 255,
            234, 255, 255, 239, 251, 240, 247, 190, 235, 241, 252, 235, 198, 135, 138, 112, 226,
            241, 229, 231, 176, 181, 0, 144, 137, 213, 236, 233, 231, 156, 175, 134, 218, 229, 248,
            237, 212, 248, 212, 228, 255, 255, 236, 253, 244, 245, 253, 255, 252, 235, 247, 249,
            252, 253, 255, 254, 255, 254, 254, 255, 240, 255, 240, 255, 255, 255, 255, 255, 255,
            253, 255, 255, 245, 255, 255, 255, 250, 255, 255, 254, 255, 254, 255, 255, 255, 255,
            255};
    assertTrue(MyImageTest.checkHistogramLines(path, redPoints, greenPoints, bluePoints));
  }

  @Test
  public void testColorCorrectPercentageBig() throws IOException {
    float[][] greyscale = new float[][]{
        {0.2126f, 0.7152f, 0.0722f},
        {0.2126f, 0.7152f, 0.0722f},
        {0.2126f, 0.7152f, 0.0722f}
    };
    MyImage image =
        (MyImage) new MyImage("res/city_small.png").matrixMultiplication(greyscale)
            .imgArrayAddition(new float[]{10, 0, -5});
    String path = "histogram-color-correct.png";
    imageService.colorCorrect(image, 0.7f, Axis.X).getHistogram().save(path);
    int[] redPoints =
        new int[]{255, 255, 252, 255, 255, 255, 255, 255, 255, 255, 250, 255, 255, 255, 252, 255,
            255, 255, 255, 255, 255, 255, 249, 255, 255, 255, 255, 255, 255, 255, 251, 255, 250,
            255, 255, 255, 255, 255, 251, 255, 253, 255, 238, 255, 255, 255, 255, 255, 255, 249,
            250, 255, 255, 255, 240, 255, 255, 250, 255, 255, 255, 245, 250, 248, 240, 255, 255,
            255, 244, 253, 255, 250, 249, 255, 239, 255, 249, 223, 255, 246, 242, 255, 248, 255,
            246, 236, 255, 250, 243, 233, 246, 255, 244, 253, 248, 243, 251, 218, 247, 255, 217,
            248, 251, 246, 255, 229, 228, 241, 192, 249, 254, 247, 252, 239, 197, 238, 183, 253,
            234, 252, 239, 252, 190, 206, 207, 238, 246, 250, 235, 250, 178, 216, 215, 240, 247,
            254, 237, 235, 202, 203, 239, 235, 247, 250, 237, 244, 203, 229, 242, 248, 235, 253,
            254, 206, 243, 246, 235, 226, 250, 255, 232, 221, 255, 250, 247, 245, 219, 255, 242,
            222, 255, 254, 237, 255, 225, 255, 234, 240, 255, 240, 231, 236, 192, 170, 220, 243,
            255, 239, 191, 229, 126, 68, 196, 222, 214, 249, 196, 167, 61, 68, 115, 209, 212, 243,
            181, 198, 150, 164, 186, 244, 242, 230, 239, 210, 193, 214, 247, 252, 227, 244, 235,
            246, 246, 247, 250, 255, 248, 249, 254, 233, 255, 255, 255, 255, 249, 254, 253, 255,
            249, 255, 255, 255, 255, 255, 254, 255, 241, 244, 255, 255, 255, 255, 255, 255, 255,
            247};
    int[] greenPoints =
        new int[]{250, 255, 252, 255, 255, 255, 255, 255, 255, 255, 255, 255, 251, 255, 252, 255,
            255, 255, 255, 255, 253, 255, 253, 255, 255, 255, 255, 255, 251, 255, 251, 255, 250,
            255, 255, 255, 255, 255, 255, 255, 250, 255, 238, 255, 255, 255, 255, 250, 255, 249,
            255, 255, 250, 255, 240, 255, 255, 255, 255, 253, 255, 240, 249, 248, 240, 255, 249,
            255, 244, 255, 255, 255, 248, 255, 239, 248, 255, 218, 251, 246, 242, 255, 252, 253,
            246, 243, 251, 247, 244, 233, 245, 255, 246, 246, 248, 227, 253, 226, 239, 253, 218,
            248, 252, 247, 243, 240, 212, 241, 203, 248, 253, 245, 232, 236, 192, 241, 193, 253,
            227, 254, 219, 237, 203, 211, 224, 237, 250, 247, 221, 229, 193, 231, 214, 246, 239,
            247, 227, 223, 222, 220, 247, 235, 254, 250, 234, 243, 210, 232, 238, 251, 235, 235,
            255, 206, 247, 245, 242, 237, 242, 249, 232, 240, 255, 250, 245, 255, 219, 240, 255,
            228, 246, 255, 232, 255, 231, 255, 234, 255, 255, 238, 237, 212, 188, 162, 224, 240,
            239, 177, 157, 157, 123, 85, 195, 229, 149, 252, 119, 200, 46, 133, 108, 214, 211, 210,
            209, 170, 235, 191, 234, 242, 240, 232, 240, 235, 230, 243, 251, 255, 226, 249, 250,
            246, 255, 255, 244, 254, 252, 249, 255, 239, 255, 255, 255, 255, 254, 255, 239, 255,
            249, 255, 255, 255, 255, 255, 255, 255, 248, 255, 249, 255, 254, 255, 250, 255, 255,
            254};
    int[] bluePoints =
        new int[]{250, 255, 255, 255, 255, 255, 255, 249, 255, 255, 255, 255, 255, 255, 252, 253,
            255, 255, 255, 255, 255, 255, 253, 251, 255, 253, 255, 255, 255, 255, 254, 255, 250,
            255, 255, 250, 255, 255, 255, 255, 255, 255, 233, 255, 255, 255, 255, 250, 255, 249,
            255, 255, 255, 255, 238, 255, 250, 249, 255, 255, 255, 240, 255, 248, 240, 255, 255,
            248, 244, 255, 248, 255, 250, 251, 239, 255, 255, 220, 253, 246, 242, 251, 247, 252,
            246, 243, 255, 254, 239, 233, 230, 253, 248, 244, 246, 243, 255, 226, 242, 243, 214,
            229, 252, 255, 252, 243, 228, 221, 193, 234, 253, 239, 252, 240, 208, 220, 184, 246,
            229, 255, 239, 250, 207, 195, 203, 231, 253, 243, 242, 242, 192, 219, 207, 246, 244,
            255, 239, 236, 223, 212, 238, 230, 244, 245, 245, 252, 197, 243, 242, 243, 225, 255,
            255, 193, 249, 255, 241, 237, 255, 245, 232, 241, 240, 250, 255, 246, 219, 250, 255,
            224, 255, 255, 236, 255, 233, 252, 210, 246, 247, 240, 237, 221, 134, 136, 150, 237,
            247, 230, 191, 166, 128, 0, 174, 182, 205, 240, 209, 220, 61, 105, 92, 209, 218, 243,
            234, 184, 242, 185, 226, 253, 255, 229, 249, 242, 224, 243, 255, 255, 227, 249, 249,
            246, 255, 255, 250, 255, 252, 249, 255, 238, 255, 241, 255, 255, 255, 255, 253, 255,
            249, 255, 255, 248, 255, 255, 255, 254, 255, 255, 249, 255, 255, 255, 250, 255, 255,
            254};
    assertTrue(MyImageTest.checkHistogramLines(path, redPoints, greenPoints, bluePoints));
  }

  @Test
  public void testLevelAdjustmentSame() {
    MyImage[] testImages = new MyImage[]{new MyImage("test/img/white.ppm"),
        new MyImage("test/img/black.ppm"), new MyImage("test/img"
                                                       + "/woGreen.ppm"),
        new MyImage("test/img/simple.ppm")};
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
        new MyImage("test/img/all_colors_inter16.ppm").imgArrayAddition(
            new float[]{0, 5, 10});
    Image adjusted = imageService.levelAdjustment(allColorImage, black, mid, white, 1, Axis.X);
    String path = "level-adjustment-histogram.png";
    adjusted.getHistogram().save(path);

    int[] redPoints =
        new int[]{0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            64};
    int[] greenPoints =
        new int[]{64, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 64};
    int[] bluePoints =
        new int[]{64, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255,
            255, 64};
    assertTrue(MyImageTest.checkHistogramLines(path, redPoints, greenPoints, bluePoints));

  }

  //black right
  @Test
  public void testLevelAdjustmentBlackRight() throws IOException {
    int black = 50;
    int mid = 128;
    int white = 255;
    MyImage allColorImage =
        new MyImage("test/img/all_colors_inter16.ppm").imgArrayAddition(
            new float[]{0, 5, 10});
    Image adjusted = imageService.levelAdjustment(allColorImage, black, mid, white, 1, Axis.X);
    String path = "level-adjustment-histogram.png";
    adjusted.getHistogram().save(path);
    int[] redPoints =
        new int[]{0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255};
    int[] greenPoints =
        new int[]{64, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255,
            255, 255};
    int[] bluePoints =
        new int[]{64, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255,
            255, 255};
    assertTrue(MyImageTest.checkHistogramLines(path, redPoints, greenPoints, bluePoints));

  }

  //mid left
  @Test
  public void testLevelAdjustmentMidLeft() throws IOException {
    int black = 0;
    int mid = 200;
    int white = 255;
    MyImage allColorImage =
        new MyImage("test/img/all_colors_inter16.ppm").imgArrayAddition(
            new float[]{0, 5, 10});
    Image adjusted = imageService.levelAdjustment(allColorImage, black, mid, white, 1, Axis.X);
    String path = "level-adjustment-histogram.png";
    adjusted.getHistogram().save(path);
    int[] redPoints =
        new int[]{0, 255, 255, 255, 255, 255, 255, 219, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 219, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 219, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 219, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            219, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 219, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 219, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 219, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 219, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255};
    int[] greenPoints =
        new int[]{0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 219, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 219, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 219, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            219, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 219, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 219, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 219, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 219, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 219, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255};
    int[] bluePoints =
        new int[]{36, 255, 255, 219, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            219, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 219,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 219, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 219, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 219, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 219, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 219, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 219,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 219, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255};
    assertTrue(MyImageTest.checkHistogramLines(path, redPoints, greenPoints, bluePoints));

  }

  //mid right
  @Test
  public void testLevelAdjustmentMidRight() throws IOException {
    int black = 0;
    int mid = 100;
    int white = 255;
    MyImage allColorImage =
        new MyImage("test/img/all_colors_inter16.ppm").imgArrayAddition(
            new float[]{0, 5, 10});
    Image adjusted = imageService.levelAdjustment(allColorImage, black, mid, white, 1, Axis.X);
    String path = "level-adjustment-histogram.png";
    adjusted.getHistogram().save(path);
    int[] redPoints =
        new int[]{0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255,
            255, 255, 255, 255, 255};
    int[] greenPoints =
        new int[]{255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 0,
            255, 255, 255, 255, 255, 255};
    int[] bluePoints =
        new int[]{255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 255, 255, 255, 255, 255, 255,
            255, 255, 0, 255, 255, 255};
    assertTrue(MyImageTest.checkHistogramLines(path, redPoints, greenPoints, bluePoints));
  }

  //white left
  @Test
  public void testLevelAdjustmentWhiteLeft() throws IOException {
    int black = 0;
    int mid = 128;
    int white = 200;
    MyImage allColorImage =
        new MyImage("test/img/all_colors_inter16.ppm").imgArrayAddition(
            new float[]{0, 5, 10});
    Image adjusted = imageService.levelAdjustment(allColorImage, black, mid, white, 1, Axis.X);
    String path = "level-adjustment-histogram.png";
    adjusted.getHistogram().save(path);
    int[] redPoints =
        new int[]{191, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 64};
    int[] greenPoints =
        new int[]{255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255,
            255, 64};
    int[] bluePoints =
        new int[]{255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            191, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 191, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 0};
    assertTrue(MyImageTest.checkHistogramLines(path, redPoints, greenPoints, bluePoints));
  }

  @Test
  public void testLevelAdjustment() throws IOException {
    MyImage testImage = new MyImage("res/city.png");
    Image adjusted = imageService.levelAdjustment(testImage, 20, 100, 255, 1, Axis.X);
    String path = "level-adjustment-histogram.png";
    adjusted.getHistogram().save(path);
    int[] redPoints =
        new int[]{250, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 252, 255, 253, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 247, 255, 255, 255, 255, 255, 255, 253, 255, 255, 255,
            255, 255, 255, 248, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 240, 255, 255,
            255, 252, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 232, 255,
            255, 255, 255, 255, 249, 255, 251, 250, 255, 255, 240, 255, 251, 255, 255, 255, 255,
            252, 255, 255, 255, 255, 230, 255, 255, 248, 255, 255, 250, 236, 255, 255, 255, 217,
            255, 255, 251, 255, 255, 255, 255, 255, 255, 255, 205, 255, 255, 253, 255, 230, 255,
            251, 252, 246, 213, 255, 255, 255, 255, 255, 255, 250, 255, 255, 224, 255, 255, 254,
            235, 249, 255, 247, 248, 255, 228, 255, 255, 255, 255, 255, 255, 247, 255, 234, 255,
            255, 246, 240, 252, 244, 246, 255, 167, 237, 255, 255, 246, 249, 252, 255, 125, 250,
            247, 244, 255, 255, 245, 248, 143, 255, 249, 255, 248, 249, 255, 250, 167, 252, 255,
            249, 238, 251, 255, 170, 255, 255, 241, 247, 253, 211, 254, 237, 243, 239, 247, 248,
            210, 255, 254, 255, 253, 244, 247, 238, 248, 250, 255, 252, 255, 248, 255, 249, 239,
            255, 251, 252, 255, 251, 251, 255, 255, 255, 253, 255, 244, 255, 252, 255, 255, 245,
            249, 254};
    int[] greenPoints =
        new int[]{251, 255, 254, 255, 255, 255, 255, 253, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 253, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 252, 255, 252, 255, 255, 255, 255, 255, 255, 252, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 248, 255, 255, 255, 255, 255, 255, 255, 251,
            255, 255, 255, 255, 255, 255, 251, 247, 255, 255, 249, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 248, 251, 255, 250, 247, 255, 255, 248, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 246, 255, 250, 255, 255, 255, 251, 241, 255, 246, 252,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 235, 244, 252, 255, 242, 255, 255, 255,
            255, 250, 251, 219, 255, 255, 255, 255, 255, 250, 249, 255, 255, 206, 255, 252, 252,
            245, 255, 255, 255, 248, 250, 202, 248, 252, 255, 255, 255, 255, 255, 255, 201, 245,
            255, 250, 249, 255, 253, 246, 255, 216, 255, 255, 255, 255, 255, 255, 239, 235, 244,
            251, 250, 249, 255, 244, 255, 255, 242, 255, 242, 255, 249, 252, 255, 255, 248, 248,
            246, 242, 255, 255, 255, 247, 255, 251, 255, 252, 236, 255, 255, 248, 254, 255, 255,
            177, 247, 253, 245, 255, 19, 243, 255, 251, 251, 248, 0, 252, 255, 255, 255, 180, 241,
            238, 243, 239, 255, 251, 253, 253, 255, 249, 255, 247, 255, 255, 255, 255, 250, 253,
            254};
    int[] bluePoints =
        new int[]{249, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 253, 255, 254,
            255, 252, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 252, 255, 255, 255,
            251, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 251, 255,
            255, 255, 255, 252, 255, 255, 248, 255, 255, 255, 255, 252, 255, 249, 255, 255, 255,
            255, 255, 250, 255, 255, 255, 255, 255, 255, 247, 255, 255, 255, 248, 255, 254, 255,
            255, 253, 255, 255, 250, 255, 243, 255, 255, 251, 255, 255, 255, 252, 255, 251, 255,
            250, 255, 255, 253, 255, 250, 255, 255, 249, 251, 255, 244, 250, 253, 255, 250, 253,
            252, 255, 255, 255, 255, 255, 246, 255, 255, 255, 252, 252, 255, 243, 255, 238, 255,
            241, 251, 255, 255, 255, 244, 255, 252, 255, 254, 255, 255, 255, 244, 255, 248, 239,
            245, 238, 255, 240, 249, 255, 228, 255, 255, 255, 255, 255, 255, 255, 255, 212, 243,
            255, 255, 251, 232, 252, 252, 255, 205, 255, 255, 255, 251, 255, 254, 248, 210, 253,
            236, 247, 255, 251, 255, 255, 212, 255, 255, 255, 255, 251, 251, 255, 229, 255, 244,
            255, 249, 238, 255, 231, 250, 255, 255, 255, 253, 224, 248, 255, 239, 255, 251, 255,
            223, 255, 252, 249, 255, 229, 255, 254, 253, 255, 242, 242, 255, 250, 245, 255, 193,
            255, 245, 255, 137, 249, 255, 253, 70, 255, 252, 244, 99, 255, 247, 234, 248, 255, 255,
            254};
    assertTrue(MyImageTest.checkHistogramLines(path, redPoints, greenPoints, bluePoints));
  }

  @Test
  public void testLevelAdjustmentPercentage() throws IOException {
    MyImage testImage = new MyImage("res/city.png");
    Image adjusted = imageService.levelAdjustment(testImage, 20, 100, 255, 0.5f, Axis.X);
    String path = "level-adjustment-histogram.png";
    adjusted.getHistogram().save(path);
    int[] redPoints =
        new int[]{251, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 253,
            255, 255, 255, 255, 249, 255, 254, 255, 255, 255, 255, 255, 255, 255, 255, 253, 252,
            255, 255, 255, 255, 255, 255, 249, 255, 255, 249, 255, 255, 255, 252, 255, 255, 255,
            248, 255, 255, 250, 255, 255, 255, 243, 255, 251, 255, 255, 255, 255, 242, 255, 255,
            255, 236, 255, 255, 255, 250, 250, 252, 255, 241, 250, 255, 255, 255, 253, 234, 255,
            235, 255, 250, 255, 246, 239, 253, 250, 224, 255, 243, 255, 252, 255, 255, 255, 218,
            252, 254, 255, 235, 252, 231, 246, 221, 247, 255, 255, 251, 235, 255, 255, 234, 224,
            254, 238, 246, 247, 249, 255, 234, 255, 255, 255, 210, 255, 249, 253, 245, 234, 246,
            243, 252, 238, 212, 255, 177, 255, 255, 255, 250, 246, 251, 255, 107, 250, 248, 245,
            240, 249, 248, 239, 165, 255, 225, 255, 249, 250, 255, 248, 179, 246, 255, 232, 247,
            237, 248, 240, 172, 248, 247, 255, 180, 218, 254, 255, 212, 249, 237, 244, 150, 252,
            240, 238, 230, 243, 245, 253, 155, 255, 250, 248, 247, 249, 242, 250, 186, 247, 255,
            253, 245, 248, 255, 187, 255, 255, 239, 250, 253, 204, 254, 242, 245, 240, 248, 251,
            218, 255, 251, 255, 247, 248, 244, 239, 247, 251, 255, 252, 254, 250, 255, 243, 237,
            250, 251, 253, 255, 252, 251, 255, 255, 255, 253, 255, 245, 243, 253, 249, 255, 250,
            251, 254};
    int[] greenPoints =
        new int[]{251, 255, 254, 255, 255, 255, 255, 255, 255, 255, 252, 255, 255, 255, 255, 255,
            255, 255, 254, 255, 255, 253, 255, 255, 253, 255, 255, 255, 255, 255, 252, 255, 255,
            255, 255, 255, 253, 255, 252, 255, 252, 253, 255, 255, 255, 250, 255, 255, 255, 255,
            255, 255, 255, 248, 255, 255, 255, 255, 245, 255, 255, 255, 252, 248, 255, 250, 252,
            255, 255, 255, 255, 255, 255, 245, 246, 250, 251, 251, 250, 255, 255, 255, 255, 255,
            255, 247, 250, 255, 255, 252, 238, 244, 253, 251, 246, 255, 255, 248, 255, 237, 247,
            253, 244, 255, 255, 255, 252, 247, 227, 252, 255, 255, 249, 246, 243, 255, 210, 249,
            253, 247, 255, 255, 250, 252, 214, 248, 253, 239, 244, 252, 255, 246, 211, 244, 252,
            250, 249, 249, 218, 255, 223, 255, 255, 255, 252, 250, 255, 240, 197, 243, 251, 249,
            241, 255, 248, 255, 249, 237, 208, 237, 252, 250, 254, 255, 255, 246, 249, 210, 244,
            242, 249, 250, 255, 246, 246, 255, 219, 255, 254, 245, 253, 255, 255, 236, 237, 249,
            251, 248, 229, 255, 238, 254, 252, 239, 255, 232, 95, 242, 251, 253, 255, 248, 248, 245,
            4, 255, 253, 255, 250, 255, 252, 255, 164, 236, 237, 244, 248, 243, 250, 255, 138, 247,
            253, 247, 255, 42, 243, 255, 249, 252, 246, 77, 252, 255, 255, 255, 217, 241, 242, 251,
            238, 255, 253, 253, 253, 255, 245, 255, 246, 255, 255, 254, 255, 252, 254, 254};
    int[] bluePoints =
        new int[]{251, 255, 255, 255, 255, 255, 255, 252, 255, 255, 253, 255, 255, 254, 255, 254,
            255, 253, 255, 255, 255, 255, 255, 255, 255, 255, 255, 252, 253, 249, 255, 255, 255,
            252, 255, 255, 252, 255, 250, 255, 255, 255, 255, 255, 255, 255, 255, 252, 252, 255,
            253, 255, 248, 252, 255, 253, 245, 255, 255, 255, 250, 252, 255, 251, 248, 255, 255,
            248, 255, 251, 253, 255, 251, 244, 255, 250, 249, 255, 253, 252, 251, 250, 254, 254,
            252, 253, 251, 252, 243, 249, 245, 250, 254, 252, 255, 255, 255, 246, 255, 251, 253,
            250, 248, 255, 236, 241, 247, 255, 255, 242, 251, 252, 244, 252, 252, 255, 245, 250,
            238, 249, 237, 239, 248, 255, 226, 255, 255, 255, 252, 252, 255, 242, 222, 234, 255,
            242, 231, 252, 253, 255, 207, 255, 253, 255, 252, 255, 254, 247, 210, 254, 228, 236,
            245, 244, 255, 247, 215, 255, 231, 255, 255, 249, 251, 255, 231, 255, 248, 217, 245,
            250, 245, 251, 215, 247, 253, 255, 212, 255, 254, 253, 229, 251, 254, 239, 215, 254,
            242, 250, 233, 250, 255, 252, 219, 253, 255, 255, 233, 255, 252, 253, 235, 255, 235,
            255, 241, 238, 255, 232, 252, 244, 255, 255, 219, 227, 247, 255, 237, 255, 250, 255,
            167, 255, 253, 248, 255, 233, 254, 254, 98, 255, 244, 243, 255, 252, 245, 254, 0, 255,
            243, 255, 118, 244, 255, 253, 77, 243, 251, 241, 187, 255, 243, 248, 254, 255, 255,
            254};
    assertTrue(MyImageTest.checkHistogramLines(path, redPoints, greenPoints, bluePoints));
  }

  @Test
  public void testLevelAdjustmentPercentageSmall() throws IOException {
    MyImage testImage = new MyImage("res/city.png");
    Image adjusted = imageService.levelAdjustment(testImage, 20, 100, 255, 0.3f, Axis.X);
    String path = "level-adjustment-histogram.png";
    adjusted.getHistogram().save(path);
    int[] redPoints =
        new int[]{252, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 253,
            255, 255, 255, 255, 252, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 251, 252,
            255, 255, 255, 255, 255, 255, 253, 255, 255, 247, 255, 255, 255, 253, 255, 255, 255,
            246, 255, 255, 254, 255, 255, 255, 238, 255, 251, 255, 255, 255, 255, 251, 255, 255,
            255, 228, 255, 255, 255, 249, 250, 249, 255, 239, 250, 255, 255, 255, 251, 249, 255,
            230, 255, 247, 255, 248, 234, 254, 254, 217, 255, 247, 255, 254, 255, 255, 255, 209,
            255, 253, 255, 229, 250, 241, 245, 214, 252, 255, 255, 253, 244, 255, 255, 229, 241,
            253, 234, 249, 245, 246, 255, 230, 255, 255, 255, 231, 255, 247, 255, 240, 246, 244,
            241, 250, 242, 228, 255, 157, 252, 255, 255, 249, 247, 251, 255, 103, 250, 246, 245,
            249, 252, 246, 243, 154, 255, 235, 255, 247, 250, 255, 249, 167, 249, 255, 242, 246,
            235, 250, 247, 179, 250, 252, 255, 221, 224, 253, 255, 217, 251, 236, 244, 198, 253,
            245, 242, 232, 242, 250, 254, 200, 255, 252, 247, 248, 250, 242, 251, 221, 249, 255,
            255, 251, 249, 255, 204, 255, 255, 244, 252, 254, 211, 255, 246, 246, 245, 249, 253,
            229, 255, 251, 255, 249, 253, 246, 244, 249, 252, 255, 254, 253, 253, 255, 245, 242,
            249, 255, 254, 255, 254, 250, 255, 255, 255, 255, 255, 252, 242, 255, 247, 255, 254,
            254, 254};
    int[] greenPoints =
        new int[]{252, 255, 255, 255, 255, 255, 255, 255, 255, 255, 252, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 253, 255, 255, 253, 255, 255, 255, 255, 255, 252, 255, 255,
            255, 255, 255, 254, 255, 254, 255, 251, 251, 255, 255, 255, 250, 255, 255, 255, 255,
            255, 255, 255, 246, 255, 255, 255, 255, 249, 255, 255, 255, 250, 245, 255, 249, 254,
            255, 255, 255, 255, 255, 255, 245, 250, 250, 246, 253, 248, 255, 255, 255, 255, 255,
            255, 244, 250, 255, 255, 250, 239, 244, 251, 253, 253, 255, 255, 252, 255, 234, 244,
            251, 242, 255, 255, 255, 251, 251, 219, 253, 255, 255, 249, 249, 250, 255, 204, 251,
            251, 245, 255, 255, 250, 250, 204, 248, 252, 248, 250, 255, 255, 250, 201, 244, 248,
            250, 252, 250, 232, 255, 217, 255, 255, 255, 254, 252, 255, 238, 216, 242, 251, 247,
            245, 255, 246, 255, 251, 238, 232, 239, 253, 249, 252, 255, 255, 246, 247, 234, 246,
            240, 255, 251, 255, 247, 251, 255, 233, 255, 253, 243, 249, 255, 255, 242, 245, 254,
            253, 254, 209, 254, 241, 253, 251, 244, 255, 237, 60, 245, 254, 251, 255, 249, 250, 248,
            0, 255, 251, 255, 252, 255, 255, 255, 165, 247, 237, 244, 250, 244, 249, 255, 179, 251,
            254, 249, 255, 134, 246, 255, 252, 254, 249, 150, 254, 255, 255, 255, 235, 245, 246,
            254, 244, 255, 254, 255, 254, 255, 248, 255, 248, 255, 255, 253, 255, 254, 255, 254};
    int[] bluePoints =
        new int[]{252, 255, 255, 255, 255, 255, 255, 252, 255, 255, 253, 255, 255, 255, 255, 255,
            255, 254, 255, 255, 255, 255, 255, 255, 255, 255, 255, 252, 253, 251, 255, 255, 255,
            254, 255, 255, 251, 255, 250, 255, 255, 255, 255, 255, 255, 255, 255, 251, 254, 255,
            251, 255, 246, 254, 255, 252, 247, 255, 255, 255, 250, 254, 255, 253, 245, 255, 255,
            248, 254, 253, 253, 255, 250, 241, 255, 250, 254, 255, 252, 250, 253, 249, 255, 253,
            249, 254, 250, 250, 241, 249, 249, 250, 253, 252, 255, 255, 255, 244, 255, 254, 251,
            250, 244, 255, 237, 240, 250, 255, 255, 241, 253, 251, 250, 254, 254, 255, 244, 248,
            237, 246, 238, 238, 247, 255, 227, 255, 255, 255, 255, 253, 255, 249, 213, 236, 255,
            244, 229, 251, 252, 255, 203, 255, 255, 255, 250, 255, 254, 247, 206, 253, 228, 243,
            251, 244, 255, 252, 211, 255, 243, 255, 255, 249, 251, 255, 229, 255, 245, 237, 251,
            249, 245, 254, 223, 249, 255, 255, 234, 255, 253, 249, 232, 251, 255, 240, 235, 255,
            249, 252, 235, 254, 255, 251, 236, 251, 255, 255, 234, 255, 253, 252, 245, 255, 241,
            255, 244, 242, 255, 240, 254, 243, 255, 255, 223, 238, 249, 255, 239, 255, 253, 255,
            157, 252, 254, 252, 255, 239, 253, 254, 66, 255, 246, 246, 255, 254, 249, 253, 0, 255,
            249, 255, 165, 247, 255, 255, 156, 245, 251, 244, 241, 255, 247, 255, 255, 255, 255,
            254};
    assertTrue(MyImageTest.checkHistogramLines(path, redPoints, greenPoints, bluePoints));
  }

  @Test
  public void testLevelAdjustmentPercentageBig() throws IOException {
    MyImage testImage = new MyImage("res/city.png");
    Image adjusted = imageService.levelAdjustment(testImage, 20, 100, 255, 0.8f, Axis.X);
    String path = "level-adjustment-histogram.png";
    adjusted.getHistogram().save(path);
    int[] redPoints =
        new int[]{250, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 249, 255, 253, 255, 255, 255, 255, 255, 255, 255, 255, 255, 254,
            255, 255, 255, 255, 255, 255, 247, 255, 255, 253, 255, 255, 255, 253, 255, 255, 255,
            253, 255, 255, 247, 255, 255, 255, 252, 255, 253, 255, 255, 255, 255, 238, 255, 255,
            255, 247, 255, 255, 255, 254, 254, 254, 255, 251, 253, 255, 255, 255, 255, 230, 255,
            249, 255, 253, 255, 248, 251, 251, 249, 246, 255, 240, 255, 251, 255, 255, 255, 244,
            251, 255, 255, 249, 255, 229, 252, 245, 247, 255, 255, 250, 233, 255, 255, 249, 216,
            255, 250, 250, 253, 255, 255, 249, 255, 255, 255, 202, 255, 253, 253, 252, 229, 253,
            249, 251, 244, 209, 255, 223, 255, 255, 255, 254, 250, 254, 255, 182, 253, 255, 252,
            235, 248, 253, 245, 220, 255, 226, 255, 255, 253, 255, 252, 242, 247, 255, 232, 255,
            251, 246, 238, 218, 242, 245, 255, 172, 231, 255, 255, 231, 247, 249, 251, 126, 251,
            241, 239, 245, 249, 244, 247, 138, 255, 247, 253, 245, 249, 250, 251, 156, 250, 255,
            247, 238, 250, 255, 175, 255, 255, 239, 246, 253, 207, 254, 235, 244, 238, 249, 249,
            214, 255, 252, 255, 250, 245, 246, 238, 247, 250, 255, 251, 255, 249, 255, 246, 238,
            252, 250, 252, 255, 252, 250, 255, 255, 255, 253, 255, 245, 248, 253, 252, 255, 249,
            250, 254};
    int[] greenPoints =
        new int[]{252, 255, 254, 255, 255, 255, 255, 254, 255, 255, 253, 255, 255, 255, 255, 255,
            255, 255, 253, 255, 255, 255, 255, 255, 254, 255, 255, 255, 255, 255, 254, 255, 255,
            255, 255, 255, 252, 255, 251, 255, 254, 255, 255, 255, 255, 251, 255, 255, 255, 255,
            255, 255, 255, 253, 255, 255, 255, 255, 245, 255, 255, 255, 254, 254, 255, 254, 251,
            255, 255, 255, 255, 255, 255, 248, 245, 254, 253, 249, 254, 255, 255, 255, 255, 255,
            255, 254, 254, 255, 255, 254, 245, 249, 255, 250, 246, 255, 255, 247, 255, 250, 253,
            255, 251, 255, 255, 255, 254, 244, 246, 250, 255, 255, 253, 248, 240, 255, 234, 251,
            254, 254, 255, 255, 254, 255, 244, 252, 255, 234, 243, 251, 255, 243, 244, 252, 255,
            255, 249, 250, 217, 255, 246, 255, 255, 255, 250, 250, 255, 249, 200, 252, 250, 251,
            243, 255, 253, 255, 248, 246, 199, 245, 251, 255, 254, 255, 255, 250, 255, 197, 243,
            253, 249, 247, 255, 252, 244, 255, 214, 255, 255, 251, 255, 255, 255, 238, 234, 244,
            251, 249, 241, 255, 242, 255, 252, 237, 255, 238, 195, 244, 251, 254, 255, 249, 246,
            245, 171, 255, 255, 255, 246, 255, 249, 255, 217, 235, 247, 251, 249, 254, 252, 255,
            157, 246, 253, 247, 255, 18, 242, 255, 251, 252, 247, 0, 251, 255, 255, 255, 193, 240,
            238, 242, 239, 255, 252, 252, 253, 255, 247, 255, 247, 255, 255, 255, 255, 252, 253,
            254};
    int[] bluePoints =
        new int[]{251, 255, 255, 255, 255, 255, 255, 253, 255, 255, 254, 255, 255, 253, 255, 254,
            255, 252, 255, 255, 255, 255, 255, 255, 255, 255, 255, 254, 255, 250, 255, 255, 255,
            251, 255, 255, 254, 255, 253, 255, 255, 255, 255, 255, 255, 255, 255, 254, 252, 255,
            255, 255, 253, 251, 255, 254, 246, 255, 255, 255, 254, 252, 255, 249, 254, 255, 255,
            253, 255, 250, 255, 255, 253, 252, 255, 254, 246, 255, 254, 254, 249, 253, 254, 255,
            254, 253, 253, 254, 249, 253, 242, 253, 254, 250, 255, 255, 255, 250, 255, 250, 255,
            249, 253, 255, 246, 253, 249, 255, 255, 247, 250, 252, 242, 250, 252, 255, 249, 252,
            248, 254, 249, 250, 255, 255, 238, 255, 255, 255, 251, 251, 255, 241, 245, 236, 255,
            239, 247, 255, 255, 255, 232, 255, 253, 255, 252, 255, 254, 251, 234, 255, 244, 238,
            243, 239, 255, 241, 235, 255, 227, 255, 255, 252, 253, 255, 248, 255, 254, 210, 244,
            255, 252, 249, 224, 248, 252, 255, 203, 255, 255, 255, 244, 254, 254, 244, 207, 253,
            234, 246, 246, 250, 255, 254, 211, 255, 255, 255, 246, 253, 252, 255, 229, 255, 236,
            255, 243, 237, 255, 227, 251, 250, 255, 255, 239, 222, 247, 255, 239, 255, 251, 255,
            209, 255, 252, 248, 255, 231, 255, 254, 204, 255, 244, 242, 255, 252, 246, 255, 121,
            255, 244, 255, 119, 248, 255, 253, 64, 255, 251, 242, 124, 255, 245, 230, 246, 255, 255,
            254};
    assertTrue(MyImageTest.checkHistogramLines(path, redPoints, greenPoints, bluePoints));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCompressNull() {
    imageService.haarWaveletCompress(null, 1);
  }

  @Test
  public void testCompressMono() {
    MyImage redImage = new MyImage("test/img/red.png");
    String expected =
        "RED:255 GREEN:0 BLUE:0    RED:255 GREEN:0 BLUE:0    "
        + "RED:255 GREEN:0 BLUE:0    RED:255 GREEN:0 BLUE:0    \n"
        + "RED:255 GREEN:0 BLUE:0    RED:255 GREEN:0 BLUE:0    "
        + "RED:255 GREEN:0 BLUE:0    RED:255 GREEN:0 BLUE:0    \n"
        + "RED:255 GREEN:0 BLUE:0    RED:255 GREEN:0 BLUE:0    "
        + "RED:255 GREEN:0 BLUE:0    RED:255 GREEN:0 BLUE:0    \n"
        + "RED:255 GREEN:0 BLUE:0    RED:255 GREEN:0 BLUE:0    "
        + "RED:255 GREEN:0 BLUE:0"
        + "    RED:255 GREEN:0 BLUE:0    \n";
    Image result = imageService.haarWaveletCompress(redImage, 0);
    assertEquals(expected, result.toString());
    result = imageService.haarWaveletCompress(redImage, 0.5f);
    expected =
        "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    "
        + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    \n"
        + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    "
        + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    \n"
        + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    "
        + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    \n"
        + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    "
        + "RED:0 GREEN:0 BLUE:0    RED:0 "
        + "GREEN:0 BLUE:0    \n";
    assertEquals(expected, result.toString());
    result = imageService.haarWaveletCompress(redImage, 1);
    assertEquals(expected, result.toString());
  }

  @Test
  public void testCompressWhite() {
    MyImage whiteImage = new MyImage("test/img/white.png");
    String expected =
        "RED:255 GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255    "
        + "RED:255 GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255    \n"
        + "RED:255 GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255    "
        + "RED:255 GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255    \n"
        + "RED:255 GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255    "
        + "RED:255 GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255    \n"
        + "RED:255 GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255    RED:255 "
        + "GREEN:255 BLUE:255    RED:255 GREEN:255 BLUE:255    \n";
    Image result = imageService.haarWaveletCompress(whiteImage, 0);
    assertEquals(expected, result.toString());

    result = imageService.haarWaveletCompress(whiteImage, 0.5f);
    expected =
        "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    "
        + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    \n"
        + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    "
        + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    \n"
        + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    "
        + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    \n"
        + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    "
        + "RED:0 GREEN:0 BLUE:0    RED:0 "
        + "GREEN:0 BLUE:0    \n";
    assertEquals(expected, result.toString());
    result = imageService.haarWaveletCompress(whiteImage, 1);
    expected =
        "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    "
        + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    \n"
        + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    "
        + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    \n"
        + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    "
        + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    \n"
        + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    "
        + "RED:0 GREEN:0 BLUE:0    RED:0 "
        + "GREEN:0 BLUE:0    \n";
    assertEquals(expected, result.toString());

  }

  @Test
  public void testCompressDi() {
    MyImage woGreenImage = new MyImage("test/img/woGreen.png");
    String expected =
        "RED:200 GREEN:0 BLUE:210    RED:200 GREEN:0 BLUE:210    "
        + "RED:200 GREEN:0 BLUE:210    RED:200 GREEN:0 BLUE:210    \n"
        + "RED:200 GREEN:0 BLUE:210    RED:200 GREEN:0 BLUE:210    "
        + "RED:200 GREEN:0 BLUE:210    RED:200 GREEN:0 BLUE:210    \n"
        + "RED:200 GREEN:0 BLUE:210    RED:200 GREEN:0 BLUE:210    "
        + "RED:200 GREEN:0 BLUE:210    RED:200 GREEN:0 BLUE:210    \n"
        + "RED:200 GREEN:0 BLUE:210    RED:200 GREEN:0 BLUE:210    "
        + "RED:200 GREEN:0 "
        + "BLUE:210    RED:200 GREEN:0 BLUE:210    \n";
    MyImage image = woGreenImage.imgArrayAddition(new float[]{0, 0, 10});
    Image result = imageService.haarWaveletCompress(image, 0);
    assertEquals(expected, result.toString());
    expected =
        "RED:0 GREEN:0 BLUE:210    RED:0 GREEN:0 BLUE:210    "
        + "RED:0 GREEN:0 BLUE:210    RED:0 GREEN:0 BLUE:210    \n"
        + "RED:0 GREEN:0 BLUE:210    RED:0 GREEN:0 BLUE:210    "
        + "RED:0 GREEN:0 BLUE:210    RED:0 GREEN:0 BLUE:210    \n"
        + "RED:0 GREEN:0 BLUE:210    RED:0 GREEN:0 BLUE:210    "
        + "RED:0 GREEN:0 BLUE:210    RED:0 GREEN:0 BLUE:210    \n"
        + "RED:0 GREEN:0 BLUE:210    RED:0 GREEN:0 BLUE:210    "
        + "RED:0 GREEN:0 BLUE:210    "
        + "RED:0 GREEN:0 BLUE:210    \n";
    result = imageService.haarWaveletCompress(image, 0.5f);
    assertEquals(expected, result.toString());
    expected =
        "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    "
        + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    \n"
        + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    "
        + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    \n"
        + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    "
        + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    \n"
        + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    "
        + "RED:0 GREEN:0 BLUE:0    RED:0 "
        + "GREEN:0 BLUE:0    \n";
    result = imageService.haarWaveletCompress(image, 1);
    assertEquals(expected, result.toString());
  }

  @Test
  public void testCompressTwoColor() {
    MyImage image = new MyImage("test/img/duoColor.png");
    Image result = imageService.haarWaveletCompress(image, 0);
    String expected = "RED:100 GREEN:200 BLUE:250    RED:100 GREEN:200 BLUE:250    \n"
                      + "RED:250 GREEN:100 BLUE:200    RED:250 GREEN:100 BLUE:200    \n";
    assertEquals(expected, result.toString());
    result = imageService.haarWaveletCompress(image, 0.5f);
    expected = "RED:175 GREEN:150 BLUE:225    RED:175 GREEN:150 BLUE:225    \n"
               + "RED:175 GREEN:150 BLUE:225    RED:175 GREEN:150 BLUE:225    \n";
    assertEquals(expected, result.toString());
    result = imageService.haarWaveletCompress(image, 1);
    expected = "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    \n"
               + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    \n";
    assertEquals(expected, result.toString());
  }

  //size 3x2
  @Test
  public void testCompressTri() {
    MyImage triImage = new MyImage("test/img/simple.ppm");
    Image result = imageService.haarWaveletCompress(triImage, 0);
    String expected = "RED:100 GREEN:85 BLUE:90    RED:30 GREEN:200 BLUE:150    \n"
                      + "RED:245 GREEN:50 BLUE:100    RED:105 GREEN:65 BLUE:75    \n"
                      + "RED:95 GREEN:205 BLUE:85    RED:215 GREEN:205 BLUE:105    \n";
    assertEquals(expected, result.toString());

    result = imageService.haarWaveletCompress(triImage, 0.5f);
    expected = "RED:96 GREEN:111 BLUE:76    RED:0 GREEN:176 BLUE:76    \n"
               + "RED:206 GREEN:26 BLUE:76    RED:101 GREEN:91 BLUE:76    \n"
               + "RED:176 GREEN:204 BLUE:123    RED:176 GREEN:204 BLUE:123    \n";
    assertEquals(expected, result.toString());

    result = imageService.haarWaveletCompress(triImage, 1);
    expected = "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    \n"
               + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    \n"
               + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    \n";
    assertEquals(expected, result.toString());

  }

  @Test
  public void testCompressBlack() {
    MyImage blackImage = new MyImage("test/img/black.ppm");
    String expected =
        "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    "
        + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    \n"
        + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    "
        + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    \n"
        + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    "
        + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    \n"
        + "RED:0 GREEN:0 BLUE:0    RED:0 GREEN:0 BLUE:0    "
        + "RED:0 GREEN:0 BLUE:0    RED:0 "
        + "GREEN:0 BLUE:0    \n";
    Image result = imageService.haarWaveletCompress(blackImage, 0);
    assertEquals(expected, result.toString());
    result = imageService.haarWaveletCompress(blackImage, 0.5f);
    assertEquals(expected, result.toString());
    result = imageService.haarWaveletCompress(blackImage, 1);
    assertEquals(expected, result.toString());
  }
}
