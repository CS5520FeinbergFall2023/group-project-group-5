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
import util.ImageUtil;
import view.ImageView;

/**
 * This is a controller that based on the user's input commands or controller.command-file to execute
 * corresponding operations on the specific image.
 */
public class ImageController {
  private final ImageService imageService;
  private final ImageView imageView;
  private MyImage myImage = null;
  private Map<String, Image> loadedImages = new HashMap<>();


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

    if (command == null) {
      throw new IllegalArgumentException("Invalid command");
    }
    StringTokenizer tokenizer = new StringTokenizer(command);
    String operation = tokenizer.nextToken();

    switch (operation) {
      case "load":
        imageView.displayMessage("Load image");
        //the file path of the image
        String filePath = tokenizer.nextToken();
        // the alias of the image
        String imageAlias = tokenizer.nextToken();
        //call the readPPM() function to get a MyImage object.
        System.out.println(filePath);
        System.out.println(imageAlias);
        //PPM format
        //MyImage loadedImage = ImageUtil.readPPM(filePath);
        //PNG format
//        File file = new File(filePath);
//        BufferedImage loadedImage = ImageIO.read(file);
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
        break;
      case "save":
        imageView.displayMessage("Save image");
        String outputPath = tokenizer.nextToken();
        String imageNameSave = tokenizer.nextToken();
        MyImage imageToSave = (MyImage) loadedImages.get(imageNameSave);
        if (imageToSave == null) {
          imageView.displayMessage("Image " + imageNameSave + "not found.");
          break;
        }
        imageToSave.save(outputPath);
        break;
      case "blur":
        if (myImage == null) {
          imageView.displayMessage("No image loaded");
        }
        imageView.displayMessage("Image blurred");
        imageService.blur(myImage);
        break;
      case "value-component":
        if (myImage == null) {
          imageView.displayMessage("No image loaded");
        }
        imageView.displayMessage("Get the value-component");
        imageService.getValue(myImage);
        break;
      case "intensity-component":
        if (myImage == null) {
          imageView.displayMessage("No image loaded");
        }
        imageView.displayMessage("Get the intensity-component");
        imageService.getIntensity(myImage);
        break;
      case "luma-component":
        if (myImage == null) {
          imageView.displayMessage("No image loaded");
        }
        imageView.displayMessage("Get the luma-component");
        imageService.getLuma(myImage);
        break;
      case "vertical-flip":
        if (myImage == null) {
          imageView.displayMessage("No image loaded");
        }
        imageView.displayMessage("Flip the image vertically");
        imageService.flip(myImage, Axis.Y);
        break;
      case "horizontal-flip":
        if (myImage == null) {
          imageView.displayMessage("No image loaded");
        }
        imageView.displayMessage("Flip the image horizontally");
        imageService.flip(myImage, Axis.X);
        break;
      case "brighten":
        if (myImage == null) {
          imageView.displayMessage("No image loaded");
        }
        imageView.displayMessage("Change the brightness of the image");
        float amount = Float.parseFloat(tokenizer.nextToken());
        if (amount > 0) {
          imageService.brighten(myImage, amount);
        }
        if (amount < 0) {
          imageService.darken(myImage, amount);
        }
        break;
      case "red-component":
        if (myImage == null) {
          imageView.displayMessage("No image loaded");
        }
        imageView.displayMessage("Split image in red component");
        imageService.splitComponent(myImage, Channel.RED);
        break;
      case "green-component":
        if (myImage == null) {
          imageView.displayMessage("No image loaded");
        }
        imageView.displayMessage("Split image in green component");
        imageService.splitComponent(myImage, Channel.GREEN);
        break;
      case "blue-component":
        if (myImage == null) {
          imageView.displayMessage("No image loaded");
        }
        imageView.displayMessage("Split image in blue component");
        imageService.splitComponent(myImage, Channel.BLUE);
        break;
      case "rgb-split":
        if (myImage == null) {
          imageView.displayMessage("No image loaded");
        }
        imageView.displayMessage("Split image");
        imageService.splitChannel(myImage);
        break;
      case "rgb-combine":
        if (myImage == null) {
          imageView.displayMessage("No image loaded");
        }
//        imageView.displayMessage("Combine image");
//        String redName = tokenizer.nextToken();
//        Image redImage = loadedImages.get(redName);
//        String greenName = tokenizer.nextToken();
//        Image greenImage = loadedImages.get(greenName);
//        String blueName = tokenizer.nextToken();
//        Image blueImage = loadedImages.get(blueName);
//        if (redImage == null || greenImage == null || greenImage == null) {
//          System.out.println("One or more of the images are not loaded");
//          break;
//        }
//        Image combinedImage = imageService.combineChannels(redImage, greenImage, blueImage);
//        break;
        Channel[] channels = new Channel[3];
        Image[] imagesToCombine = new Image[3];
        channels[0] = Channel.RED;
        channels[1] = Channel.GREEN;
        channels[2] = Channel.BLUE;
        for(int i =0; i< 3 ; i++) {
          String imageName = tokenizer.nextToken();
          if(!loadedImages.containsKey(imageName)) {
            imageView.displayMessage("Image named " + imageName + "not loaded.");
            return;
          }
          imagesToCombine[i] = loadedImages.get(imageName);
        }
        try{
          Image combinedImage = imageService.combineChannels(channels, imagesToCombine);
          loadedImages.put("combinedImage", combinedImage);
          imageView.displayMessage("Images combined successfully");
        }
        catch (IllegalArgumentException e ) {
          imageView.displayMessage(e.getMessage());
        }
        break;
      case "sharpen":
        if (myImage == null) {
          imageView.displayMessage("No image loaded");
        }
        imageView.displayMessage("Sharpen image");
        imageService.sharpen(myImage);
        break;
      case "sepia":
        if (myImage == null) {
          imageView.displayMessage("No image loaded");
        }
        imageView.displayMessage("Sepia image");
        imageService.getSepia(myImage);
        break;
      default:
        System.out.println("Invalid command " + operation);

    }
  }
}