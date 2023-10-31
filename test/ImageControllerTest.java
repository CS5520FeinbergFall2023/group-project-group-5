import org.junit.Test;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import controller.ImageController;
import model.Image;
import model.MyImage;
import view.ImageView;

import static org.junit.Assert.assertEquals;


/**
 * This test class is test the controller component. For the controller, need to test whether it
 * correctly gets the command that user inputted, correctly gets the related images and transfers
 * those information to ImageService component correctly or not.
 */
public class ImageControllerTest {

  /**
   * Test the blur command.
   *
   * @throws IOException if there's an error related to I/O operations.
   */
  @Test
  public void testBlurCommand() throws IOException {

    String blurCommand = "blur manhattan-small manhattan-small-blur";
    StringReader blurReader = new StringReader(blurCommand);
    StringWriter blurWriter = new StringWriter();
    PrintWriter blurPrintWriter = new PrintWriter(blurWriter);

    ImageView blurImageView = new ImageView(blurReader, blurPrintWriter);
    MockImageService mockService = new MockImageService();
    ImageController blurController = new ImageController(mockService, blurImageView);
    blurController.executeCommand(blurCommand);

    Image executeBlur = blurController.loadedImages.get("manhattan-small-blur");
    Image expectBlur = new MyImage("test/img/manhattan-small-blur.png");
    assertEquals(expectBlur, executeBlur);


  }

  /**
   * Test the value-component command.
   *
   * @throws IOException if there's an error related to I/O operations.
   */
  @Test
  public void testValueComponent() throws IOException {

    String valueCommand = "value-component Koala koala-value-greyscale";
    StringReader valueReader = new StringReader(valueCommand);
    StringWriter valueWriter = new StringWriter();
    PrintWriter valuePrintWriter = new PrintWriter(valueWriter);
    ImageView valueImageView = new ImageView(valueReader, valuePrintWriter);
    MockImageService mockImageService = new MockImageService();
    ImageController valueController = new ImageController(mockImageService, valueImageView);
    valueController.executeCommand(valueCommand);

    Image executeValue =valueController.loadedImages.get("koala-value-greyscale");
    Image expectValue = new MyImage("test/img/koala-value-greyscale.png");

    assertEquals(expectValue, executeValue);
  }

  /**
   * Test the intensity-component command.
   *
   * @throws IOException if there's an error related to I/O operations.
   */
  @Test
  public void testIntensityComponent() throws IOException {
    String intensityCommand = "intensity-component manhattan-small " +
          "manhattan-small-intensity-greyscale";
    StringReader intensityReader = new StringReader(intensityCommand);
    StringWriter intensityWriter = new StringWriter();
    PrintWriter intensityPrintWriter = new PrintWriter(intensityWriter);

    ImageView intensityImageView = new ImageView(intensityReader, intensityPrintWriter);
    MockImageService mockImageService = new MockImageService();
    ImageController intensityController = new ImageController(mockImageService, intensityImageView);
    intensityController.executeCommand(intensityCommand);

    Image executeIntensity =intensityController.loadedImages.get
          ("manhattan-small-intensity-greyscale");
    Image expectIntensity = new MyImage("test/img/manhattan-small-intensity-greyscale.png");

    assertEquals(expectIntensity, executeIntensity);
  }

  /**
   * Test the luma-component command.
   *
   * @throws IOException if there's an error related to I/O operations.
   */
  @Test
  public void testLumaComponent() throws IOException {
    String lumaCommand = "luma-component manhattan-small manhattan-small-luma-greyscale";
    StringReader lumaReader = new StringReader(lumaCommand);
    StringWriter lumaWriter = new StringWriter();
    PrintWriter lumaPrintWriter = new PrintWriter(lumaWriter);
    ImageView lumaImageView = new ImageView(lumaReader, lumaPrintWriter);
    MockImageService mockImageService = new MockImageService();
    ImageController lumaController = new ImageController(mockImageService, lumaImageView);
    lumaController.executeCommand(lumaCommand);

    Image executeLuma =lumaController.loadedImages.get("manhattan-small-luma-greyscale");
    Image expectLuma = new MyImage("test/img/manhattan-small-luma-greyscale.png");

    assertEquals(expectLuma, executeLuma);
  }

  /**
   * Test the RGB combine command.
   * @throws IOException if there's an error related to I/O operations.
   */
  @Test
  public void testRGBCombine() throws IOException {

    String combineCommand = "rgb-combine manhattan-small manhattan-small-red " +
          "manhattan-small-green manhattan-small-blue";
    StringReader combineReader = new StringReader(combineCommand);
    StringWriter combineWriter = new StringWriter();
    PrintWriter combinePrintWriter = new PrintWriter(combineWriter);

    ImageView combineImageView = new ImageView(combineReader, combinePrintWriter);
    MockImageService mockImageService = new MockImageService();
    ImageController combineController = new ImageController(mockImageService, combineImageView);
    combineController.executeCommand(combineCommand);

    Image executeCombine = combineController.loadedImages.get("manhattan-small");
    Image expectCombine = new MyImage("test/img/manhattan-small.png");

    assertEquals(expectCombine, executeCombine);

  }

  /**
   * Test the RGB Split command.
   *
   * @throws IOException if there's an error related to I/O operations.
   */
  @Test
  public void testRGBSplit() throws IOException {

    String splitCommand = "rgb-split manhattan-small manhattan-small-red manhattan-small-green " +
          "manhattan-small-blue";
    StringReader splitReader = new StringReader(splitCommand);
    StringWriter splitWriter = new StringWriter();
    PrintWriter splitPrintWriter = new PrintWriter(splitWriter);

    ImageView splitImageView = new ImageView(splitReader, splitPrintWriter);
    MockImageService mockImageService = new MockImageService();
    ImageController controller = new ImageController(mockImageService, splitImageView);
    controller.executeCommand(splitCommand);

    Image[] splitImages = new Image[3];
    splitImages[0] = controller.loadedImages.get("manhattan-small-red");
    splitImages[1] = controller.loadedImages.get("manhattan-small-green");
    splitImages[2] = controller.loadedImages.get("manhattan-small-blue");

    Image myImage1 = new MyImage("test/img/manhattan-small-red.png");
    Image myImage2 = new MyImage("test/img/manhattan-small-green.png");
    Image myImage3 = new MyImage("test/img/manhattan-small-blue.png");

    assertEquals(myImage1, splitImages[0]);
    assertEquals(myImage2, splitImages[1]);
    assertEquals(myImage3, splitImages[2]);

  }

  /**
   * Test the split component command.
   *
   * @throws IOException if there's an error related to I/O operations.
   */
  @Test
  public void testSplitComponent() throws IOException {
    String splitCommand = "red-component manhattan-small manhattan-small-red";
    StringReader splitReader = new StringReader(splitCommand);
    StringWriter splitWriter = new StringWriter();
    PrintWriter splitPrintWriter = new PrintWriter(splitWriter);
    ImageView splitImageView = new ImageView(splitReader,
          splitPrintWriter);
    MockImageService mockImageService = new MockImageService();
    ImageController splitController = new ImageController(mockImageService, splitImageView);
    splitController.executeCommand(splitCommand);

    Image executeSplit =splitController.loadedImages.get("manhattan-small-red");
    Image expectSplit = new MyImage("test/img/manhattan-small-red.png");

    assertEquals(expectSplit, executeSplit);
  }

  /**
   * Test the flip command.
   *
   * @throws IOException if there's an error related to I/O operations.
   */
  @Test
  public void testFlip() throws IOException {
    String flipCommand = "horizontal-flip manhattan-small manhattan-small-horizontal";
    StringReader flipReader = new StringReader(flipCommand);
    StringWriter flipWriter = new StringWriter();
    PrintWriter flipPrintWriter = new PrintWriter(flipWriter);
    ImageView flipImageView = new ImageView(flipReader, flipPrintWriter);
    MockImageService mockImageService = new MockImageService();
    ImageController flipController = new ImageController(mockImageService, flipImageView);
    flipController.executeCommand(flipCommand);

    Image execute =flipController.loadedImages.get("manhattan-small-horizontal");
    Image expect = new MyImage("test/img/manhattan-small-horizontal.png");

    assertEquals(expect, execute);

  }

  /**
   * Test the brighten command.
   *
   * @throws IOException if there's an error related to I/O operations.
   */
  @Test
  public void testBrighten() throws IOException {
    String brightenCommand = "brighten 50 manhattan-small manhattan-small-brighter-by-50";
    StringReader brightenReader = new StringReader(brightenCommand);
    StringWriter brightenWriter = new StringWriter();
    PrintWriter brightenPrintWriter = new PrintWriter(brightenWriter);
    ImageView brightenImageView = new ImageView(brightenReader, brightenPrintWriter);
    MockImageService mockImageService = new MockImageService();
    ImageController brightenController = new ImageController(mockImageService,
          brightenImageView);
    brightenController.executeCommand(brightenCommand);

    Image executeIncrease =brightenController.loadedImages.get("manhattan-small-brighter-by-50");
    Image expectIncrease = new MyImage("test/img/manhattan-small-brighter-by-50.png");

    assertEquals(expectIncrease, executeIncrease);
  }

  /**
   * Test the darken command.
   *
   * @throws IOException if there's an error related to I/O operations.
   */
  @Test
  public void testDarken() throws IOException {
    String darkenCommand = "brighten -50 manhattan-small manhattan-small-darker-by-50";
    StringReader darkenReader = new StringReader(darkenCommand);
    StringWriter darkenWriter = new StringWriter();
    PrintWriter darkenPrintWriter = new PrintWriter(darkenWriter);
    ImageView darkenImageView = new ImageView(darkenReader, darkenPrintWriter);
    MockImageService mockImageService = new MockImageService();
    ImageController darkenController = new ImageController(mockImageService,
          darkenImageView);
    darkenController.executeCommand(darkenCommand);

    Image executeDecrease =darkenController.loadedImages.get("manhattan-small-darker-by-50");
    Image expectDecrease = new MyImage("test/img/manhattan-small-darker-by-50.png");

    assertEquals(expectDecrease, executeDecrease);
  }

  /**
   * Test the sharpen command.
   *
   * @throws IOException if there's an error related to I/O operations.
   */
  @Test
  public void testSharpen() throws IOException {
    String sharpenCommand = "sharpen manhattan-small manhattan-small-sharpen";
    StringReader sharpenReader = new StringReader(sharpenCommand);
    StringWriter sharpenWriter = new StringWriter();
    PrintWriter sharpenPrintWriter = new PrintWriter(sharpenWriter);
    ImageView sharpenImageView = new ImageView(sharpenReader, sharpenPrintWriter);
    MockImageService mockImageService = new MockImageService();
    ImageController sharpenController = new ImageController(mockImageService, sharpenImageView);
    sharpenController.executeCommand(sharpenCommand);

    Image executeSharpen =sharpenController.loadedImages.get("manhattan-small-sharpen");
    Image expectSharpen = new MyImage("test/img/manhattan-small-sharpen.png");

    assertEquals(expectSharpen, executeSharpen);
  }

  /**
   * Test the sepia command.
   *
   * @throws IOException if there's an error related to I/O operations.
   */
  @Test
  public void testSepia() throws IOException {
    String sepiaCommand = "sepia manhattan-small manhattan-small-sepia";
    StringReader sepiaReader = new StringReader(sepiaCommand);
    StringWriter sepiaWriter = new StringWriter();
    PrintWriter sepiaPrintWriter = new PrintWriter(sepiaWriter);
    ImageView sepiaImageView = new ImageView(sepiaReader, sepiaPrintWriter);
    MockImageService mockImageService = new MockImageService();
    ImageController sepiaController = new ImageController(mockImageService, sepiaImageView);
    sepiaController.executeCommand(sepiaCommand);

    Image executeSepia =sepiaController.loadedImages.get("manhattan-small-sepia");
    Image expectSepia = new MyImage("test/img/manhattan-small-sepia.png");

    assertEquals(expectSepia, executeSepia);
  }

}

