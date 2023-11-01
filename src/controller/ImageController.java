package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import model.Axis;
import model.Channel;
import model.image.Image;
import model.image.MyImage;
import service.ImageService;
import view.ImageView;

import static java.lang.System.exit;

/**
 * This is a controller that based on the user's input commands on controller or on the command-file
 * to execute corresponding operations on the specific image.
 */
public class ImageController {
  private final ImageService imageService;
  private final ImageView imageView;
  public Map<String, Image> loadedImages = new HashMap<>();

  /**
   * The constructor of the ImageController in this program with the given image service and view.
   *
   * @param imageService the service responsible for image processing operations.
   * @param imageView    the view that interacts with the user and displays results/messages.
   */
  public ImageController(ImageService imageService, ImageView imageView) {
    this.imageService = imageService;
    this.imageView = imageView;
  }

  /**
   * Starts the user interaction loop, prompting the user for commands, based on the commands to
   * execute the corresponding operations, and displaying the results or errors until the user
   * entering the "exit" command. Commands that start with "#" are considered comments and are
   * ignored.
   */
  public void start() {
    String commandFirst = null;
    while (true) {
      String command = imageView.getUserCommand();
      command = command.trim();
      if (command.isEmpty()) {
        continue;
      }
      if (command.startsWith("#")) {
        if (commandFirst == "#") {
          imageView.displayMessage("There are multiple comments. Please modify it.");
          break;
        }
        commandFirst = "#";
        continue;
      }
      commandFirst = command.substring(0, 1);
      if ("exit".equalsIgnoreCase(command.trim())) {
        exit(0);
      }
      executeCommand(command);
    }
  }
  //enter the file path only once, if the file path is invalid, directly exit.

  /**
   * Starts the processing of user commands from a specified file. Each line in the file represents
   * a command, which will be executed in order. Commands that start with "#" are considered
   * comments and are ignored. If the "exit" command is found in the file, then exiting the
   * program.
   *
   * @param filePath the path to the file containing the list of commands to execute.
   * @throws IOException if there's an error reading from the specified file.
   */

  public void startFromFile(String filePath) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String command;
      String commandFirst = null;
      while ((command = reader.readLine()) != null) {
        if (command.isEmpty()) {
          if (command.isEmpty() || command.startsWith("#")) {
            continue;
          }
          if (command.startsWith("#")) {
            if (commandFirst == "#") {
              imageView.displayMessage("There are multiple comments. Please modify it.");
              break;
            }
            commandFirst = "#";
            continue;
          }
          commandFirst = command.substring(0, 1);
          if ("exit".equalsIgnoreCase(command.trim())) {
            exit(0);
          }
          executeCommand(command);
        }
      }
    } catch (FileNotFoundException e) {
      imageView.displayMessage("Invalid file path. Please enter a valid file path.");
    }
  }

  /**
   * Executes a given image processing command. Each different kinds of command has a related
   * function to achieve the goal.
   *
   * @param command the full string command provided by the user for execution.
   * @throws IllegalArgumentException if the command is null or empty.
   */
  public void executeCommand(String command) {
    if (command == null || command.isEmpty()) {
      throw new IllegalArgumentException("Invalid command");
    }

    StringTokenizer tokenizer = new StringTokenizer(command);
    if (!tokenizer.hasMoreTokens()) {
      imageView.displayMessage("Empty command received. Please provide a valid command.");
      return;
    }
    // Get the command's name.
    String operation = tokenizer.nextToken();
    switch (operation) {
      // Load command.
      case "load":
        // The file path of the image.
        String filePath = tokenizer.nextToken();
        // The alias of the image.
        String imageAlias = tokenizer.nextToken();
        MyImage loadedImage = new MyImage(filePath);
        if (loadedImage == null) {
          imageView.displayMessage("Failed to load the image from " + filePath);
          break;
        }
        if (loadedImages.containsKey(imageAlias)) {
          imageView.displayMessage("Overwriting existing image " + imageAlias);
        } else {
          imageView.displayMessage("Loading new image: " + imageAlias);
        }
        // Store the image in the map.
        loadedImages.put(imageAlias, loadedImage);
        break;
      // Save command.
      case "save":
        String outputPath = tokenizer.nextToken();
        String imageNameSave = tokenizer.nextToken();
        MyImage imageToSave = (MyImage) loadedImages.get(imageNameSave);
        if (imageToSave == null) {
          imageView.displayMessage("Image " + imageNameSave + " not found.");
          break;
        }
        imageToSave.save(outputPath);
        imageView.displayMessage("Save image");
        break;
      // Blur command.
      case "blur":
        String imageAliasBlur = tokenizer.nextToken();
        Image imageBlur = loadedImages.get(imageAliasBlur);
        if (imageBlur == null) {
          imageView.displayMessage("No image loaded");
          break;
        }
        Image blurImage = imageService.blur(imageBlur);
        String imageAliasBlurred = tokenizer.nextToken();
        // Store the image in the map.
        loadedImages.put(imageAliasBlurred, blurImage);
        imageView.displayMessage("Image blurred");
        break;
      // Value component command.
      case "value-component":
        String imageAliasValue = tokenizer.nextToken();
        Image imageValueComponent = loadedImages.get(imageAliasValue);
        if (imageValueComponent == null) {
          imageView.displayMessage("No image loaded");
          break;
        }
        Image valueImage = imageService.getValue(imageValueComponent);
        String imageAliasGetValue = tokenizer.nextToken();
        // Store the image in the map.
        loadedImages.put(imageAliasGetValue, valueImage);
        imageView.displayMessage("Get the value-component");
        break;
      // Intensity component command.
      case "intensity-component":
        String imageAliasIntensity = tokenizer.nextToken();
        Image imageIntensityComponent = loadedImages.get(imageAliasIntensity);
        if (imageIntensityComponent == null) {
          imageView.displayMessage("No image loaded");
          break;
        }
        Image intensityImage = imageService.getIntensity(imageIntensityComponent);
        String imageAliasGetIntensity = tokenizer.nextToken();
        // Store the image in the map.
        loadedImages.put(imageAliasGetIntensity, intensityImage);
        imageView.displayMessage("Get the intensity-component");
        break;
      // Luma component command.
      case "luma-component":
        String imageAliasLuma = tokenizer.nextToken();
        Image imageLumaComponent = loadedImages.get(imageAliasLuma);
        if (imageLumaComponent == null) {
          imageView.displayMessage("No image loaded");
          break;
        }
        Image lumaImage = imageService.getLuma(imageLumaComponent);
        String imageAliasGetLuma = tokenizer.nextToken();
        // Store the image in the map.
        loadedImages.put(imageAliasGetLuma, lumaImage);
        imageView.displayMessage("Get the luma-component");
        break;
      // Flip vertical command.
      case "vertical-flip":
        String imageAliasFlipVertical = tokenizer.nextToken();
        Image imageFlipVertical = loadedImages.get(imageAliasFlipVertical);
        if (imageFlipVertical == null) {
          imageView.displayMessage("No image loaded");
          break;
        }
        Image vertialImage = imageService.flip(imageFlipVertical, Axis.X);
        String imageAliasVertical = tokenizer.nextToken();
        // Store the image in the map.
        loadedImages.put(imageAliasVertical, vertialImage);
        imageView.displayMessage("Flip the image vertically");
        break;
      // Flip horizontal command.
      case "horizontal-flip":
        String imageAliasFlipHorizontal = tokenizer.nextToken();
        Image imageFlipHorizontal = loadedImages.get(imageAliasFlipHorizontal);
        if (imageFlipHorizontal == null) {
          imageView.displayMessage("No image loaded");
          break;
        }
        Image hoizontalImage = imageService.flip(imageFlipHorizontal, Axis.Y);
        String imageAliasHorizontal = tokenizer.nextToken();
        // Store the image in the map.
        loadedImages.put(imageAliasHorizontal, hoizontalImage);
        imageView.displayMessage("Flip the image horizontally");
        break;
      // Change the images' brightness command.
      case "brighten":
        float amount = Float.parseFloat(tokenizer.nextToken());
        String imageAliasBrighten = tokenizer.nextToken();
        Image imageBrighten = loadedImages.get(imageAliasBrighten);
        if (imageBrighten == null) {
          imageView.displayMessage("No image loaded");
          break;
        }
        Image brightenImage = imageService.brighten(imageBrighten, amount);
        String imageAliasAfterBrighten = tokenizer.nextToken();
        loadedImages.put(imageAliasAfterBrighten, brightenImage);
        // Increase brightness.
        if (amount > 0) {
          imageView.displayMessage("Increase the brightness of the image");
        }
        if (amount < 0) {
          imageView.displayMessage("Decrease the brightness of the image");
        }
        break;
      // Red component command.
      case "red-component":
        String imageAliasRedComponent = tokenizer.nextToken();
        Image imageRedComponent = loadedImages.get(imageAliasRedComponent);
        if (imageRedComponent == null) {
          imageView.displayMessage("No image loaded");
          break;
        }
        Image redComponentImage = imageService.splitComponent(imageRedComponent, Channel.RED);
        String imageAliasGetRed = tokenizer.nextToken();
        // Store the image in the map.
        loadedImages.put(imageAliasGetRed, redComponentImage);
        imageView.displayMessage("Split image in red component");
        break;
      // Green component command.
      case "green-component":
        String imageAliasGreenComponent = tokenizer.nextToken();
        Image imageGreenComponent = loadedImages.get(imageAliasGreenComponent);
        if (imageGreenComponent == null) {
          imageView.displayMessage("No image loaded");
          break;
        }
        Image greenComponentImage = imageService.splitComponent(imageGreenComponent, Channel.GREEN);
        String imageAliasGetGreen = tokenizer.nextToken();
        // Store the image in the map.
        loadedImages.put(imageAliasGetGreen, greenComponentImage);
        imageView.displayMessage("Split image in green component");
        break;
      // Blue component command.
      case "blue-component":
        String imageAliasBlueComponent = tokenizer.nextToken();
        Image imageBlueComponent = loadedImages.get(imageAliasBlueComponent);
        if (imageBlueComponent == null) {
          imageView.displayMessage("No image loaded");
          break;
        }
        Image blueComponentImage = imageService.splitComponent(imageBlueComponent, Channel.BLUE);
        String imageAliasGetBlue = tokenizer.nextToken();
        // Store the image in the map.
        loadedImages.put(imageAliasGetBlue, blueComponentImage);
        imageView.displayMessage("Split image in blue component");
        break;
      // RGB Split command.
      case "rgb-split":
        String imageAliasSplit = tokenizer.nextToken();
        String imageAliasSplitR = tokenizer.nextToken();
        String imageAliasSplitG = tokenizer.nextToken();
        String imageAliasSplitB = tokenizer.nextToken();
        Image imageSplit = loadedImages.get(imageAliasSplit);
        if (imageSplit == null) {
          imageView.displayMessage("No image loaded");
          break;
        }
        Image[] result = imageService.splitChannel(imageSplit);
        // Store the image in the map.
        loadedImages.put(imageAliasSplitR, result[0]);
        loadedImages.put(imageAliasSplitG, result[1]);
        loadedImages.put(imageAliasSplitB, result[2]);
        imageView.displayMessage("Split the image.");
        break;
      // RGB combine command.
      case "rgb-combine":
        Channel[] channels = {Channel.RED, Channel.GREEN, Channel.BLUE};
        Image[] imagesToCombine = new Image[3];
        String combinedImageName = tokenizer.nextToken();
        String singleChannelImageNameR = tokenizer.nextToken();
        Image singleChannelImageR = loadedImages.get(singleChannelImageNameR);
        String singleChannelImageNameG = tokenizer.nextToken();
        Image singleChannelImageG = loadedImages.get(singleChannelImageNameG);
        String singleChannelImageNameB = tokenizer.nextToken();
        Image singleChannelImageB = loadedImages.get(singleChannelImageNameB);
        if (singleChannelImageR == null || singleChannelImageG == null
              || singleChannelImageB == null) {
          imageView.displayMessage("No image loaded");
          break;
        }
        imagesToCombine[0] = loadedImages.get(singleChannelImageR);
        imagesToCombine[1] = loadedImages.get(singleChannelImageG);
        imagesToCombine[2] = loadedImages.get(singleChannelImageB);
        try {
          Image combinedImage = imageService.combineChannels(channels, imagesToCombine);
          // Store the image in the map.
          loadedImages.put(combinedImageName, combinedImage);
          imageView.displayMessage("Images combined successfully");
        } catch (IllegalArgumentException e) {
          imageView.displayMessage(e.getMessage());
        }
        break;
      // Sharpen command.
      case "sharpen":
        String imageAliasSharpen = tokenizer.nextToken();
        Image imageSharpen = loadedImages.get(imageAliasSharpen);
        if (imageSharpen == null) {
          imageView.displayMessage("No image loaded");
          break;
        }
        Image sharpenImage = imageService.sharpen(imageSharpen);
        String imageAliasSharpened = tokenizer.nextToken();
        // Store the image in the map.
        loadedImages.put(imageAliasSharpened, sharpenImage);
        imageView.displayMessage("Sharpen image");
        break;
      // Sepia command.
      case "sepia":
        String imageAliasSepia = tokenizer.nextToken();
        Image imageSepia = loadedImages.get(imageAliasSepia);
        if (imageSepia == null) {
          imageView.displayMessage("No image loaded");
          break;
        }
        Image getSepiaImage = imageService.getSepia(imageSepia);
        String imageAliasGetSepia = tokenizer.nextToken();
        // Store the image in the map.
        loadedImages.put(imageAliasGetSepia, getSepiaImage);
        imageView.displayMessage("Sepia image");
        break;
      default:
        //System.out.println("Please enter valid command, " + operation + " is invalid.");
        imageView.displayMessage("Please enter valid command, " + operation + " is invalid.");
    }
    imageView.displayMessage("Please enter command:");
  }
}

