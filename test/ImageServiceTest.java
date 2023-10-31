import org.junit.Before;
import org.junit.Test;

import model.Axis;
import model.Channel;
import model.Image;
import model.MyImage;
import service.ImageService;

import static org.junit.Assert.assertEquals;

public class ImageServiceTest {
  private ImageService imageService;

  @Before
  public void setUp() throws Exception {
    imageService = new ImageService();
  }

  //  -------------------Create images that visualize individual R,G,B components of an image.--------
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

  //  -------------Create images that visualize the value, intensity or luma of an image.-------------
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

    Image expectedImageBlack = new MyImage("test/img/monochromatic/black_value.ppm");
    Image expectedImageWhite = new MyImage("test/img/monochromatic/white_value.ppm");
    Image expectedImageRed = new MyImage("test/img/monochromatic/red_value.ppm");
    Image expectedImageGreen = new MyImage("test/img/monochromatic/green_value.ppm");
    Image expectedImageBlue = new MyImage("test/img/monochromatic/blue_value.ppm");

    assertEquals(resultImageBlack, expectedImageBlack);
    assertEquals(resultImageWhite, expectedImageWhite);
    assertEquals(resultImageRed, expectedImageRed);
    assertEquals(resultImageGreen, expectedImageGreen);
    assertEquals(resultImageBlue, expectedImageBlue);
  }

  @Test
  public void testGetValueDichromatic() {
    Image testImageWoBlue = new MyImage("test/img/dichromatic/woBlue.ppm");
    Image testImageWoRed = new MyImage("test/img/dichromatic/woRed.ppm");
    Image testImageWoGreen = new MyImage("test/img/dichromatic/woGreen.ppm");

    Image resultImageWoBlue = imageService.getValue(testImageWoBlue);
    Image resultImageWoRed = imageService.getValue(testImageWoRed);
    Image resultImageWoGreen = imageService.getValue(testImageWoGreen);

    Image expectedImageWoBlue = new MyImage("test/img/dichromatic/woBlue_value.ppm");
    Image expectedImageWoRed = new MyImage("test/img/dichromatic/woRed_value.ppm");
    Image expectedImageWoGreen = new MyImage("test/img/dichromatic/woGreen_value.ppm");

    assertEquals(resultImageWoBlue, expectedImageWoBlue);
    assertEquals(resultImageWoRed, expectedImageWoRed);
    assertEquals(resultImageWoGreen, expectedImageWoGreen);
  }

  @Test
  public void testGetValueTrichromatic() {
    Image testImage = new MyImage("test/img/trichromatic/simple.ppm");
    Image resultImage = imageService.getValue(testImage);
    Image expectedImage = new MyImage("test/img/trichromatic/simple_value.ppm");

    assertEquals(resultImage, expectedImage);
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

    Image expectedImageBlack = new MyImage("test/img/monochromatic/black_intensity.ppm");
    Image expectedImageWhite = new MyImage("test/img/monochromatic/white_intensity.ppm");
    Image expectedImageRed = new MyImage("test/img/monochromatic/red_intensity.ppm");
    Image expectedImageGreen = new MyImage("test/img/monochromatic/green_intensity.ppm");
    Image expectedImageBlue = new MyImage("test/img/monochromatic/blue_intensity.ppm");

    assertEquals(resultImageBlack, expectedImageBlack);
    assertEquals(resultImageWhite, expectedImageWhite);
    assertEquals(resultImageRed, expectedImageRed);
    assertEquals(resultImageGreen, expectedImageGreen);
    assertEquals(resultImageBlue, expectedImageBlue);
  }

  @Test
  public void testGetIntensityDichromatic() {
    Image testImageWoBlue = new MyImage("test/img/dichromatic/woBlue.ppm");
    Image testImageWoRed = new MyImage("test/img/dichromatic/woRed.ppm");
    Image testImageWoGreen = new MyImage("test/img/dichromatic/woGreen.ppm");

    Image resultImageWoBlue = imageService.getIntensity(testImageWoBlue);
    Image resultImageWoRed = imageService.getIntensity(testImageWoRed);
    Image resultImageWoGreen = imageService.getIntensity(testImageWoGreen);

    Image expectedImageWoBlue = new MyImage("test/img/dichromatic/woBlue_intensity.ppm");
    Image expectedImageWoRed = new MyImage("test/img/dichromatic/woRed_intensity.ppm");
    Image expectedImageWoGreen = new MyImage("test/img/dichromatic/woGreen_intensity.ppm");

    assertEquals(resultImageWoBlue, expectedImageWoBlue);
    assertEquals(resultImageWoRed, expectedImageWoRed);
    assertEquals(resultImageWoGreen, expectedImageWoGreen);
  }

  @Test
  public void testGetIntensityTrichromatic() {
    Image testImage = new MyImage("test/img/trichromatic/simple.ppm");
    Image resultImage = imageService.getIntensity(testImage);
    Image expectedImage = new MyImage("test/img/trichromatic/simple_intensity.ppm");

    assertEquals(resultImage, expectedImage);
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

    Image expectedImageBlack = new MyImage("test/img/monochromatic/black_luma.ppm");
    Image expectedImageWhite = new MyImage("test/img/monochromatic/white_luma.ppm");
    Image expectedImageRed = new MyImage("test/img/monochromatic/red_luma.ppm");
    Image expectedImageGreen = new MyImage("test/img/monochromatic/green_luma.ppm");
    Image expectedImageBlue = new MyImage("test/img/monochromatic/blue_luma.ppm");

    assertEquals(resultImageBlack, expectedImageBlack);
    assertEquals(resultImageWhite, expectedImageWhite);
    assertEquals(resultImageRed, expectedImageRed);
    assertEquals(resultImageGreen, expectedImageGreen);
    assertEquals(resultImageBlue, expectedImageBlue);
  }

  @Test
  public void testGetLumaDichromatic() {
    Image testImageWoBlue = new MyImage("test/img/dichromatic/woBlue.ppm");
    Image testImageWoRed = new MyImage("test/img/dichromatic/woRed.ppm");
    Image testImageWoGreen = new MyImage("test/img/dichromatic/woGreen.ppm");

    Image resultImageWoBlue = imageService.getLuma(testImageWoBlue);
    Image resultImageWoRed = imageService.getLuma(testImageWoRed);
    Image resultImageWoGreen = imageService.getLuma(testImageWoGreen);

    Image expectedImageWoBlue = new MyImage("test/img/dichromatic/woBlue_luma.ppm");
    Image expectedImageWoRed = new MyImage("test/img/dichromatic/woRed_luma.ppm");
    Image expectedImageWoGreen = new MyImage("test/img/dichromatic/woGreen_luma.ppm");

    assertEquals(resultImageWoBlue, expectedImageWoBlue);
    assertEquals(resultImageWoRed, expectedImageWoRed);
    assertEquals(resultImageWoGreen, expectedImageWoGreen);
  }

  @Test
  public void testGetLumaTrichromatic() {
    Image testImage = new MyImage("test/img/trichromatic/simple.ppm");
    Image resultImage = imageService.getLuma(testImage);
    Image expectedImage = new MyImage("test/img/trichromatic/simple_luma.ppm");

    assertEquals(resultImage, expectedImage);
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
    Image expectedImage = new MyImage("test/img/trichromatic/simple+2.ppm");
    assertEquals(resultImage, expectedImage);
  }

  @Test
  public void testBrightenTwice() {
    Image testImage = new MyImage("test/img/trichromatic/simple.ppm");
    Image resultImage = imageService.brighten(testImage, 2);
    resultImage = imageService.brighten(resultImage, 2);
    Image expectedImage = new MyImage("test/img/trichromatic/simple+4.ppm");
    assertEquals(resultImage, expectedImage);
  }

  @Test
  public void testBrightenWhite() {
    Image testImage = new MyImage("test/img/monochromatic/white.ppm");
    Image resultImage = imageService.brighten(testImage, 2);
    Image expectedImage = new MyImage("test/img/monochromatic/white.ppm");
    assertEquals(resultImage, expectedImage);
  }



  @Test
  public void testDarkenOnce() {
    Image testImage = new MyImage("test/img/trichromatic/simple.ppm");
    Image resultImage = imageService.brighten(testImage, -2);
    Image expectedImage = new MyImage("test/img/trichromatic/simple-2.ppm");
    assertEquals(resultImage, expectedImage);
  }

  @Test
  public void testDarkenTwice() {
    Image testImage = new MyImage("test/img/trichromatic/simple.ppm");
    Image resultImage = imageService.brighten(testImage, -2);
    resultImage = imageService.brighten(resultImage, -2);
    Image expectedImage = new MyImage("test/img/trichromatic/simple-4.ppm");
    assertEquals(resultImage, expectedImage);
  }

  @Test
  public void testDarkenBlack() {
    Image testImage = new MyImage("test/img/monochromatic/black.ppm");
    Image resultImage = imageService.brighten(testImage, -2);
    Image expectedImage = new MyImage("test/img/monochromatic/black.ppm");
    assertEquals(resultImage, expectedImage);
  }

  @Test
  public void testDarkenThenBrighten() {
    Image testImage = new MyImage("test/img/trichromatic/simple.ppm");
    Image resultImage = imageService.brighten(testImage, -2);
    resultImage = imageService.brighten(resultImage, 2);
    Image expectedImage = new MyImage("test/img/trichromatic/simple.ppm");
    assertEquals(resultImage, expectedImage);
  }

  @Test
  public void testBrightenTwiceThenDarken() {
    Image testImage = new MyImage("test/img/trichromatic/simple.ppm");
    Image resultImage = imageService.brighten(testImage, 2);
    resultImage = imageService.brighten(resultImage, 2);
    resultImage = imageService.brightengit(resultImage, -2);
    Image expectedImage = new MyImage("test/img/trichromatic/simple+2.ppm");
    assertEquals(resultImage, expectedImage);
  }

  //
//  Split a single image into 3 images representing each of the three channels.
  // different formats
  // of monochromatic, dichromatic and trichromatic images
  // repeatedly split
  @Test
  public void testSplitChannelMonochromatic() {
    Image testImage = new MyImage("test/img/monochromatic/red.ppm");
    Image[] resultImages = imageService.splitChannel(testImage);
    Image expectedimageR = new MyImage("test/img/monochromatic/red_grayScale_r.ppm");
    Image expectedimageG = new MyImage("test/img/monochromatic/red_grayScale_g.ppm");
    Image expectedimageB = new MyImage("test/img/monochromatic/red_grayScale_b.ppm");
    assertEquals(resultImages[0], expectedimageR);
    assertEquals(resultImages[1], expectedimageG);
    assertEquals(resultImages[2], expectedimageB);
  }

  @Test
  public void testSplitChannelDichromatic() {
    Image testImage = new MyImage("test/img/dichromatic/woBlue.ppm");
    Image[] resultImages = imageService.splitChannel(testImage);
    Image expectedimageR = new MyImage("test/img/dichromatic/woBlue_r.ppm");
    Image expectedimageG = new MyImage("test/img/dichromatic/woBlue_g.ppm");
    Image expectedimageB = new MyImage("test/img/dichromatic/woBlue_b.ppm");
    assertEquals(resultImages[0], expectedimageR);
    assertEquals(resultImages[1], expectedimageG);
    assertEquals(resultImages[2], expectedimageB);
  }

  @Test
  public void testSplitChannelTrichromatic() {
    Image testImage = new MyImage("test/img/trichromatic/simple.ppm");
    Image[] resultImages = imageService.splitChannel(testImage);
    Image expectedimageR = new MyImage("test/img/trichromatic/simple_greyScale_r.ppm");
    Image expectedimageG = new MyImage("test/img/trichromatic/simple_greyScale_g.ppm");
    Image expectedimageB = new MyImage("test/img/trichromatic/simple_greyScale_b.ppm");
    assertEquals(resultImages[0], expectedimageR);
    assertEquals(resultImages[1], expectedimageG);
    assertEquals(resultImages[2], expectedimageB);
  }

  @Test
  public void testSplitChannelTrichromaticTwice() {
    Image testImage = new MyImage("test/img/trichromatic/simple.ppm");
    Image[] resultImages = imageService.splitChannel(testImage);
    resultImages = imageService.splitChannel(resultImages[0]);
    Image expectedimageR = new MyImage("test/img/trichromatic/simple_greyScale_r_r.ppm");
    Image expectedimageG = new MyImage("test/img/trichromatic/simple_greyScale_r_g.ppm");
    Image expectedimageB = new MyImage("test/img/trichromatic/simple_greyScale_r_b.ppm");
    assertEquals(resultImages[0], expectedimageR);
    assertEquals(resultImages[1], expectedimageG);
    assertEquals(resultImages[2], expectedimageB);
  }

  //  Combine three greyscale image into a single color image whose R,G,B values come from the three images.
  // (at least one of the)input is not legal greyscale image.
  // different formats
  // result color is black/white/red/green/blue/mixed color
  @Test
  public void testCombineChannelsMonochromaticTwoColor() {
    Image imageR = new MyImage("test/img/monochromatic/red.ppm");
    Image imageG = new MyImage("test/img/monochromatic/green.ppm");
    Image imageB = new MyImage("test/img/monochromatic/black.ppm");

    Image resultImages = imageService.combineChannels(new Channel[]{Channel.RED, Channel.GREEN,
        Channel.BLUE}, new Image[]{imageR, imageG, imageB});
    Image expectedImage = new MyImage("test/img/dichromatic/RedAndGreen.ppm");

    assertEquals(resultImages, expectedImage);
  }

  @Test
  public void testCombineChannelsMonochromaticThreeColor() {
    Image imageR = new MyImage("test/img/monochromatic/red.ppm");
    Image imageG = new MyImage("test/img/monochromatic/green.ppm");
    Image imageB = new MyImage("test/img/monochromatic/blue.ppm");

    Image resultImages = imageService.combineChannels(new Channel[]{Channel.RED, Channel.GREEN,
        Channel.BLUE}, new Image[]{imageR, imageG, imageB});
    Image expectedImage = new MyImage("test/img/monochromatic/white.ppm");

    assertEquals(resultImages, expectedImage);
  }

  @Test
  public void testCombineChannelsRose() {
    Image imageR = new MyImage("test/img/split/rose_onlyRed.ppm");
    Image imageG = new MyImage("test/img/split/rose_onlyGreen.ppm");
    Image imageB = new MyImage("test/img/split/rose_onlyBlue.ppm");

    Image resultImages = imageService.combineChannels(new Channel[]{Channel.RED, Channel.GREEN,
        Channel.BLUE}, new Image[]{imageR, imageG, imageB});
    Image expectedImage = new MyImage("test/img/split/rose.ppm");

    assertEquals(resultImages, expectedImage);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCombineChannelsIllegalInputFirstChannel() {
    Image imageR = new MyImage("test/img/monochromatic/white.ppm");
    Image imageG = new MyImage("test/img/monochromatic/green.ppm");
    Image imageB = new MyImage("test/img/monochromatic/blue.ppm");

    Image resultImages = imageService.combineChannels(new Channel[]{Channel.RED, Channel.GREEN,
        Channel.BLUE}, new Image[]{imageR, imageG, imageB});
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCombineChannelsIllegalInputSecondChannel() {
    Image imageR = new MyImage("test/img/monochromatic/red.ppm");
    Image imageG = new MyImage("test/img/monochromatic/white.ppm");
    Image imageB = new MyImage("test/img/monochromatic/blue.ppm");

    Image resultImages = imageService.combineChannels(new Channel[]{Channel.RED, Channel.GREEN,
        Channel.BLUE}, new Image[]{imageR, imageG, imageB});
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCombineChannelsIllegalInputNoChannel() {
    Image resultImages = imageService.combineChannels(new Channel[]{}, new Image[]{});
  }

  //  Blur or sharpen an image as defined above.
  // different formats
  // of monochromatic, dichromatic and trichromatic images
  // repeatedly sharpen
  @Test
  public void testBlurSimple() {
    Image testImage = new MyImage("test/img/trichromatic/simple.ppm");
    Image resultImages = imageService.blur(testImage);
    Image expectedImage = new MyImage("test/img/trichromatic/simple_blur.ppm");
    assertEquals(resultImages, expectedImage);
  }

  @Test
  public void testBlurOnce() {
    Image testImage = new MyImage("test/img/cupcake.png");
    Image resultImages = imageService.blur(testImage);
    Image expectedImage = new MyImage("test/img/cupcake_blurOnce.png");

    assertEquals(resultImages, expectedImage);
  }

  @Test
  public void testBlurTwice() {
    Image testImage = new MyImage("test/img/cupcake.png");
    Image resultImages = imageService.blur(testImage);
    resultImages = imageService.blur(resultImages);
    Image expectedImage = new MyImage("test/img/cupcake_blurTwice.png");

    assertEquals(resultImages, expectedImage);
  }

  @Test
  public void testSharpenSimple() {
    Image testImage = new MyImage("test/img/trichromatic/simple.ppm");
    Image resultImages = imageService.sharpen(testImage);
    Image expectedImage = new MyImage("test/img/trichromatic/simple_sharpen.ppm");
    assertEquals(resultImages, expectedImage);
  }

  @Test
  public void testSharpenOnce() {
    Image testImage = new MyImage("test/img/cupcake.png");
    Image resultImages = imageService.sharpen(testImage);
    Image expectedImage = new MyImage("test/img/cupcake_sharpenOnce.png");
    assertEquals(resultImages, expectedImage);
  }

  @Test
  public void testSharpenTwice() {
    Image testImage = new MyImage("test/img/cupcake.png");
    Image resultImages = imageService.sharpen(testImage);
    resultImages = imageService.sharpen(resultImages);
    Image expectedImage = new MyImage("test/img/cupcake_sharpenTwice.png");
    assertEquals(resultImages, expectedImage);
  }

  @Test
  public void testBlurThenSharp() {
    Image testImage = new MyImage("test/img/cupcake.png");
    Image resultImages = imageService.blur(testImage);
    resultImages = imageService.sharpen(resultImages);
    Image expectedImage = new MyImage("test/img/cupcake_blurThenSharp.png");
    assertEquals(resultImages, expectedImage);
  }

  //  Convert an image into sepia as defined above.
  // different formats
  // of monochromatic, dichromatic and trichromatic images
  // repeatedly made sepia
  @Test
  public void testGetSepiaSimple() {
    Image testImage = new MyImage("test/img/trichromatic/simple.ppm");
    Image resultImages = imageService.getSepia(testImage);
    Image expectedImage = new MyImage("test/img/trichromatic/simple_sepia.ppm");
    assertEquals(resultImages, expectedImage);
  }

  @Test
  public void testGetSepiaCity() {
    Image testImage = new MyImage("test/img/city_small.png");
    Image resultImages = imageService.getSepia(testImage);
    Image expectedImage = new MyImage("test/img/city_small_sepia.png");
    assertEquals(resultImages, expectedImage);
  }

  @Test
  public void testGetSepiaCityTwice() {
    Image testImage = new MyImage("test/img/city_small.png");
    Image resultImages = imageService.getSepia(testImage);
    resultImages = imageService.getSepia(resultImages);
    Image expectedImage = new MyImage("test/img/city_small_sepia_sepia.png");
    assertEquals(resultImages, expectedImage);
  }

// A combination of the previous methods

//  Save an image to an ASCII PPM, JPG or PNG file (see below).
  //save with absolute path
  //save with relative
  //src image is create directly from this program
  //src image read from local and directly saved without modification
  //src image read from local and saved with modification
  //src format same as saved format
  //src format different as saved format
  //different color and size

}
