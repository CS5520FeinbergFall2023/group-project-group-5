package util;

import java.io.InputStreamReader;
import java.io.PrintWriter;

import controller.ImageController;
import service.ImageService;
import view.ImageView;


/**
 * This is a utility class for the Image application, providing the main entry point. This class
 * initializes various components of the Image application, including the ImageService, ImageView,
 * and ImageController. It prompts the user to select a mode of input (console or from file) and
 * starts the application.
 */
public class ImageUtil {

  /**
   * The main entry point for the Image application.
   *
   * @param args Command line arguments.
   */
  public static void main(String[] args) {
    ImageService imageService = new ImageService();
    ImageView imageView = new ImageView(new InputStreamReader(System.in),
        new PrintWriter(System.out, true));
    ImageController controller = new ImageController(imageService, imageView);

    if (args.length > 0 && "-file".equals(args[0]) && args.length > 1) {
      String scriptFileName = args[1];
      controller.startFromFile(scriptFileName);
    } else {
      imageView.displayMessage("Please select which mode you want to input the commands: "
            + "1. Console 2. From file 3. Exit");
      while (true) {
        int choice = imageView.getModeChoice();
        switch (choice) {
          case 1:
            imageView.displayMessage("Please enter the command (input 'exit' to exit): ");
            controller.start();
            return;
          case 2:
            String filePath = imageView.getFilePath();
            if (filePath != null && !filePath.isEmpty()) {
              controller.startFromFile(filePath);
              return;
            } else {
              imageView.displayMessage("Invalid file path.");
            }
            break;
          case 3:
            return;
          default:
            imageView.displayMessage("Please select a valid choice (1 or 2 or 3).");
        }
      }
    }
  }
}

