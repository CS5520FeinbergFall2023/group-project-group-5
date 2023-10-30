import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;


import controller.ImageController;
import model.MyImage;
import service.ImageService;
import view.ImageView;

import static org.junit.Assert.assertTrue;


public class ImageControllerTest {

  /**
   * Test whether the load command could execute correctly.
   * @throws IOException
   */
  @Test
  public void testLoadJPGCommand() throws IOException {
    String inCommand = "load D:\\23Fall\\PDP\\Projects\\CS5010_Assignment4\\test\\img\\car.jpg " +
          "car \nexit\n";
    StringReader stringReader = new StringReader(inCommand);
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);

    ImageView imageView = new ImageView(stringReader,printWriter);
    ImageService imageService = new ImageService();
    ImageController controller = new ImageController(imageService, imageView);

    controller.start();
    String output = stringWriter.toString();
    assertTrue(output.contains("Load image"));
    assertTrue(output.contains("car"));
  }

  @Test
  public void testLoadJPGCommand2() throws IOException {
    String inCommand = "load D:\\23Fall\\PDP\\Projects\\CS5010_Assignment4\\test\\img\\car.jpg car";
    StringReader stringReader = new StringReader(inCommand);
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);

    ImageView imageView = new ImageView(stringReader,printWriter);
    ImageService imageService = new ImageService();
    ImageController controller = new ImageController(imageService, imageView);

    //controller.start();
    controller.executeCommand(inCommand);
    String output = stringWriter.toString();
    assertTrue(output.contains("Load image"));
    assertTrue(output.contains("car"));
  }



  @Test
  public void testLoadPPMCommand() {

  }

  @Test
  public void testLoadPNGCommand() {

  }

  /**
   * Test when the load command is invalid, whether it would execute correctly.
   */
  @Test
  public void testInvalidLoadCommand() {

  }

  @Test
  public void testImageNullLoadCommand() {

  }

  @Test
  public void testOverwriteLoadCommand() {

  }

  /**
   * Test whether the load command could execute correctly.
   * @throws IOException
   */
  @Test
  public void testSaveCommand() throws IOException {
    String inCommand = "save D:\\23Fall\\PDP\\Projects\\CS5010_Assignment4\\test\\img\\city.png " +
          "city1 \nexit\n";
    StringReader stringReader = new StringReader(inCommand);
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);

    ImageView imageView = new ImageView(stringReader,printWriter);
    ImageService imageService = new ImageService();
    ImageController controller = new ImageController(imageService, imageView);

    MyImage myImage = new MyImage("D:\\23Fall\\PDP\\Projects\\CS5010_Assignment4\\test\\img" +
          "\\city.png");
    controller.loadedImages.put("city1", myImage);
    controller.start();
    String output = stringWriter.toString();
    assertTrue(output.contains("Save image"));
  }

  /**
   * Test the save command when the image is null.
   * @throws IOException
   */
  @Test
  public void testInvalidSaveCommand() throws IOException {
    String inCommand = "save D:\\23Fall\\PDP\\Projects\\CS5010_Assignment4\\test\\img\\cake.png " +
          "cake1 \nexit\n";
    StringReader stringReader = new StringReader(inCommand);
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);

    ImageView imageView = new ImageView(stringReader,printWriter);
    ImageService imageService = new ImageService();
    ImageController controller = new ImageController(imageService, imageView);

    controller.loadedImages.get("cake1");
    controller.start();
    String output = stringWriter.toString();
    System.out.println(output);
    assertTrue(output.contains("Image cake1 not found"));
  }

//  private ImageController loadHelper(ImageView imageView) throws IOException {
//    String inCommand = "load D:\\23Fall\\PDP\\Projects\\CS5010_Assignment4\\test\\img\\car.jpg car";
//    StringReader stringReader = new StringReader(inCommand + "\nexit\n");
//    StringWriter stringWriter = new StringWriter();
//    PrintWriter printWriter = new PrintWriter(stringWriter);
//
//    ImageService imageService = new ImageService();
//    ImageController controller = new ImageController(imageService, imageView);
//    controller.start();
//    return controller;
//  }

  @Test
  public void testBlurCommand() throws IOException {

//    String inCommand = "load D:\\23Fall\\PDP\\Projects\\CS5010_Assignment4\\test\\img\\car.jpg " +
//          "car ";
//    StringReader stringReader = new StringReader(inCommand);
//    StringWriter stringWriter = new StringWriter();
//    PrintWriter printWriter = new PrintWriter(stringWriter);
//
//    ImageView imageView = new ImageView(stringReader,printWriter);
//    ImageService imageService = new ImageService();
//    ImageController controller = new ImageController(imageService, imageView);
//
//    controller.executeCommand(inCommand);
//    String output = stringWriter.toString();
//
//    String inCommand2 = "blur car car-blurred ";
//    StringReader stringReader2 = stringReader.
//    StringWriter stringWriter2 = new StringWriter();
//    PrintWriter printWriter2 = new PrintWriter(stringWriter);
//
//    ImageView imageView = new ImageView(stringReader,printWriter);
//    ImageService imageService = new ImageService();
//
//    String imagePath = "D:\\23Fall\\PDP\\Projects\\CS5010_Assignment4\\test\\img\\car.jpg";
//    MyImage myImage = new MyImage(imagePath);
//    controller.loadedImages.put("car-blurred",myImage);
//    controller.start();
//    String output = stringWriter.toString();
//    assertTrue(output.contains("No image loaded"));
//    assertTrue(output.contains("Image blurred"));

    // 1. Load the image first
    String ldCommand = "load D:\\23Fall\\PDP\\Projects\\CS5010_Assignment4\\test\\img\\car.jpg car";
    StringReader loadReader = new StringReader(ldCommand );
    StringWriter loadWriter = new StringWriter();
    PrintWriter loadPrintWriter = new PrintWriter(loadWriter);

    ImageView loadImageView = new ImageView(loadReader, loadPrintWriter);
    ImageService loadImageService = new ImageService();
    ImageController loadController = new ImageController(loadImageService, loadImageView);

    loadController.executeCommand(ldCommand);
    String loadOutput = loadWriter.toString();
    assertTrue(loadOutput.contains("Load image"));
    assertTrue(loadOutput.contains("car"));

    // 2. Now apply the blur command
    String blurCommand = "blur car car-blurred";
    StringReader blurReader = new StringReader(blurCommand);
    StringWriter blurWriter = new StringWriter();
    PrintWriter blurPrintWriter = new PrintWriter(blurWriter);

    ImageView blurImageView = new ImageView(blurReader, blurPrintWriter);
    ImageService blurImageService = new ImageService();
    ImageController blurController = new ImageController(blurImageService, blurImageView);
    blurController.loadedImages = loadController.loadedImages; // Transfer the loaded images

    blurController.executeCommand(blurCommand);

    String blurOutput = blurWriter.toString();
    assertTrue(blurOutput.contains("Image blurred"));

    // 3. Try to blur again
    String blurCommand2 = "blur car-blurred car-blurred2";
    StringReader blurReader2 = new StringReader(blurCommand2);
    StringWriter blurWriter2 = new StringWriter();
    PrintWriter blurPrintWriter2 = new PrintWriter(blurWriter2);

    ImageView blurImageView2 = new ImageView(blurReader2, blurPrintWriter2);
    ImageService blurImageService2 = new ImageService();
    ImageController blurController2 = new ImageController(blurImageService2, blurImageView2);
    blurController2.loadedImages = blurController.loadedImages; // Transfer the loaded images

    blurController2.executeCommand(blurCommand2);

    String blurOutput2 = blurWriter2.toString();
    assertTrue(blurOutput2.contains("Image blurred"));



  }

  @Test
  public void testValueComponent() throws IOException {
    // 1. Load the image first
    String ldCommand = "load D:\\23Fall\\PDP\\Projects\\CS5010_Assignment4\\test\\img\\car.jpg car";
    StringReader loadReader = new StringReader(ldCommand );
    StringWriter loadWriter = new StringWriter();
    PrintWriter loadPrintWriter = new PrintWriter(loadWriter);

    ImageView loadImageView = new ImageView(loadReader, loadPrintWriter);
    ImageService loadImageService = new ImageService();
    ImageController loadController = new ImageController(loadImageService, loadImageView);

    loadController.executeCommand(ldCommand);

    String loadOutput = loadWriter.toString();
    assertTrue(loadOutput.contains("Load image"));
    assertTrue(loadOutput.contains("car"));


    String valueCommand = "value-component car car-valued";
    StringReader valueReader = new StringReader(valueCommand);
    StringWriter valueWriter = new StringWriter();
    PrintWriter valuePrintWriter = new PrintWriter(valueWriter);

    ImageView valueImageView = new ImageView(valueReader, valuePrintWriter);
    ImageService valueImageService = new ImageService();
    ImageController valueController = new ImageController(valueImageService, valueImageView);
    valueController.loadedImages = loadController.loadedImages; // Transfer the loaded images

    valueController.executeCommand(valueCommand);

    String valueOutput = valueWriter.toString();
    assertTrue(valueOutput.contains("Get the value-component"));
  }

  @Test
  public void testIntensityComponent() throws IOException {
    // 1. Load the image first
    String ldCommand = "load D:\\23Fall\\PDP\\Projects\\CS5010_Assignment4\\test\\img\\car.jpg car";
    StringReader loadReader = new StringReader(ldCommand );
    StringWriter loadWriter = new StringWriter();
    PrintWriter loadPrintWriter = new PrintWriter(loadWriter);

    ImageView loadImageView = new ImageView(loadReader, loadPrintWriter);
    ImageService loadImageService = new ImageService();
    ImageController loadController = new ImageController(loadImageService, loadImageView);

    loadController.executeCommand(ldCommand);

    String loadOutput = loadWriter.toString();
    assertTrue(loadOutput.contains("Load image"));
    assertTrue(loadOutput.contains("car"));


    String intensityCommand = "intensity-component car car-intensity";
    StringReader intensityReader = new StringReader(intensityCommand);
    StringWriter intensityWriter = new StringWriter();
    PrintWriter intensityPrintWriter = new PrintWriter(intensityWriter);

    ImageView intensityImageView = new ImageView(intensityReader, intensityPrintWriter);
    ImageService intensityImageService = new ImageService();
    ImageController intensityController = new ImageController(intensityImageService,
          intensityImageView);
    intensityController.loadedImages = loadController.loadedImages; // Transfer the loaded images

    intensityController.executeCommand(intensityCommand);

    String intensityOutput = intensityWriter.toString();
    assertTrue(intensityOutput.contains("Get the intensity-component"));
  }

  @Test
  public void testLumaComponent() throws IOException {
    // 1. Load the image first
    String ldCommand = "load D:\\23Fall\\PDP\\Projects\\CS5010_Assignment4\\test\\img\\car.jpg car";
    StringReader loadReader = new StringReader(ldCommand );
    StringWriter loadWriter = new StringWriter();
    PrintWriter loadPrintWriter = new PrintWriter(loadWriter);

    ImageView loadImageView = new ImageView(loadReader, loadPrintWriter);
    ImageService loadImageService = new ImageService();
    ImageController loadController = new ImageController(loadImageService, loadImageView);

    loadController.executeCommand(ldCommand);

    String loadOutput = loadWriter.toString();
    assertTrue(loadOutput.contains("Load image"));
    assertTrue(loadOutput.contains("car"));


    String lumaCommand = "luma-component car car-luma";
    StringReader lumaReader = new StringReader(lumaCommand);
    StringWriter lumaWriter = new StringWriter();
    PrintWriter lumaPrintWriter = new PrintWriter(lumaWriter);

    ImageView lumaImageView = new ImageView(lumaReader, lumaPrintWriter);
    ImageService lumaImageService = new ImageService();
    ImageController lumaController = new ImageController(lumaImageService, lumaImageView);
    lumaController.loadedImages = loadController.loadedImages; // Transfer the loaded images

    lumaController.executeCommand(lumaCommand);

    String lumaOutput = lumaWriter.toString();
    assertTrue(lumaOutput.contains("Get the luma-component"));
  }

  @Test
  public void testRGBSplitAndCombine() throws IOException {
    // 1. Load the image first
    String ldCommand = "load D:\\23Fall\\PDP\\Projects\\CS5010_Assignment4\\test\\img\\car.jpg car";
    StringReader loadReader = new StringReader(ldCommand );
    StringWriter loadWriter = new StringWriter();
    PrintWriter loadPrintWriter = new PrintWriter(loadWriter);

    ImageView loadImageView = new ImageView(loadReader, loadPrintWriter);
    ImageService loadImageService = new ImageService();
    ImageController loadController = new ImageController(loadImageService, loadImageView);

    loadController.executeCommand(ldCommand);

    String loadOutput = loadWriter.toString();
    assertTrue(loadOutput.contains("Load image"));
    assertTrue(loadOutput.contains("car"));

    // 2. Split the image
    String splitCommand = "rgb-split car car-red car-green car-blue";
    StringReader splitReader = new StringReader(splitCommand);
    StringWriter splitWriter = new StringWriter();
    PrintWriter splitPrintWriter = new PrintWriter(splitWriter);

    ImageView splitImageView = new ImageView(splitReader, splitPrintWriter);
    ImageService splitImageService = new ImageService();
    ImageController splitController = new ImageController(splitImageService, splitImageView);
    splitController.loadedImages = loadController.loadedImages; // Transfer the loaded images

    splitController.executeCommand(splitCommand);

    String splitOutput = splitWriter.toString();
    assertTrue(splitOutput.contains("Split the image"));

    // 3. Combine three images.
    String combineCommand = "rgb-combine car2 car-red car-green car-blue";
    StringReader combineReader = new StringReader(combineCommand);
    StringWriter combineWriter = new StringWriter();
    PrintWriter combinePrintWriter = new PrintWriter(combineWriter);

    ImageView combineImageView = new ImageView(combineReader, combinePrintWriter);
    ImageService combineImageService = new ImageService();
    ImageController combineController = new ImageController(combineImageService, combineImageView);
    combineController.loadedImages = splitController.loadedImages;
    combineController.executeCommand(combineCommand);

    String combineOutput = combineWriter.toString();
    assertTrue(combineOutput.contains("Images combined successfully"));


  }

  @Test
  public void testFlipVerticalAndHorizontal() throws IOException {
    // 1. Load the image first
    String ldCommand = "load test\\img\\car.jpg car";
    StringReader loadReader = new StringReader(ldCommand );
    StringWriter loadWriter = new StringWriter();
    PrintWriter loadPrintWriter = new PrintWriter(loadWriter);

    ImageView loadImageView = new ImageView(loadReader, loadPrintWriter);
    ImageService loadImageService = new ImageService();
    ImageController loadController = new ImageController(loadImageService, loadImageView);

    loadController.executeCommand(ldCommand);

    String loadOutput = loadWriter.toString();
    assertTrue(loadOutput.contains("Load image"));
    assertTrue(loadOutput.contains("car"));

    // 2. flip the image vertically.
    String flipVerticalCommand = "vertical-flip car car-vertical";
    StringReader flipVerticalReader = new StringReader(flipVerticalCommand);
    StringWriter flipVerticalWriter = new StringWriter();
    PrintWriter flipVerticalPrintWriter = new PrintWriter(flipVerticalWriter);

    ImageView flipVerticalImageView = new ImageView(flipVerticalReader, flipVerticalPrintWriter);
    ImageService flipVerticalImageService = new ImageService();
    ImageController flipVerticalController = new ImageController(flipVerticalImageService,
          flipVerticalImageView);
    flipVerticalController.loadedImages = loadController.loadedImages; // Transfer the loaded images

    flipVerticalController.executeCommand(flipVerticalCommand);

    String flipVerticalOutput = flipVerticalWriter.toString();
    assertTrue(flipVerticalOutput.contains("Flip the image vertically"));

    // 3. flip the image horizontally.
    String flipHorizontalCommand = "horizontal-flip car-vertical car-vertical-horizontal";
    StringReader flipHorizontalReader = new StringReader(flipHorizontalCommand);
    StringWriter flipHorizontalWriter = new StringWriter();
    PrintWriter flipHorizontalPrintWriter = new PrintWriter(flipHorizontalWriter);

    ImageView flipHorizontalImageView = new ImageView(flipHorizontalReader,
          flipHorizontalPrintWriter);
    ImageService flipHorizontalImageService = new ImageService();
    ImageController flipHorizontalController = new ImageController(flipHorizontalImageService,
          flipHorizontalImageView);
    flipHorizontalController.loadedImages = flipVerticalController.loadedImages;

    flipHorizontalController.executeCommand(flipHorizontalCommand);

    String flipHorizontalOutput = flipHorizontalWriter.toString();
    assertTrue(flipHorizontalOutput.contains("Flip the image horizontally"));






  }


}

