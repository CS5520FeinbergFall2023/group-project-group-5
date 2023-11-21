package gui;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import controller.ImageGUIController;
import service.ImageService;

/**
 * This class represents the main entry point for the GUI for this program.
 */
public class ImageGUI {

  /**
   * The main entry point for tha program.
   *
   * @param args the arguments for the program
   */
  public static void main(String[] args) {

    ImageManipulationFrame.setDefaultLookAndFeelDecorated(false);
    ImageManipulationFrame frame = new ImageManipulationFrame();
    ImageService imageService = new ImageService();
    ImageGUIController imageGUIController = new ImageGUIController(imageService, frame);
    frame.setController(imageGUIController);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);

    try {
      // Set cross-platform Java L&F (also called "Metal")
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    } catch (UnsupportedLookAndFeelException e) {
      // handle exception
    } catch (ClassNotFoundException e) {
      // handle exception
    } catch (InstantiationException e) {
      // handle exception
    } catch (IllegalAccessException e) {
      // handle exception
    } catch (Exception e) {
    }

  }

}
