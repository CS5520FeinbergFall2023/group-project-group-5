package controller;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import model.Axis;
import model.Channel;
import model.Image;
import model.MyImage;
import service.ImageService;
import view.ImageView;

/**
 * This is a controller that based on the user's input commands or controller.command-file to execute
 * corresponding operations on the specific image.
 */
public class ImageController {
  private final ImageService imageService;
  private final ImageView imageView;
  private MyImage myImage = null;
  public Map<String, Image> loadedImages = new HashMap<>();


  public ImageController(ImageService imageService, ImageView imageView) {
    this.imageService = imageService;
    this.imageView = imageView;
  }

  public void start() throws IOException {
    while (true) {
      String command = imageView.getUserCommand();

      if ("exit".equalsIgnoreCase(command.trim())) {
        break;
      }
      executeCommand(command);
    }
  }

  public void startFromFile(String filePath) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String command;
      while ((command = reader.readLine()) != null) {
        if ("exit".equalsIgnoreCase(command.trim())) {
          break;
        }
        executeCommand(command);
      }
    }
  }

  public void executeCommand(String command) throws IOException {
    if (command == null || command.isEmpty()) {
      throw new IllegalArgumentException("Invalid command");
    }

    StringTokenizer tokenizer = new StringTokenizer(command);
    if (!tokenizer.hasMoreTokens()) {
      imageView.displayMessage("Empty command received. Please provide a valid command.");
      return;
    }
    String operation = tokenizer.nextToken();

    switch (operation) {
      case "load":
        //the file path of the image
        String filePath = tokenizer.nextToken();
        // the alias of the image
        String imageAlias = tokenizer.nextToken();
        System.out.println(filePath);
        System.out.println(imageAlias);
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
        loadedImages.put(imageAlias, loadedImage);
        myImage = loadedImage;
        System.out.println(myImage.getHeight());
        System.out.println(myImage.getWidth());
        imageView.displayMessage("Load image");
        break;
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
      case "blur":
        String imageAliasBlur = tokenizer.nextToken();
        Image imageBlur = loadedImages.get(imageAliasBlur);
        if (imageBlur == null) {
          imageView.displayMessage("No image loaded");
        }
        imageService.blur(imageBlur);
        loadedImages.put(imageAliasBlur, imageBlur);
        imageView.displayMessage("Image blurred");
        break;
      case "value-component":
        String imageAliasValue = tokenizer.nextToken();
        Image imageValueComponent = loadedImages.get(imageAliasValue);
        if (imageValueComponent == null) {
          imageView.displayMessage("No image loaded");
        }
        imageService.getValue(imageValueComponent);
        loadedImages.put(imageAliasValue, imageValueComponent);
        imageView.displayMessage("Get the value-component");
        break;
      case "intensity-component":
        String imageAliasIntensity = tokenizer.nextToken();
        Image imageIntensityComponent = loadedImages.get(imageAliasIntensity);
        if (imageIntensityComponent == null) {
          imageView.displayMessage("No image loaded");
        }
        imageService.getIntensity(imageIntensityComponent);
        loadedImages.put(imageAliasIntensity, imageIntensityComponent);
        imageView.displayMessage("Get the intensity-component");
        break;
      case "luma-component":
        String imageAliasLuma = tokenizer.nextToken();
        Image imageLumaComponent = loadedImages.get(imageAliasLuma);
        if (imageLumaComponent == null) {
          imageView.displayMessage("No image loaded");
        }
        imageService.getLuma(imageLumaComponent);
        loadedImages.put(imageAliasLuma, imageLumaComponent);
        imageView.displayMessage("Get the luma-component");
        break;
      case "vertical-flip":
        String imageAliasFlipVertical = tokenizer.nextToken();
        Image imageFlipVertical = loadedImages.get(imageAliasFlipVertical);
        if (imageFlipVertical == null) {
          imageView.displayMessage("No image loaded");
        }
        imageService.flip(imageFlipVertical, Axis.X);
        loadedImages.put(imageAliasFlipVertical, imageFlipVertical);
        imageView.displayMessage("Flip the image vertically");
        break;
      case "horizontal-flip":
        String imageAliasFlipHorizontal = tokenizer.nextToken();
        Image imageFlipHorizontal = loadedImages.get(imageAliasFlipHorizontal);
        if (imageFlipHorizontal == null) {
          imageView.displayMessage("No image loaded");
        }
        imageService.flip(imageFlipHorizontal, Axis.Y);
        loadedImages.put(imageAliasFlipHorizontal, imageFlipHorizontal);
        imageView.displayMessage("Flip the image horizontally");
        break;
      case "brighten":
        String imageAliasBrighten = tokenizer.nextToken();
        Image imageFlipBrighten = loadedImages.get(imageAliasBrighten);
        if (imageFlipBrighten == null) {
          imageView.displayMessage("No image loaded");
        }
        float amount = Float.parseFloat(tokenizer.nextToken());
        if (amount > 0) {
          imageService.brighten(imageFlipBrighten, amount);
          loadedImages.put(imageAliasBrighten, imageFlipBrighten);
          imageView.displayMessage("Increase the brightness of the image");
        }
        if (amount < 0) {
          imageService.darken(imageFlipBrighten, amount);
          loadedImages.put(imageAliasBrighten, imageFlipBrighten);
          imageView.displayMessage("Decrease the brightness of the image");
        }
        break;
      case "red-component":
        String imageAliasRedComponent = tokenizer.nextToken();
        Image imageRedComponent = loadedImages.get(imageAliasRedComponent);
        if (imageRedComponent == null) {
          imageView.displayMessage("No image loaded");
        }
        imageService.splitComponent(imageRedComponent, Channel.RED);
        loadedImages.put(imageAliasRedComponent, imageRedComponent);
        imageView.displayMessage("Split image in red component");
        break;
      case "green-component":
        String imageAliasGreenComponent = tokenizer.nextToken();
        Image imageGreenComponent = loadedImages.get(imageAliasGreenComponent);
        if (imageGreenComponent == null) {
          imageView.displayMessage("No image loaded");
        }
        imageService.splitComponent(imageGreenComponent, Channel.GREEN);
        loadedImages.put(imageAliasGreenComponent, imageGreenComponent);
        imageView.displayMessage("Split image in green component");
        break;
      case "blue-component":
        String imageAliasBlueComponent = tokenizer.nextToken();
        Image imageBlueComponent = loadedImages.get(imageAliasBlueComponent);
        if (imageBlueComponent == null) {
          imageView.displayMessage("No image loaded");
        }
        imageService.splitComponent(imageBlueComponent, Channel.BLUE);
        loadedImages.put(imageAliasBlueComponent, imageBlueComponent);
        imageView.displayMessage("Split image in blue component");
        break;
      case "rgb-split":
        String imageAliasSplit = tokenizer.nextToken();
        String imageAliasSplitR = tokenizer.nextToken();
        String imageAliasSplitG = tokenizer.nextToken();
        String imageAliasSplitB = tokenizer.nextToken();
        Image imageSplit = loadedImages.get(imageAliasSplit);
        if (imageSplit == null) {
          imageView.displayMessage("No image loaded");
        }
        Image[] result = imageService.splitChannel(imageSplit);
        loadedImages.put(imageAliasSplitR, result[0]);
        loadedImages.put(imageAliasSplitG, result[1]);
        loadedImages.put(imageAliasSplitB, result[2]);
        imageView.displayMessage("Split the image.");
        break;
      case "rgb-combine":
        Channel[] channels = new Channel[3];
        Image[] imagesToCombine = new Image[3];
        channels[0] = Channel.RED;
        channels[1] = Channel.GREEN;
        channels[2] = Channel.BLUE;
        String combineName = tokenizer.nextToken();
        for (int i = 0; i < 3; i++) {
          String imageName = tokenizer.nextToken();
          if (!loadedImages.containsKey(imageName)) {
            imageView.displayMessage("Image named " + imageName + "not loaded.");
            return;
          }
          imagesToCombine[i] = loadedImages.get(imageName);

        }try {
          Image combinedImage = imageService.combineChannels(channels, imagesToCombine);
          loadedImages.put(combineName, combinedImage);
          imageView.displayMessage("Images combined successfully");
        } catch (IllegalArgumentException e) {
          imageView.displayMessage(e.getMessage());
        }
        break;
      case "sharpen":
        String imageAliasSharpen = tokenizer.nextToken();
        Image imageSharpen = loadedImages.get(imageAliasSharpen);
        if (imageSharpen == null) {
          imageView.displayMessage("No image loaded");
        }
        imageService.sharpen(imageSharpen);
        loadedImages.put(imageAliasSharpen, imageSharpen);
        imageView.displayMessage("Sharpen image");
        break;
      case "sepia":
        String imageAliasSepia = tokenizer.nextToken();
        Image imageSepia = loadedImages.get(imageAliasSepia);
        if (imageSepia == null) {
          imageView.displayMessage("No image loaded");
        }
        imageService.getSepia(imageSepia);
        loadedImages.put(imageAliasSepia, imageSepia);
        imageView.displayMessage("Sepia image");
        break;
      default:
        System.out.println("Invalid command " + operation);

    }
  }
}
