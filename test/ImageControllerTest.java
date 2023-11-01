import org.junit.Test;

import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import controller.ImageController;
import model.image.Image;
import model.image.MyImage;
import view.ImageView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * This test class is to test the controller component. For the controller, need to test whether it
 * correctly gets the command that user inputted, correctly gets the related images and transfers
 * those information to ImageService component correctly or not. Also, this test class tests whether
 * two ways (console and text file) for users to input their commands work correctly (allow comments
 * exist) or not.
 */
public class ImageControllerTest {

  @Test
  public void testPathNotExist() {
    String command = "load xxxx xxx\n exit";
    StringReader reader = new StringReader(command);
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    ImageView blurImageView = new ImageView(reader, printWriter);
    MockImageService mockService = new MockImageService(printWriter);
    ImageController controller = new ImageController(mockService, blurImageView);
    controller.start();
    String output = stringWriter.toString();
    assertTrue(output.contains("Path does not exist or something wrong with file format."));
  }

  @Test
  public void testPathHasSpaceDoubleQuote() {
    String command = "load \"test/img/car copy.jpg\" xxx\n exit";
    StringReader reader = new StringReader(command);
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    ImageView blurImageView = new ImageView(reader, printWriter);
    MockImageService mockService = new MockImageService(printWriter);
    ImageController controller = new ImageController(mockService, blurImageView);
    controller.start();
    String output = stringWriter.toString();
    assertTrue(output.contains("Loading new image: xxx"));
  }

  @Test
  public void testPathHasSpaceSingleQuote() {
    String command = "load 'test/img/car copy.jpg' xxx\n exit";
    StringReader reader = new StringReader(command);
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    ImageView blurImageView = new ImageView(reader, printWriter);
    MockImageService mockService = new MockImageService(printWriter);
    ImageController controller = new ImageController(mockService, blurImageView);
    controller.start();
    String output = stringWriter.toString();
    assertTrue(output.contains("Loading new image: xxx"));
  }


  @Test
  public void testCommandsTooLong() {
    String command = "load test/img/car copy.jpg xxx\n exit";
    StringReader reader = new StringReader(command);
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    ImageView blurImageView = new ImageView(reader, printWriter);
    MockImageService mockService = new MockImageService(printWriter);
    ImageController controller = new ImageController(mockService, blurImageView);
    controller.start();
    String output = stringWriter.toString();
    assertTrue(output.contains(
        "Argument number not right. Use double or single quotes for path with space."));
  }

  @Test
  public void testCommandsTooShort() {
    String command = "load\n exit";
    StringReader reader = new StringReader(command);
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    ImageView blurImageView = new ImageView(reader, printWriter);
    MockImageService mockService = new MockImageService(printWriter);
    ImageController controller = new ImageController(mockService, blurImageView);
    controller.start();
    String output = stringWriter.toString();
    assertTrue(output.contains(
        "Argument number not right. Use double or single quotes for path with space."));
  }

  /**
   * Test the blur command when there is no image loaded.
   */
  @Test
  public void testNullBlurCommand() {

    String blurCommand = "blur cupcake cupcake_blurOnce\n exit";
    StringReader blurReader = new StringReader(blurCommand);
    StringWriter blurWriter = new StringWriter();
    PrintWriter blurPrintWriter = new PrintWriter(blurWriter);

    ImageView blurImageView = new ImageView(blurReader, blurPrintWriter);
    MockImageService mockService = new MockImageService(blurPrintWriter);
    ImageController blurController = new ImageController(mockService, blurImageView);
    blurController.start();

    String output = blurWriter.toString();
    assertTrue(output.contains("No image loaded"));

  }

  /**
   * Test the blur command when there exist a image.
   */
  @Test
  public void testBlurCommand() {
    String blurCommand = "load test/img/cupcake.png cupcake\n blur cupcake cupcake_blurOnce\n exit";
    StringReader blurReader = new StringReader(blurCommand);
    StringWriter blurWriter = new StringWriter();
    PrintWriter blurPrintWriter = new PrintWriter(blurWriter);

    Image targetImage = new MyImage("test/img/cupcake.png");
    ImageView blurImageView = new ImageView(blurReader, blurPrintWriter);
    MockImageService mockService = new MockImageService(blurPrintWriter);
    ImageController blurController = new ImageController(mockService, blurImageView);
    blurController.start();
    String output = blurWriter.toString();
    assertTrue(output.contains("Image blurred"));

    Image executeBlur = blurController.loadedImages.get("cupcake_blurOnce");
    Image expectBlur = new MyImage("test/img/cupcake_blurOnce.png");
    assertEquals(expectBlur, executeBlur);
    assertTrue(output.contains(targetImage.hashCode() + ""));
  }

  /**
   * Test the value-component command when there is no image loaded.
   */
  @Test
  public void testNullValueComponent() {

    String valueCommand = "value-component black black_value\n exit";
    StringReader valueReader = new StringReader(valueCommand);
    StringWriter valueWriter = new StringWriter();
    PrintWriter valuePrintWriter = new PrintWriter(valueWriter);
    ImageView valueImageView = new ImageView(valueReader, valuePrintWriter);
    MockImageService mockImageService = new MockImageService(valuePrintWriter);
    ImageController valueController = new ImageController(mockImageService, valueImageView);
    valueController.start();
    String output = valueWriter.toString();
    assertTrue(output.contains("No image loaded"));

  }

  /**
   * Test the value component command when there exists a image.
   */
  @Test
  public void testValueComponent() {
    String valueCommand = "load test/img/monochromatic/black.ppm black\n "
                          + "value-component black black_value\n exit";
    StringReader valueReader = new StringReader(valueCommand);
    StringWriter valueWriter = new StringWriter();
    PrintWriter valuePrintWriter = new PrintWriter(valueWriter);
    ImageView valueImageView = new ImageView(valueReader, valuePrintWriter);
    MockImageService mockImageService = new MockImageService(valuePrintWriter);
    ImageController valueController = new ImageController(mockImageService, valueImageView);
    valueController.start();
    String output = valueWriter.toString();
    assertTrue(output.contains("Get the value-component"));

    Image executeValue = valueController.loadedImages.get("black_value");
    System.out.println(executeValue);

    Image targetImage = new MyImage("test/img/monochromatic/black.ppm");
    assertTrue(output.contains(targetImage.hashCode() + ""));
  }

  /**
   * Test the intensity-component command when the image is null.
   */
  @Test
  public void testNullIntensityComponent() {
    String intensityCommand = "intensity-component black black_intensity\n exit";
    StringReader intensityReader = new StringReader(intensityCommand);
    StringWriter intensityWriter = new StringWriter();
    PrintWriter intensityPrintWriter = new PrintWriter(intensityWriter);

    ImageView intensityImageView = new ImageView(intensityReader, intensityPrintWriter);
    MockImageService mockImageService = new MockImageService(intensityPrintWriter);
    ImageController intensityController = new ImageController(mockImageService, intensityImageView);
    intensityController.start();
    String output = intensityWriter.toString();
    assertTrue(output.contains("No image loaded"));
  }

  /**
   * Test the intensity-component command.
   */
  @Test
  public void testIntensityComponent() {
    String intensityCommand = "load test/img/monochromatic/black.ppm black\n "
                              + "intensity-component black black_intensity\n exit";
    StringReader intensityReader = new StringReader(intensityCommand);
    StringWriter intensityWriter = new StringWriter();
    PrintWriter intensityPrintWriter = new PrintWriter(intensityWriter);

    ImageView intensityImageView = new ImageView(intensityReader, intensityPrintWriter);
    MockImageService mockImageService = new MockImageService(intensityPrintWriter);
    ImageController intensityController = new ImageController(mockImageService, intensityImageView);
    intensityController.start();
    String output = intensityWriter.toString();
    assertTrue(output.contains("Get the intensity-component"));
    Image executeIntensity = intensityController.loadedImages.get("black_intensity");
    Image expectIntensity = new MyImage("res/city_small_intensity.png");
    assertEquals(expectIntensity, executeIntensity);
    assertTrue(output.contains(new MyImage("test/img/monochromatic/black.ppm").hashCode() + ""));
  }

  /**
   * Test the luma-component command when there is not image exists.
   */
  @Test
  public void testNullLumaComponent() {
    String lumaCommand = "luma-component black black_luma\n exit";
    StringReader lumaReader = new StringReader(lumaCommand);
    StringWriter lumaWriter = new StringWriter();
    PrintWriter lumaPrintWriter = new PrintWriter(lumaWriter);
    ImageView lumaImageView = new ImageView(lumaReader, lumaPrintWriter);
    MockImageService mockImageService = new MockImageService(lumaPrintWriter);
    ImageController lumaController = new ImageController(mockImageService, lumaImageView);
    lumaController.start();
    String output = lumaWriter.toString();
    assertTrue(output.contains("No image loaded"));
  }

  /**
   * Test the luma-component command.
   */
  @Test
  public void testLumaComponent() {
    String lumaCommand = "load test/img/monochromatic/black.ppm black\n "
                         + "luma-component black black_luma\n exit";
    StringReader lumaReader = new StringReader(lumaCommand);
    StringWriter lumaWriter = new StringWriter();
    PrintWriter lumaPrintWriter = new PrintWriter(lumaWriter);
    ImageView lumaImageView = new ImageView(lumaReader, lumaPrintWriter);
    MockImageService mockImageService = new MockImageService(lumaPrintWriter);
    ImageController lumaController = new ImageController(mockImageService, lumaImageView);
    lumaController.start();

    String output = lumaWriter.toString();
    assertTrue(output.contains("Get the luma-component"));

    Image executeLuma = lumaController.loadedImages.get("black_luma");
    Image expectLuma = new MyImage("res/city_small_luma.png");
    assertTrue(output.contains(new MyImage("test/img/monochromatic/black.ppm").hashCode() + ""));

    assertEquals(expectLuma, executeLuma);
  }

  /**
   * Test the RGB combine command when there are not images.
   */
  @Test
  public void testNullRGBCombine() {

    String combineCommand = "rgb-combine rose rose_onlyRed rose_onlyGreen rose_onlyBlue\n exit";
    StringReader combineReader = new StringReader(combineCommand);
    StringWriter combineWriter = new StringWriter();
    PrintWriter combinePrintWriter = new PrintWriter(combineWriter);

    ImageView combineImageView = new ImageView(combineReader, combinePrintWriter);
    MockImageService mockImageService = new MockImageService(combinePrintWriter);
    ImageController combineController = new ImageController(mockImageService, combineImageView);
    combineController.start();
    String output = combineWriter.toString();
    assertTrue(output.contains("No image loaded"));

  }

  /**
   * Test the RGB combine command.
   */
  @Test
  public void testRGBCombine() {

    String combineCommand = "load test/img/split/rose_onlyRed.jpg rose_onlyRed\n "
                            + "load test/img/split/rose_onlyGreen.jpg rose_onlyGreen\n"
                            + "load test/img/split/rose_onlyBlue.jpg rose_onlyBlue\n"
                            + "rgb-combine rose rose_onlyRed rose_onlyGreen rose_onlyBlue\n exit";
    StringReader combineReader = new StringReader(combineCommand);
    StringWriter combineWriter = new StringWriter();
    PrintWriter combinePrintWriter = new PrintWriter(combineWriter);

    ImageView combineImageView = new ImageView(combineReader, combinePrintWriter);
    MockImageService mockImageService = new MockImageService(combinePrintWriter);
    ImageController combineController = new ImageController(mockImageService, combineImageView);
    combineController.start();
    String output = combineWriter.toString();
    assertTrue(output.contains("Images combined successfully"));

    Image executeCombine = combineController.loadedImages.get("rose");
    Image expectCombine = new MyImage("test/img/split/rose.ppm");

    Image targetImageR = new MyImage("test/img/split/rose_onlyRed.jpg");
    Image targetImageG = new MyImage("test/img/split/rose_onlyGreen.jpg");
    Image targetImageB = new MyImage("test/img/split/rose_onlyBlue.jpg");

    assertEquals(expectCombine, executeCombine);
    assertTrue(output.contains(targetImageR.hashCode() + ""));
    assertTrue(output.contains(targetImageG.hashCode() + ""));
    assertTrue(output.contains(targetImageB.hashCode() + ""));
  }

  /**
   * Test the RGB Split command when there is not exist image.
   */
  @Test
  public void testNullRGBSplit() {

    String splitCommand = "rgb-split rose rose_onlyRed rose_onlyGreen rose_onlyBlue\n exit";
    StringReader splitReader = new StringReader(splitCommand);
    StringWriter splitWriter = new StringWriter();
    PrintWriter splitPrintWriter = new PrintWriter(splitWriter);

    ImageView splitImageView = new ImageView(splitReader, splitPrintWriter);
    MockImageService mockImageService = new MockImageService(splitPrintWriter);
    ImageController controller = new ImageController(mockImageService, splitImageView);
    controller.start();
    String output = splitWriter.toString();
    assertTrue(output.contains("No image loaded"));
  }


  /**
   * Test the RGB Split command.
   */
  @Test
  public void testRGBSplit() {
    String splitCommand = "load test/img/split/rose.jpg rose\n"
                          + "rgb-split rose rose_onlyRed rose_onlyGreen rose_onlyBlue\n exit";
    StringReader splitReader = new StringReader(splitCommand);
    StringWriter splitWriter = new StringWriter();
    PrintWriter splitPrintWriter = new PrintWriter(splitWriter);

    ImageView splitImageView = new ImageView(splitReader, splitPrintWriter);
    MockImageService mockImageService = new MockImageService(splitPrintWriter);
    ImageController controller = new ImageController(mockImageService, splitImageView);
    controller.start();
    String output = splitWriter.toString();
    assertTrue(output.contains("Split the image."));

    Image[] splitImages = new Image[3];
    splitImages[0] = controller.loadedImages.get("rose_onlyRed");
    splitImages[1] = controller.loadedImages.get("rose_onlyGreen");
    splitImages[2] = controller.loadedImages.get("rose_onlyBlue");

    Image myImage1 = new MyImage("test/img/split/rose_onlyRed.jpg");
    Image myImage2 = new MyImage("test/img/split/rose_onlyGreen.jpg");
    Image myImage3 = new MyImage("test/img/split/rose_onlyBlue.jpg");

    assertEquals(myImage1, splitImages[0]);
    assertEquals(myImage2, splitImages[1]);
    assertEquals(myImage3, splitImages[2]);

    Image targetImage = new MyImage("test/img/split/rose.jpg");
    assertTrue(output.contains(targetImage.hashCode() + ""));
  }

  /**
   * Test the split component command when there is not exist image.
   */
  @Test
  public void testNullSplitComponent() {
    String splitCommand = "red-component simple simple_greyScale_r\n exit";
    StringReader splitReader = new StringReader(splitCommand);
    StringWriter splitWriter = new StringWriter();
    PrintWriter splitPrintWriter = new PrintWriter(splitWriter);
    ImageView splitImageView = new ImageView(splitReader,
        splitPrintWriter);
    MockImageService mockImageService = new MockImageService(splitPrintWriter);
    ImageController splitController = new ImageController(mockImageService, splitImageView);
    splitController.start();
    String output = splitWriter.toString();
    assertTrue(output.contains("No image loaded"));
  }

  /**
   * Test the split component command.
   */
  @Test
  public void testSplitComponent() {
    String splitCommand = "load test/img/trichromatic/simple.ppm simple\n "
                          + "red-component simple simple_greyScale_r\n exit";
    StringReader splitReader = new StringReader(splitCommand);
    StringWriter splitWriter = new StringWriter();
    PrintWriter splitPrintWriter = new PrintWriter(splitWriter);
    ImageView splitImageView = new ImageView(splitReader,
        splitPrintWriter);
    MockImageService mockImageService = new MockImageService(splitPrintWriter);
    ImageController splitController = new ImageController(mockImageService, splitImageView);
    splitController.start();
    String output = splitWriter.toString();
    assertTrue(output.contains("Split image in red component"));

    Image executeSplit = splitController.loadedImages.get("simple_greyScale_r");
    Image expectSplit = new MyImage("res/city_small_red_channel_greyscale.png");

    assertEquals(expectSplit, executeSplit);

    Image targetImage = new MyImage("test/img/trichromatic/simple.ppm");
    assertTrue(output.contains(targetImage.hashCode() + ""));
  }

  /**
   * Test the flip command when there is not exist image.
   */
  @Test
  public void testNullFlip() {
    String flipCommand = "horizontal-flip car car_horizontallyFlipped\n exit";
    StringReader flipReader = new StringReader(flipCommand);
    StringWriter flipWriter = new StringWriter();
    PrintWriter flipPrintWriter = new PrintWriter(flipWriter);

    ImageView flipImageView = new ImageView(flipReader, flipPrintWriter);
    MockImageService mockImageService = new MockImageService(flipPrintWriter);
    ImageController flipController = new ImageController(mockImageService, flipImageView);
    flipController.start();
    String output = flipWriter.toString();
    assertTrue(output.contains("No image loaded"));

  }

  /**
   * Test the flip command.
   */
  @Test
  public void testFlip() {
    String flipCommand = "load res/car.png car\n "
                         + "horizontal-flip car car_horizontallyFlipped\n exit";
    StringReader flipReader = new StringReader(flipCommand);
    StringWriter flipWriter = new StringWriter();
    PrintWriter flipPrintWriter = new PrintWriter(flipWriter);

    ImageView flipImageView = new ImageView(flipReader, flipPrintWriter);
    MockImageService mockImageService = new MockImageService(flipPrintWriter);
    ImageController flipController = new ImageController(mockImageService, flipImageView);
    flipController.start();
    String output = flipWriter.toString();
    assertTrue(output.contains("Flip the image horizontally"));

    Image execute = flipController.loadedImages.get("car_horizontallyFlipped");
    Image expect = new MyImage("res/car_doubleFlipped.png");

    assertEquals(expect, execute);

    Image targetImage = new MyImage("res/car.png");
    assertTrue(output.contains(targetImage.hashCode() + ""));
  }

  /**
   * Test the brighten command when there is not exist a image.
   */
  @Test
  public void testNullBrighten() {
    String brightenCommand = "brighten -4 simple simple-4 \n exit";
    StringReader brightenReader = new StringReader(brightenCommand);
    StringWriter brightenWriter = new StringWriter();
    PrintWriter brightenPrintWriter = new PrintWriter(brightenWriter);

    ImageView brightenImageView = new ImageView(brightenReader, brightenPrintWriter);
    MockImageService mockImageService = new MockImageService(brightenPrintWriter);
    ImageController brightenController = new ImageController(mockImageService,
        brightenImageView);
    brightenController.start();

    String output = brightenWriter.toString();
    assertTrue(output.contains("No image loaded"));
  }

  /**
   * Test the brighten command when there exist a image.
   */
  @Test
  public void testBrighten() {
    String brightenCommand = "load test/img/trichromatic/simple.ppm simple\n "
                             + "brighten -4 simple simple-4 \n exit";
    StringReader brightenReader = new StringReader(brightenCommand);
    StringWriter brightenWriter = new StringWriter();
    PrintWriter brightenPrintWriter = new PrintWriter(brightenWriter);

    ImageView brightenImageView = new ImageView(brightenReader, brightenPrintWriter);
    MockImageService mockImageService = new MockImageService(brightenPrintWriter);
    ImageController brightenController = new ImageController(mockImageService,
        brightenImageView);
    brightenController.start();
    String output = brightenWriter.toString();
    assertTrue(output.contains("Decrease the brightness of the image"));

    Image executeIncrease = brightenController.loadedImages.get("simple-4");
    Image expectIncrease = new MyImage("test/img/trichromatic/simple-4.ppm");

    assertEquals(expectIncrease, executeIncrease);

    Image targetImage = new MyImage("test/img/trichromatic/simple.ppm");
    assertTrue(output.contains(targetImage.hashCode() + ""));
  }

  /**
   * Test the sharpen command when there is not image exist.
   */
  @Test
  public void testNullSharpen() {
    String sharpenCommand = "sharpen cupcake cupcake_sharpenOnce \n exit";
    StringReader sharpenReader = new StringReader(sharpenCommand);
    StringWriter sharpenWriter = new StringWriter();
    PrintWriter sharpenPrintWriter = new PrintWriter(sharpenWriter);

    ImageView sharpenImageView = new ImageView(sharpenReader, sharpenPrintWriter);
    MockImageService mockImageService = new MockImageService(sharpenPrintWriter);
    ImageController sharpenController = new ImageController(mockImageService, sharpenImageView);
    sharpenController.start();
    String output = sharpenWriter.toString();
    assertTrue(output.contains("No image loaded"));
  }

  /**
   * Test the sharpen command.
   */
  @Test
  public void testSharpen() {
    String sharpenCommand = "load test/img/cupcake.png cupcake\n "
                            + "sharpen cupcake cupcake_sharpenOnce\n exit";
    StringReader sharpenReader = new StringReader(sharpenCommand);
    StringWriter sharpenWriter = new StringWriter();
    PrintWriter sharpenPrintWriter = new PrintWriter(sharpenWriter);

    ImageView sharpenImageView = new ImageView(sharpenReader, sharpenPrintWriter);
    MockImageService mockImageService = new MockImageService(sharpenPrintWriter);
    ImageController sharpenController = new ImageController(mockImageService, sharpenImageView);
    sharpenController.start();
    String output = sharpenWriter.toString();
    assertTrue(output.contains("Sharpen image"));

    Image executeSharpen = sharpenController.loadedImages.get("cupcake_sharpenOnce");
    Image expectSharpen = new MyImage("test/img/cupcake_sharpenOnce.png");

    assertEquals(expectSharpen, executeSharpen);

    Image targetImage = new MyImage("test/img/cupcake.png");
    assertTrue(output.contains(targetImage.hashCode() + ""));
  }

  /**
   * Test the sepia command when there is not image exist.
   */
  @Test
  public void testNullSepia() {
    String sepiaCommand = "sepia city-small city-small-sepia\n exit";
    StringReader sepiaReader = new StringReader(sepiaCommand);
    StringWriter sepiaWriter = new StringWriter();
    PrintWriter sepiaPrintWriter = new PrintWriter(sepiaWriter);

    ImageView sepiaImageView = new ImageView(sepiaReader, sepiaPrintWriter);
    MockImageService mockImageService = new MockImageService(sepiaPrintWriter);
    ImageController sepiaController = new ImageController(mockImageService, sepiaImageView);
    sepiaController.start();
    String output = sepiaWriter.toString();
    assertTrue(output.contains("No image loaded"));
  }

  /**
   * Test the sepia command.
   */
  @Test
  public void testSepia() {
    String sepiaCommand = "load test/img/city_small.png city_small\n "
                          + "sepia city_small city_small_sepia\n exit";
    StringReader sepiaReader = new StringReader(sepiaCommand);
    StringWriter sepiaWriter = new StringWriter();
    PrintWriter sepiaPrintWriter = new PrintWriter(sepiaWriter);

    ImageView sepiaImageView = new ImageView(sepiaReader, sepiaPrintWriter);
    MockImageService mockImageService = new MockImageService(sepiaPrintWriter);
    ImageController sepiaController = new ImageController(mockImageService, sepiaImageView);
    sepiaController.start();
    String output = sepiaWriter.toString();
    assertTrue(output.contains("Sepia image"));


    Image executeSepia = sepiaController.loadedImages.get("city_small_sepia");
    Image expectSepia = new MyImage("test/img/city_small_sepia.png");

    assertEquals(expectSepia, executeSepia);

    Image targetImage = new MyImage("test/img/city_small.png");
    assertTrue(output.contains(targetImage.hashCode() + ""));
  }

  /**
   * Test when user enter invalid command.
   */
  @Test
  public void testInvalidCommand() {
    StringReader input = new StringReader("loae test/img/mall.jpg mall\n exit");
    StringWriter output = new StringWriter();
    PrintWriter printWriter = new PrintWriter(output, true);

    MockImageView mockView = new MockImageView(input, printWriter);
    MockImageService service = new MockImageService(printWriter);
    ImageController controller = new ImageController(service, mockView);

    controller.start();
    String consoleOutput = output.toString();

    assertTrue(consoleOutput.contains("Please enter valid command, loae is invalid."));
    assertTrue(consoleOutput.contains("Please enter command:"));
  }

  /**
   * Test a input command with a single-line comment.
   */
  @Test
  public void testSingleCommentSingleCommand() {
    StringReader input = new StringReader("# This is a comment\n "
                                          + "load test\\img\\car.jpg car\n exit");
    StringWriter output = new StringWriter();
    PrintWriter printWriter = new PrintWriter(output, true);

    MockImageView mockView = new MockImageView(input, printWriter);
    MockImageService mockService = new MockImageService(printWriter);
    ImageController testController = new ImageController(mockService, mockView);

    testController.start();
    String consoleOutput = output.toString();

    assertFalse(consoleOutput.contains("error"));
    assertTrue(consoleOutput.contains("Loading new image: car"));
  }

  /**
   * Test when user input multiple line of commands.
   */
  @Test
  public void testMultipleCommands() {
    StringReader input = new StringReader("load test\\img\\car.jpg car\n "
                                          + "blur car car-blurred\n brighten 2 car car-brighten\n"
                                          + " exit");
    StringWriter output = new StringWriter();
    PrintWriter printWriter = new PrintWriter(output, true);

    MockImageView mockView = new MockImageView(input, printWriter);
    MockImageService mockService = new MockImageService(printWriter);
    ImageController testController = new ImageController(mockService, mockView);

    testController.start();
    String consoleOutput = output.toString();

    assertFalse(consoleOutput.contains("error"));
    assertTrue(consoleOutput.contains("Loading new image: car"));
    assertTrue(consoleOutput.contains("Image blurred"));
    assertTrue(consoleOutput.contains("Increase the brightness of the image"));

    Image targetImage = new MyImage("test/img/car.jpg");
    assertTrue(consoleOutput.contains(targetImage.hashCode() + ""));
  }

  /**
   * Test when user input multiple line of commands and their comments.
   */
  @Test
  public void testMultipleCommentsAndCommands() {
    StringReader input = new StringReader("load test\\img\\car.jpg car\n "
                                          + "# want to blur the car\n blur car car-blurred\n "
                                          + "# want to change the image's brightness\n "
                                          + "brighten 2 car car-brighten\n "
                                          + "exit");
    StringWriter output = new StringWriter();
    PrintWriter printWriter = new PrintWriter(output, true);

    MockImageView mockView = new MockImageView(input, printWriter);
    MockImageService mockService = new MockImageService(printWriter);
    ImageController testController = new ImageController(mockService, mockView);

    testController.start();
    String consoleOutput = output.toString();

    assertFalse(consoleOutput.contains("error"));
    assertTrue(consoleOutput.contains("Loading new image: car"));
    assertTrue(consoleOutput.contains("Image blurred"));
    assertTrue(consoleOutput.contains("Increase the brightness of the image"));

    Image targetImage = new MyImage("test/img/car.jpg");
    assertTrue(consoleOutput.contains(targetImage.hashCode() + ""));
  }

  /**
   * Test if the command user inputted includes continous comments and a single command. It can not
   * execute correctly.
   */
  @Test
  public void testContinousCommentsAndCommands() {
    StringReader input = new StringReader("# I want to load a beautiful image\n "
                                          + "# This image is about a car\n"
                                          + " load test\\img\\car.jpg car\n"
                                          + " # want to blur the car\n "
                                          + "blur car car-blurred\n"
                                          + " # want to change the image's brightness\n "
                                          + "brighten 2 car car-brighten\n exit");
    StringWriter output = new StringWriter();
    PrintWriter printWriter = new PrintWriter(output, true);

    MockImageView mockView = new MockImageView(input, printWriter);
    MockImageService mockService = new MockImageService(printWriter);
    ImageController testController = new ImageController(mockService, mockView);

    testController.start();
    String consoleOutput = output.toString();

    assertTrue(consoleOutput.contains("There are multiple comments. Please modify it."));
  }

  /**
   * Test when user enter an invalid file path.
   */
  @Test
  public void testInvalidFilePath() {
    String input = "test/file/invalid/not/exist.txt\n";
    StringReader inputReader = new StringReader(input);
    StringWriter output = new StringWriter();
    PrintWriter printWriter = new PrintWriter(output, true);

    MockImageView mockView = new MockImageView(inputReader, printWriter);
    MockImageService mockImageService = new MockImageService(printWriter);
    ImageController controller = new ImageController(mockImageService, mockView);
    String filePath = mockView.getFilePath();

    controller.startFromFile(filePath);
    String outputFile = output.toString();

    assertTrue(outputFile.contains("Invalid file path. Please enter a valid file path."));
  }

  /**
   * Test if the file with valid file path includes wrong commands.
   */
  @Test
  public void testFileCommandError() {

    String input = "test/file/commdswitherrors.txt\n";
    StringReader inputReader = new StringReader(input);
    StringWriter output = new StringWriter();
    PrintWriter printWriter = new PrintWriter(output, true);

    MockImageView mockView = new MockImageView(inputReader, printWriter);
    MockImageService mockImageService = new MockImageService(printWriter);
    ImageController controller = new ImageController(mockImageService, mockView);
    String filePath = mockView.getFilePath();
    controller.startFromFile(filePath);
    String outputFile = output.toString();
    String exceptionMessage = "Please enter valid command, blor is invalid.";
    assertTrue(outputFile.contains(exceptionMessage));

  }

  /**
   * Test if the file with valid file path includes multiple commands.
   */
  @Test
  public void testFileMultipleCommands() {
    String input = "test/file/textcommand.txt\n";
    StringReader inputReader = new StringReader(input);
    StringWriter output = new StringWriter();
    PrintWriter printWriter = new PrintWriter(output, true);

    MockImageView mockView = new MockImageView(inputReader, printWriter);
    MockImageService mockImageService = new MockImageService(printWriter);
    ImageController controller = new ImageController(mockImageService, mockView);
    String filePath = mockView.getFilePath();

    controller.startFromFile(filePath);
    String outputFile = output.toString();

    assertTrue(outputFile.contains("Loading new image: car"));
    assertTrue(outputFile.contains("Image blurred"));
    assertTrue(outputFile.contains("Increase the brightness of the image"));
  }

  /**
   * Test if the file with valid file path includes multiple comments and commands. This tests
   * contains absolute path, so it won't run on every environment.
   */
  @Test
  public void testFileCommentsAndMultipleCommands() {
    String input = "test/file/commandWithMultipleComments.txt\n";
    StringReader inputReader = new StringReader(input);
    StringWriter output = new StringWriter();
    PrintWriter printWriter = new PrintWriter(output, true);

    MockImageView mockView = new MockImageView(inputReader, printWriter);
    MockImageService mockImageService = new MockImageService(printWriter);
    ImageController controller = new ImageController(mockImageService, mockView);
    String filePath = mockView.getFilePath();

    controller.startFromFile(filePath);
    String outputFile = output.toString();

    assertTrue(outputFile.contains("Loading new image: car"));
    assertTrue(outputFile.contains("Image blurred"));
    assertTrue(outputFile.contains("Increase the brightness of the image"));
    assertTrue(outputFile.contains("Decrease the brightness of the image"));

  }

  /**
   * Test if the file with valid file path includes a single command.
   */
  @Test
  public void testFileSingleCommand() {
    String input = "test/file/textsinglecommand.txt\n";
    StringReader inputReader = new StringReader(input);
    StringWriter output = new StringWriter();
    PrintWriter printWriter = new PrintWriter(output, true);

    MockImageView mockView = new MockImageView(inputReader, printWriter);
    MockImageService mockImageService = new MockImageService(printWriter);
    ImageController controller = new ImageController(mockImageService, mockView);
    String filePath = mockView.getFilePath();

    controller.startFromFile(filePath);
    String outputFile = output.toString();

    assertTrue(outputFile.contains("Loading new image: car"));
  }

  /**
   * Test if the file with valid file path includes a single comment and a single command, and the
   * image is not loaded before, which means can not do the execution.
   */
  @Test
  public void testFileNullSingleCommentSingleCommand() {
    String input = "test/file/textsinglecommandwithsinglecomment.txt\n";
    StringReader inputReader = new StringReader(input);
    StringWriter output = new StringWriter();
    PrintWriter printWriter = new PrintWriter(output, true);

    MockImageView mockView = new MockImageView(inputReader, printWriter);
    MockImageService mockImageService = new MockImageService(printWriter);
    ImageController controller = new ImageController(mockImageService, mockView);
    String filePath = mockView.getFilePath();

    controller.startFromFile(filePath);
    String outputFile = output.toString();

    assertTrue(outputFile.contains("No image loaded"));
  }

  /**
   * Test if the file with valid file path includes a single comment and a single command, and can
   * execute correctly.
   */
  @Test
  public void testFileSingleCommentSingleCommand() {
    String input = "test/file/textsinglecommentcommand.txt\n";
    StringReader inputReader = new StringReader(input);
    StringWriter output = new StringWriter();
    PrintWriter printWriter = new PrintWriter(output, true);

    MockImageView mockView = new MockImageView(inputReader, printWriter);
    MockImageService mockImageService = new MockImageService(printWriter);
    ImageController controller = new ImageController(mockImageService, mockView);
    String filePath = mockView.getFilePath();

    controller.startFromFile(filePath);
    String outputFile = output.toString();

    assertTrue(outputFile.contains("Loading new image: mall"));
  }

  /**
   * Test if the file with valid file path includes continous comments and a single command. It can
   * not execute correctly.
   */
  @Test
  public void testFileContinuousCommentSingleCommand() {
    String input = "test/file/textcontinouscomment.txt\n";
    StringReader inputReader = new StringReader(input);
    StringWriter output = new StringWriter();
    PrintWriter printWriter = new PrintWriter(output, true);

    MockImageView mockView = new MockImageView(inputReader, printWriter);
    MockImageService mockImageService = new MockImageService(printWriter);
    ImageController controller = new ImageController(mockImageService, mockView);
    String filePath = mockView.getFilePath();

    controller.startFromFile(filePath);
    String outputFile = output.toString();

    assertTrue(outputFile.contains("There are multiple comments. Please modify it."));
  }

}

