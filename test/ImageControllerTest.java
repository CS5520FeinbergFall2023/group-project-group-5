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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * This test class is test the controller component. For the controller, need to test whether it
 * correctly gets the command that user inputted, correctly gets the related images and transfers
 * those information to ImageService component correctly or not. Also, this test class tests whether
 * two ways (console and text file) for users to input their commands work correctly (allow comments
 * exist) or not.
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

  /**
   * Test when user enter invalid command.
   */
  @Test
  public void testInvalidCommand() {
    StringReader input = new StringReader("loae test/img/mall.jpg mall\n exit");
    StringWriter output = new StringWriter();
    PrintWriter printWriter = new PrintWriter(output, true);
    MockImageView mockView = new MockImageView(input, printWriter);
    MockImageService service = new MockImageService();
    ImageController controller = new ImageController(service, mockView);

    //controller.executeCommand("loae test/img/mall.jpg mall");
    controller.start();
    String consoleOutput = output.toString();

    assertTrue(consoleOutput.contains("Please enter valid command, loae is invalid."));
    assertTrue(consoleOutput.contains("Please enter command:"));
  }

  /**
   * Test a input command with a single-line comment.
   *
   * @throws IOException if there's an error related to I/O operations.
   */
  @Test
  public void testSingleCommentSingleCommand() throws IOException {
    StringReader input = new StringReader
          ("# This is a comment\n load test\\img\\car.jpg car\n exit");
    StringWriter output= new StringWriter();
    PrintWriter printWriter = new PrintWriter(output, true);
    MockImageView mockView = new MockImageView(input, printWriter);
    MockImageService mockService = new MockImageService();
    ImageController testController = new ImageController(mockService, mockView);

    testController.start();
    String consoleOutput = output.toString();

    assertFalse(consoleOutput.contains("error"));
    assertTrue(consoleOutput.contains("Loading new image: car"));
  }

  /**
   * Test when user input multiple line of commands.
   *
   * @throws IOException if there's an error related to I/O operations.
   */
  @Test
  public void testMultipleCommands() throws IOException {
    StringReader input = new StringReader
          ("load test\\img\\car.jpg car\n blur car car-blurred\n brighten 2 car car-brighten\n "
                + "exit");
    StringWriter output= new StringWriter();
    PrintWriter printWriter = new PrintWriter(output, true);
    MockImageView mockView = new MockImageView(input, printWriter);
    MockImageService mockService = new MockImageService();
    ImageController testController = new ImageController(mockService, mockView);

    testController.start();
    String consoleOutput = output.toString();

    assertFalse(consoleOutput.contains("error"));
    assertTrue(consoleOutput.contains("Loading new image: car"));
    assertTrue(consoleOutput.contains("Image blurred"));
    assertTrue(consoleOutput.contains("Increase the brightness of the image"));
  }

  /**
   * Test when user input multiple line of commands and their comments.
   *
   * @throws IOException if there's an error related to I/O operations.
   */
  @Test
  public void testMultipleCommentsAndCommands() throws IOException {
    StringReader input = new StringReader
          ("load test\\img\\car.jpg car\n # want to blur the car\n blur car car-blurred\n" +
                " # want to change the image's brightness\n brighten 2 car car-brighten\n "
                + "exit");
    StringWriter output= new StringWriter();
    PrintWriter printWriter = new PrintWriter(output, true);
    MockImageView mockView = new MockImageView(input, printWriter);
    MockImageService mockService = new MockImageService();
    ImageController testController = new ImageController(mockService, mockView);

    testController.start();
    String consoleOutput = output.toString();

    assertFalse(consoleOutput.contains("error"));
    assertTrue(consoleOutput.contains("Loading new image: car"));
    assertTrue(consoleOutput.contains("Image blurred"));
    assertTrue(consoleOutput.contains("Increase the brightness of the image"));
  }

  /**
   *
   * @throws IOException if there's an error related to I/O operations.
   */
  @Test
  public void testInvalidFilePath() throws IOException {

    String input = "test/file/invalid/not/exist.txt\n";
    StringReader inputReader = new StringReader(input);
    StringWriter output= new StringWriter();
    PrintWriter printWriter = new PrintWriter(output, true);
    MockImageView mockView = new MockImageView(inputReader,printWriter);
    MockImageService mockImageService = new MockImageService();
    ImageController controller = new ImageController(mockImageService,mockView);
    String filePath = mockView.getFilePath();

    controller.startFromFile(filePath);
    String outputFile = output.toString();

    assertTrue(outputFile.contains("Invalid file path. Please enter a valid file path."));
  }

  /**
   * Test if the file with valid file path includes wrong commands.
   *
   * @throws IOException if there's an error related to I/O operations.
   */
  @Test
  public void testFileCommandError() throws IOException {

    String input = "test/file/commdswitherrors.txt\n";
    StringReader inputReader = new StringReader(input);
    StringWriter output= new StringWriter();
    PrintWriter printWriter = new PrintWriter(output, true);
    MockImageView mockView = new MockImageView(inputReader,printWriter);
    MockImageService mockImageService = new MockImageService();
    ImageController controller = new ImageController(mockImageService,mockView);
    String filePath = mockView.getFilePath();
    controller.startFromFile(filePath);
    String outputFile = output.toString();
    String exceptionMessage = "Please enter valid command, blor is invalid.";
    assertTrue(outputFile.contains(exceptionMessage));

  }

  /**
   * Test if the file with valid file path includes multiple commands.
   *
   * @throws IOException if there's an error related to I/O operations.
   */
  @Test
  public void testFileMultipleCommands() throws IOException {
    String input = "test/file/textcommand.txt\n";
    StringReader inputReader = new StringReader(input);
    StringWriter output= new StringWriter();
    PrintWriter printWriter = new PrintWriter(output, true);
    MockImageView mockView = new MockImageView(inputReader,printWriter);
    MockImageService mockImageService = new MockImageService();
    ImageController controller = new ImageController(mockImageService,mockView);
    String filePath = mockView.getFilePath();

    controller.startFromFile(filePath);
    String outputFile = output.toString();

    assertTrue(outputFile.contains("Loading new image: car"));
    assertTrue(outputFile.contains("Image blurred"));
    assertTrue(outputFile.contains("Increase the brightness of the image"));
  }

  /**
   * Test if the file with valid file path includes multiple comments and commands.
   *
   * @throws IOException if there's an error related to I/O operations.
   */
  @Test
  public void testFileCommentsAndMultipleCommands() throws IOException {
    String input = "test/file/commandWithMultipleComments.txt\n";
    StringReader inputReader = new StringReader(input);
    StringWriter output= new StringWriter();
    PrintWriter printWriter = new PrintWriter(output, true);
    MockImageView mockView = new MockImageView(inputReader,printWriter);
    MockImageService mockImageService = new MockImageService();
    ImageController controller = new ImageController(mockImageService,mockView);
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
   *
   * @throws IOException if there's an error related to I/O operations.
   */
  @Test
  public void testFileSingleCommand() throws IOException {
    String input = "test/file/textsinglecommand.txt\n";
    StringReader inputReader = new StringReader(input);
    StringWriter output= new StringWriter();
    PrintWriter printWriter = new PrintWriter(output, true);
    MockImageView mockView = new MockImageView(inputReader,printWriter);
    MockImageService mockImageService = new MockImageService();
    ImageController controller = new ImageController(mockImageService,mockView);
    String filePath = mockView.getFilePath();

    controller.startFromFile(filePath);
    String outputFile = output.toString();

    assertTrue(outputFile.contains("Loading new image: koala"));
  }

  /**
   * Test if the file with valid file path includes a single comment and a single command.
   *
   * @throws IOException if there's an error related to I/O operations.
   */
  @Test
  public void testFileSingleCommentAndSingleCommand() throws IOException {
    String input = "test/file/textsinglecommandwithsinglecomment.txt\n";
    StringReader inputReader = new StringReader(input);
    StringWriter output= new StringWriter();
    PrintWriter printWriter = new PrintWriter(output, true);
    MockImageView mockView = new MockImageView(inputReader,printWriter);
    MockImageService mockImageService = new MockImageService();
    ImageController controller = new ImageController(mockImageService,mockView);
    String filePath = mockView.getFilePath();

    controller.startFromFile(filePath);
    String outputFile = output.toString();

    assertTrue(outputFile.contains("Get the value-component"));
  }

}

