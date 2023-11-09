import org.junit.Before;
import org.junit.Test;

import model.Axis;
import model.Channel;
import model.image.Image;
import model.image.MyImage;
import service.ImageService;

import static org.junit.Assert.assertEquals;

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
    imageService.blur(null,1,Axis.X);
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
    imageService.sharpen(null,1,Axis.X);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetSepiaNull() {
    imageService.getSepia(null,1,Axis.X);
  }


  //---------------Create images that visualize individual R,G,B components of an image.------------


  @Test
  public void testSplitComponentRed() {
    Image testImage = new MyImage("test/img/split/rose.ppm");
    Image testImageRed = imageService.splitComponent(testImage, Channel.RED);
    Image expectedRed = new MyImage("test/img/split/rose_onlyRed.ppm");
    assertEquals(testImageRed, expectedRed);
  }

  @Test
  public void testSplitComponentGreen() {
    Image testImage = new MyImage("test/img/split/rose.ppm");
    Image testImageGreen = imageService.splitComponent(testImage, Channel.GREEN);
    Image expectedGreen = new MyImage("test/img/split/rose_onlyGreen.ppm");
    assertEquals(testImageGreen, expectedGreen);
  }

  @Test
  public void testSplitComponentBlue() {
    Image testImage = new MyImage("test/img/split/rose.ppm");
    Image testImageBlue = imageService.splitComponent(testImage, Channel.BLUE);
    Image expectedBlue = new MyImage("test/img/split/rose_onlyBlue.ppm");
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
    Image testImageBlack = new MyImage("test/img/monochromatic/black.ppm");
    Image testImageWhite = new MyImage("test/img/monochromatic/white.ppm");
    Image testImageRed = new MyImage("test/img/monochromatic/red.ppm");
    Image testImageGreen = new MyImage("test/img/monochromatic/green.ppm");
    Image testImageBlue = new MyImage("test/img/monochromatic/blue.ppm");

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
    Image testImageWoBlue = new MyImage("test/img/dichromatic/woBlue.ppm");
    Image testImageWoRed = new MyImage("test/img/dichromatic/woRed.ppm");
    Image testImageWoGreen = new MyImage("test/img/dichromatic/woGreen.ppm");

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
    Image testImage = new MyImage("test/img/trichromatic/simple.ppm");
    Image resultImage = imageService.getValue(testImage);
    String expected = "RED:100 GREEN:100 BLUE:100    RED:200 GREEN:200 BLUE:200    \n"
                      + "RED:245 GREEN:245 BLUE:245    RED:105 GREEN:105 BLUE:105    \n"
                      + "RED:205 GREEN:205 BLUE:205    RED:215 GREEN:215 BLUE:215    \n";
    assertEquals(expected, resultImage.toString());
  }

  @Test
  public void testGetIntensityMonochromatic() {
    Image testImageBlack = new MyImage("test/img/monochromatic/black.ppm");
    Image testImageWhite = new MyImage("test/img/monochromatic/white.ppm");
    Image testImageRed = new MyImage("test/img/monochromatic/red.ppm");
    Image testImageGreen = new MyImage("test/img/monochromatic/green.ppm");
    Image testImageBlue = new MyImage("test/img/monochromatic/blue.ppm");

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
    Image testImageWoBlue = new MyImage("test/img/dichromatic/woBlue.ppm");
    Image testImageWoRed = new MyImage("test/img/dichromatic/woRed.ppm");
    Image testImageWoGreen = new MyImage("test/img/dichromatic/woGreen.ppm");

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
    Image testImage = new MyImage("test/img/trichromatic/simple.ppm");
    Image resultImage = imageService.getIntensity(testImage);
    String expected = "RED:92 GREEN:92 BLUE:92    RED:127 GREEN:127 BLUE:127    \n"
                      + "RED:132 GREEN:132 BLUE:132    RED:82 GREEN:82 BLUE:82    \n"
                      + "RED:128 GREEN:128 BLUE:128    RED:175 GREEN:175 BLUE:175    \n";
    assertEquals(expected, resultImage.toString());
  }


  @Test
  public void testGetLumaMonochromatic() {
    Image testImageBlack = new MyImage("test/img/monochromatic/black.ppm");
    Image testImageWhite = new MyImage("test/img/monochromatic/white.ppm");
    Image testImageRed = new MyImage("test/img/monochromatic/red.ppm");
    Image testImageGreen = new MyImage("test/img/monochromatic/green.ppm");
    Image testImageBlue = new MyImage("test/img/monochromatic/blue.ppm");

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
    Image testImageWoBlue = new MyImage("test/img/dichromatic/woBlue.ppm");
    Image testImageWoRed = new MyImage("test/img/dichromatic/woRed.ppm");
    Image testImageWoGreen = new MyImage("test/img/dichromatic/woGreen.ppm");

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
    Image testImage = new MyImage("test/img/trichromatic/simple.ppm");
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
    Image testImagePPM = new MyImage("test/img/car.png");
    Image flippedImagePPM = imageService.flip(testImagePPM, Axis.Y);
    Image expectedImagePPM = new MyImage("test/img/car_horizontallyFlipped.png");
    assertEquals(flippedImagePPM, expectedImagePPM);
  }

  @Test
  public void testFlipVertically() {
    Image testImagePPM = new MyImage("test/img/car.png");
    Image flippedImagePPM = imageService.flip(testImagePPM, Axis.X);
    Image expectedImagePPM = new MyImage("test/img/car_verticallyFlipped.png");
    assertEquals(flippedImagePPM, expectedImagePPM);
  }

  @Test
  public void testFlipBoth() {
    Image testImagePPM = new MyImage("test/img/car.png");
    Image flippedImagePPM = imageService.flip(testImagePPM, Axis.X);
    Image doubleFlippedImagePPM = imageService.flip(flippedImagePPM, Axis.Y);
    Image expectedImagePPM = new MyImage("test/img/car_doubleFlipped.png");
    assertEquals(doubleFlippedImagePPM, expectedImagePPM);
  }

  @Test
  public void testFlipHorizontallyTwice() {
    Image testImagePPM = new MyImage("test/img/car.png");
    Image flippedImagePPM = imageService.flip(testImagePPM, Axis.Y);
    Image doubleFlippedImagePPM = imageService.flip(flippedImagePPM, Axis.Y);
    Image expectedImagePPM = new MyImage("test/img/car.png");
    assertEquals(doubleFlippedImagePPM, expectedImagePPM);
  }

  @Test
  public void testFlipVerticallyTwice() {
    Image testImagePPM = new MyImage("test/img/car.png");
    Image flippedImagePPM = imageService.flip(testImagePPM, Axis.X);
    Image doubleFlippedImagePPM = imageService.flip(flippedImagePPM, Axis.X);
    Image expectedImagePPM = new MyImage("test/img/car.png");
    assertEquals(doubleFlippedImagePPM, expectedImagePPM);
  }

  @Test
  public void testFlipMultipleTimes() {
    Image testImagePPM = new MyImage("test/img/car.png");
    Image flippedImagePPM = imageService.flip(testImagePPM, Axis.Y);
    Image doubleFlippedImagePPM = imageService.flip(flippedImagePPM, Axis.Y);
    Image tripleFlippedImagePPM = imageService.flip(doubleFlippedImagePPM, Axis.Y);
    Image expectedImagePPM = new MyImage("test/img/car_horizontallyFlipped.png");
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
    Image testImage = new MyImage("test/img/trichromatic/simple.ppm");
    Image resultImage = imageService.brighten(testImage, 2);

    String expected = "RED:102 GREEN:87 BLUE:92    RED:32 GREEN:202 BLUE:152    \n"
                      + "RED:247 GREEN:52 BLUE:102    RED:107 GREEN:67 BLUE:77    \n"
                      + "RED:97 GREEN:207 BLUE:87    RED:217 GREEN:207 BLUE:107    \n";
    System.out.println(resultImage);
    assertEquals(expected, resultImage.toString());
  }

  @Test
  public void testBrightenTwice() {
    Image testImage = new MyImage("test/img/trichromatic/simple.ppm");
    Image resultImage = imageService.brighten(testImage, 2);
    resultImage = imageService.brighten(resultImage, 2);

    String expected = "RED:104 GREEN:89 BLUE:94    RED:34 GREEN:204 BLUE:154    \n"
                      + "RED:249 GREEN:54 BLUE:104    RED:109 GREEN:69 BLUE:79    \n"
                      + "RED:99 GREEN:209 BLUE:89    RED:219 GREEN:209 BLUE:109    \n";
    assertEquals(expected, resultImage.toString());
  }

  @Test
  public void testBrightenWhite() {
    Image testImage = new MyImage("test/img/monochromatic/white.ppm");
    Image resultImage = imageService.brighten(testImage, 2);
    Image expectedImage = new MyImage("test/img/monochromatic/white.ppm");
    assertEquals(expectedImage, resultImage);
  }

  //part of the image/channel is already max while others are not
  @Test
  public void testBrightenPartiallyMax() {
    Image testImage = new MyImage("test/img/trichromatic/simple.ppm");
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
    Image testImage = new MyImage("test/img/trichromatic/simple.ppm");
    Image resultImage = imageService.brighten(testImage, -2);

    String expected = "RED:98 GREEN:83 BLUE:88    RED:28 GREEN:198 BLUE:148    \n"
                      + "RED:243 GREEN:48 BLUE:98    RED:103 GREEN:63 BLUE:73    \n"
                      + "RED:93 GREEN:203 BLUE:83    RED:213 GREEN:203 BLUE:103    \n";
    assertEquals(expected, resultImage.toString());
  }

  @Test
  public void testDarkenTwice() {
    Image testImage = new MyImage("test/img/trichromatic/simple.ppm");
    Image resultImage = imageService.brighten(testImage, -2);
    resultImage = imageService.brighten(resultImage, -2);

    String expected = "RED:96 GREEN:81 BLUE:86    RED:26 GREEN:196 BLUE:146    \n"
                      + "RED:241 GREEN:46 BLUE:96    RED:101 GREEN:61 BLUE:71    \n"
                      + "RED:91 GREEN:201 BLUE:81    RED:211 GREEN:201 BLUE:101    \n";
    assertEquals(expected, resultImage.toString());
  }

  @Test
  public void testDarkenBlack() {
    Image testImage = new MyImage("test/img/monochromatic/black.ppm");
    Image resultImage = imageService.brighten(testImage, -2);
    Image expectedImage = new MyImage("test/img/monochromatic/black.ppm");
    assertEquals(expectedImage, resultImage);
  }

  //part of the image/channel is already max while others are not
  @Test
  public void testDarkenPartiallyMin() {
    Image testImage = new MyImage("test/img/trichromatic/simple.ppm");
    System.out.println(testImage);
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
    Image testImage = new MyImage("test/img/trichromatic/simple.ppm");
    Image resultImage = imageService.brighten(testImage, -2);
    resultImage = imageService.brighten(resultImage, 2);
    Image expectedImage = new MyImage("test/img/trichromatic/simple.ppm");
    assertEquals(expectedImage, resultImage);
  }

  @Test
  public void testBrightenTwiceThenDarken() {
    Image testImage = new MyImage("test/img/trichromatic/simple.ppm");
    Image resultImage = imageService.brighten(testImage, 2);
    resultImage = imageService.brighten(resultImage, 2);
    resultImage = imageService.brighten(resultImage, -2);
    Image expectedImage = new MyImage("test/img/trichromatic/simple+2.ppm");
    assertEquals(expectedImage, resultImage);
  }


  //  Split a single image into 3 images representing each of the three channels.
  // different formats
  // of monochromatic, dichromatic and trichromatic images
  // repeatedly split
  @Test
  public void testSplitChannelMonochromatic() {
    Image testImage = new MyImage("test/img/monochromatic/red.ppm");
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
    Image testImage = new MyImage("test/img/dichromatic/woBlue.ppm");
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
    Image testImage = new MyImage("test/img/trichromatic/simple.ppm");
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
    Image testImage = new MyImage("test/img/trichromatic/simple.ppm");
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
    Image image = new MyImage("test/img/monochromatic/red.ppm");
    Image resultImages =
        imageService.combineChannels(new Channel[]{Channel.RED, Channel.GREEN, Channel.BLUE},
            imageService.splitChannel(image));
    assertEquals(image, resultImages);

    image = new MyImage("test/img/dichromatic/woBlue.ppm");
    resultImages =
        imageService.combineChannels(new Channel[]{Channel.RED, Channel.GREEN, Channel.BLUE},
            imageService.splitChannel(image));
    assertEquals(image, resultImages);

    //rectangle
    image = new MyImage("test/img/trichromatic/simple.ppm");
    resultImages =
        imageService.combineChannels(new Channel[]{Channel.RED, Channel.GREEN, Channel.BLUE},
            imageService.splitChannel(image));
    assertEquals(image, resultImages);
  }

  @Test
  public void testCombineChannelsRose() {
    Image expectedImage = new MyImage("test/img/split/rose.ppm");
    Image resultImages =
        imageService.combineChannels(new Channel[]{Channel.RED, Channel.GREEN, Channel.BLUE},
            imageService.splitChannel(expectedImage));
    assertEquals(expectedImage, resultImages);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCombineChannelsIllegalInputNotGreyscale() {
    Image imageR = new MyImage("test/img/monochromatic/white.ppm");
    Image imageG = new MyImage("test/img/monochromatic/green.ppm");
    Image imageB = new MyImage("test/img/monochromatic/blue.ppm");

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
    Image testImage = new MyImage("test/img/trichromatic/simple.ppm");
    Image resultImages = imageService.blur(testImage,1,Axis.X);
    Image expectedImage = new MyImage("test/img/trichromatic/simple_blur.ppm");
    assertEquals(expectedImage, resultImages);
  }

  @Test
  public void testBlurOnce() {
    Image testImage = new MyImage("test/img/cupcake.png");
    Image resultImages = imageService.blur(testImage,1,Axis.X);
    Image expectedImage = new MyImage("test/img/cupcake_blurOnce.png");

    assertEquals(expectedImage, resultImages);
  }

  @Test
  public void testBlurTwice() {
    Image testImage = new MyImage("test/img/cupcake.png");
    Image resultImages = imageService.blur(testImage,1,Axis.X);
    resultImages = imageService.blur(resultImages,1,Axis.X);
    Image expectedImage = new MyImage("test/img/cupcake_blurTwice.png");

    assertEquals(expectedImage, resultImages);
  }

  @Test
  public void testSharpenSimple() {
    Image testImage = new MyImage("test/img/trichromatic/simple.ppm");
    Image resultImages = imageService.sharpen(testImage,1,Axis.X);
    Image expectedImage = new MyImage("test/img/trichromatic/simple_sharpen.ppm");
    assertEquals(expectedImage, resultImages);
  }

  @Test
  public void testSharpenOnce() {
    Image testImage = new MyImage("test/img/cupcake.png");
    Image resultImages = imageService.sharpen(testImage,1,Axis.X);
    Image expectedImage = new MyImage("test/img/cupcake_sharpenOnce.png");
    assertEquals(expectedImage, resultImages);
  }

  @Test
  public void testSharpenTwice() {
    Image testImage = new MyImage("test/img/cupcake.png");
    Image resultImages = imageService.sharpen(testImage,1,Axis.X);
    resultImages = imageService.sharpen(resultImages,1,Axis.X);
    Image expectedImage = new MyImage("test/img/cupcake_sharpenTwice.png");
    assertEquals(expectedImage, resultImages);
  }

  @Test
  public void testBlurThenSharp() {
    Image testImage = new MyImage("test/img/cupcake.png");
    Image resultImages = imageService.blur(testImage,1,Axis.X);
    resultImages = imageService.sharpen(resultImages,1,Axis.X);
    Image expectedImage = new MyImage("test/img/cupcake_blurThenSharp.png");
    assertEquals(expectedImage, resultImages);
  }

  //  Convert an image into sepia as defined above.
  // different formats
  // of monochromatic, dichromatic and trichromatic images
  // repeatedly made sepia
  @Test
  public void testGetSepiaSimple() {
    Image testImage = new MyImage("test/img/trichromatic/simple.ppm");
    Image resultImages = imageService.getSepia(testImage,1,Axis.X);
    Image expectedImage = new MyImage("test/img/trichromatic/simple_sepia.ppm");
    assertEquals(expectedImage, resultImages);
  }

  @Test
  public void testGetSepiaCity() {
    Image testImage = new MyImage("test/img/city_small.png");
    Image resultImages = imageService.getSepia(testImage,1,Axis.X);
    Image expectedImage = new MyImage("test/img/city_small_sepia.png");
    assertEquals(expectedImage, resultImages);
  }

  @Test
  public void testGetSepiaCityTwice() {
    Image testImage = new MyImage("test/img/city_small.png");
    Image resultImages = imageService.getSepia(testImage,1,Axis.X);
    resultImages = imageService.getSepia(resultImages,1,Axis.X);
    Image expectedImage = new MyImage("test/img/city_small_sepia_sepia.png");
    assertEquals(expectedImage, resultImages);
  }

}
